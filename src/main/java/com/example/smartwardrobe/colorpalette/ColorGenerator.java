package com.example.smartwardrobe.colorpalette;

import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.model.Item;

import java.util.Arrays;
import java.util.Random;

import static com.example.smartwardrobe.enums.ItemColor.*;

public class ColorGenerator {

    ItemColor[] reds = {MOHOGAMY, WINE, BERRY, RUBY, CRIMSON, RED, ROSE, BLUSH};
    ItemColor[] oranges = {RUST, BRONZE, FIRE, ORANGE, APRICOT, HONEY, MERIGOLD, CANTALOUPE};
    ItemColor[] yellows = {GOLDENROD, YELLOW_JASMINE, CITRINE, GOLD, YELLOW, BRIGHT_YELLOW, BUTTER, PALE_YELLOW};
    ItemColor[] greens = {JUNIPER, CROCODILE, EMERALD, GREEN, PEAR, FERN, SEAFOAM, MINT};
    ItemColor[] blues = {DENIM, NAVY, ADMIRAL, BLUE, CERULEAN, TEAL, SKY, ARCTIC};
    ItemColor[] purples = {INDIGO, GRAPE, VIOLET, MARDIGRAS, MAGENTA, ELECTRIC_PURPLE, ORCHID, LAVANDER};
    ItemColor[] pinks = {DARK_PINK, BRIGHT_PINK, FUCSIA, WATERMELON, TAFFY, PINK, SALMON, CREPE};
    ItemColor[][] colors = {reds, oranges, yellows, greens, blues, purples, pinks};
    ItemColor[] nonColors = {BLACK, GREY, WHITE};

    public Integer[] colorNumber(ItemColor color){
        for(int i = 0; i < colors.length; i++){
            for( int j = 0; j < colors[i].length; j++){
                if(colors[i][j]==color){
                    return new Integer[]{i, j};
                }
            }
        }
        return null;
    }
    public ItemColor colorKind(int i, int j){
        return colors[i][j];
    }

    public ItemColor[] monoChromatic(ItemColor color){
        int i,j,max;
        i = colorNumber(color)[0];
        j = colorNumber(color)[1];
        max = colors[i].length;
        Random rand = new Random();
        ItemColor color2 = colors[i][rand.nextInt(max)];
        ItemColor color3 = colors[i][rand.nextInt(max)];
        while(color == color2){
            color2 = colors[i][rand.nextInt(max)];
        }
        while(color == color3){
            color3 = colors[i][rand.nextInt(max)];
        }
        while(color2 == color3){
            color3 = colors[i][rand.nextInt(max)];
        }
        return new ItemColor[] {color2, color3};

    }
    public ItemColor getPastel(ItemColor color){
        int i,j,max;
        i = colorNumber(color)[0];
        j = colors[i].length - 1;
        return colorKind(i,j);

    }
    public ItemColor[] pastel(ItemColor color){
        int i,j,max;
        color = getPastel(color);
        i = colorNumber(color)[0];
        j = colorNumber(color)[1];
        max = colors.length;
        Random rand = new Random();
        ItemColor color2 = colors[rand.nextInt(max)][j];
        ItemColor color3 = colors[rand.nextInt(max)][j];
        while(color == color2){
            color2 = colors[rand.nextInt(max)][j];
        }
        while(color == color3){
            color3 = colors[rand.nextInt(max)][j];
        }
        while(color2 == color3){
            color3 = colors[rand.nextInt(max)][j];
        }
        return new ItemColor[] {color2, color3};
    }
    public static void main(String[] args) {
        ColorGenerator colorGenerator = new ColorGenerator();
        System.out.println(Arrays.toString(colorGenerator.colorNumber(WATERMELON)));
        //System.out.println(colorGenerator.colorKind(3,5));
        System.out.println(Arrays.toString(colorGenerator.monoChromatic(WATERMELON)));
        System.out.println(Arrays.toString(colorGenerator.pastel(WATERMELON)));
    }
}
