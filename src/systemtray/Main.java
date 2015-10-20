/**
 * Created by dominikschafer on 19/10/15.
 */
package systemtray;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Main {

    public static String localPath;
    public static String c2d2aUrl;
    public static JFrame historyFrame;
    public static JFrame settingsFrame;
    public static PopupMenu popMenu;

    public static void main(String[] args) throws AWTException {
        historyFrame  = new JFrame("Historie");
        settingsFrame  = new JFrame("Einstellungen");
        localPath = "/Users/dominikschafer/Downloads/";
        c2d2aUrl = "http://www.google.de";



        popMenu= new PopupMenu();

        MenuItem close = new MenuItem("Schließen");
        close.addActionListener(new MenuActionListener());

        MenuItem settings = new MenuItem("Einstellungen");
        settings.addActionListener(new MenuActionListener());

        MenuItem showFolder = new MenuItem("Ordner anzeigen");
        showFolder.addActionListener(new MenuActionListener());

        MenuItem showBrowser = new MenuItem("Browser öffnen");
        showBrowser.addActionListener(new MenuActionListener());

        CheckboxMenuItem sync = new CheckboxMenuItem("Synchronsieren");
        sync.setState(true);
        sync.addActionListener(new MenuActionListener());

        popMenu.add(close);
        popMenu.addSeparator();
        popMenu.add(showFolder);
        popMenu.add(showBrowser);
        popMenu.add(settings);
        popMenu.addSeparator();
        popMenu.add(sync);

        BufferedImage im = null;
        try {
            im = ImageIO.read(Main.class.getResource("1445303429_icon-141-box-filled.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image img = im;
        TrayIcon trayIcon = new TrayIcon(img, "C2D2A Client", popMenu);
        trayIcon.addActionListener(new trayActionListener());
        trayIcon.addMouseListener(new trayMouseListener());
        SystemTray.getSystemTray().add(trayIcon);

    }

    public static class trayActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());

        }

    }

    public static class trayMouseListener implements MouseListener{
        public void mouseEntered(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            Point pos = e.getLocationOnScreen();
            openHistory(pos);
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            historyFrame.setVisible(false);
        }

    }

    public static class FrameFocusListener implements FocusListener{

        public void focusLost(FocusEvent e) {
            historyFrame.setVisible(false);
        }

        public void focusGained(FocusEvent e) {

        }


    }

    public static class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand() == "Schließen"){
                System.exit(0);
            }

            if(e.getActionCommand() == "Einstellungen"){
                openSettings();

            }

            if(e.getActionCommand() == "Synchronsieren"){


            }

            if(e.getActionCommand() == "Ordner anzeigen"){
                try {
                    Runtime.getRuntime().exec("open " +  localPath);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            if(e.getActionCommand() == "Browser öffnen"){
                try {
                    Runtime.getRuntime().exec("open " + c2d2aUrl);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        }
    }

    public static void openHistory(Point pos){
        if(historyFrame.isVisible() == false) {

            int width = 200;
            int height = 200;

            Rectangle screen = getScreenBoundsAt(pos);
            if (pos.x + width > screen.x + screen.width) {
                pos.x = screen.x + screen.width - width;
            }
            if (pos.x < screen.x) {
                pos.x = screen.x;
            }

            if (pos.y + height > screen.y + screen.height) {
                pos.y = screen.y + screen.height - height;
            }
            if (pos.y < screen.y) {
                pos.y = screen.y;
            }

            JLabel topLabel = new JLabel("  Letzte Änderungen:");
            topLabel.setVerticalAlignment(JLabel.TOP);
            historyFrame.add(topLabel);

            historyFrame.pack();

            historyFrame.addFocusListener(new FrameFocusListener());
            historyFrame.requestFocus();
            historyFrame.setSize(width, height);
            historyFrame.setLocation(pos.x + 150, pos.y);
            historyFrame.setResizable(false);
            if(historyFrame.isUndecorated() == false){
                historyFrame.setUndecorated(true);
            }
            historyFrame.setVisible(true);
        }else{
            historyFrame.setVisible(false);
        }

    }

    public static void openSettings(){

        //settingsFrame.pack();
        settingsFrame.add(new Label("Test"));
        settingsFrame.setVisible(true);
        settingsFrame.setSize(600,600);
        settingsFrame.setLocation(100,100);

    }

    public static Rectangle getScreenBoundsAt(Point pos) {
        GraphicsDevice gd = getGraphicsDeviceAt(pos);
        Rectangle bounds = null;

        if (gd != null) {
            bounds = gd.getDefaultConfiguration().getBounds();
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());

            bounds.x += insets.left;
            bounds.y += insets.top;
            bounds.width -= (insets.left + insets.right);
            bounds.height -= (insets.top + insets.bottom);
        }
        return bounds;
    }

    public static GraphicsDevice getGraphicsDeviceAt(Point pos) {
        GraphicsDevice device = null;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice lstGDs[] = ge.getScreenDevices();

        ArrayList<GraphicsDevice> lstDevices = new ArrayList<GraphicsDevice>(lstGDs.length);

        for (GraphicsDevice gd : lstGDs) {
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            Rectangle screenBounds = gc.getBounds();

            if (screenBounds.contains(pos)) {
                lstDevices.add(gd);
            }
        }

        if (lstDevices.size() == 1) {
            device = lstDevices.get(0);
        }
        return device;
    }
}