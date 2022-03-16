package com.example.smartwardrobe.integration;

import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.service.HistoryService;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.OutfitService;
import com.example.smartwardrobe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OutfitService outfitService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Rollback
    void testFindItemById() throws Exception {
        Item item = new Item();
        item.setCode("code");
        Item savedItem = itemService.saveItem(item);

        mockMvc.perform(post("/item/" + savedItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(savedItem.getCode())));
    }

    @Test
    @Rollback
    void testSaveItem() throws Exception {
        ItemDto item = new ItemDto();
        item.setCode("code1");
        item.setId(123);
        item.setItemCategory("JEANS");
        item.setItemColor("MOHOGAMY");
        item.setLastWashingDay("2022-01-02");
        item.setLastWearing("2022-01-20");
        item.setMaterial("WOOL");
        item.setNrOfWearsSinceLastWash(0);
        item.setSize("M");
        item.setStyle("CASUAL");
        item.setWashingZoneColor("BLACK");
        Item savedItem = itemService.saveItem(itemService.convertDtoToEntity(item));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(item);

        mockMvc.perform(post("/item").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedItem.getId().intValue())));
    }

    @Test
    void TestGetAllItems() throws Exception {
        List<Item> databaseItems = itemRepository.findItemIfDirty();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer();
        String json = objectWriter.writeValueAsString(databaseItems);

        mockMvc.perform(get("/item/findDirty"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(json));
    }

    @ParameterizedTest
    @ValueSource(strings = {"WHITE", "COLOR", "BLACK"})
    void testGetDirtyItemsByColor(String color) throws Exception {
        List<Item> allDirtyItems = itemRepository.findItemIfDirty();
        List<Item> coloredDirtyItems = allDirtyItems.stream().filter(item -> item.getItemColor().name().equals(color)).collect(Collectors.toList());
        JSONParser jsonParser = new JSONParser();
        Set<JSONObject> jsonObjectSet = new HashSet<>();
        try (FileReader fileReader = new FileReader("src/main/java/com/example/smartwardrobe/json/wash_instructions.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            for(Item item: coloredDirtyItems){
                jsonObjectSet.add((JSONObject) jsonObject.get(item.getMaterial().name()));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Pair<List<Item>, Set<JSONObject>> myResult = Pair.of(coloredDirtyItems, jsonObjectSet);

        mockMvc.perform(get("/item/findDirtyByColor/" + color))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(myResult.toString()));
    }

    @Test
    void testGetItemsByStyle() throws Exception {
        List<Item> items = itemRepository.findByStyle(Style.CASUAL);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer();
        String json = objectWriter.writeValueAsString(items);
        mockMvc.perform(get("/item/getByStyle/{styleName}", "CASUAL"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(json));
    }

    @Test
    @Rollback
    void testWashItem() throws Exception {
        Item item = new Item();
        Item savedItem = itemRepository.save(item);
        mockMvc.perform(patch("/item/washing/{itemId}", savedItem.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastWashingDay", is(LocalDate.now().toString())));
    }

    @Test
    @Rollback
    void testSaveOutfit() throws Exception {

        Outfit outfit = new Outfit();
        List<Item> itemList = new ArrayList<>();
        outfit.setItems(itemList);
        Coat coat = new Coat();
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setId(1L);
        outfit.setCoat(coat);
        Outfit savedOutfit = outfitService.saveOutfit(outfit);
        int savedOutfitId = savedOutfit.getId().intValue();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(outfit);

        mockMvc.perform(post("/outfit").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedOutfitId)));
    }

    @Test
    @Rollback
    void testRateOutfit() throws Exception {
        Outfit outfit = new Outfit();
        List<Item> itemList = new ArrayList<>();
        outfit.setItems(itemList);
        Coat coat = new Coat();
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setId(1L);
        outfit.setCoat(coat);
        Outfit savedOutfit = outfitService.saveOutfit(outfit);
        int savedOutfitNrOfStars = savedOutfit.getNrOfStars();
        int savedOutfitNrOfReviews = savedOutfit.getNrOfReviews();

        mockMvc.perform(patch("/outfit/{outfitId}/{nrOfStars}", savedOutfit.getId().toString(), 4))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nrOfStars", is(savedOutfitNrOfStars + 4)))
                .andExpect(jsonPath("$.nrOfReviews", is(savedOutfitNrOfReviews + 1)))
                .andExpect(jsonPath("$.rating", is((double)(savedOutfitNrOfStars + 4) / (savedOutfitNrOfReviews + 1))));
    }

}