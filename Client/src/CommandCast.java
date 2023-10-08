import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CommandCast {
    public static void CmdReviewMain(String cmd, MainWindow frm){
        switch (cmd) {
            case "Fetch Employees" -> new Sender("FtchEmp;", frm);
            case "Fetch Orders" -> new Sender("FtchOrd;", frm);
            case "Fetch Menu" -> new Sender("FtchMnu;", frm);
            case "Add Row" -> addNewRow(frm);
            case "Remove" -> removeRow(frm);
            case "Log Out" -> {
                frm.dispose();
                new LoginWindow();
            }
            default -> new Sender(cmd + ";Incorrect Input", frm);
        }
    }

    public static void CmdReviewLogin(String cmd, String login, String pass, MainWindow frm){
        switch (cmd) {
            case "Log in" -> new Sender("Auth;" + login + ";" + pass, frm);
            case "Sign up" -> new Sender("Reg;" + login + ";" + pass, frm);
            default -> new Sender(cmd + ";Incorrect Input", frm);
        }
    }
    public static void SrvWrdReview(String cmd, MainWindow frm){
        String option = cmd.split(";", 2)[0];
        String[] response = cmd.substring(option.length()+1).split(";");

        switch (option) {
            case "waiter" -> {
                LoginWindow.reg.dispose();
                new MainWindow(option);
            }
            case "administrator" -> {
                LoginWindow.reg.dispose();
                new MainWindow("admin");
            }
            case "UsrAdd" -> {
                LoginWindow.reg.dispose();
                new MainWindow("waiter");
            }
            case "UsrExst" -> {
                JOptionPane.showMessageDialog(null,
                        "User already exists!",
                        "Login Warning", JOptionPane.WARNING_MESSAGE);
                new MainWindow(response[0]);
            }
            case "NoUsr" -> JOptionPane.showMessageDialog(null,
                    "No such user exists!",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            case "OrdInf" -> fillTable(new String[]{"ID", "PRODUCT", "SUPPLIER", "QUANTITY", "DELIVERY DATE"}, response, frm);
            case "MnuInf" -> fillTable(new String[]{"ID", "NAME", "SERVING", "PRICE"}, response, frm);
            case "EmpInf" -> fillTable(new String[]{"ID", "NAME", "POSITION", "PASSWORD"}, response, frm);
        }
    }
    private static void fillTable(String[] columnNames, String[] response, MainWindow frm){
        String[][] info;
        int totalRows = (response.length) / columnNames.length;
        info = new String[totalRows][columnNames.length];

        for(int j = 0; j < totalRows; j++) {
            System.arraycopy(response, j * columnNames.length, info[j], 0, columnNames.length);
        }
        frm.mainTable = frm.infoTable(info, columnNames);
        frm.mainScrollPane.setViewportView(frm.mainTable);
    }

    public static void addNewRow(MainWindow frm){
        String[] rows = new String[frm.mainTable.getRowCount()];
        String controlValue = String.valueOf((Integer.parseInt(frm.mainTable.getValueAt(frm.mainTable.getRowCount()-1, 0).toString()) + 1));
        rows[0] = controlValue;
        DefaultTableModel model = (DefaultTableModel)frm.mainTable.getModel();
        model.addRow(rows);
        frm.mainTable.setModel(model);
    }

    public static void removeRow(MainWindow frm){
        if (frm.mainTable.getSelectedRow() != -1) {
            System.out.println(frm.mainTable.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) frm.mainTable.getModel();
            convertToRequest(frm.mainTable.getSelectedRow(), frm);
            model.removeRow(frm.mainTable.getSelectedRow());
            frm.mainTable.setModel(model);
        }else {
            JOptionPane.showMessageDialog(frm,
                    "No row selected. Try again!",
                    "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void convertToRequest(int rowChanged, MainWindow frm){
        DefaultTableModel model = (DefaultTableModel) frm.mainTable.getModel();
        StringBuilder requestBuilder;
        int controlValue = Integer.parseInt(model.getValueAt(rowChanged, 0).toString());
        if (controlValue > 1000) {
            requestBuilder = new StringBuilder("UpdMnu;");
        } else if (controlValue > 100) {
            requestBuilder = new StringBuilder("UpdOrd;");
        }else{
            requestBuilder = new StringBuilder("UpdEmp;");
        }
        for (int i = 0 ; i < frm.mainTable.getColumnCount(); i++){
            requestBuilder.append(model.getValueAt(rowChanged, i)).append(";");
        }
        assert false;
        String request = requestBuilder.toString();
        if (!request.contains("null")){
            new Sender(request, frm);
        }
    }
}
