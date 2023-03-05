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
                case "200": { //Авторизация
                    if(DBControl.AuthorizeUser(str[1],str[2])) //Если есть такой логин пароль
                    {response = "201";} //То авторизация успешна
                    else{
                        response = "101";//Не успешна
                    }
                    break;
                }
                case "300": { //Регистрация
                    if(DBControl.RegisterUser(str[1],str[2])){//Если такой учетки нет
                    response = "301";//Регистрация успешна
                    }
                    else{
                        response = "102";//Не уcпешна
                    }
                    break;
                }
                case "400": { //Админ
                    if(DBControl.AuthorizeAdmin(str[1],str[2])){//Если такой учетки нет
                        response = "401";//Регистрация успешна
                    }
                    else{
                        response = "103";//Не уcпешна
                    }
                    break;
                }

                case "500": {
                    //Пользоваетль вышел из учетки
                    response = "501";
                    break;
                }
                case "800": {
                    response = "801;" + DBControl.getData(); //Отправка таблицы
                    break;
                }

                case "900": {
                    if(str[1].equals("admin")){
                        response = "902;" + DBControl.getUserInfo(true, null);
                    }
                    else if (str[1].equals("user")) {
                        response = "901;" + DBControl.getUserInfo(false, str[2]);
                    }
                    else{
                        response = "903";
                    }
                    break;
                }

                case "600":{

                    if(DBControl.TakeCar(str[2],str[1])) {
                        response = "601";
                    }
                    else{
                        response = "602";
                    }
                    break;
                }
                case "700" :{
                    if(DBControl.GiveCar(str[1])) {
                        response = "701";
                    }
                    else{
                        response = "702";
                    }
                    break;
                }


                default: {
                    response = "Incorrect request from user! request was: " + request;
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