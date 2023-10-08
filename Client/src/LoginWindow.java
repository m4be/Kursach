import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

public class LoginWindow extends Component implements ActionListener {
    static JFrame reg = new JFrame();
    JLabel progLabel = new JLabel("MenuControl");
    JLabel nLabel = new JLabel("Name:");
    JLabel pLabel = new JLabel("Password:");
    JTextField name = new JTextField();
    JPasswordField pass = new JPasswordField();
    JButton submitAuthorization = new JButton("Log in");
    JButton submitRegistration = new JButton("Sign up");
    ImageIcon logo = new ImageIcon(Objects.requireNonNull(LoginWindow.class.getResource("img/menulogo.png")));
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(LoginWindow.class.getResource("img/menuicon.png")));

    LoginWindow() {
        reg.setBackground(new Color(253, 253, 253));
        reg.setTitle("Authorization Window");
        reg.setSize(400,500);
        reg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reg.setResizable(false);
        reg.setLayout(null);
        reg.setVisible(true);
        reg.setIconImage(icon.getImage());
        submitAuthorization.addActionListener(this);
        submitRegistration.addActionListener(this);
        pass.setEchoChar('*');
        reg.add(Button(submitAuthorization, 35, 390, 150, 40));
        reg.add(Button(submitRegistration, 205, 390, 150, 40));
        reg.add(Field(name, 35, 190, 320, 40));
        reg.add(Field(pass, 35, 290, 320, 40));
        reg.add(Label(nLabel, 35, 150, 300, 40));
        reg.add(Label(pLabel, 35, 250, 300, 40));
        reg.add(picLabel(progLabel, 10, 35, 300, 128));
    }

    JButton Button(JButton b, int x, int y, int w, int h){
        b.setBounds(x, y, w, h);
        b.setFont(new Font("Inter", Font.PLAIN,20));
        b.setForeground(new Color(85, 85, 85));
        b.setBackground(new Color(217, 217, 217));

        return b;
    }
    JTextField Field(JTextField f, int x, int y, int w, int h){
        f.setBounds(x, y, w, h);
        f.setFont(new Font("Inter", Font.ITALIC,20));
        f.setForeground(new Color(85, 85, 85));
        f.setBackground(new Color(253, 253, 253));

        return f;
    }

    JLabel Label(JLabel l, int x, int y, int w, int h){
        l.setBounds(x, y, w, h);
        l.setFont(new Font("Inter", Font.BOLD, 20));
        l.setForeground(new Color(85, 85, 85));
        return l;
    }
    JLabel picLabel(JLabel l, int x, int y, int w, int h){
        l.setIcon(logo);
        l.setIconTextGap(-25);
        l.setBounds(x, y, w, h);
        l.setFont(new Font("Inter", Font.BOLD, 30));
        l.setForeground(new Color(85, 85, 85));
        return l;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String nm = name.getText();
        String ps = String.valueOf(pass.getPassword());
        name.setText("");
        pass.setText("");
        System.out.println(nm + " " + ps);
        if ((nm.matches("\\w+")) && (ps.matches("\\w+"))) {
            CommandCast.CmdReviewLogin(cmd, nm, ps, null);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Input must only contain letters and numbers!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
