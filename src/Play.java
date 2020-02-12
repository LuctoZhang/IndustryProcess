import javax.swing.*;
import java.awt.*;

public class Play extends JFrame{
    public static void main(String[] args) {
                Window window = new Window();
                //window.setSize(1880,1000);
                //window.setLocation(20,20);
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                sizeWindowOnScreen(window,0.9,0.9);
                window.setVisible(true);
                window.setLayout(new BorderLayout());
    }
    /**
     *
     * @param window
     * @param widthRate 宽度比例
     * @param heightRate 高度比例
     */
    private static void sizeWindowOnScreen(Window window, double widthRate, double heightRate)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(new Dimension((int)(screenSize.width * widthRate),(int)(screenSize.height *heightRate)));
        window.setLocation((int)(screenSize.width * (1-widthRate)/2),(int)(screenSize.height *(1-heightRate)/2));
    }

}
