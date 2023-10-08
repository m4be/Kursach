import java.sql.*;
import java.util.logging.Level;
public class DBControl {

    public DBControl() {
    }
    public static String fetchOrders() {
        String response = "";
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from orders");

            while (rs.next()) {
                int id = rs.getInt("id");
                String product = rs.getString("product").trim();
                String supplier = rs.getString("supplier").trim();
                int quantity = rs.getInt("quantity");
                Date deliveryDate = rs.getDate("deli_date");
                response += id + ";" + product + ";" + supplier + ";" + quantity + ";" + deliveryDate + ";";
                System.out.println(response);
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            Server.LOG.log(Level.WARNING, "DATABASE RETRIEVAL ERROR: " + e);
        }
        Server.LOG.log(Level.INFO, "ORDERS RETRIEVED");
        return response;
    }
    public static String fetchMenu() {
        String response = "";
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from menu");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name").trim();
                int serving = rs.getInt("serving");
                float price = rs.getFloat("price");
                response += id + ";" + name + ";" + serving + ";" + price + ";";
                System.out.println(response);
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            Server.LOG.log(Level.WARNING, "DATABASE RETRIEVAL ERROR: " + e);
        }
        return response;
    }

    public static String fetchEmployees() {
        String response = "";
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from employees");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name").trim();
                String position = rs.getString("position");
                String password = rs.getString("password");
                response += id + ";" + name + ";" + position + ";" + password + ";";
                System.out.println(response);
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            Server.LOG.log(Level.WARNING, "DATABASE RETRIEVAL ERROR: " + e);
        }
        return response;
    }

    public static String addUser(String userName, String password) {
        String response = "";
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from employees");
            while (rs.next()) {
                String dbName = rs.getString("name").trim();
                String dbPosition = rs.getString("position").trim();
                String dbPassword = rs.getString("password").trim();

                if ((dbName.equals(userName))&&(dbPassword.equals(password))){
                    response = dbPosition;
                }
            }
            if (!response.isEmpty()){
                response = "UsrExst;" + response;
            }
            else{
                String sql = "insert into employees (name, position, password) values (?, ?, ?)";
                PreparedStatement pst;
                pst = con.prepareStatement(sql);

                pst.setString(1, userName);
                pst.setString(2, "waiter");
                pst.setString(3, password);
                pst.executeUpdate();
                response = "UsrAdd;";
                pst.close();
            }
            System.out.println(response);
            con.close();
        }catch (SQLException e){
            Server.LOG.log(Level.WARNING, "DATABASE ADDITION ERROR: " + e);
        }
        return response;
    }

    public static String checkUser(String login, String password) {
        String response = "";
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from employees");

            while (rs.next()) {
                String dbName = rs.getString("name").trim();
                String dbPosition = rs.getString("position").trim();
                String dbPassword = rs.getString("password").trim();
                if ((dbName.equals(login))&&(dbPassword.equals(password))){
                    response = dbPosition + ";";
                }
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            Server.LOG.log(Level.WARNING, "DATABASE SEARCH ERROR: " + e);
        }
        if (response.isEmpty()){
            response = "NoUsr;";
        }
        return response;
    }

    public static String updateOrders(String[] clientData){
        String response = null;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from orders");

            String sqlDelete = "delete from orders where id = ?";
            String sqlAdd = "insert into orders (product, supplier, quantity, deli_date) values (?, ?, ?, ?)";
            PreparedStatement pst = null;
            while (rs.next()) {
                int dbID = rs.getInt("id");
                if (dbID == Integer.parseInt(clientData[0])){
                    String dbProduct = rs.getString("product").trim();
                    String dbSupplier = rs.getString("supplier").trim();
                    String dbQuantity = rs.getString("quantity");
                    Date dbDeliDate = rs.getDate("deli_date");
                    if ((dbProduct.equals(clientData[1]))&&
                            (dbSupplier.equals(clientData[2]))&&
                            (dbQuantity.equals(clientData[3]))&&
                            (dbDeliDate.equals(Date.valueOf(clientData[4])))){
                        pst = con.prepareStatement(sqlDelete);
                        pst.setInt(1, dbID);
                        response = "ORDER DELETED AT ROW: " + dbID;
                    }
                    if (!dbProduct.equals(clientData[1])){
                        String sqlUpdate = "update orders set product = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[1]);
                        response = "ORDER UPDATED AT ROW: " + dbID;
                    }
                    if (!dbSupplier.equals(clientData[2])) {
                        String sqlUpdate = "update orders set supplier = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[2]);
                        response = "ORDER UPDATED AT ROW: " + dbID;
                    }
                    if (!dbQuantity.equals(clientData[3])) {
                        String sqlUpdate = "update orders set quantity = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[3]);
                        response = "ORDER UPDATED AT ROW: " + dbID;
                    }
                    if (!dbDeliDate.equals(Date.valueOf(clientData[4]))) {
                        String sqlUpdate = "update orders set deli_date = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setDate(1, Date.valueOf(clientData[4]));
                        response = "ORDER UPDATED AT ROW: " + dbID;
                    }
                }
            }
            if (response == null){
                pst = con.prepareStatement(sqlAdd);
                pst.setString(1, clientData[1]);
                pst.setString(2, clientData[2]);
                pst.setString(3, clientData[3]);
                pst.setDate(4, Date.valueOf(clientData[4]));
                response = "ORDER ADDED AT ROW: " + clientData[0];
            }
            pst.executeUpdate();
            pst.close();
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            Server.LOG.log(Level.WARNING, "ORDERS UPDATE ERROR: " + e);
        }
        return response;
    }

    public static String updateEmployees(String[] clientData){
        String response = null;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from employees");

            String sqlDelete = "delete from employees where id = ?";
            String sqlAdd = "insert into employees (name, position, password) values (?, ?, ?)";
            PreparedStatement pst = null;
            while (rs.next()) {
                int dbID = rs.getInt("id");
                if (dbID == Integer.parseInt(clientData[0])){
                    String dbName = rs.getString("name").trim();
                    String dbPosition = rs.getString("position").trim();
                    String dbPassword = rs.getString("password");
                    if ((dbName.equals(clientData[1]))&&
                            (dbPosition.equals(clientData[2]))&&
                            (dbPassword.equals(clientData[3]))){
                        pst = con.prepareStatement(sqlDelete);
                        pst.setInt(1, dbID);
                        response = "EMPLOYEE DELETED AT ROW: " + dbID;
                    }
                    if (!dbName.equals(clientData[1])){
                        String sqlUpdate = "update employees set name = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[1]);
                        response = "EMPLOYEE UPDATED AT ROW: " + dbID;
                    }
                    if (!dbPosition.equals(clientData[2])) {
                        String sqlUpdate = "update employees set position = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[2]);
                        response = "EMPLOYEE UPDATED AT ROW: " + dbID;
                    }
                    if (!dbPassword.equals(clientData[3])) {
                        String sqlUpdate = "update employees set password = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[3]);
                        response = "EMPLOYEE UPDATED AT ROW: " + dbID;
                    }
                }
            }
            if (response == null){
                pst = con.prepareStatement(sqlAdd);
                pst.setString(1, clientData[1]);
                pst.setString(2, clientData[2]);
                pst.setString(3, clientData[3]);
                response = "EMPLOYEE ADDED AT ROW: " + clientData[0];
            }
            pst.executeUpdate();
            pst.close();
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            Server.LOG.log(Level.WARNING, "EMPLOYEES UPDATE ERROR: " + e);
        }
        return response;
    }

    public static String updateMenu(String[] clientData){
        String response = null;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/administration",
                    "root", "1862");

            Statement st;
            st = con.createStatement();

            ResultSet rs;
            rs = st.executeQuery("select * from menu");

            String sqlDelete = "delete from menu where id = ?";
            String sqlAdd = "insert into menu (name, serving, price) values (?, ?, ?)";
            PreparedStatement pst = null;
            while (rs.next()) {
                int dbID = rs.getInt("id");
                if (dbID == Integer.parseInt(clientData[0])){
                    String dbName = rs.getString("name").trim();
                    int dbServing = rs.getInt("serving");
                    float dbPrice = rs.getFloat("price");
                    if ((dbName.equals(clientData[1]))&&
                            (dbServing == Integer.parseInt(clientData[2]))&&
                            (dbPrice == Float.parseFloat(clientData[3]))){
                        pst = con.prepareStatement(sqlDelete);
                        pst.setInt(1, dbID);
                        response = "MENU ITEM DELETED AT ROW: " + dbID;
                    }
                    if (!dbName.equals(clientData[1])){
                        String sqlUpdate = "update menu set name = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[1]);
                        response = "MENU UPDATED AT ROW: " + dbID;
                    }
                    if (!(dbServing == Integer.parseInt(clientData[2]))) {
                        String sqlUpdate = "update menu set serving = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[2]);
                        response = "MENU UPDATED AT ROW: " + dbID;
                    }
                    if (!(dbPrice == Float.parseFloat(clientData[3]))) {
                        String sqlUpdate = "update menu set price = ? where id = ?";
                        pst = con.prepareStatement(sqlUpdate);
                        pst.setInt(2, dbID);
                        pst.setString(1, clientData[3]);
                        response = "MENU UPDATED AT ROW: " + dbID;
                    }
                }
            }
            if (response == null){
                pst = con.prepareStatement(sqlAdd);
                pst.setString(1, clientData[1]);
                pst.setString(2, clientData[2]);
                pst.setString(3, clientData[3]);
                response = "MENU ITEM ADDED AT ROW: " + clientData[0];
            }
            pst.executeUpdate();
            pst.close();
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            Server.LOG.log(Level.WARNING, "MENU UPDATE ERROR: " + e);
        }
        return response;
    }
}
