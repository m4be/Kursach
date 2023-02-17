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
                while (true) {
                    try {
                        writer = new BufferedWriter(
                                new OutputStreamWriter(
                                        clientSocket.getOutputStream()));
                        reader = new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream()));
                        String response;
                        String request = reader.readLine();
                        System.out.println("Client: " + request);
                        switch (request) {
                            case "Penis": {
                                response = "Viewing form server! ";
                                break;
                            }
                            case "Pizda": {
                                response = "Fetching form server! " + DBControl.orderFetch();
                                break;
                            }
                            case "Boobs": {
                                response = "Submitting to server! ";

                                break;
                            }
                            default: {
                                response = "Incorrect request from user! ";
                                break;
                            }
                        }
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
                }
            }finally {
                server.close();
                System.out.println("Server has stopped!");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}