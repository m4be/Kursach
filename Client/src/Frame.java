import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Frame extends JFrame implements ActionListener {

    protected JButton view = new JButton();
    protected JButton fetch = new JButton();

    protected JButton submit = new JButton();
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

        Changer(view,10,10,300,50,"View Information");
        Changer(fetch,10,70,300,50,"Fetch Information");
        Changer(submit,10,130,300,50,"Fetch Information");
        this.add(view);
        this.add(fetch);
        this.add(submit);

        view.addActionListener(this);
        fetch.addActionListener(this);
        submit.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd){
            case  "View Information":
                new Sender("Penis");
            case "Fetch Information":
                new Sender("Pizda");
            case "Submit Information":
                new Sender("Boobs");
        }
    }
}
