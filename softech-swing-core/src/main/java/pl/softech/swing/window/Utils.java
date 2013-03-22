package pl.softech.swing.window;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Sławomir Śledź
 * @since 1.0
 */
public class Utils {

    public static JFrame getJFrame(JPanel panel, String title, int width,
            int height) {

        JFrame f = new JFrame(title);
        f.setContentPane(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(width, height));
        return f;
    }
}
