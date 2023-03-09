import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {

    private static ServerSocket server; // серверсокет

    static Logger LOGGER = Logger.getLogger(Server.class.getName());;

    static {
        try{
            FileHandler fh = new FileHandler("logger.txt");
            LOGGER.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            LOGGER.setUseParentHandlers(false); //отключаем вывод в консоль
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(8000);
                server.setReuseAddress(true);
                System.out.println("Server is running!");

                LOGGER.log(Level.INFO," Server has started");

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