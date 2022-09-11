package com.vadpol.service;

public interface WaterService {
    String individualWaterBalance(String chatId);

    boolean checkWaterBalance(String chatId, String number);

    void deleteIndividualWaterBalance(String chatId);


}
