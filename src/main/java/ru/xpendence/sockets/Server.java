package ru.xpendence.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] ar)    {
        /** Порт, через который будет установлено соединение. Число от 1025 до 65535 */
        int port = 6666;
        try {
            /**
             * ServerSocket ожидает подключение от клиента.
             * При инициализации ServerSocket необходимо указать порт в качестве параметра конструктора.
             */
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client...");

            /**
             * Socket — и есть сокет.
             * Создание (упрощённое): Socket socket = new ServerSocket(port).accept();
             * Сокет ожидает подключения.
             * На выполнении метода accept() выполнение программы зависает
             * (непонятно каким образом, в выполнении метода я цикла не увидел).
             * Наверное, это происходит в классе implAccept(Socket s);
             */
            Socket socket = ss.accept();
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();

            /** Входной и выходной потоки сокета. Одинаковый алгоритм для сервера и клиента. */
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            /** Data- (Input / Output) -Stream лучше подходит для обработки тестовых сообщений. */
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line = null;
            while(true) {
                /**
                 * Здесь исполняется 3 действия:
                 * 1. readUTF() — принимает запрос от клиента (строку текста).
                 * 2. writeUTF() — отправляет ответ (всё, что угодно).
                 * 3. flush() — очистка кэша.
                 *
                 * Цикл гоняется до бесконечности.
                 */
                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
                out.flush(); // заставляем поток закончить передачу данных.
                System.out.println("Waiting for the next line...");
                System.out.println();
            }
        } catch(Exception x) {
            x.printStackTrace();
        }
    }
}
