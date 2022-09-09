package com.vadpol.service.impl;

import com.vadpol.dao.WaterDAO;
import com.vadpol.service.WaterService;

public class WaterServiceImpl implements WaterService {
    private final WaterDAO waterDAO;

    public WaterServiceImpl(WaterDAO waterDAO) {
        this.waterDAO = waterDAO;
    }

    @Override
    public String individualWaterBalance(String chatId) {
        return WaterDAO.individualWaterBalance(chatId);
    }

    @Override
    public boolean checkWaterBalance(String chatId, String number) {
        return WaterDAO.checkWaterBalance(chatId, number);
    }

    @Override
    public void deleteIndividualWaterBalance(String chatId) {
        WaterDAO.deleteIndividualWaterBalance(chatId);
    }
}
