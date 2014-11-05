import javax.swing.*;
import java.awt.*;

/**
 * Created by slnkv on 1/5/14.
 */
class MainFrame extends JFrame {
    public MainFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle("NWO13A14 on Mars");
        //изменять размер окна
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("static/robot.png");
        setIconImage(img);

        MenuComp ic = new MenuComp( this );
        add(ic);


    }

    public static final int DEFAULT_WIDTH = 1000;
    public static final int DEFAULT_HEIGHT = 750;
}