package com.vadpol.Methods;

import HillelProject.ConnectionSQL.ConnectToSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/*
 Класс который содениняет ТелеграмБот с Базой Данных.
*/
public class ProductCaloriesMethod {
    private static ConnectToSQL mainJava;
    private static ProductsClass products;
    public static String result;
    // Создает объект класса Products
    public static void products() {
        products = new ProductsClass();
    }
    static List<String> productes = new ArrayList<>();
    /**
     * Метод который создает подключение к базе данных,
     * после того как пользователь напишет свой продукт проверяет базу данных на наличе этого продукта
     * и выводит его. В случае если продукта нет, выводит что такого продукта нет.
     **/
    public static String process(String message) {
        List<ProductsClass> product = new ArrayList<>();
        String result = "Такого продукта нет";
        try {

            // комманда для SQL которая выводит базу данных
            String sqlWorker = ("select * from d1cfnt21boubau.products.products where \"Products_Name\" ilike " + "\'%" + message + "%\'");

            // Создаем подключение к базе данных
            Statement statement = mainJava.connection.createStatement();

            // Выполняем команду Select для SQL
            ResultSet resultSet = statement.executeQuery(sqlWorker);

            while (resultSet.next()) {
                products();
                result = resultSet.getString(1);
                if (result.toUpperCase().contains(message.toUpperCase())) {
                    products.setCalories(resultSet.getInt(5));
                    products.setProduct_name(resultSet.getString(1));
                    products.setProtein(resultSet.getDouble(2));
                    products.setCarbohydrates(resultSet.getDouble(3));
                    products.setFats(resultSet.getDouble(4));
                    product.add(products);
                }
                result = product.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(""));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}