import java.io.*;
import java.net.Socket;

public class Sender {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    Sender(String msg){
        try {
            try {
                // адрес - локальный хост, порт - 8000, такой же как у сервера
                clientSocket = new Socket("localhost", 8000); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(
                        new InputStreamReader(
                                System.in));
                // читать соообщения с сервера
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(
                        new OutputStreamWriter(
                                clientSocket.getOutputStream()));
                // если соединение произошло и потоки успешно созданы - мы можем
                //  работать дальше и предложить клиенту что то ввести
                // если нет - вылетит исключение

                out.write(msg + "\n"); // отправляем сообщение на сервер

                out.flush();
                String serverWord = in.readLine(); // ждём, что скажет сервер
                System.out.println(serverWord); // получив - выводим на экран

                Frame.text.setText(serverWord);


            } finally { // в любом случае необходимо закрыть сокет и потоки
                clientSocket.close();
                in.close();
                out.close();

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }


}
