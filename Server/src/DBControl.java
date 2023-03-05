import java.sql.*;

public class DBControl {
    public static String getData() {
        Connection con;
        String response = "";
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AutoPark",
                    "root", "1234");

            Statement st;
            st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("select * from Cars");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name").trim();
                int amount  = rs.getInt("amount");

                response += id + ";" + name + ";" + amount + ";";

                System.out.println(response);
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return response;
    }

    public static String getUserInfo(boolean flag, String userName){
        Connection con;
        String response = "";
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AutoPark",
                    "root", "1234");

            Statement st;
            st = con.createStatement();
            ResultSet rs;

            if(flag) {
                rs = st.executeQuery("select * from Orders");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("user").trim();
                    int carID = rs.getInt("carID");

                    response += id + ";" + carID + ";" + name + ";";
                }
            }
            else{
                rs = st.executeQuery("select Orders.id, name from Orders join Cars on Cars.id = carID where Orders.user = \'" + userName + "\';");
                while (rs.next()) {
                    int id = rs.getInt("Orders.id");
                    String name = rs.getString("name").trim();

                    response += id + ";" + name + ";";
                }
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return response;
    }

    public static boolean AuthorizeUser(String name, String password){
        Connection con;
        boolean result = false;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AutoPark",
                    "root", "1234");

            Statement st;
            st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("select * from Users where name = \"" + name + "\" and password = \"" + password + "\"");

            if(rs.next())
                result = true;

            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public static boolean RegisterUser(String name, String password) {
        Connection con;
        boolean result = false;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AutoPark",
                    "root", "1234");

            Statement st;
            st = con.createStatement();
            ResultSet rs;

            rs = st.executeQuery("select * from Users where name = '" + name + "';");
            if(!rs.next()) {
                String sql = "insert into Users (name,password)" + " values(?,?);";
                PreparedStatement preparedStmt = con.prepareStatement(sql);
                preparedStmt.setString(1, name);
                preparedStmt.setString(2, password);
                preparedStmt.executeUpdate();
                result = true;
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public static boolean AuthorizeAdmin(String name, String password){
        Connection con;
        boolean result = false;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AutoPark",
                    "root", "1234");

            Statement st;
            st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("select * from Admins where name = \"" + name + "\" and password = \"" + password + "\"");

            if(rs.next())
                result = true;

            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public static boolean TakeCar(String name, String id){
        Connection con;
        boolean result = false;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AutoPark",
                    "root", "1234");

            Statement st;
            st = con.createStatement();
            ResultSet rs;


            rs = st.executeQuery("select amount from Cars where id = '" + id + "';");
            rs.next();
            if(rs.getInt("amount") > 0) {


                String sql = "update Cars set amount = amount - 1 where id = ?;";
                PreparedStatement preparedStmt = con.prepareStatement(sql);
                preparedStmt.setString(1, id);
                preparedStmt.executeUpdate();

                sql = "insert into Orders(user,carID) values(?,?);";
                preparedStmt = con.prepareStatement(sql);
                preparedStmt.setString(1, name);
                preparedStmt.setString(2, id);
                preparedStmt.executeUpdate();
                result = true;
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public static boolean GiveCar(String id){

        Connection con;
        boolean result = false;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AutoPark",
                    "root", "1234");

            Statement st;
            st = con.createStatement();
            ResultSet rs;


            rs = st.executeQuery("select carID from Orders where id ='" + id + "';");
            if(rs.next()){

                int carID = rs.getInt("carID");

                String sql = "update Cars set amount = amount + 1 where id = ?;";
                PreparedStatement preparedStmt = con.prepareStatement(sql);
                preparedStmt.setString(1, Integer.toString(carID));
                preparedStmt.executeUpdate();

                sql = "delete from Orders where id = ?;";
                preparedStmt = con.prepareStatement(sql);
                preparedStmt.setString(1, id);
                preparedStmt.executeUpdate();

                result = true;
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
}

