import java.sql.*;
public class DBControl {
    public static void DBConnect(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Orders",
                    "root", "1862");

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from info");

            int id;
            String product;
            String supplier;
            int quantity;
            String deliveryDate;

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                product = resultSet.getString("product").trim();
                supplier = resultSet.getString("supplier").trim();
                quantity = resultSet.getInt("quantity");
                deliveryDate = resultSet.getString("deli_date").trim();
                System.out.println(" Id: " + id
                        + "\n Product: " + product + "\n Supplier: " + supplier + "\n Quantity: " + quantity + "\n Date of delivery: " + deliveryDate);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
