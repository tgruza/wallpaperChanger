package Views;

import Services.Service;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame {

    private JPanel panel1;
    private JButton setChanges;
    private JComboBox timeChooser;
    private JTextPane destinationDir;
    private JRadioButton installArtGruza;
    private JRadioButton changeDir;
    private JTextField setDestinationFolderTextField;
    private JTextArea massagesBox;
    private JLabel destDirText;
    private JButton minimizeToTray;

    public Main(Service service) {}

    public void setMinimizeToTray(ActionListener actionListener) {
        minimizeToTray.addActionListener(actionListener);
    }

    public void setChangesListener(ActionListener actionListener){
        setChanges.addActionListener(actionListener);
    }

    public void show(JFrame frame) {
        frame.setTitle("WallPaper Changer 1.0");
        File file = new File("");
        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath() + "\\src\\main\\resources\\2.jpg");
        frame.setIconImage(imageIcon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);

        frame.setSize(700, 400);
        frame.setVisible(true);
    }


    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getSetChanges() {
        return setChanges;
    }

    public JComboBox getTimeChooser() {
        return timeChooser;
    }

    public JTextPane getDestinationDir() {
        return destinationDir;
    }

    public JRadioButton getInstallArtGruza() {
        return installArtGruza;
    }

    public JRadioButton getChangeDir() {
        return changeDir;
    }

    public JTextField getSetDestinationFolderTextField() {
        return setDestinationFolderTextField;
    }

    public JTextArea getMassagesBox() {
        return massagesBox;
    }

    public JLabel getDestDirText() {
        return destDirText;
    }

    public JButton getMinimizeToTray() {
        return minimizeToTray;
    }
}
