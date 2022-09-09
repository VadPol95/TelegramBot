package com.vadpol.service.impl;

import com.vadpol.dao.IndividualDataDAO;
import com.vadpol.service.DatabaseService;

public class DatabaseServiceImpl implements DatabaseService {

    private final IndividualDataDAO dataDAO;

    public DatabaseServiceImpl(IndividualDataDAO dataDAO) {
        this.dataDAO = dataDAO;
    }

    @Override
    public void insertWaterBalanceToSql(String chatId, String number, String value) {
        IndividualDataDAO.insertWaterBalanceToSql(chatId, number, value);
    }

    @Override
    public void insertChatIdInWaterReminderSQL(String chatId) {
        IndividualDataDAO.insertChatIdInWaterReminderSQL(chatId);

    }

    @Override
    public void deleteChatIdInWaterReminderSQL(String chatId) {
        IndividualDataDAO.deleteChatIdInWaterReminderSQL(chatId);
    }

    @Override
    public void insertIndividualDataToSql(String chatId, String number, String value) {
        IndividualDataDAO.insertIndividualDataToSql(chatId, number, value);
    }

    @Override
    public String individualCaloriesCalculation(String chatId) {
        return IndividualDataDAO.individualCaloriesCalculation(chatId);
    }

    @Override
    public void deleteIndividualCaloriesCalculation(String chatId) {
        IndividualDataDAO.deleteIndividualCaloriesCalculation(chatId);
    }

    @Override
    public boolean checkIndividualCaloriesCalculation(String chatId, String number) {
        return IndividualDataDAO.checkIndividualCaloriesCalculation(chatId, number);
    }
}
