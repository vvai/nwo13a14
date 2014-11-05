import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.net.URL;

import logic.pathfinder.*;

/**
 * Created by slnkv on 1/5/14.
 */
public class DrawMars extends JComponent {

    private Field field;

    public boolean noLife = false;

    public static int SIZE = 80;
    public static int COUNT = 9;

    private static Image robot;
    private static Image wall;
    private static Image life;
    private static Image empty;
    private static Image horizontal;
    private static Image vertical;
    private static Image leftup;
    private static Image leftdown;
    private static Image rightup;
    private static Image rightdown;
    private static Image noLifeIm;


    DrawMars(Field field) {
        setSize(720, 720);
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
        this.field = field;
        try {

            robot = ImageIO.read(DrawMars.class.getResource("static/robot.png"));
            empty = ImageIO.read(DrawMars.class.getResource("static/empty.png"));
            wall = ImageIO.read(DrawMars.class.getResource("static/wall.png"));
            life = ImageIO.read(DrawMars.class.getResource("static/life.png"));

            horizontal = ImageIO.read(DrawMars.class.getResource("static/horizontal.png"));
            vertical = ImageIO.read(DrawMars.class.getResource("static/vertical.png"));
            leftdown = ImageIO.read(DrawMars.class.getResource("static/leftdown.png"));
            leftup = ImageIO.read(DrawMars.class.getResource("static/leftup.png"));
            rightup = ImageIO.read(DrawMars.class.getResource("static/rightup.png"));
            rightdown = ImageIO.read(DrawMars.class.getResource("static/rightdown.png"));
            noLifeIm = ImageIO.read(DrawMars.class.getResource("static/notLife.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        field.generateField();
        try {
            field.findPath();
        } catch (WrongMapException e) {
            e.printStackTrace();
        }
        */
    }

    private Image getImage(CellType cellType) {
        if (cellType == CellType.EMPTY) return empty;
        else if (cellType == CellType.EXIT) return life;
        else if (cellType == CellType.HERO) return robot;
        else if (cellType == CellType.WALL) return wall;
        else if (cellType == CellType.HORIZONTAL) return horizontal;
        else if (cellType == CellType.VERTICAL) return vertical;
        else if (cellType == CellType.LEFTDOWN) return leftdown;
        else if (cellType == CellType.LEFTUP) return leftup;
        else if (cellType == CellType.RIGHTDOWN) return rightdown;
        else if (cellType == CellType.RIGHTUP) return rightup;

        return empty;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

            for (int i = 0; i < COUNT; i++) {
                for (int j = 0; j < COUNT ; j++) {
                    CellType type = field.getCellType(i, j);
                    g2.drawImage(getImage(type),i * SIZE, j * SIZE, null);


                }
            }
        if (noLife == true) {
            g2.drawImage(noLifeIm, 200, 200, null);
        }
    }

    //public static final int DEFAULT_SIZE = 700;

    private class MouseHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            Point point = event.getPoint();
            double x = point.getX();
            double y = point.getY();

            int xo = (int) ( x / SIZE );
            int yo = (int) ( y / SIZE );
            //System.out.println(xo + " : " + yo);
            logic.struct.Point zeroPoint = new logic.struct.Point(-1,-1);

            if (field.findHero().equals(zeroPoint)) {
                field.setCell(CellType.HERO, xo, yo);
            } else if (field.findExit().equals(zeroPoint)) {
                field.setCell(CellType.EXIT, xo, yo);
            } else {
                field.setCell(CellType.WALL, xo, yo);
            }
            repaint();
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        public void mouseMoved(MouseEvent event) {

        }
        public void mouseDragged(MouseEvent event) {

        }
    }

}
