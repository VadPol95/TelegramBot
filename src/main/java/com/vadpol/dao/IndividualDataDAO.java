package com.vadpol.dao;

import com.vadpol.connectionToSql.ConnectToSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IndividualDataDAO {

    // Расчет индивидуальных калорий взятых из базы данных
    public static String individualCaloriesCalculation(String chatId) {

        double womanHeight = 3.1;
        double manHeight = 4.8;
        double manWeight = 13.4;
        double womanWeight = 9.2;
        double manYear = 5.7;
        double womanYear = 4.3;
        double result = 1;


        try {

            // комманда для SQL которая выводит базу данных
            String height = ("select information from d1cfnt21boubau.products.\"individualCalories\" where \"chatId\"=" + chatId + " and number=2");
            String weight = ("select information from d1cfnt21boubau.products.\"individualCalories\" where \"chatId\"=" + chatId + " and number=3");
            String age = ("select information from d1cfnt21boubau.products.\"individualCalories\" where \"chatId\"=" + chatId + " and number=4");
            String sex = ("select information from d1cfnt21boubau.products.\"individualCalories\" where \"chatId\"=" + chatId + " and number=5");
            String activity = ("select information from d1cfnt21boubau.products.\"individualCalories\" where \"chatId\"=" + chatId + " and number=6");
            // Создаем подключение к базе данных
            Statement statementHeight = ConnectToSQL.connection.createStatement();
            Statement statementWeight = ConnectToSQL.connection.createStatement();
            Statement statementAge = ConnectToSQL.connection.createStatement();
            Statement statementSex = ConnectToSQL.connection.createStatement();
            Statement statementActivity = ConnectToSQL.connection.createStatement();

            // Выполняем команду Select для SQL
            ResultSet resultSetHeight = statementHeight.executeQuery(height);
            ResultSet resultSetWeight = statementWeight.executeQuery(weight);
            ResultSet resultSetAge = statementAge.executeQuery(age);
            ResultSet resultSetSex = statementSex.executeQuery(sex);
            ResultSet resultSetActivity = statementActivity.executeQuery(activity);

            resultSetHeight.next();
            resultSetSex.next();
            resultSetActivity.next();
            resultSetAge.next();
            resultSetWeight.next();


            // формуда расчета индивидуальный калорий для мужчины и женщины

            /*
               для мужчин: BMR = 88.36 + (13.4 x вес, кг) + (4.8 х рост, см) – (5.7 х возраст, лет)
               для женщин: BMR = 447.6 + (9.2 x вес, кг) + (3.1 х рост, cм) – (4.3 х возраст, лет)

               Коэффициенты активности при расчете нормы калорий:

               Минимальный уровень активности — 1.2
               Низкий уровень активности — 1.375
               Средний уровень активности — 1.55
               Высокий уровень — 1.725
               Очень высокий —  1.9

               Норма калорий = BMR x Уровень активности
             */
            if (Double.parseDouble(resultSetSex.getString(1)) == (88.36)) {
                result = Double.parseDouble(resultSetActivity.getString(1))
                        * ((Double.parseDouble(resultSetSex.getString(1))
                        + (manWeight * Double.parseDouble(resultSetWeight.getString(1)))
                        + (manHeight * Double.parseDouble(resultSetHeight.getString(1)))
                        - (manYear * Double.parseDouble(resultSetAge.getString(1)))));
            } else if (Double.parseDouble(resultSetSex.getString(1)) == (447.6)) {
                result = Double.parseDouble(resultSetActivity.getString(1))
                        * ((Double.parseDouble(resultSetSex.getString(1))
                        + (womanWeight * Double.parseDouble(resultSetWeight.getString(1)))
                        + (womanHeight * Double.parseDouble(resultSetHeight.getString(1)))
                        - (womanYear * Double.parseDouble(resultSetAge.getString(1)))));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Double.toString((int) result);
    }

    // Удаляет из базы данных информацию о пользователе
    public static void deleteIndividualCaloriesCalculation(String chatId) {
        try {

            String deleteInfo = ("delete from d1cfnt21boubau.products.\"individualCalories\" where \"chatId\"=" + chatId);
            Statement statement = ConnectToSQL.connection.createStatement();
            statement.executeUpdate(deleteInfo);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Проверяет наличие пунктов в базе данных для следующего шага в цикле
    public static boolean checkIndividualCaloriesCalculation(String chatId, String number) {
        try {
            boolean result = false;
            String numbers = ("select number from d1cfnt21boubau.products.\"individualCalories\" where \"chatId\"=" + chatId);

            Statement statement = ConnectToSQL.connection.createStatement();
            ResultSet resultSetHeight = statement.executeQuery(numbers);
            while (resultSetHeight.next()) {
                if (resultSetHeight.getString(1).equals(number)) {
                    result = true;
                }


            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertIndividualDataToSql(String chatId, String number, String value) {

        try {

            // комманда для SQL которая выводит базу данных
            String sqlWorker = ("insert into d1cfnt21boubau.products.\"individualCalories\" (\"chatId\", number, information)values (" + chatId + "," + number + "," + value + ")");


            // Создаем подключение к базе данных
            Statement statement = ConnectToSQL.connection.createStatement();

            // Выполняем команду Select для SQL
            int resultSet = statement.executeUpdate(sqlWorker);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertChatIdInWaterReminderSQL(String chatId) {

        try {

            // комманда для SQL которая выводит базу данных
            String sqlWorker = ("insert into d1cfnt21boubau.products.\"waterReminder\" (\"ChatId\") values (" + chatId + ")");


            // Создаем подключение к базе данных
            Statement statement = ConnectToSQL.connection.createStatement();

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
            Statement statement = ConnectToSQL.connection.createStatement();
            statement.executeUpdate(deleteInfo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertWaterBalanceToSql(String chatId, String number, String value) {

        try {

            // комманда для SQL которая выводит базу данных
            String sqlWorker = ("insert into d1cfnt21boubau.products.\"waterBalance\" (\"chatId\", number, weight)values (" + chatId + "," + number + "," + value + ")");


            // Создаем подключение к базе данных
            Statement statement = ConnectToSQL.connection.createStatement();

            // Выполняем команду Select для SQL
            int resultSet = statement.executeUpdate(sqlWorker);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
