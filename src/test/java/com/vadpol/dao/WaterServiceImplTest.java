package com.vadpol.dao;

import com.vadpol.connection.ConnectToSQL;
import com.vadpol.service.impl.WaterServiceImpl;
import org.junit.jupiter.api.Test;

class WaterServiceImplTest {
    private final WaterServiceImpl waterService = new WaterServiceImpl();

    @Test
    void individualWaterBalance() {
    }

    @Test
    void checkWaterBalance() {
        ConnectToSQL.mainJava();

        String chatId = "5";
        String number = "5";

        waterService.checkWaterBalance(chatId, number);
    }

    @Test
    void deleteIndividualWaterBalance() {
        ConnectToSQL.mainJava();

        String chatId = "5";

        waterService.deleteIndividualWaterBalance(chatId);

    }
}