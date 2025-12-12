import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * BabbelApp - volledige werkende code
 */
public class BabbelApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel container;

    // pagina namen
    private static final String PAGE_LOGIN = "login";
    private static final String PAGE_MAIN = "main";
    private static final String PAGE_ACTI = "activiteiten";
    private static final String PAGE_MENU = "menu";
    private static final String PAGE_HUISDIER = "huisdier";
    private static final String PAGE_PROFIEL = "profiel";
    private static final String PAGE_SETTINGS = "settings";

    // user profiel data
    private ImageIcon profielfoto = null;
    private String volledigeNaam = "Onbekende gebruiker";
    private String email = "Onbekend";
    private int totaalActiviteiten = 0;

    private JLabel profielfotoLabel;
    private JLabel naamLabel;
    private JLabel emailLabel;
    private JLabel totaalActLabel;

    // instellingen
    private boolean darkMode = false;
    private int letterGrootte = 14;
    private boolean notificaties = true;

    // activiteitenlijst
    private JPanel activiteitenLijstPanel;

    public BabbelApp() {
        setTitle("BabbelApp");
        setSize(360, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // maak pagina's
        container.add(createLoginPage(), PAGE_LOGIN);
        container.add(createMainPage(), PAGE_MAIN);
        container.add(createActiPage(), PAGE_ACTI);
        container.add(createMenuPage(), PAGE_MENU);
        container.add(createHuisdierPage(), PAGE_HUISDIER);
        container.add(createProfilePage(), PAGE_PROFIEL);
        container.add(createSettingsPage(), PAGE_SETTINGS);

        add(container);
        showPage(PAGE_LOGIN);
    }

    private void showPage(String pageName) {
        updateProfilePage();
        cardLayout.show(container, pageName);
    }

    // ---------------- LOGIN ----------------
    private JPanel createLoginPage() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x9DD9D9));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(60, 30, 60, 30));

        JLabel label = new JLabel("BabbelApp");
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField gebruikersnaam = new JTextField();
        gebruikersnaam.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        gebruikersnaam.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPasswordField wachtwoord = new JPasswordField();
        wachtwoord.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        wachtwoord.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JButton inlogbutton = new JButton("🔓 INLOGGEN");
        inlogbutton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inlogbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inlogbutton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        inlogbutton.addActionListener(e -> {
            String naam = gebruikersnaam.getText().trim();
            if (!naam.isEmpty()) volledigeNaam = naam;
            email = (gebruikersnaam.getText().trim().isEmpty() ? "onbekend@mail.nl" : gebruikersnaam.getText().trim() + "@mail.com");
            showPage(PAGE_MAIN);
        });

        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Gebruikersnaam:"));
        panel.add(gebruikersnaam);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Wachtwoord:"));
        panel.add(wachtwoord);
        panel.add(Box.createVerticalStrut(20));
        panel.add(inlogbutton);

        return panel;
    }

    // ---------------- MAIN ----------------
    private JPanel createMainPage() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(0xE8F7F7));

        JButton menubutton = new JButton("☰ MENU");
        menubutton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        menubutton.setBounds(10, 10, 100, 36);
        menubutton.addActionListener(e -> showPage(PAGE_MENU));
        panel.add(menubutton);

        JButton profielKnop = new JButton("👤");
        profielKnop.setToolTipText("Profiel");
        profielKnop.setFont(new Font("SansSerif", Font.PLAIN, 20));
        profielKnop.setBounds(300, 10, 44, 36);
        profielKnop.addActionListener(e -> showPage(PAGE_PROFIEL));
        panel.add(profielKnop);

        JLabel welkomtekst = new JLabel("Welkom in de BabbelApp", SwingConstants.CENTER);
        welkomtekst.setFont(new Font("SansSerif", Font.BOLD, 16));
        welkomtekst.setOpaque(true);
        welkomtekst.setBackground(new Color(0xB4F8C8));
        welkomtekst.setBounds(20, 70, 320, 42);
        panel.add(welkomtekst);

        JButton babbelknop = new JButton("<html><center>💬<br>KLIK HIER<br>OM TE<br>BABBELEN</center></html>");
        babbelknop.setFont(new Font("SansSerif", Font.PLAIN, 20));
        babbelknop.setBounds(40, 140, 280, 220);
        panel.add(babbelknop);

        return panel;
    }

    // ---------------- ACTIVITEITEN PAGE ----------------
    private JPanel createActiPage() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(0xE8F7F7));

        JButton menubutton = new JButton("☰ MENU");
        menubutton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        menubutton.setBounds(10, 10, 100, 36);
        menubutton.addActionListener(e -> showPage(PAGE_MENU));
        panel.add(menubutton);

        JLabel header = new JLabel("📅 Activiteiten", SwingConstants.LEFT);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setBounds(20, 60, 260, 30);
        panel.add(header);

        JButton addButton = new JButton("➕ Activiteit toevoegen");
        addButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        addButton.setBounds(200, 60, 160, 30);
        addButton.addActionListener(e -> openActivityForm());
        panel.add(addButton);

        activiteitenLijstPanel = new JPanel();
        activiteitenLijstPanel.setLayout(new BoxLayout(activiteitenLijstPanel, BoxLayout.Y_AXIS));
        activiteitenLijstPanel.setBackground(new Color(0xE8F7F7));

        JScrollPane lijstScroll = new JScrollPane(activiteitenLijstPanel);
        lijstScroll.setBounds(12, 100, 340, 540);
        lijstScroll.setBorder(null);
        lijstScroll.getVerticalScrollBar().setUnitIncrement(12);

        panel.add(lijstScroll);
        return panel;
    }

    private void openActivityForm() {
        JTextField titelField = new JTextField();
        JTextField beschrijvingField = new JTextField();
        JTextField datumField = new JTextField();
        JTextField locatieField = new JTextField();
        JTextField tijdField = new JTextField();
        JTextField omvangField = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Titel:")); form.add(titelField);
        form.add(new JLabel("Beschrijving:")); form.add(beschrijvingField);
        form.add(new JLabel("Datum (dd-mm-jjjj):")); form.add(datumField);
        form.add(new JLabel("Locatie:")); form.add(locatieField);
        form.add(new JLabel("Tijd:")); form.add(tijdField);
        form.add(new JLabel("Omvang:")); form.add(omvangField);

        int result = JOptionPane.showConfirmDialog(this, form, "Nieuwe Activiteit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            addActivity(
                    safeText(titelField.getText(), "Geen titel"),
                    safeText(beschrijvingField.getText(), ""),
                    safeText(datumField.getText(), ""),
                    safeText(locatieField.getText(), ""),
                    safeText(tijdField.getText(), ""),
                    safeText(omvangField.getText(), "")
            );
        }
    }

    private String safeText(String s, String fallback) {
        if (s == null) return fallback;
        String t = s.trim();
        return t.isEmpty() ? fallback : t;
    }

    private void addActivity(String titel, String beschrijving, String datum, String locatie, String tijd, String omvang) {
        RoundedPanel card = new RoundedPanel(16, new Color(0xFFFFFF));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(12, 12, 12, 12));
        card.setMaximumSize(new Dimension(320, 140));
        card.setPreferredSize(new Dimension(320, 140));

        JLabel titleLabel = new JLabel(titel);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        String descHtml = beschrijving.isEmpty() ? "" : "<div style='margin-top:6px;'>" + escapeHtml(beschrijving) + "</div>";
        JLabel descLabel = new JLabel("<html>" + descHtml + "</html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(titleLabel, BorderLayout.NORTH);
        center.add(descLabel, BorderLayout.CENTER);

        String infoText = "<html>" +
                (datum.isEmpty() ? "" : "📅 " + escapeHtml(datum) + " &nbsp;&nbsp;") +
                (tijd.isEmpty() ? "" : "⏰ " + escapeHtml(tijd) + " &nbsp;&nbsp;") +
                (locatie.isEmpty() ? "" : "📍 " + escapeHtml(locatie) + " &nbsp;&nbsp;") +
                (omvang.isEmpty() ? "" : "👥 " + escapeHtml(omvang)) +
                "</html>";
        JLabel infoLabel = new JLabel(infoText);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        card.add(center, BorderLayout.CENTER);
        card.add(infoLabel, BorderLayout.SOUTH);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(10));

        activiteitenLijstPanel.add(wrapper);
        activiteitenLijstPanel.revalidate();
        activiteitenLijstPanel.repaint();

        totaalActiviteiten++;
        updateProfilePage();
    }

    private String escapeHtml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    // ---------------- MENU ----------------
    private JPanel createMenuPage() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0xE8F7F7));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(40, 30, 40, 30));

        JButton homeMenu = new JButton("🏠 Home");
        homeMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        homeMenu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        homeMenu.addActionListener(e -> showPage(PAGE_MAIN));

        JButton actiMenu = new JButton("📅 Activiteiten");
        actiMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        actiMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        actiMenu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        actiMenu.addActionListener(e -> showPage(PAGE_ACTI));

        JButton huisdierMenu = new JButton("🐶 Mijn Huisdier");
        huisdierMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        huisdierMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        huisdierMenu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        huisdierMenu.addActionListener(e -> showPage(PAGE_HUISDIER));

        JButton profielMenu = new JButton("👤 Profiel");
        profielMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        profielMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        profielMenu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        profielMenu.addActionListener(e -> showPage(PAGE_PROFIEL));

        JButton instellingenMenu = new JButton("⚙️ Instellingen");
        instellingenMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        instellingenMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        instellingenMenu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        instellingenMenu.addActionListener(e -> showPage(PAGE_SETTINGS));

        JButton logoutButton = new JButton("🚪 Uitloggen");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        logoutButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        logoutButton.addActionListener(e -> showPage(PAGE_LOGIN));

        panel.add(homeMenu);
        panel.add(Box.createVerticalStrut(8));
        panel.add(actiMenu);
        panel.add(Box.createVerticalStrut(8));
        panel.add(huisdierMenu);
        panel.add(Box.createVerticalStrut(8));
        panel.add(profielMenu);
        panel.add(Box.createVerticalStrut(8));
        panel.add(instellingenMenu);
        panel.add(Box.createVerticalStrut(20));
        panel.add(logoutButton);

        return panel;
    }

    // ---------------- PROFIEL PAGINA ----------------
    private JPanel createProfilePage() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(0xE8F7F7));

        JButton menuButton = new JButton("☰");
        menuButton.setToolTipText("Terug naar menu");
        menuButton.setBounds(10, 10, 48, 36);
        menuButton.addActionListener(e -> showPage(PAGE_MENU));
        panel.add(menuButton);

        JLabel titel = new JLabel("Mijn Profiel", SwingConstants.CENTER);
        titel.setBounds(20, 10, 300, 50);
        titel.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(titel);

        profielfotoLabel = new JLabel();
        profielfotoLabel.setBounds(120, 70, 120, 120);
        profielfotoLabel.setOpaque(false);
        profielfotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(profielfotoLabel);

        JButton uploadBtn = new JButton("📤 Foto Uploaden");
        uploadBtn.setBounds(110, 200, 140, 36);
        uploadBtn.addActionListener(e -> uploadFoto());
        panel.add(uploadBtn);

        naamLabel = new JLabel("Naam: " + volledigeNaam);
        naamLabel.setBounds(30, 260, 300, 28);
        naamLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(naamLabel);

        emailLabel = new JLabel("Email: " + email);
        emailLabel.setBounds(30, 295, 300, 28);
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(emailLabel);

        totaalActLabel = new JLabel("Totaal activiteiten: " + totaalActiviteiten);
        totaalActLabel.setBounds(30, 330, 300, 28);
        totaalActLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(totaalActLabel);

        if (profielfoto == null) {
            profielfoto = makeDefaultProfileIcon(120);
            profielfotoLabel.setIcon(profielfoto);
        } else {
            profielfotoLabel.setIcon(makeRoundedIcon(profielfoto, 120));
        }

        return panel;
    }

    private void updateProfilePage() {
        if (naamLabel != null) naamLabel.setText("Naam: " + volledigeNaam);
        if (emailLabel != null) emailLabel.setText("Email: " + email);
        if (totaalActLabel != null) totaalActLabel.setText("Totaal activiteiten: " + totaalActiviteiten);
        if (profielfotoLabel != null) {
            if (profielfoto == null) {
                profielfoto = makeDefaultProfileIcon(120);
                profielfotoLabel.setIcon(profielfoto);
            } else {
                profielfotoLabel.setIcon(makeRoundedIcon(profielfoto, 120));
            }
        }
    }

    // ---------------- INSTELLINGEN PAGINA ----------------
    private JPanel createSettingsPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(40, 30, 40, 30));
        panel.setBackground(new Color(0xE8F7F7));

        // --- TERUG NAAR MENU BUTTON ---
        JButton menuButton = new JButton("☰ MENU");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        menuButton.addActionListener(e -> showPage(PAGE_MENU));
        panel.add(menuButton);
        panel.add(Box.createVerticalStrut(20));

        JLabel titel = new JLabel("⚙️ Instellingen");
        titel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titel);
        panel.add(Box.createVerticalStrut(20));

        // --- THEMA ---
        JLabel themaLabel = new JLabel("Thema:");
        themaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(themaLabel);

        String[] themaOpties = {"Light", "Dark"};
        JComboBox<String> themaCombo = new JComboBox<>(themaOpties);
        themaCombo.setSelectedIndex(darkMode ? 1 : 0);
        themaCombo.addActionListener(e -> {
            darkMode = themaCombo.getSelectedIndex() == 1;
            updateTheme();
        });
        panel.add(themaCombo);
        panel.add(Box.createVerticalStrut(10));

        // --- NAAM / EMAIL ---
        JLabel naamLabelSet = new JLabel("Naam:");
        JTextField naamField = new JTextField(volledigeNaam);
        panel.add(naamLabelSet);
        panel.add(naamField);

        JLabel emailLabelSet = new JLabel("Email:");
        JTextField emailField = new JTextField(email);
        panel.add(emailLabelSet);
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));

        JButton saveNaamEmailBtn = new JButton("💾 Opslaan");
        saveNaamEmailBtn.addActionListener(e -> {
            volledigeNaam = naamField.getText().trim();
            email = emailField.getText().trim();
            updateProfilePage();
            JOptionPane.showMessageDialog(this, "Gegevens opgeslagen!");
        });
        panel.add(saveNaamEmailBtn);
        panel.add(Box.createVerticalStrut(10));

        // --- WACHTWOORD ---
        JButton changePwdBtn = new JButton("🔑 Wachtwoord wijzigen");
        changePwdBtn.addActionListener(e -> {
            JPasswordField pwdField = new JPasswordField();
            JPasswordField pwdField2 = new JPasswordField();
            JPanel pwdPanel = new JPanel(new GridLayout(0, 2));
            pwdPanel.add(new JLabel("Nieuw wachtwoord:")); pwdPanel.add(pwdField);
            pwdPanel.add(new JLabel("Bevestig wachtwoord:")); pwdPanel.add(pwdField2);
            int result = JOptionPane.showConfirmDialog(this, pwdPanel, "Wachtwoord wijzigen", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if (String.valueOf(pwdField.getPassword()).equals(String.valueOf(pwdField2.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Wachtwoord gewijzigd!");
                } else {
                    JOptionPane.showMessageDialog(this, "Wachtwoorden komen niet overeen.", "Fout", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(changePwdBtn);
        panel.add(Box.createVerticalStrut(10));

        // --- LETTERGROOTTE ---
        JLabel fontLabel = new JLabel("Lettergrootte:");
        JSlider fontSlider = new JSlider(10, 24, letterGrootte);
        fontSlider.setMajorTickSpacing(2);
        fontSlider.setPaintTicks(true);
        fontSlider.setPaintLabels(true);
        fontSlider.addChangeListener(e -> letterGrootte = fontSlider.getValue());
        panel.add(fontLabel);
        panel.add(fontSlider);
        panel.add(Box.createVerticalStrut(10));

        // --- NOTIFICATIES ---
        JCheckBox notifBox = new JCheckBox("Notificaties aan", notificaties);
        notifBox.addActionListener(e -> notificaties = notifBox.isSelected());
        panel.add(notifBox);

        return panel;
    }


    private void updateTheme() {
        Color bg = darkMode ? new Color(0x333333) : new Color(0xE8F7F7);
        container.setBackground(bg);
        SwingUtilities.updateComponentTreeUI(this);
    }

    // ---------------- FOTO UPLOAD ----------------
    private void uploadFoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Kies een profielfoto");
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(f);
                if (img != null) {
                    profielfoto = new ImageIcon(img);
                    if (profielfotoLabel != null) {
                        profielfotoLabel.setIcon(makeRoundedIcon(profielfoto, 120));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Kon afbeelding niet lezen.", "Fout", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Fout bij openen afbeelding: " + ex.getMessage(), "Fout", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private ImageIcon makeDefaultProfileIcon(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(0xB4F8C8));
        g.fillOval(0, 0, size, size);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("SansSerif", Font.BOLD, size / 2));
        FontMetrics fm = g.getFontMetrics();
        String initial = (volledigeNaam == null || volledigeNaam.isEmpty()) ? "G" : volledigeNaam.substring(0, 1).toUpperCase();
        int tw = fm.stringWidth(initial);
        int th = fm.getAscent();
        g.drawString(initial, (size - tw) / 2, (size + th) / 2 - 6);
        g.dispose();
        return new ImageIcon(img);
    }

    private ImageIcon makeRoundedIcon(ImageIcon srcIcon, int diameter) {
        Image src = srcIcon.getImage();
        BufferedImage scaled = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, diameter, diameter, null);
        g2.dispose();

        BufferedImage rounded = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rounded.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter));
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(rounded);
    }

    // ---------------- HUISDIER ----------------
    private JPanel createHuisdierPage() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(0xE8F7F7));

        JButton menu = new JButton("☰ MENU");
        menu.setBounds(10, 10, 100, 36);
        menu.addActionListener(e -> showPage(PAGE_MENU));
        panel.add(menu);

        JLabel label3 = new JLabel("🐾 Mijn Huisdier", SwingConstants.CENTER);
        label3.setBounds(20, 60, 320, 36);
        label3.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(label3);

        JLabel placeholder = new JLabel("<html><center>Hier kun je je virtuele huisdier beheren.<br>Kom later terug voor meer functies!</center></html>", SwingConstants.CENTER);
        placeholder.setBounds(20, 120, 320, 200);
        panel.add(placeholder);

        return panel;
    }

    // ---------------- hulpfuncties ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BabbelApp app = new BabbelApp();
            app.setVisible(true);
        });
    }

    // ---------- RoundedPanel inner class for "cards" ----------
    static class RoundedPanel extends JPanel {
        private int cornerRadius = 15;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            int shadowGap = 3;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setColor(new Color(0,0,0,20));
            g2.fillRoundRect(shadowGap, shadowGap, width - shadowGap*2, height - shadowGap*2, cornerRadius, cornerRadius);

            g2.setColor(backgroundColor != null ? backgroundColor : getBackground());
            g2.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, cornerRadius, cornerRadius);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
