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

                response += id + " " + name + " " + amount + " ";

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
                result = true;




                //st.executeUpdate("insert Users (name,password) values('" + name + "' , '" + password+ "' ;");
                String sql = "insert into Users (name,password)" + " values(?,?);";
                PreparedStatement preparedStmt = con.prepareStatement(sql);
                preparedStmt.setString(1, name);
                preparedStmt.setString(2, password);
                preparedStmt.executeUpdate();
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
}

