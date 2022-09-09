package com.vadpol.util;


import com.vadpol.connectionToSql.ConnectToSQL;
import com.vadpol.telegramBot.TelegramBotTest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class WaterReminderMethod implements Job {
    TelegramBotTest telegramBot = new TelegramBotTest();


    // метод отправляет напоминание выпить воду
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        // комманда для SQL которая выводит базу данных
        String sqlWorker = ("select distinct \"ChatId\" from d1cfnt21boubau.products.\"waterReminder\"");

        // Создаем подключение к базе данных

        try {
            Statement statement = ConnectToSQL.connection.createStatement();


            // Выполняем команду Select для SQL
            ResultSet resultSet = statement.executeQuery(sqlWorker);



            /* Отправляет в чат сообщение о том что нужно выпить воду
             */
            while (resultSet.next()) {
                String result = resultSet.getString(1);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(result);
                sendMessage.setText("Напоминаю, сейчас было бы не плохо выпить стакан воды");
                try {
                    telegramBot.execute(sendMessage);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectToSQL.closeConnection();
    }
}
