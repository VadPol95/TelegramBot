package com.vadpol.recipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialOfTheDayRecipeTest {

    @Test
    void processBreakFast() {
        String message = "Завтрак";
        assertTrue(true, SpecialOfTheDayRecipe.processBreakFast(message));
    }

    @Test
    void processLunch() {
        String message = "Обед";
        assertTrue(true, SpecialOfTheDayRecipe.processLunch(message));
    }

    @Test
    void specialOfTheDayDesertHalf() {
        String message = "Дессерт";
        assertTrue(true, SpecialOfTheDayRecipe.specialOfTheDayDesertHalf(message));
    }

    @Test
    void specialOfTheDayDinner() {
        String message = "Ужин";
        assertTrue(true, SpecialOfTheDayRecipe.specialOfTheDayDinner(message));
    }
}