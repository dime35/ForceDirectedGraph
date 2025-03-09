import java.awt.*;
import javax.swing.*;


public class Driver implements Runnable {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 900;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Driver());

    }
    @Override
    public void run() {
        initGUI();
    }

    public void initGUI() {

        SimPanel panel = new SimPanel();
        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(Box.createHorizontalGlue());


        box.add(panel);
        box.add(Box.createHorizontalGlue());

        JFrame frame = new JFrame("Force Directed Graph");

        frame.setBackground(Color.BLACK);
        frame.add(box);
        frame.setSize(WIDTH, HEIGHT);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocusInWindow();



    }



}

