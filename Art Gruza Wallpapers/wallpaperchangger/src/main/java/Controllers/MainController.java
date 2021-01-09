package Controllers;

import Services.Service;
import Views.Main;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainController extends TimerTask {

    private JFrame frame;
    private Service service;
    private Views.Main mainView;
    private String destinationDir;
    private SystemTray tray;
    private TrayIcon trayIcon;
    private Timer timer = new Timer();
    private static TimerTask timerTask;
    private static int i = 0;

    public MainController(Service service, Main mainView) {
        this.service = service;
        this.mainView = mainView;

        mainView.setChangesListener(new setChanges());
        mainView.setMinimizeToTray(new setMinimizeToTray());
    }

    class setMinimizeToTray implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!SystemTray.isSupported()) {
                System.out.println("SystemTray is not supported");
                return;
            }
            mainView.setVisible(false);
            if (trayIcon == null) {
                final PopupMenu popup = new PopupMenu();

                File file = new File("");
                ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath() + "\\src\\main\\resources\\icon.png");

                trayIcon = new TrayIcon(imageIcon.getImage());
                tray = SystemTray.getSystemTray();

                // Create a pop-up menu components
                MenuItem aboutItem = new MenuItem("About");
                aboutItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,
                                "Wallpaper Changer App" +
                                        "\nVersion: 1.0" +
                                        "\nAuthor: Tomasz Gruza" +
                                        "\nPainted by: Grzegorz Gruza" +
                                        "\nhttps://www.facebook.com/Art-Gruza-278843425500629");
                    }
                });
                MenuItem exitItem = new MenuItem("Exit");
                exitItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tray.remove(trayIcon);
                        System.exit(2);
                    }
                });

                //Add components to pop-up menu
                popup.add(aboutItem);
                popup.addSeparator();
                popup.add(exitItem);
                trayIcon.setPopupMenu(popup);
                trayIcon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainView.setVisible(true);
                    }
                });

                try {
                    tray.add(trayIcon);
                } catch (AWTException e1) {
                    mainView.getMassagesBox().setText("TrayIcon could not be added.");
                }
            }
        }
    }

    class setChanges implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                mainView.getMassagesBox().setText("");
                if (mainView.getInstallArtGruza().isSelected()) {
                    String destination = mainView.getSetDestinationFolderTextField().getText();
                    String password = "bardzotrudnedoodgadnieciahaslo13243546";
                    File file = new File("");
                    String source = file.getAbsolutePath().replace("wallpaperchangger", "ArtGruzaPictures.zip");
                    File artGruzaFile = new File(source);

                    installArtGruza(artGruzaFile, source, destination, password);

                    destinationDir = destination + "\\ArtGruzaPictures";
                    setWallpaperWithTimer();

                }

                if (mainView.getChangeDir().isSelected()) {
                    destinationDir = mainView.getSetDestinationFolderTextField().getText();
                    setWallpaperWithTimer();
                }

            } catch (ZipException ex) {
                mainView.getMassagesBox().setText("Change destination directory");
            }
        }
    }

    private void setWallpaperWithTimer() {
        long counter = Integer.parseInt(mainView.getTimeChooser().getSelectedItem().toString()) * 60000;
        if (timerTask != null)  {
            timerTask.cancel();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (service.getAllImagePaths(destinationDir).isEmpty()) {
                        mainView.getMassagesBox().setText("Folder is empty, please change destination directory and press Start.");
                        timerTask.cancel();
                    }

                    String imgPath;
                    if (i + 1 >= service.getAllImagePaths(destinationDir).size()) {
                        i = 0;
                    }
                    imgPath = service.getAllImagePaths(destinationDir)
                            .get(i)
                            .getAbsolutePath();
                    service.setWallPaper(imgPath);
                    mainView.getMassagesBox().setText("Wallpaper has been changed. AMISZ KRÓLEM!");
                    i++;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, counter);
    }  //Method takes counter from combobox (always != null), closes previous timerTask, checks if folder is empty and set wallpaper with timer

    private void installArtGruza(File file, String source, String destination, String password) throws ZipException {
        if (file.exists()) {
            ZipFile zipFile = new ZipFile(source);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
        } else {
            mainView.getMassagesBox().setText("Folder doesn't exist. Please change destination folder");
        }
    } //Method unzipping file with ArtGruza images to user directory

    public void show(JFrame frame) {
        mainView.show(frame);

    }

    @Override
    public void run() {

    }
}

