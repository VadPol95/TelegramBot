package com.vadpol.model;

import lombok.Getter;
import lombok.Setter;

/* Класс Products, создан для переменных из Таблицы Базы Данных, в случае, если продукт будет в базе
то переменные берут значение этих продуктов.
*/
@Getter
@Setter
public class Product {

    private double calories;
    private String product_name;
    private double protein;
    private double carbohydrates;
    private double fats;

    @Override
    public String toString() {
        return
                "Название продукта: " + product_name + "; " +"\n" +
                        "Белки: " + protein + "; " +
                        "Углеводы: " + carbohydrates + "; " +
                        "Жиры: " + fats + "; " +
                        "Калории: " + calories + ";" +"\n" + "\n" ;
    }
}
