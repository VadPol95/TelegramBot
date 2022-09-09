package com.vadpol.telegramBot;

import com.vadpol.ConnectionSQL.ConnectToSQL;
import com.vadpol.Methods.*;
import com.vadpol.recipe.SpecialOfTheDayRecipe;
import com.vadpol.util.ProductCaloriesMethod;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


// Класс ТелеграмБота, методы для получение и отправки сообщений.
public class TelegramBot extends TelegramLongPollingBot {


    public TelegramBot() {
        ConnectToSQL.mainJava();
        ProductCaloriesMethod.products();
    }

    HashMap<String, String> hashForProducts = new HashMap<>();


    // метод обработки получения и отправки сообщений в телеграмм
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        ConnectToSQL.mainJava();

        if (message != null && message.hasText()) {

            //Команда "/Start"
            if (message.getText().equals("/start")) {
                message.getChatId();
                sendText(message, "Выберите действие из меню");
            } else if (message.getText().equals("\uD83E\uDD5E" + " Завтрак")) {
                message.getChatId();
                sendText2(message, SpecialOfTheDayRecipe.processBreakFast(message.getText()));
            } else if (message.getText().equals("\uD83E\uDD57" + " Обед")) {
                message.getChatId();
                sendText2(message, SpecialOfTheDayRecipe.processLunch(message.getText()));
            } else if (message.getText().equals("\uD83E\uDDC1" + " Десерт/Перекус")) {
                message.getChatId();
                sendText2(message, SpecialOfTheDayRecipe.specialOfTheDayDesertHalf(message.getText()));
            } else if (message.getText().equals("\uD83C\uDF5D" + " Ужин")) {
                message.getChatId();
                sendText2(message, SpecialOfTheDayRecipe.specialOfTheDayDinner(message.getText()));
            } else if (message.getText().equals("Назад в меню")) {
                message.getChatId();
                sendText(message, "Выберите действие из меню");
            }
            // Команда Рецепт Дня
            else if (message.getText().equals("Рецепты")) {
                message.getChatId();
                sendText2(message, "Выберите рецепт из меню");
                hashForProducts.clear();
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message.getChatId()));
                WaterBalanceMethods.deleteIndividualWaterBalance(String.valueOf(message.getChatId()));

            }

            // Если несколько раз нажать команду "Калории продуктов".
            else if (message.getText() != null && hashForProducts.containsKey("1") && message.getText().equals("Калории продуктов")) {
                message.getChatId();
                sendText(message, "Блин, да понял я уже, давай вводи продукт");
                hashForProducts.clear();

            }


            // Выводит данные продуктов из базы данных после ввода продукта
            else if (message.getText() != null && hashForProducts.containsKey("1") && !message.getText().equals("Калории продуктов") && !message.getText().equals("Счетчик калорий") && !message.getText().equals("Водный баланс") && !message.getText().equals("Рецепты")) {
                if (!Pattern.matches("[а-я; А-Я]+[0-9]?[0-9]*", message.getText())) {
                    inlineButton2(message, "Введите только буквы");
                } else if (!Pattern.matches("[а-я; А-Я]{3,}+[0-9]?[0-9]*", message.getText())) {
                    inlineButton2(message, "Введите больше двух букв");
                } else {
                    message.getChatId();
                    String messages = update.getMessage().getText();
                    String response = ProductCaloriesMethod.process(messages);
                    inlineButton2(message, response);
                }
            }

            // Реакция на нажатие кнопки "Калории продуктов"
            else if (message.getText().equals("Калории продуктов") && !hashForProducts.containsKey("1") && !message.getText().equals("Счетчик калорий") && !message.getText().equals("Водный баланс") && !message.getText().equals("Рецепты")) {
                message.getChatId();
                hashForProducts.clear();
                inlineButton1(message, "Введите название продукта: ");
                hashForProducts.put("1", "Калории продуктов");
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message.getChatId()));
                WaterBalanceMethods.deleteIndividualWaterBalance(String.valueOf(message.getChatId()));

            }

            // Выбор пола в методе Индивидуальный расчет калорий
            else if (message.getText() != null && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message.getChatId()), "3") && !IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message.getChatId()), "4") && !message.getText().equals("Калории продуктов") && !message.getText().equals("Счетчик калорий") && !message.getText().equals("Водный баланс") && !message.getText().equals("Рецепты")) {
                message.getChatId();
                if (!Pattern.matches("[0-9]+[\\.]?[0-9]*", message.getText())) {
                    inlineButton1(message, "Пожайлуста вводите только цифры\nВведите свой возраст(например: 25): ");
                } else if (Double.parseDouble(message.getText()) < 150 && Double.parseDouble(message.getText()) > 0) {
                    inlineButton3(message, "Выберите Ваш пол из списка:");
                    InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message.getChatId()), "4", message.getText());

                } else {
                    inlineButton1(message, "Пожайлуста вводите реальные цифры\nВведите свой возраст(например: 25): ");
                }
            }

            //Выбор возраста в методе Индивидуальный расчет калорий
            else if (message.getText() != null && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message.getChatId()), "2") && !IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message.getChatId()), "3") && !message.getText().equals("Калории продуктов") && !message.getText().equals("Счетчик калорий") && !message.getText().equals("Водный баланс") && !message.getText().equals("Рецепты")) {
                message.getChatId();
                if (!Pattern.matches("[0-9]+[\\.]?[0-9]*", message.getText())) {
                    inlineButton1(message, "Пожайлуста вводите только цифры\nВведите свой вес(например: 50): ");
                } else if (Double.parseDouble(message.getText()) < 250 && Double.parseDouble(message.getText()) > 0) {
                    inlineButton1(message, "Введите свой возраст(например: 25)");
                    InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message.getChatId()), "3", message.getText());

                } else {
                    inlineButton1(message, "Пожайлуста вводите реальные цифры\nВведите свой вес(например: 50): ");
                }


            }

            //Выбор веса в методе Индивидуальный расчет калорий
            else if (message.getText() != null && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message.getChatId()), "1") && !IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message.getChatId()), "2") && !message.getText().equals("Калории продуктов") && !message.getText().equals("Счетчик калорий") && !message.getText().equals("Водный баланс") && !message.getText().equals("Рецепты")) {
                message.getChatId();
                if (!Pattern.matches("[0-9]+[\\.]?[0-9]*", message.getText())) {
                    inlineButton1(message, "Пожайлуста вводите только цифры\nВведите свой рост(например: 175): ");
                } else if (Double.parseDouble(message.getText()) < 250 && Double.parseDouble(message.getText()) > 0) {
                    inlineButton1(message, "Введите свой вес(например: 50): ");
                    InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message.getChatId()), "2", message.getText());

                } else {
                    inlineButton1(message, "Пожайлуста вводите реальные цифры\nВведите свой рост(например: 175): ");
                }

            }

            //Выбор роста в методе Индивидуальный расчет калорий
            else if (message.getText().equals("Счетчик калорий") && !message.getText().equals("Калории продуктов") && !message.getText().equals("Водный баланс") && !message.getText().equals("Рецепты")) {
                hashForProducts.clear();
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message.getChatId()));
                WaterBalanceMethods.deleteIndividualWaterBalance(String.valueOf(message.getChatId()));
                inlineButton1(message, "Индивидуальный расчет суточной нормы калорий.\nВведите свой рост (например: 175): ");
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message.getChatId()), "1", "1");

            } else if (message.getText() != null && WaterBalanceMethods.checkWaterBalance(String.valueOf(message.getChatId()), "1") && !message.getText().equals("Калории продуктов") && !message.getText().equals("Счетчик калорий") && !message.getText().equals("Водный баланс") && !message.getText().equals("Рецепты")) {
                if (!Pattern.matches("[0-9]+[\\.]?[0-9]*", message.getText())) {
                    inlineButton1(message, "Пожайлуста вводите только цифры\nВведите свой вес(например: 50): ");
                } else if (Double.parseDouble(message.getText()) < 150 && Double.parseDouble(message.getText()) > 10) {
                    InsertWaterBalanceSQL.insertWaterBalanceToSql(String.valueOf(message.getChatId()), "2", message.getText());
                    String response = WaterBalanceMethods.individualWaterBalance(String.valueOf(message.getChatId()));
                    sendText(message, "Ваша идеальная дневная норма воды: " + response + " мл.");
                    WaterBalanceMethods.deleteIndividualWaterBalance(String.valueOf(message.getChatId()));
                    inlineButton5(message, "Хотите я вам буду напоминать о приеме воды в течении дня?");
                } else {
                    inlineButton1(message, "Та не звезди, нормально же общались. Давай реальный вес (например: 50): ");
                }

            } else if (message.getText().equals("Водный баланс") && !message.getText().equals("Калории продуктов") && !message.getText().equals("Счетчик калорий") && !message.getText().equals("Рецепты")) {
                hashForProducts.clear();
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message.getChatId()));
                WaterBalanceMethods.deleteIndividualWaterBalance(String.valueOf(message.getChatId()));
                inlineButton1(message, "Индивидуальный расчет суточного водного баланса.\nВведите свой вес (например: 50): ");
                InsertWaterBalanceSQL.insertWaterBalanceToSql(String.valueOf(message.getChatId()), "1", "1");

            } else
                sendText(message, "Че ты несешь? Выбери что-то из меню.");


            // Реакция на Inline меню
        } else if (update.hasCallbackQuery()) {
            Message message1 = update.getCallbackQuery().getMessage();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setParseMode(ParseMode.MARKDOWN);
            sendMessage.setChatId(String.valueOf(message1.getChatId()));

            // Реакция на Хватит в Inline меню
            if (data.equals("Хватит")) {
                sendMessage.setChatId(String.valueOf(message1.getChatId()));
                sendMessage.setText("Хорошо");
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message1.getChatId()));
                WaterBalanceMethods.deleteIndividualWaterBalance(String.valueOf(message1.getChatId()));
                hashForProducts.clear();

            }
            // Реакция на Cancel в Inline меню
           else if (data.equals("Cancel")) {
                sendMessage.setChatId(String.valueOf(message1.getChatId()));
                sendMessage.setText("Хорошо");
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message1.getChatId()));
                WaterBalanceMethods.deleteIndividualWaterBalance(String.valueOf(message1.getChatId()));
                hashForProducts.clear();

            }
            // Реакция на Да в Inline меню в выборе упоминаний пить воду
            else if (data.equals("Да")) {
                sendMessage.setChatId(String.valueOf(message1.getChatId()));
                sendMessage.setText("Раз в час с 09:00 до 20:00 я вам буду напоминать пить воду");
                InsertUserDataToSQLWaterReminder.insertChatIdInWaterReminderSQL(String.valueOf(message1.getChatId()));

            }

            else  if (data.equals("Нет")) {
                sendMessage.setChatId(String.valueOf(message1.getChatId()));
                sendMessage.setText("Значит в другой раз");
                InsertUserDataToSQLWaterReminder.deleteChatIdInWaterReminderSQL(String.valueOf(message1.getChatId()));

            }

            // Реакция на выбор пола Мужской в Inline меню
            else  if (data.equals("Мужской")) {
                inlineButton4(message1, "Выберите степень физической активности из списка:");
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message1.getChatId()), "5", "88.36");


                // Реакция на выбор пола Женский в Inline меню
            } else if (data.equals("Женский")) {
                inlineButton4(message1, "Выберите степень физической активности из списка:");
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message1.getChatId()), "5", "447.6");

                // Реакция на выбор "Нет физических нагрузок" в Inline меню
            } else if (data.equals("Нет физических нагрузок") && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message1.getChatId()), "5")) {
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message1.getChatId()), "6", "1.2");
                sendMessage.setText("Ваша суточная норма употребления каллорий: " + IndividualDataMethods.individualCaloriesCalculation(String.valueOf(message1.getChatId())));
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message1.getChatId()));


                // Реакция на выбор "Нагрузки 1–3 раза в неделю" в Inline меню
            } else if (data.equals("Нагрузки 1–3 раза в неделю") && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message1.getChatId()), "5")) {
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message1.getChatId()), "6", "1.375");
                sendMessage.setText("Ваша суточная норма употребления каллорий: " + IndividualDataMethods.individualCaloriesCalculation(String.valueOf(message1.getChatId())));
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message1.getChatId()));


                // Реакция на выбор "Нагрузки 3–5 раз в неделю" в Inline меню
            } else if (data.equals("Нагрузки 3–5 раз в неделю") && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message1.getChatId()), "5")) {
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message1.getChatId()), "6", "1.55");
                sendMessage.setText("Ваша суточная норма употребления каллорий: " + IndividualDataMethods.individualCaloriesCalculation(String.valueOf(message1.getChatId())));
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message1.getChatId()));


                // Реакция на выбор "Нагрузки 6–7 раз в неделю" в Inline меню
            } else if (data.equals("Нагрузки 6–7 раз в неделю") && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message1.getChatId()), "5")) {
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message1.getChatId()), "6", "1.725");
                sendMessage.setText("Ваша суточная норма употребления каллорий: " + IndividualDataMethods.individualCaloriesCalculation(String.valueOf(message1.getChatId())));
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message1.getChatId()));

                // Реакция на выбор "Ежедневно более одной тренировки" в Inline меню
            } else if (data.equals("Ежедневно более одной тренировки") && IndividualDataMethods.checkIndividualCaloriesCalculation(String.valueOf(message1.getChatId()), "5")) {
                InsertIndividualDataSQL.insertIndividualDataToSql(String.valueOf(message1.getChatId()), "6", "1.9");
                sendMessage.setText("Ваша суточная норма употребления каллорий: " + IndividualDataMethods.individualCaloriesCalculation(String.valueOf(message1.getChatId())));
                IndividualDataMethods.deleteIndividualCaloriesCalculation(String.valueOf(message1.getChatId()));

            }
            else
                sendMessage.setText("Пожайлуста вводите реальные цифры\nВведите свой рост(например: 175): ");

            try {
                execute(sendMessage);

            } catch (TelegramApiException e) {
                e.printStackTrace();

            }

        }


        ConnectToSQL.closeConnection();

    }


    // Метод отправки сообщения
    private void sendText(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new
                ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Рецепты");
        keyboardFirstRow.add("Счетчик калорий");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("Калории продуктов");
        keyboardSecondRow.add("Водный баланс");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    // Меню при отправки сообщения "Калории продуктов"
    private void inlineButton1(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        //Inline KeyBoard
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Cancel");
        inlineKeyboardButton.setCallbackData("Cancel");
        inlineKeyboardButtons.add(inlineKeyboardButton);
        inlineButtons.add(inlineKeyboardButtons);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Меню выхода из перечисления калории для продуктов
    void inlineButton2(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        //Inline KeyBoard
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Хватит");
        inlineKeyboardButton.setCallbackData("Хватит");
        inlineKeyboardButtons.add(inlineKeyboardButton);
        inlineButtons.add(inlineKeyboardButtons);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Inline меню для выбора пола в Индивидуальный расчет калорий
    void inlineButton3(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        //Inline KeyBoard
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Мужской");
        inlineKeyboardButton1.setCallbackData("Мужской");
        inlineKeyboardButton2.setText("Женский");
        inlineKeyboardButton2.setCallbackData("Женский");
        inlineKeyboardButtons.add(inlineKeyboardButton1);
        inlineKeyboardButtons.add(inlineKeyboardButton2);
        inlineButtons.add(inlineKeyboardButtons);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    // Inline меню для выбора физической активности в Индивидуальный расчет калорий
    void inlineButton4(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        //Inline KeyBoard
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        keyboardButtonsRow3.add(inlineKeyboardButton5);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём ряд
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);


        inlineKeyboardButton1.setText("Нет физических нагрузок");
        inlineKeyboardButton1.setCallbackData("Нет физических нагрузок");
        inlineKeyboardButton2.setText("Нагрузки 1–3 раза в неделю");
        inlineKeyboardButton2.setCallbackData("Нагрузки 1–3 раза в неделю");
        inlineKeyboardButton3.setText("Нагрузки 3–5 раз в неделю");
        inlineKeyboardButton3.setCallbackData("Нагрузки 3–5 раз в неделю");
        inlineKeyboardButton4.setText("Нагрузки 6–7 раз в неделю");
        inlineKeyboardButton4.setCallbackData("Нагрузки 6–7 раз в неделю");
        inlineKeyboardButton5.setText("Ежедневно более одной тренировки");
        inlineKeyboardButton5.setCallbackData("Ежедневно более одной тренировки");

        inlineKeyboardButtons.add(inlineKeyboardButton1);
        inlineKeyboardButtons.add(inlineKeyboardButton2);
        inlineKeyboardButtons.add(inlineKeyboardButton3);
        inlineKeyboardButtons.add(inlineKeyboardButton4);
        inlineKeyboardButtons.add(inlineKeyboardButton5);
        inlineButtons.add(inlineKeyboardButtons);
        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    //  Inline меню для выбора пола в Индивидуальный расчет калорий
    public void inlineButton5(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        //Inline KeyBoard
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Да");
        inlineKeyboardButton1.setCallbackData("Да");
        inlineKeyboardButton2.setText("Нет");
        inlineKeyboardButton2.setCallbackData("Нет");
        inlineKeyboardButtons.add(inlineKeyboardButton1);
        inlineKeyboardButtons.add(inlineKeyboardButton2);
        inlineButtons.add(inlineKeyboardButtons);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendText2(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new
                ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("\uD83E\uDD5E" + " Завтрак");
        keyboardFirstRow.add("\uD83E\uDD57" + " Обед");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("\uD83C\uDF5D" + " Ужин");
        keyboardSecondRow.add("\uD83E\uDDC1" + " Десерт/Перекус");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add("Назад в меню");
        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "Calories_Products_Bot";
    }

    @Override
    public String getBotToken() {
        return "5415629103:AAGqia5q5sxnDMOhReE8KUDNTn1IxWOrObU";
    }
}