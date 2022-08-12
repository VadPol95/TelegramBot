package com.vadpol.Methods;

/* Класс Products, создан для переменных из Таблицы Базы Данных, в случае, если продукт будет в базе
то переменные берут значение этих продуктов.
*/
public class ProductsClass {

    private double calories;
    private String product_name;
    private double protein;
    private double carbohydrates;
    private double fats;


    public double getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

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
