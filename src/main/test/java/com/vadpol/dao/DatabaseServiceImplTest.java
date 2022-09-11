package com.vadpol.dao;

import com.vadpol.connection.ConnectToSQL;
import com.vadpol.service.impl.DatabaseServiceImpl;
import org.junit.jupiter.api.Test;

class DatabaseServiceImplTest {

    private final DatabaseServiceImpl databaseService = new DatabaseServiceImpl();

    @Test
    void individualCaloriesCalculation() {
    }

    @Test
    void deleteIndividualCaloriesCalculation() {
        ConnectToSQL.mainJava();

        String chatId = "5";
        databaseService.deleteIndividualCaloriesCalculation(chatId);
    }

    @Test
    void checkIndividualCaloriesCalculation() {
        ConnectToSQL.mainJava();

        String chatId = "5";
        String number = "200";

        databaseService.checkIndividualCaloriesCalculation(chatId, number);
    }

    @Test
    void insertIndividualDataToSql() {

        ConnectToSQL.mainJava();

        String chatId = "5";
        String number = "200";
        String value = "200";

        databaseService.insertIndividualDataToSql(chatId, number, value);


    }

    @Test
    void insertChatIdInWaterReminderSQL() {
        ConnectToSQL.mainJava();

        String chatId = "123321";

        databaseService.insertChatIdInWaterReminderSQL(chatId);
    }

    @Test
    void deleteChatIdInWaterReminderSQL() {
        ConnectToSQL.mainJava();

        String chatId = "512319576";
        databaseService.deleteChatIdInWaterReminderSQL(chatId);
    }

    @Test
    void insertWaterBalanceToSql() {
        ConnectToSQL.mainJava();

        String chatId = "5";
        String number = "200";
        String value = "200";

        databaseService.insertWaterBalanceToSql(chatId, number, value);
    }
}