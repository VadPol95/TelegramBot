package com.vadpol.dao;

import com.vadpol.connectionToSql.ConnectToSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WaterDAO {

    // Расчет индивидуальных калорий взятых из базы данных
    public static String individualWaterBalance(String chatId) {

        double weightConstant = 30;
        double result = 1;

        try {

            // комманда для SQL которая выводит базу данных

            String weight = ("select weight from d1cfnt21boubau.products.\"waterBalance\" where \"chatId\"=" + chatId + " and number=2");

            // Создаем подключение к базе данных
            Statement statementWeight = ConnectToSQL.connection.createStatement();

            // Выполняем команду Select для SQL
            ResultSet resultSetWeight = statementWeight.executeQuery(weight);

            resultSetWeight.next();

            // формуда расчета индивидуальный калорий для мужчины и женщины

            /*
             Норма водного баланса = Weight x 30;
             */
            result = Double.parseDouble(resultSetWeight.getString(1)) * weightConstant;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Double.toString(result);
    }

    //Проверяет наличие пунктов в базе данных для следующего шага в цикле
    public static boolean checkWaterBalance(String chatId, String number) {
        try {
            boolean result = false;
            String numbers = ("select weight from d1cfnt21boubau.products.\"waterBalance\" where \"chatId\"=" + chatId);

            Statement statement = ConnectToSQL.connection.createStatement();
            ResultSet resultSetWeight = statement.executeQuery(numbers);
            while (resultSetWeight.next()) {
                if (resultSetWeight.getString(1).equals(number)) {
                    result = true;
                }


            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Удаляет из базы данных информацию о пользователе
    public static void deleteIndividualWaterBalance(String chatId) {
        try {

            String deleteInfo = ("delete from d1cfnt21boubau.products.\"waterBalance\" where \"chatId\"=" + chatId);
            Statement statement = ConnectToSQL.connection.createStatement();
            statement.executeUpdate(deleteInfo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
