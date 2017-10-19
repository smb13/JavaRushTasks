package com.javarush.task.task30.task3008.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mikhail Shamanin on 28.06.2017.
 */
public class BotClient extends Client {

    @Override
    protected String getUserName() {
        return new String(new StringBuilder("date_bot_").append(String.valueOf((int)(Math.random()*100))));
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    public class BotSocketThread extends Client.SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            System.out.println(message);
            String[] sp = message.split(": ");
            if (sp.length == 2) {
                String userName = sp[0];
                String request = sp[1];
                Date currDate = Calendar.getInstance().getTime();
                String begStr = new String(new StringBuilder("Информация для ").append(userName).append(": "));
                if (request.equalsIgnoreCase("дата")) {
                    sendTextMessage(begStr + new SimpleDateFormat("d.MM.YYYY").format(currDate));
                } else if (request.equalsIgnoreCase("день")) {
                    sendTextMessage(begStr + new SimpleDateFormat("d").format(currDate));
                } else if (request.equalsIgnoreCase("месяц")) {
                    sendTextMessage(begStr + new SimpleDateFormat("MMMM").format(currDate));
                } else if (request.equalsIgnoreCase("год")) {
                    sendTextMessage(begStr + new SimpleDateFormat("YYYY").format(currDate));
                } else if (request.equalsIgnoreCase("время")) {
                    sendTextMessage(begStr + new SimpleDateFormat("H:mm:ss").format(currDate));
                } else if (request.equalsIgnoreCase("час")) {
                    sendTextMessage(begStr + new SimpleDateFormat("H").format(currDate));
                } else if (request.equalsIgnoreCase("минуты")) {
                    sendTextMessage(begStr + new SimpleDateFormat("m").format(currDate));
                } else if (request.equalsIgnoreCase("секунды")) {
                    sendTextMessage(begStr + new SimpleDateFormat("s").format(currDate));
                }
            }
        }
    }

    public static void main (String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
