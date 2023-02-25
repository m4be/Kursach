import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Frame extends JFrame implements ActionListener {



    JTable table;
    DefaultTableModel model;

    private JPanel currentPanel;
    private JTextField name;
    private JTextField password;
    private JButton submitAuthorization;
    private JButton submitRegistration;
    private JButton submitAdmin;
    private JButton submitExit;

    private boolean authorized;

    Frame() {
        int width = 720;
        int height = 600;
        this.setTitle("Course_Work");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);


        this.add(Authorization());
        this.add(Menu());
        this.add(MainPanel());
        this.add(SubPanel());
        this.add(RefreshButton());

        this.setVisible(true);

        authorized = false;
    }


    private JPanel Authorization(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setBounds(50,10,400,120);

        name = new JTextField();
        name.setBounds(150,5,220,30);

        password = new JTextField();
        password.setBounds(150,40,220,30);

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(25,5,68,30);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(25,40,68,30);

        submitAuthorization = new JButton("Auth");
        submitAuthorization.setBounds(220,85,150,30);

        submitRegistration = new JButton("Register");
        submitRegistration.setBounds(25,85,150,30);

        submitRegistration.addActionListener(this);
        submitAuthorization.addActionListener(this);

        panel.add(name);
        panel.add(password);
        panel.add(nameLabel);
        panel.add(passwordLabel);
        panel.add(submitAuthorization);
        panel.add(submitRegistration);
        return panel;
    }

    private JPanel Menu(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setBounds(500,10,150,120);

        submitAdmin = new JButton("Admin");
        submitAdmin.setBounds(10,20,130,40);

        submitExit = new JButton("Exit");
        submitExit.setBounds(10,70,130,40);

        submitExit.addActionListener(this);
        submitAdmin.addActionListener(this);

        panel.add(submitAdmin);
        panel.add(submitExit);

        return panel;
    }

    private JPanel MainPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setBounds(50,160,400,380);

/*        String[] columnNames = {"First Name", "Last Name", "Action"};
        Object[][] data =
                {
                        {"Homer", "Simpson", "delete Homer"},
                        {"Madge", "Simpson", "delete Madge"},
                        {"Bart",  "Simpson", "delete Bart"},
                        {"Lisa",  "Simpson", "delete Lisa"},
                };
*/
        model = new DefaultTableModel();
        table = new JTable( model );

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Amount");
        model.addColumn("Action");


        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        scrollPane.setBounds(0,0,400,380);


        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );

                System.out.println(modelRow);

                //model.setValueAt("1",modelRow,1);

                ((DefaultTableModel)table.getModel()).removeRow(modelRow);
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, delete, 3);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        panel.add(scrollPane);



        return panel;
    }

    private JPanel SubPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setBounds(460,160,210,380);



        return panel;
    }

    private JButton RefreshButton(){
        JButton button = new JButton("Refresh");
        button.setLayout(null);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        button.setBounds(390,135,60,20);
        button.addActionListener(this);

        return button;
    }

    void getServerMessage(String text){
        String[] str = text.split(" ");
        switch (str[0]){
            case "201":
                authorized = true;
                refresh();
                break;


            case "801":
                System.out.println("\n dlina:" + (str.length - 1) + "\n");
                model.setRowCount(0); //Очистка таблицы
                for(int i = 0; i < (((str.length) - 1) / 3);i++)
                    model.addRow(new Object[]{str[1+(3*i)],str[2+(3*i)],str[3+(3*i)],"take"});



        }



    }

    void refresh(){
        if(authorized)
            new Sender("800", this);
        else{
            System.out.println("НЕ авторизован");
        }
        //else Выкинуть окно с ошибкой
    }

    void exit(){
        authorized = false;
        model.setRowCount(0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        String cmd = e.getActionCommand();
        switch (cmd){
            case "Auth":
                new Sender("200 " + name.getText() + " " + password.getText(),this);
                break;
            case "Register":
                new Sender("300 " + name.getText()+ " " + password.getText(),this);
                break;
            case "Admin":
                new Sender("400 " + name.getText()+ " " + password.getText(),this);
                break;
            case "Exit":
                new Sender("500 " + name.getText()+ " " + password.getText(),this);
                exit();
                break;
            case "Refresh":
                refresh();
                break;

        }
    }


    /*
    Список кодов:
    200 - Авторизация    201 - Успешно
    300 - Регистрация    301 - Успешно
    400 - Админ          401 - Успешно
    500 - Выход из учетки

    Если удалось зайти то глобальная переменная Authorized == true

    (И наверное переменные для имени (чтобы зафиксировать учетку пользователя (Ато понапишет говна )))

    После выхода == false

    Если переменная False => на самой форме выскакивает ошибка при попытке отправить запрос в бд (Таблица)

    Если Админ - то подгружается немного другие данные (Данные о заказах ВСЕХ пользователей)
    Admin == true

    Сделать файл ДЕкодер (ответственный за преобразование данных)

    101 - Авторизация не удалась (Нет такой учетки)
    102 - регистрация не удалась (Такая учетка уже есть)
    103 - Вход в админку не удался (Нет такой учетки)

    Для того чтобы указывать пользователю правильные ошибки

    После АВТОРИЗАЦИИ у пользователя высвечивается список всей техники(у Админа тоже)
    У пользователя на панели справа ЕГО заказы
    У Админа ЗАКАЗЫ ВСЕХ ПОЛЬЗОВАТЕЛЕЙ

    + Добавить кнопку для удаления по аналогии с основной таблицей

    600 - Пользователь запрашивает технику (ID)
    700 - Пользователь Удаляет технику их своих заказов

    601 - Не удалось заказать технику(Нет на складе <1)



    800 - Перезагрузка данных (ЗАпрос серверу)
    801 - Отправка данных (успех) (ответ пользователю)


    (Отпрака актуального списка машин (т.е. из БД))
    + Список заказаных/админ машин

    Сервер присылает либо ошибку
    Либо полный список для указанного пользователя

    КНОПКА == if Admin then кнопка на главной панели пропадает

    !!!!! После каждого нажатия на кнопку дополнительно обновляется вся форма для показа корректной информации

    */
}