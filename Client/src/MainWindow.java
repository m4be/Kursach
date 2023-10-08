import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainWindow extends JFrame implements ActionListener {
    String modeCheck;
    JButton seeOrders = new JButton("Fetch Orders");
    JButton seeUsers = new JButton("Fetch Employees");
    JButton seeMenu = new JButton("Fetch Menu");
    JButton logOut = new JButton("Log Out");
    JButton addRow = new JButton("Add Row");
    JButton removeRow = new JButton("Remove");
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(LoginWindow.class.getResource("img/menuicon.png")));
    JScrollPane mainScrollPane = new JScrollPane();
    JTable mainTable = new JTable();
    MainWindow frm;

    MainWindow(String mode) {
        frm = this;
        modeCheck = mode;
        this.setTitle("Menu Administration Utility");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        this.setIconImage(icon.getImage());
        this.add(Button(seeOrders, 35, 35, 200));
        this.add(Button(seeMenu, 35, 95, 200));

        if (Objects.equals(modeCheck, "admin")){
            this.add(Button(seeUsers, 35, 155, 200));
            this.add(Button(addRow, 274,650, 120));
            this.add(Button(removeRow, 400,650, 120));
            seeUsers.addActionListener(this);
            removeRow.addActionListener(this);
            addRow.addActionListener(this);
        }
        this.add(Button(logOut, 824, 650, 150));
        this.add(scrollPane(mainScrollPane, 274,35,700,600));

        seeOrders.addActionListener(this);
        seeMenu.addActionListener(this);

        logOut.addActionListener(this);
    }

    JButton Button(JButton b, int x, int y, int w){
        b.setBounds(x, y, w, 40);
        b.setFont(new Font("Inter", Font.PLAIN,20));
        b.setForeground(new Color(85, 85, 85));
        b.setBackground(new Color(217, 217, 217));

        return b;
    }

    public JScrollPane scrollPane(JScrollPane sp, int x, int y, int w, int h){
        sp.setBounds(x, y, w, h);
        return sp;
    }

    public JTable infoTable(String[][] info, String[] columnNames){

        DefaultTableModel model = new DefaultTableModel(info, columnNames);
        if(Objects.equals(this.modeCheck, "admin")){
            this.mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            model.addTableModelListener(e -> {
                if (e.getType() == TableModelEvent.UPDATE){
                    int rowChanged = e.getLastRow();
                    CommandCast.convertToRequest(rowChanged, this);
                }
            });
            this.mainTable.setModel(model);
        }
        else {
            this.mainTable = new JTable(model){
                public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
                }
            };
            this.mainTable.setRowSelectionAllowed(false);
        }
        return this.mainTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        CommandCast.CmdReviewMain(cmd, this);
    }
}
