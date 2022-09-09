package com.vadpol.service;

public interface DatabaseService {
    void insertWaterBalanceToSql(String chatId, String number, String value);
    void insertChatIdInWaterReminderSQL(String chatId);
    void deleteChatIdInWaterReminderSQL(String chatId);
    void insertIndividualDataToSql(String chatId, String number, String value);
    String individualCaloriesCalculation(String chatId);
    void deleteIndividualCaloriesCalculation(String chatId);
    boolean checkIndividualCaloriesCalculation(String chatId, String number);
}
