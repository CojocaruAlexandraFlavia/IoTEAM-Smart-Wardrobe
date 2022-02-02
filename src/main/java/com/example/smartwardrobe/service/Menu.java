package com.example.smartwardrobe.service;

import static java.lang.System.exit;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.service.*;
import com.example.smartwardrobe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Service
public class Menu { //throws Exception {
    @Autowired
    static ItemService itemService;

    @Autowired
    static UserService userService;

    @Autowired
    static OutfitService outfitService;

    public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    public static void menuSelect () throws Exception {
        String[] options = {
                "1 - Check out items in wardrobe",
                "2 - Recommend me an appropriate outfit",
                "3 - Rate one of my outfits",
                "4 - Update items in my wardrobe",
                "5 - Show me my items in the order I've most worn them",
                "6 - Show me what items I need to wash",
                "7 - Exit",
        };
        Scanner scanner = new Scanner(System.in);
        int option = 1;
        while (option != 7){
            printMenu(options);
            try {
                option = scanner.nextInt();
                switch (option){
                    case 1: option1(); break; //System.out.println(itemService.findAllItems()); break;
                    case 2: option2(scanner); break;
                    case 3: option3(); break;
                    case 4: option4(scanner); break;
                    case 5: option5(); break;
                    case 6: option6(scanner); break;
                    case 7: exit(0);
                }
            }
            catch (Exception ex){
                System.out.println("Please enter an integer value between 1 and " + options.length);
                scanner.next();
            }
        }
    }

    private static void option1() {
        List<Item> listToShow = itemService.findAllItems();
        for(int i = 0; i < listToShow.toArray().length; i++){
            System.out.println(listToShow.get(i));
        }
    }

    private static void option2(Scanner scanner) throws Exception{
        String[] options = {
                "1- I want a monochromatic outfit",
                "2- I want an analog outfit",
                "3- I want a pastel outfit",
                "4 - Go back"
        };
        printMenu(options);
        int option = 1;
        try {
            option = scanner.nextInt();
            switch (option){
                case 1:
                    List<Outfit> monochromaticOutfits = outfitService.recommendMonochromaticOutfit();
                    for(int i = 0; i < monochromaticOutfits.toArray().length; i++){
                        System.out.println("Outfit " + i + monochromaticOutfits.get(i));
                    }
                    System.out.println("Please introduce the number of the outfit you want to wear (or -1 if " +
                            "you changed your mind): ");
                    try {
                        int choice = scanner.nextInt();
                        if (choice == -1) {
                            System.out.println("Maybe next time");
                            break;
                        } else {
                            if (choice >= 1 && choice <= monochromaticOutfits.toArray().length) {
                                outfitService.selectRecommendedOutfit(choice);
                                System.out.println("You're now wearing outfit number " + choice);
                            }
                        }
                    }catch(Exception ex){
                            System.out.println("Please introduce a valid choice!");
                            scanner.next();
                        }
                    break; //System.out.println(itemService.findAllItems()); break;
                case 2:
                    List<Outfit> analogOutfits = outfitService.recommendAnalogousOutfit();
                    for(int i = 0; i < analogOutfits.toArray().length; i++){
                        System.out.println("Outfit " + i + analogOutfits.get(i));
                    }
                    System.out.println("Please introduce the number of the outfit you want to wear (or -1 if " +
                            "you changed your mind): ");
                    try {
                        int choice = scanner.nextInt();
                        if (choice == -1) {
                            System.out.println("Maybe next time");
                            break;
                        } else {
                            if (choice >= 1 && choice <= analogOutfits.toArray().length) {
                                outfitService.selectRecommendedOutfit(choice);
                                System.out.println("You're now wearing outfit number " + choice);
                            }
                        }
                    }catch(Exception ex){
                        System.out.println("Please introduce a valid choice!");
                        scanner.next();
                    }
                    break;
                case 3:
                    List<Outfit> pastelOutfits = outfitService.recommendPastelOutfit();
                    for(int i = 0; i < pastelOutfits.toArray().length; i++){
                        System.out.println("Outfit " + i + pastelOutfits.get(i));
                    }
                    System.out.println("Please introduce the number of the outfit you want to wear (or -1 if " +
                            "you changed your mind): ");
                    try {
                        int choice = scanner.nextInt();
                        if (choice == -1) {
                            System.out.println("Maybe next time");
                            break;
                        } else {
                            if (choice >= 1 && choice <= pastelOutfits.toArray().length) {
                                outfitService.selectRecommendedOutfit(choice);
                                System.out.println("You're now wearing outfit number " + choice);
                            }
                        }
                    }catch(Exception ex){
                        System.out.println("Please introduce a valid choice!");
                        scanner.next();
                    }
                    break;
                case 4: break;
            }
        }
        catch (Exception ex){
            System.out.println("Please enter an integer value between 1 and " + options.length);
            scanner.next();
        }
    }

    private static void option3(){

    }
    private static void option4(Scanner scanner) {
        List<Item> itemsToReview = itemService.updateWardrobe(userService.findUserById(Long.parseLong("1")));
        String[] options2 = {
                "1 - Donate",
                "2 - Turn it into rugs",
                "3 - Throw it away",
                "4 - Give to my little brother/sister",
                "5 - Just leave it there"
        };
        printMenu(options2);
        System.out.println("Choose what do to with the items you need to review");
        int option = 1;
        try {
            for(int i = 0; i < itemsToReview.toArray().length; i++) {
                option = scanner.nextInt();
                switch (option) {
                    case 1: itemService.deleteItemById(itemsToReview.get(i).getId()); break;
                    case 2: itemService.deleteItemById(itemsToReview.get(i).getId()); break;
                    case 3: itemService.deleteItemById(itemsToReview.get(i).getId()); break;
                    case 4: itemService.deleteItemById(itemsToReview.get(i).getId()); break;
                    case 5: break;
                }
            }
        }
        catch (Exception ex){
            System.out.println("Please enter an integer value between 1 and " + options2.length);
            scanner.next();
        }

    }
    private static void option5() {
    }
    private static void option6(Scanner scanner) throws FileNotFoundException {
        /// Show me what items I need to wash
        Pair<List<Item>, Set<JSONObject>> dirtyWhiteItems = itemService.getDirtyItemsByColor("WHITE");
        Pair<List<Item>, Set<JSONObject>> dirtyBlackItems = itemService.getDirtyItemsByColor("BLACK");
        Pair<List<Item>, Set<JSONObject>> dirtyColorItems = itemService.getDirtyItemsByColor("COLOR");
        List<Item> dirtyItems = itemService.findItemIfDirty();
        String[] options = {
                "1 - Show me all the dirty items",
                "1 - Show me the dirty white items",
                "2 - Show me the dirty black items",
                "3 - Show me the dirty colored items",
                "4 - Show washing instructions for dirty white items",
                "5 - Show washing instructions for dirty black items",
                "6 - Show washing instructions for dirty colored items",
                "7 - Wash white items",
                "8 - Wash black items",
                "9 - Wash colored items",
                "10 - Search for a specific item",
                "11 - Go back"
        };
        printMenu(options);
        System.out.println("Choose what do to with the items you need to review");
        int option = 1;
        try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        for(int i = 0; i < dirtyItems.toArray().length; i++){
                            System.out.print(dirtyItems.get(i));
                        }
                        break;
                    case 2:
                        for(int i = 0; i < dirtyWhiteItems.getFirst().toArray().length; i++){
                            System.out.println(dirtyWhiteItems.getFirst().get(i));
                        }
                        break;
                    case 3:
                        for(int i = 0; i < dirtyBlackItems.getFirst().toArray().length; i++){
                            System.out.println(dirtyWhiteItems.getFirst().get(i));
                        }
                        break;
                    case 4:
                        for(int i = 0; i < dirtyColorItems.getFirst().toArray().length; i++){
                            System.out.println(dirtyColorItems.getFirst().get(i));
                        }
                        break;
                    case 5:
                        for(JSONObject ob : dirtyWhiteItems.getSecond()) {
                            List<Item> instr = userService.convertObjectToList(ob);
                            System.out.println(instr);
                        }
                        break;
                    case 6:
                        for(JSONObject ob : dirtyBlackItems.getSecond()) {
                            List<Item> instr = userService.convertObjectToList(ob);
                            System.out.println(instr);
                        }
                        break;
                    case 7:
                        for(JSONObject ob : dirtyColorItems.getSecond()) {
                            List<Item> instr = userService.convertObjectToList(ob);
                            System.out.println(instr);
                        }
                        break;
                    case 8:
                        for(int i = 0; i < dirtyWhiteItems.getFirst().toArray().length; i++){
                            System.out.println(itemService.washItem(dirtyWhiteItems.getFirst().get(i).getId().toString()));
                        }
                        break;
                    case 9:
                        for(int i = 0; i < dirtyBlackItems.getFirst().toArray().length; i++){
                            System.out.println(itemService.washItem(dirtyBlackItems.getFirst().get(i).getId().toString()));
                        }
                        break;
                    case 10:
                        for(int i = 0; i < dirtyColorItems.getFirst().toArray().length; i++){
                            System.out.println(itemService.washItem(dirtyColorItems.getFirst().get(i).getId().toString()));
                        }
                        break;
                    case 11:
                        System.out.println("Enter teh id of the item you're searching for: ");
                        long idItem = 1;
                        try{
                            idItem = scanner.nextLong();
                            boolean found = false;
                            for(int i = 0; i < dirtyItems.toArray().length; i++){
                                if(dirtyItems.get(i).getId() == idItem){
                                    System.out.println("The item you searched for is dirty.");
                                    found = true;
                                    System.out.println("Do you want to wash it?(y/n)");
                                    String choice = scanner.next();
                                    if(choice.equals("y")){
                                        itemService.washItem(dirtyItems.get(i).getId().toString());
                                    } else{
                                        if(choice.equals("n")) {
                                            System.out.println("Next time then. At least you know where it is now!");
                                        } else{
                                            System.out.println("Please enter y for yes and n for no!");
                                        }
                                    }
                                }
                            }
                            if(!found){
                                System.out.println("The item you searched for is clean.");
                            }
                        }
                        catch(Exception e){
                            System.out.println("Please enter a valid id");
                            scanner.next();
                        }
                        break;
                    case 12: break;
            }
        }
        catch (Exception ex){
            System.out.println("Please enter an integer value between 1 and " + options.length);
            scanner.next();
        }

    }


}
