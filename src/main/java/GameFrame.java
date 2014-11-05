import javax.swing.*;
import java.awt.*;
import logic.pathfinder.*;

/**
 * Created by slnkv on 1/5/14.
 */
public class GameFrame extends JFrame {

    public static final int SIZE_FIELD = 9;
    private Field field;

    public GameFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle("Autopilot");
        //изменять размер окна
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("static/robot.png");
        setIconImage(img);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        field = new Field(SIZE_FIELD, SIZE_FIELD);
        DrawMars drawMars = new DrawMars(field);
        add(drawMars);

        Control control = new Control(field, drawMars);
        add(control);

    }

    public static final int DEFAULT_WIDTH = 950;
    public static final int DEFAULT_HEIGHT = 750;
}
