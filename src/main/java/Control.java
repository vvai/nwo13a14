import logic.pathfinder.Field;
import logic.pathfinder.PathExistanceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by slnkv on 1/7/14.
 */
public class Control  extends JComponent {

    private Field field;
    private DrawMars drawMars;
    private boolean noLife = false;

    public Control(Field field, DrawMars drawMars) {
        this.field = field;
        this.drawMars = drawMars;
        setSize(180, 750);
        setLocation(720, 0);
        setBackground(Color.red);
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    private Rectangle clean = new Rectangle(25, 45, 150, 60);
    private Rectangle random = new Rectangle(25, 125, 150, 60);
    private Rectangle run = new Rectangle(25, 220, 150, 60);


    public void paintComponent(Graphics g) {
        setLocation(720, 0);
        Graphics2D g2 = (Graphics2D) g;

        try {
            URL resource = MenuComp.class.getResource("static/control.png");
            Image image = ImageIO.read(resource);
            // Image image = ImageIO.read(new File("static/control.png"));
            g.drawImage(image, 0, 0, null);
        } catch (IOException e) {

        }

        //g2.draw(clean);
        //g2.draw(random);
        //g2.draw(run);

    }

    private class MouseHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            Point point = event.getPoint();
            double x = point.getX();
            double y = point.getY();
            if (run.contains(point)) {
                try {
                    //field.findPath();
                    field.findWay();
                    if (field.pathExists() == PathExistanceType.NOPATH ) {
                       drawMars.noLife = true;
                    } else {
                        drawMars.noLife = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (random.contains(point)) {
                field.generateField();
                drawMars.noLife = false;
            } else if (clean.contains(point)) {
                field.cleanField();
                drawMars.noLife = false;
            }
            drawMars.repaint();


        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        public void mouseMoved(MouseEvent event) {
            Point point = event.getPoint();
            double x = point.getX();
            double y = point.getY();
            if( clean.contains(point) || random.contains(point) || run.contains(point) ) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }
        public void mouseDragged(MouseEvent event) {

        }
    }

}
