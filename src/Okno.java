import java.awt.*;
import javax.swing.*;

public class Okno extends JFrame {
    public Okno() {
        {
            Pole pan = new Pole();
            Container cont = getContentPane();
            cont.add(pan);

            setTitle("Игра\"Морской бой\"");
            setBounds(0, 0, 900, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        }
    }
}