package com.vadpol.Methods;

import HillelProject.ConnectionSQL.ConnectToSQL;

import java.sql.SQLException;
import java.sql.Statement;



public class InsertIndividualDataSQL {

    private static ConnectToSQL mainJava;
    public static void process(String chatId, String number, String value) {

        try {

        // комманда для SQL которая выводит базу данных
        String sqlWorker = ("insert into d1cfnt21boubau.products.\"individualCalories\" (\"chatId\", number, information)values ("+chatId+","+number+","+value+")");


        // Создаем подключение к базе данных
        Statement statement = mainJava.connection.createStatement();

        // Выполняем команду Select для SQL
        int resultSet = statement.executeUpdate(sqlWorker);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

