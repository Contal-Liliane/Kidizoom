import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Classe principale
public class Administration extends JFrame {
    private String motDePasseHash = hashPassword("ohlaboulette"); // Mot de passe crypté
    private JPasswordField passwordField;
    private JLabel resultLabel;
    private JButton verifyButton, modifyButton;

    public Administration() {
        // Configuration de la fenêtre
        setTitle("Vérification du mot de passe");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));
        getContentPane().setBackground(Color.BLACK); // Fond noir

        // Champ de saisie du mot de passe (gris foncé)
        passwordField = new JPasswordField(15);
        passwordField.setBackground(new Color(50, 50, 50)); // Fond gris foncé
        passwordField.setForeground(Color.WHITE); // Texte blanc

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.BLACK);
        inputPanel.add(new JLabel("Mot de passe :", JLabel.CENTER)).setForeground(Color.WHITE);
        inputPanel.add(passwordField);

        // Bouton de vérification (or, arrondi)
        verifyButton = new RoundedButton("Vérifier");
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifierMotDePasse();
            }
        });

        // Label pour afficher le résultat
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setForeground(Color.WHITE); // Texte blanc

        // Bouton pour modifier le mot de passe (invisible au début)
        modifyButton = new RoundedButton("Modifier le mot de passe");
        modifyButton.setVisible(false);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierMotDePasse();
            }
        });

        // Ajout des éléments à la fenêtre
        add(inputPanel);
        add(verifyButton);
        add(resultLabel);
        add(modifyButton);

        // Affichage de la fenêtre
        setVisible(true);
    }

    // Méthode pour hacher un mot de passe en SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage", e);
        }
    }

    // Vérifie si le mot de passe entré est correct
    private void verifierMotDePasse() {
        String motDePasseUtilisateur = new String(passwordField.getPassword());
        String hashUtilisateur = hashPassword(motDePasseUtilisateur);

        if (hashUtilisateur.equals(motDePasseHash)) {
            resultLabel.setText("✅ Code bon !");
            resultLabel.setForeground(Color.GREEN);
            modifyButton.setVisible(true); // Affiche le bouton Modifier
        } else {
            resultLabel.setText("❌ Code faux !");
            resultLabel.setForeground(Color.RED);
            modifyButton.setVisible(false); // Cache le bouton Modifier
        }
    }

    // Permet de modifier le mot de passe après validation
    private void modifierMotDePasse() {
        String nouveauMotDePasse = JOptionPane.showInputDialog(this, "Entrez le nouveau mot de passe :");
        if (nouveauMotDePasse != null && !nouveauMotDePasse.isEmpty()) {
            motDePasseHash = hashPassword(nouveauMotDePasse);
            JOptionPane.showMessageDialog(this, "✅ Mot de passe modifié avec succès !");
            passwordField.setText(""); // Réinitialise le champ
            resultLabel.setText(""); // Efface le message
            modifyButton.setVisible(false); // Cache le bouton Modifier
        }
    }

    // Classe interne pour créer des boutons arrondis
    class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setBackground(new Color(255, 215, 0)); // Or
            setForeground(Color.BLACK); // Texte noir
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Arial", Font.BOLD, 14));
            setPreferredSize(new Dimension(150, 40));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Coins arrondis
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // Pas de bordure
        }
    }

    // Lancer l'application
    public static void main(String[] args) {
        new Administration();
    }
}