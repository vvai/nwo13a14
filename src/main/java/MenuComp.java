import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by slnkv on 1/5/14.
 */
public class MenuComp extends JComponent {

    private Image image;
    private JFrame mainFrame;

    public MenuComp(JFrame mainFrame) {
        try {
            this.mainFrame = mainFrame;


            System.out.println("current-dir" + System.getProperty("user.dir"));
            File file = new File(System.getProperty("user.dir"));
            String[] directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });
            System.out.println(Arrays.toString(directories));

            URL resource = MenuComp.class.getResource("static/menu.png");
            image = ImageIO.read(resource);
            //image = ImageIO.read(new File("static/menu.png"));
            addMouseListener(new MouseHandler());
            addMouseMotionListener(new MouseMotionHandler());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        if (image == null) {
            System.out.println("empty!");
            return;
        }

        g.drawImage(image, 0, 0, null);
        //g.drawRect(370, 450, 200, 35);
    }

    private class MouseHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            Point point = event.getPoint();
            double x = point.getX();
            double y = point.getY();

            if( (x>370) && (x<570) && (y>450) && (y<485)) {
                mainFrame.setVisible(false);
                GameFrame gameFrame = new GameFrame();
                gameFrame.setVisible(true);
            }
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        public void mouseMoved(MouseEvent event) {
            Point point = event.getPoint();
            double x = point.getX();
            double y = point.getY();

            if( (x>370) && (x<570) && (y>450) && (y<485)) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }
        public void mouseDragged(MouseEvent event) {

        }
    }


    }
