import java.io.*;
import java.net.Socket;



public class Handler implements Runnable {
    private final Socket clientSocket;

    BufferedReader reader; // поток чтения из сокета
    BufferedWriter writer; // поток записи в сокет

    public Handler(Socket socket) {
        clientSocket = socket;
    }

    public void run() {
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            clientSocket.getOutputStream()));
            reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String response;
        String request;
        try {
            request = reader.readLine();
            System.out.println("Client: " + request);

            String[] str = request.split(" ");

            switch (str[0]) {
                case "200": {
                    if(DBControl.Authorize(str[1],str[2]))
                    {response = "201";}
                    else{
                        response = "101";
                    }
                    break;
                }
                case "300": {
                    response = "Fetching form server! " + DBControl.getData();
                    break;
                }
                case "400": {
                    response = "Submitting to server! ";
                    break;
                }
                default: {
                    response = "Incorrect request from user! ";
                    break;
                }
            }
            writer.write(response);
            writer.flush();
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}