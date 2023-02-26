import java.io.*;
import java.net.Socket;

public class Sender {

    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;


    Sender(String msg, Frame frm){
        try {
            try {
                clientSocket = new Socket("localhost", 8000);
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                out = new BufferedWriter(
                        new OutputStreamWriter(
                                clientSocket.getOutputStream()));

                out.write(msg + "\n");



                out.flush();
                String serverWord = in.readLine();



                //Тут распознание кодов добавить (можно через отдельный класс, но тогда
                // придется передавать ссылку на фрейм)
                // Пока что оно прямо в фрейме

                System.out.println("Ответочка" + serverWord);
                frm.getServerMessage(serverWord); //Переименовать



            } finally {
                clientSocket.close();
                in.close();
                out.close();

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }


}