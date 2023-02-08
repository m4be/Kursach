import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader reader; // поток чтения из сокета
    private static BufferedWriter writer; // поток записи в сокет

    public static void main(String[] args) throws IOException {
        try {
            try {
                server = new ServerSocket(8000);
                System.out.println("Server is running!");
                clientSocket = server.accept();
                while (true)
                    try {
                        writer = new BufferedWriter(
                                new OutputStreamWriter(
                                        clientSocket.getOutputStream()));
                        reader = new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream()));
                        String request = reader.readLine();
                        System.out.println("Request:" + request);
                        String response = "Hello form server! Request is:";
                        System.out.println(response + request);
                        writer.write(response + request);
                        writer.newLine();
                        writer.flush();
                    } finally {
                        clientSocket.close();
                        reader.close();
                        writer.close();
                        clientSocket = server.accept();
                    }
            } finally {
                server.close();
                System.out.println("Server has stopped!");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}