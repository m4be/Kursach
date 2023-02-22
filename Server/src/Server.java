import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket server; // серверсокет


    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(8000);
                server.setReuseAddress(true);
                System.out.println("Server is running!");

                while (true) {
                    Socket clientSocket = server.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                    Handler clientSock = new Handler(clientSocket);
                    new Thread(clientSock).start();
                }
            }finally {
                server.close();
                System.out.println("Server has stopped!");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}