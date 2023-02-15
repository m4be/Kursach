import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {

    protected JButton view = new JButton();
    protected JButton fetch = new JButton();
    protected JButton submit = new JButton();

//private JPanel author = Authorization(); //
//private JPanel registr = Registration(); //

    private JPanel currentPanel;

    private JTextField name;
    private JTextField password;
    private JButton submitAuthorization;
    private JButton submitRegistration;

    void Changer(JButton b,int x, int y, int w, int h,String name){
        b.setBounds(x,y,w,h);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        b.setText(name);
//b.setBackground(Color.BLACK);
//b.setForeground(Color.GREEN);
    }

    Frame() {
        int width = 960;
        int height = 600;
        this.setTitle("Course_Work");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        Changer(view,10,70,300,50,"View Information");
        Changer(fetch,10,130,300,50,"Fetch Information");
        Changer(submit,10,190,300,50,"Fetch Information");
        this.add(view);
        this.add(fetch);
        this.add(submit);
        this.add(Authorization());

        view.addActionListener(this);
        fetch.addActionListener(this);
        submit.addActionListener(this);
        submitRegistration.addActionListener(this);
        submitAuthorization.addActionListener(this);

        this.setVisible(true);
    }

    private JPanel Authorization(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setBounds(320,10,252,120);

        name = new JTextField();
        name.setBounds(71,5,170,30);

        password = new JTextField();
        password.setBounds(71,40,170,30);

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(3,5,68,30);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(3,40,68,30);

        submitAuthorization = new JButton("Auth");
        submitAuthorization.setBounds(120,85,100,30);

        submitRegistration = new JButton("Register");
        submitRegistration.setBounds(10,85,100,30);

        panel.add(name);
        panel.add(password);
        panel.add(nameLabel);
        panel.add(passwordLabel);
        panel.add(submitAuthorization);
        panel.add(submitRegistration);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        String cmd = e.getActionCommand();
        switch (cmd){
            case "View Information":
                new Sender("Penis");
                break;
            case "Fetch Information":
                new Sender("Pizda");
                break;
            case "Submit Information":
                new Sender("Boobs");
                break;
            case "Auth":
                new Sender("Auth: " + name.getText()+ " " + password.getText());
                break;
            case "Register":
                new Sender("Register: " + name.getText()+ " " + password.getText());
                break;

//1 )Попробовать через 1 панель в которой будет менятся тип currentPanel
//2 ) Добавить аторизацию и регистрациюв одно окно чтобы не ебать мозги
        }
    }
}