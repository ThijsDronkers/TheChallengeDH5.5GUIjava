import javax.swing.*;
import java.awt.*;

public class BabbelLogin extends JFrame {

    public BabbelLogin() {
        setTitle("Babbel App - Inloggen");
        setSize(390, 844);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Gebruikersnaam:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Wachtwoord:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Inloggen");
        loginButton.addActionListener(e -> openMainScreen());

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private void openMainScreen() {
        JFrame mainFrame = new JFrame("Babbel App - Home");
        mainFrame.setSize(390, 844);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        JButton menuButton = new JButton("Menu");
        JButton profileButton = new JButton("Profiel");
        topBar.add(menuButton, BorderLayout.WEST);
        topBar.add(profileButton, BorderLayout.EAST);

        JButton babbelButton = new JButton("Babbel!");
        babbelButton.setFont(new Font("Arial", Font.BOLD, 24));

        // Menu venster
        JPopupMenu menu = new JPopupMenu();
        JMenuItem activiteitenItem = new JMenuItem("Activiteiten");
        JMenuItem huisdierenItem = new JMenuItem("Huisdieren");

        activiteitenItem.addActionListener(e -> openActiviteitenScreen(mainFrame));
        huisdierenItem.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Huisdieren scherm geopend!"));

        menu.add(activiteitenItem);
        menu.add(huisdierenItem);

        menuButton.addActionListener(e -> menu.show(menuButton, 0, menuButton.getHeight()));

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(babbelButton, BorderLayout.CENTER);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        this.dispose();
    }

    private void openActiviteitenScreen(JFrame parent) {
        JFrame actFrame = new JFrame("Activiteiten");
        actFrame.setSize(390, 844);
        actFrame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());

        JButton nieuwActBtn = new JButton("Activiteit aanmaken");
        nieuwActBtn.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(nieuwActBtn, BorderLayout.NORTH);

        // Voorbeeld bestaande activiteiten met tijd, datum en adres
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Fietsen in het park - 10:00 - 12-12-2025 - Parkweg 12, Den Haag");
        listModel.addElement("Samen koken - 18:00 - 12-12-2025 - Keukendreef 5, Den Haag");
        listModel.addElement("Filmavond - 20:00 - 12-12-2025 - Filmstraat 8, Den Haag");

        JList<String> activiteitenList = new JList<>(listModel);
        activiteitenList.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(activiteitenList);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Klik op 'Activiteit aanmaken'
        nieuwActBtn.addActionListener(e -> {
            JTextField titelField = new JTextField();
            JTextField beschrijvingField = new JTextField();
            JTextField datumField = new JTextField();
            JTextField locatieField = new JTextField();
            JTextField tijdField = new JTextField();

            Object[] message = {
                    "Titel:", titelField,
                    "Beschrijving:", beschrijvingField,
                    "Datum (dd-mm-jjjj):", datumField,
                    "Locatie:", locatieField,
                    "Tijd (uu:mm):", tijdField
            };

            int option = JOptionPane.showConfirmDialog(actFrame, message, "Nieuwe Activiteit", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String nieuwAct = titelField.getText() + " - " + tijdField.getText() + " - " + datumField.getText() + " - " + locatieField.getText();
                listModel.addElement(nieuwAct);
            }
        });

        actFrame.add(panel);
        actFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new BabbelLogin();
    }
}