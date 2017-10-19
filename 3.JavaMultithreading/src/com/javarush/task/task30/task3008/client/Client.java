package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Mikhail Shamanin on 28.06.2017.
 */
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    protected String getServerAddress() {
        String inputStr = null;
        while (true) {
            inputStr = ConsoleHelper.readString();
            if (inputStr.matches("/\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b/") || inputStr.equals("localhost")) {
                break;
            }
        }
        return inputStr;
    }
    //должен запросить ввод адреса сервера у пользователя и вернуть введенное значение. Адрес может быть строкой, содержащей ip,
    //если клиент и сервер запущен на разных машинах или ‘localhost’, если клиент и сервер работают на одной машине.

    protected int getServerPort() {
        return ConsoleHelper.readInt();
    }
    //должен запрашивать ввод порта сервера и возвращать его.

    protected String getUserName(){
        return ConsoleHelper.readString();
    }
    //должен запрашивать и возвращать имя пользователя.

    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    //в данной реализации клиента всегда должен возвращать true (мы всегда отправляем текст введенный в консоль).
    //Этот метод может быть переопределен, если мы будем писать какой-нибудь другой клиент, унаследованный от нашего,
    // который не должен отправлять введенный в консоль текст.

    protected SocketThread getSocketThread() {
        return new SocketThread();
    }



    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            System.out.println("Произошла ошибка при отправке сообщения");
            clientConnected = false;
        }
    }

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        synchronized (this) {
            try {
                //while (!Thread.currentThread().interrupted()) {
                    wait();
                //}
            } catch (Exception e) {
                System.out.println("Произошла ошибка.");
                return;
            }
            if (!clientConnected) {
                System.out.println("Произошла ошибка во время работы клиента.");
                return;
            }
            System.out.println("Соединение установлено. Для выхода наберите команду ‘exit’.");
            while (clientConnected) {
                String msg = ConsoleHelper.readString();
                if (msg.equals("exit")) {
                    break;
                }
                if (shouldSendTextFromConsole()) {
                    sendTextMessage(msg);
                }
            }

        }
    }


    public class SocketThread extends Thread {

        protected void processIncomingMessage(String message) {
            System.out.println(message);
        }
        //должен выводить текст message в консоль.


        protected void informAboutAddingNewUser(String userName) {
            System.out.println(userName + " присоединился к чату");
        }
        //должен выводить в консоль информацию о том, что участник с именем userName присоединился к чату.


        protected void informAboutDeletingNewUser(String userName) {
            System.out.println(userName + " покинул чат");
        }
        //должен выводить в консоль, что участник с именем userName покинул чат.


        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notifyAll();
            }
        }
        //этот метод должен:
        //а) Устанавливать значение поля clientConnected внешнего объекта Client в соответствии с переданным параметром.
        //б) Оповещать (пробуждать ожидающий) основной поток класса Client.
        //Подсказка: используй синхронизацию на уровне текущего объекта внешнего класса и метод notify.
        //Для класса SocketThread внешним классом является класс Client.


        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message msg = connection.receive();
                if (msg.getType() == MessageType.NAME_REQUEST) {
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else if (msg.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    return;
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message msg = connection.receive();
                if (msg.getType() == MessageType.TEXT) {
                    processIncomingMessage(msg.getData());
                } else if (msg.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(msg.getData());
                } else if (msg.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(msg.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        public void run() {
            String address = getServerAddress();
            int port = getServerPort();
            try {
                Client.this.connection = new Connection(new Socket(address, port));
                clientHandshake();
                clientMainLoop();
            } catch (ClassNotFoundException e1) {
                notifyConnectionStatusChanged(false);
            } catch (IOException e1) {
                notifyConnectionStatusChanged(false);
            }
        }
    }


    public static void main (String[] args) {
        Client client = new Client();
        client.run();

    }
}
