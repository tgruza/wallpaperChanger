import Controllers.MainController;
import Services.Service;
import Services.ServiceImpl;
import Views.Main;

import javax.swing.*;

public class MainWithUI {
    private MainController _controller;

    public static void main(String[] args) {
        Service service = new ServiceImpl();
        Main mainView = new Main(service);
        JFrame frame = new JFrame();

        MainController mainController = new MainController(service, mainView);

        mainController.show(frame);
    }
}
