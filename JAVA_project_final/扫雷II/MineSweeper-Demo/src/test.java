import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class test extends JFrame implements ActionListener {
    private JFrame fr;
    private JButton a1;
    private JButton a2;
    private JButton a3;
    private JPanel pa;
    private JLabel la;
    public test() {
        fr.setSize(500,500);
        pa = new JPanel();
        la = new JLabel("请选择难度");
        a1 = new JButton("简单");
        a2 = new JButton("普通");
        a3 = new JButton("困难");
        ButtonGroup group = new ButtonGroup();
        group.add(a1);
        group.add(a2);
        group.add(a3);
        pa.add(la);
        pa.add(a1);
        pa.add(a2);
        pa.add(a3);
        fr.add(pa);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        a1.addActionListener(this);
        a2.addActionListener(this);
        a3.addActionListener(this);
        fr.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==a1||e.getSource()==a2||e.getSource()==a3) {
            fr.dispose();
        }

    }
}

