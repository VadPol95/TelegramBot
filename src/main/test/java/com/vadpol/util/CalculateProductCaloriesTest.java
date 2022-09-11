package com.vadpol.util;

import com.vadpol.connection.ConnectToSQL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculateProductCaloriesTest {

    @Test
    void process() {
        ConnectToSQL.mainJava();
        String str = CalculateProductCalories.process("Смалец");
        assertTrue(true, str);

    }
}