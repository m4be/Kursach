import java.io.*;
import java.net.Socket;
import java.util.logging.Level;


public class Handler implements Runnable {
    private final Socket clientSocket;

    BufferedReader reader; // поток чтения из сокета
    BufferedWriter writer; // поток записи в сокет

    public Handler(Socket socket) {
        clientSocket = socket;
       // Server.LOGGER.log(Level.INFO," Request from : " + socket.getInetAddress().getHostAddress());

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
                case "200": { //Авторизация
                    if(DBControl.AuthorizeUser(str[1],str[2])) //Если есть такой логин пароль
                    {
                        response = "201";
                        Server.LOGGER.log(Level.INFO, "[" + clientSocket.getInetAddress().getHostAddress() + "] Authorization for [" + str[1] + "] was successful");
                    } //То авторизация успешна
                    else{
                        response = "101";//Не успешна
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Authorization for [" + str[1] + "] wasn't successful");
                    }
                    break;
                }
                case "300": { //Регистрация
                    if(DBControl.RegisterUser(str[1],str[2])){//Если такой учетки нет
                    response = "301";
                    Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Registration for login : [" + str[1] + "] and password: ["+ str[2]+"] was successful");
                    //Регистрация успешна
                    }
                    else{
                        response = "102";//Не уcпешна
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Registration for login : [" + str[1] + "] and password: ["+ str[2]+"] wasn't successful");

                    }
                    break;
                }
                case "400": { //Админ
                    if(DBControl.AuthorizeAdmin(str[1],str[2])){//Если такой учетки нет
                        response = "401";//Вход в админку успешен
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Authorization for Admin: [" + str[1] + "] was successful");

                    }
                    else{
                        response = "103";//Не уcпешен
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Authorization for Admin: [" + str[1] + "] wasn't successful");

                    }
                    break;
                }

                case "500": {
                    //Пользоваетль вышел из учетки
                    response = "501";
                    Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] User : [" + str[1] + "] logged out");
                    break;
                }
                case "800": {
                    response = "801;" + DBControl.getData(); //Отправка таблицы
                    Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Sending main table");

                    break;
                }

                case "900": {
                    if(str[1].equals("admin")){
                        response = "902;" + DBControl.getUserInfo(true, null);
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Sending sub table for admin");

                    }
                    else if (str[1].equals("user")) {
                        response = "901;" + DBControl.getUserInfo(false, str[2]);
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Sending sub table for user");

                    }
                    else{
                        response = "903";
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Unable to send a sub table");
                    }
                    break;
                }

                case "600":{

                    if(DBControl.TakeCar(str[2],str[1])) {
                        response = "601"; //Удалось заказать технику
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] User: ["+str[2] +"] successfully obtained a car: ["+ str[1]+"]");
                    }
                    else{
                        response = "602";//Не удалось заказать технику(Нет на складе <1)
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] User: ["+str[2] +"] couldn't obtain a car: ["+ str[1]+"]");

                    }
                    break;
                }
                case "700" :{
                    if(DBControl.GiveCar(str[1])) {
                        response = "701";
                        Server.LOGGER.log(Level.INFO,"[" + clientSocket.getInetAddress().getHostAddress() + "] Car: ["+ str[1]+"] was successfully returned");
                    }
                    else{
                        response = "702";
                        Server.LOGGER.log(Level.INFO,"Car: ["+ str[1]+"] wasn't returned");
                    }
                    break;
                }
                default: {
                    response = "Incorrect request from user! request was: " + request;
                    Server.LOGGER.log(Level.WARNING, "[" + clientSocket.getInetAddress().getHostAddress() + "] " + response);
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