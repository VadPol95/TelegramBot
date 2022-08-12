package com.vadpol.Methods;

import HillelProject.ConnectionSQL.ConnectToSQL;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertUserDataToSQLWaterReminder {
    private static ConnectToSQL mainJava;
    public static void insertChatIdInWaterReminderSQL(String chatId) {

        try {

            // комманда для SQL которая выводит базу данных
            String sqlWorker = ("insert into d1cfnt21boubau.products.\"waterReminder\" (\"ChatId\") values ("+chatId+")");


            // Создаем подключение к базе данных
            Statement statement = mainJava.connection.createStatement();

            // Выполняем команду Select для SQL
            int resultSet = statement.executeUpdate(sqlWorker);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Удаляет из базы данных информацию о пользователе
    public static void deleteChatIdInWaterReminderSQL(String chatId) {
        try {

            String deleteInfo = ("delete from d1cfnt21boubau.products.\"waterReminder\" where \"ChatId\"=" + chatId);
            Statement statement = mainJava.connection.createStatement();
            statement.executeUpdate(deleteInfo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

