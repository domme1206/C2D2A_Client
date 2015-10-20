/**
 * Created by dominikschafer on 19/10/15.
 */
package systemtray;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;


public class Main {

    public static void main(String[] args) throws AWTException {
        PopupMenu popMenu= new PopupMenu();

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
        popMenu.add(settings);
        popMenu.add(showFolder);
        popMenu.add(showBrowser);
        popMenu.add(sync);

        Image img = Toolkit.getDefaultToolkit().getImage("/Users/dominikschafer/Downloads/1445303429_icon-141-box-filled.png");
        TrayIcon trayIcon = new TrayIcon(img, "C2D2A Client", popMenu);
        SystemTray.getSystemTray().add(trayIcon);
    }

    public static class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());

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
                    Runtime.getRuntime().exec("open /Users/dominikschafer/Downloads/");
                } catch (IOException e1) {
                    System.out.println(e1);
                    e1.printStackTrace();
                }

            }

            if(e.getActionCommand() == "Browser öffnen"){
                try {
                    Runtime.getRuntime().exec("open http://www.dropbox.com");
                } catch (IOException e1) {
                    System.out.println(e1);
                    e1.printStackTrace();
                }

            }

        }
    }


    public static void openSettings(){

        JFrame settingsFrame  = new JFrame("Einstellungen");
        settingsFrame.pack();
        settingsFrame.add(new Label("Test"));
        settingsFrame.setVisible(true);
        settingsFrame.setSize(600,600);
        settingsFrame.setLocation(100,100);

    }
}