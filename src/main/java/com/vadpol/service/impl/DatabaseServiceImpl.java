package com.vadpol.service.impl;

import com.vadpol.dao.IndividualDataDAO;
import com.vadpol.service.DatabaseService;

public class DatabaseServiceImpl implements DatabaseService {

    private final IndividualDataDAO dataDAO = new IndividualDataDAO();

    @Override
    public void insertWaterBalanceToSql(String chatId, String number, String value) {
        dataDAO.insertWaterBalanceToSql(chatId, number, value);
    }

    @Override
    public void insertChatIdInWaterReminderSQL(String chatId) {
        dataDAO.insertChatIdInWaterReminderSQL(chatId);

    }

    @Override
    public void deleteChatIdInWaterReminderSQL(String chatId) {
        dataDAO.deleteChatIdInWaterReminderSQL(chatId);
    }

    @Override
    public void insertIndividualDataToSql(String chatId, String number, String value) {
        dataDAO.insertIndividualDataToSql(chatId, number, value);
    }

    @Override
    public String individualCaloriesCalculation(String chatId) {
        return dataDAO.individualCaloriesCalculation(chatId);
    }

    @Override
    public void deleteIndividualCaloriesCalculation(String chatId) {
        dataDAO.deleteIndividualCaloriesCalculation(chatId);
    }

    @Override
    public boolean checkIndividualCaloriesCalculation(String chatId, String number) {
        return dataDAO.checkIndividualCaloriesCalculation(chatId, number);
    }
}
