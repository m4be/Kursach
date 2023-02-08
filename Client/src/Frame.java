import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Frame extends JFrame implements ActionListener {

    protected JButton test = new JButton();
    protected JButton test2 = new JButton();
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
        this.setTitle("Kursach");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        Changer(test,10,10,200,200,"Bebra");
        Changer(test2,100,500,100,100,"test2");
        this.add(test);
        this.add(test2);

        test.addActionListener(this);
        test2.addActionListener(this);

        this.setVisible(true);



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == test){
            new Sender("Penis");
        }
        if(e.getSource() == test2){
            new Sender("Pizda");
        }
    }
}
