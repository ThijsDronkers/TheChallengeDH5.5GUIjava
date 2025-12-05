import javax.swing.*;
import java.awt.*;

public class BabbelApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel container;

    // namen voor de pagina's in de CardLayout
    private static final String PAGE_LOGIN = "login";
    private static final String PAGE_MAIN = "main";
    private static final String PAGE_ACTI = "activiteiten";
    private static final String PAGE_MENU = "menu";
    private static final String PAGE_HUISDIER = "huisdier";

    public BabbelApp() {
        // Basis window
        setTitle("BabbelApp");
        setSize(320, 568);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // centreer in het scherm

        // Icon (zelfde bestand als in Python: images.png)
        // Zet images.png in dezelfde map als deze .java of geef een pad op.
        ImageIcon icon = new ImageIcon("images.png");
        setIconImage(icon.getImage());

        // Container met CardLayout (vergelijkbaar met tkraise + meerdere Frames)
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Pagina's maken
        JPanel pageLogin = createLoginPage();
        JPanel pageMain = createMainPage();
        JPanel pageActi = createActiPage();
        JPanel pageMenu = createMenuPage();
        JPanel pageHuisdier = createHuisdierPage();

        // Pagina's toevoegen aan container
        container.add(pageLogin, PAGE_LOGIN);
        container.add(pageMain, PAGE_MAIN);
        container.add(pageActi, PAGE_ACTI);
        container.add(pageMenu, PAGE_MENU);
        container.add(pageHuisdier, PAGE_HUISDIER);

        add(container);

        // start met login-pagina
        showPage(PAGE_LOGIN);
    }

    private void showPage(String pageName) {
        cardLayout.show(container, pageName);
    }

    // ================== LOGIN PAGINA ======================
    private JPanel createLoginPage() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x9DD9D9));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("BabbelApp");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField gebruikersnaam = new JTextField(15);
        gebruikersnaam.setMaximumSize(new Dimension(200, 30));
        gebruikersnaam.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField wachtwoord = new JPasswordField(15);
        wachtwoord.setMaximumSize(new Dimension(200, 30));
        wachtwoord.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton inlogbutton = new JButton("INLOGGEN");
        inlogbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inlogbutton.addActionListener(e -> showPage(PAGE_MAIN));

        panel.add(Box.createVerticalStrut(50));
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(gebruikersnaam);
        panel.add(Box.createVerticalStrut(10));
        panel.add(wachtwoord);
        panel.add(Box.createVerticalStrut(10));
        panel.add(inlogbutton);

        return panel;
    }

    // ================== HOME PAGINA ======================
    private JPanel createMainPage() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x9DD9D9));
        panel.setLayout(null); // we gebruiken absolute posities zoals jij met place

        // MENU-knop linksboven
        JButton menubutton = new JButton("MENU");
        menubutton.setFont(new Font("Arial", Font.PLAIN, 15));
        menubutton.setBounds(0, 0, 100, 30);
        menubutton.addActionListener(e -> showPage(PAGE_MENU));

        JLabel welkomtekst = new JLabel("Welkom in de BabbelApp", SwingConstants.CENTER);
        welkomtekst.setFont(new Font("Arial", Font.PLAIN, 15));
        welkomtekst.setOpaque(true);
        welkomtekst.setBackground(new Color(0xB4F8C8));
        welkomtekst.setBounds(10, 80, 280, 40);

        JButton babbelknop = new JButton("<html>KLIK HIER<br>OM TE<br>BABBELEN</html>");
        babbelknop.setFont(new Font("Arial", Font.PLAIN, 30));
        babbelknop.setBounds(30, 150, 250, 200);

        panel.add(menubutton);
        panel.add(welkomtekst);
        panel.add(babbelknop);

        return panel;
    }

    // ================== ACTIVITEITEN PAGINA ======================
    private JPanel createActiPage() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x9DD9D9));
        panel.setLayout(null);

        JButton menubutton = new JButton("MENU");
        menubutton.setFont(new Font("Arial", Font.PLAIN, 15));
        menubutton.setBounds(0, 0, 100, 30);
        menubutton.addActionListener(e -> showPage(PAGE_MENU));

        JLabel label2 = new JLabel("Activiteiten", SwingConstants.CENTER);
        label2.setFont(new Font("Arial", Font.PLAIN, 15));
        label2.setOpaque(true);
        label2.setBackground(new Color(0xB4F8C8));
        label2.setBounds(10, 80, 280, 40);

        JButton addButton = new JButton("+ activiteit toevoegen");
        addButton.setFont(new Font("Arial", Font.PLAIN, 15));
        addButton.setBounds(30, 160, 250, 40);

        panel.add(menubutton);
        panel.add(label2);
        panel.add(addButton);

        return panel;
    }

    // ================== MENU PAGINA ======================
    private JPanel createMenuPage() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x9DD9D9));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton homeMenu = new JButton("Home");
        homeMenu.setFont(new Font("Arial", Font.PLAIN, 15));
        homeMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeMenu.addActionListener(e -> showPage(PAGE_MAIN));

        JButton actiMenu = new JButton("Activiteiten");
        actiMenu.setFont(new Font("Arial", Font.PLAIN, 15));
        actiMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        actiMenu.addActionListener(e -> showPage(PAGE_ACTI));

        JButton huisdierMenu = new JButton("Mijn Huisdier");
        huisdierMenu.setFont(new Font("Arial", Font.PLAIN, 15));
        huisdierMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        huisdierMenu.addActionListener(e -> showPage(PAGE_HUISDIER));

        JButton logoutButton = new JButton("Uitloggen");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 15));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> showPage(PAGE_LOGIN));

        panel.add(Box.createVerticalStrut(50));
        panel.add(homeMenu);
        panel.add(Box.createVerticalStrut(10));
        panel.add(actiMenu);
        panel.add(Box.createVerticalStrut(10));
        panel.add(huisdierMenu);
        panel.add(Box.createVerticalStrut(150));
        panel.add(logoutButton);

        return panel;
    }

    // ================== HUISDIER PAGINA ======================
    private JPanel createHuisdierPage() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x9DD9D9));
        panel.setLayout(null);

        JButton menubutton = new JButton("MENU");
        menubutton.setFont(new Font("Arial", Font.PLAIN, 15));
        menubutton.setBounds(0, 0, 100, 30);
        menubutton.addActionListener(e -> showPage(PAGE_MENU));

        JLabel label3 = new JLabel("Mijn Huisdier", SwingConstants.CENTER);
        label3.setFont(new Font("Arial", Font.PLAIN, 15));
        label3.setOpaque(true);
        label3.setBackground(new Color(0xB4F8C8));
        label3.setBounds(10, 80, 280, 40);

        panel.add(menubutton);
        panel.add(label3);

        return panel;
    }

    // ================== MAIN ======================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BabbelApp app = new BabbelApp();
            app.setVisible(true);
        });
    }
}


