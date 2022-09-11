package com.vadpol.service.impl;

import com.vadpol.dao.WaterDAO;
import com.vadpol.service.WaterService;

public class WaterServiceImpl implements WaterService {
    private final WaterDAO waterDAO = new WaterDAO();


    @Override
    public String individualWaterBalance(String chatId) {
        return waterDAO.individualWaterBalance(chatId);
    }

    @Override
    public boolean checkWaterBalance(String chatId, String number) {
        return waterDAO.checkWaterBalance(chatId, number);
    }

    @Override
    public void deleteIndividualWaterBalance(String chatId) {
        waterDAO.deleteIndividualWaterBalance(chatId);
    }
}
