import java.sql.*;

public class DBControl {
    public static String orderFetch() {
        Connection con;
        //String response = null;
        String response = "";
        try {
            con = DriverManager.getConnection(
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
                response += id + " " + product + " " + supplier + " " + quantity + " " + deliveryDate;

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
}

//    public static void orderAdd(int addId, String addProduct, String addSupplier, int addQuantity, String addDeliDate) {
  //      try {
    //        connection = DriverManager.getConnection(
      //            "jdbc:mysql://localhost:3306/administration",
         //           "root", "1862");

        //    Statement statement;
          //  statement = connection.createStatement();
            //ResultSet resultSet;
            //resultSet = statement.executeQuery("insert into orders " +
              //      "values (" + addId + addProduct + addSupplier + addQuantity + addDeliDate + ");");

//            resultSet.close();
  //          statement.close();
    //        connection.close();

      //  }catch (SQLException e){
        //    System.out.println(e);
       // }
   // }
//}
