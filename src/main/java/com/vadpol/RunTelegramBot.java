package com.vadpol;


// Класс Main, этот класс активирует телеграмБот, этот же класс запускается в Heroku.


import com.vadpol.telegramBot.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class RunTelegramBot {


    public static void main(String[] args) throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            TelegramBot bot = new TelegramBot();
            telegramBotsApi.registerBot(bot);


//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//
//            scheduler.start();
//            JobDetail jobDetail = newJob(WaterReminderMethod.class)
//                    .withIdentity("WaterDrink")
//                    .build();
//
//            ScheduleBuilder scheduleBuilder =
//                    CronScheduleBuilder.cronSchedule("0 0 9-20 ? * * *").inTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
//            Trigger trigger = TriggerBuilder.newTrigger().
//                    withSchedule(scheduleBuilder).build();
//
//            scheduler.scheduleJob(jobDetail, trigger);


        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
//        } catch (SchedulerException e) {
//            throw new RuntimeException(e);
//
//        }
    }
}