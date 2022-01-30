package com.example.smartwardrobe.colorpalette;

import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.model.Item;

import java.util.Arrays;
import java.util.Random;

import static com.example.smartwardrobe.enums.ItemColor.*;

public class ColorGenerator {
    /*
    the color arrays are written from the darkest color of the spectrum to the lightest one
    the colors array is composed of the color arrays in the color wheel order
     */
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
        if(color == GREY || color == WHITE || color == BLACK){
            if(color == GREY)
                return new ItemColor[]{BLACK, WHITE};
            if(color == BLACK)
                return new ItemColor[]{GREY, WHITE};
            if(color == WHITE)
                return new ItemColor[]{BLACK, GREY};
        }
        System.out.println(color);
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
        if(color == GREY || color == WHITE || color == BLACK)
            return GREY;
        int i,j,max;
        i = colorNumber(color)[0];
        j = colors[i].length - 1;
        return colorKind(i,j);

    }
    public ItemColor[] pastel(ItemColor color){
        /*
        pastel palette gets the pastel color of the color family of the parameter
        and returns 2 random pastel colors from the spectrum
         */
        int i,j,max;
        color = getPastel(color);
        j = 7;
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
    public ItemColor[] analogous(ItemColor color) {
        /*
        according to color theory, analogous
        comprises colors that are right next to each other on the color wheel
         */
        if(color == GREY || color == WHITE || color == BLACK){
            return null;
        }
        int i,j,max;
        i = colorNumber(color)[0];
        j = colorNumber(color)[1];
        max = colors.length - 1;
        ItemColor color2, color3;
        if(i > 0 && i < max) {
            color2 = colors[i - 1][j];
            color3 = colors[i + 1][j];
        } else if (i == 0){
            color2 = colors[max][j];
            color3 = colors[i + 1][j];
        } else {
            color2 = colors[i - 1][j];
            color3 = colors[0][j];
        }
        return new ItemColor[] {color2, color3};
    }
    public ItemColor[] splitComplementary(ItemColor color){
        /*
        according to color theory, split-complementary
        uses a base color with two colors on either side of its opposite color.
         */
        int i,j,max;
        i = colorNumber(color)[0];
        j = colorNumber(color)[1];
        max = colors.length - 1;
        ItemColor color2, color3;
        if(i >= 4 && i <= max-2) {
            color2 = colors[i - 4][j];
            color3 = colors[i + 2][j];
        } else if (i < 4){
            color2 = colors[max + i - 3][j];
            color3 = colors[i + 2][j];
        } else {
            color2 = colors[i - 4][j];
            color3 = colors[max + 2 - i][j];
        }
        return new ItemColor[] {color2, color3};
    }

}