package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Administration extends JPanel {
    private final Dictionnaire dictionnaire = new Dictionnaire();
    private static final String MOTDEPASSE_FICHIER = "motdepasse.txt";
    private String motDePasseHache;

    public Administration() {
        chargerMotDePasse();
        setLayout(new GridLayout(4, 1));

        // Créer et ajouter les boutons avec les couleurs spécifiques
        JButton ajouterBouton = new JButton("Ajouter un mot");
        ajouterBouton.setBackground(Color.BLUE);  // Bleu
        ajouterBouton.addActionListener(e -> ajouterMot());
        add(ajouterBouton);

        JButton supprimerBouton = new JButton("Supprimer un mot");
        supprimerBouton.setBackground(Color.GREEN);  // Vert
        supprimerBouton.addActionListener(e -> supprimerMot());
        add(supprimerBouton);

        JButton afficherMotBouton = new JButton("Afficher les mots");
        afficherMotBouton.setBackground(new Color(139, 69, 19));  // Marron
        afficherMotBouton.addActionListener(e -> afficherMots());
        add(afficherMotBouton);

        JButton changeMotDePasseBouton = new JButton("Modifier le mot de passe");
        changeMotDePasseBouton.setBackground(Color.GRAY);  // Gris foncé
        changeMotDePasseBouton.addActionListener(e -> changerMotDePasse());
        add(changeMotDePasseBouton);
    }

    public static boolean demanderMotDePasse(Component parent) {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(parent, passwordField, "Entrez le mot de passe:", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String motDePasseEntre = new String(passwordField.getPassword());
            Administration adminPanel = new Administration();
            return adminPanel.checkMotDePasse(motDePasseEntre);
        }
        return false;
    }

    private void chargerMotDePasse() {
        try (BufferedReader br = new BufferedReader(new FileReader(MOTDEPASSE_FICHIER))) {
            motDePasseHache = br.readLine();
        } catch (IOException e) {
            motDePasseHache = motDePasseHache("password"); // Mot de passe par défaut
        }
    }

    private void enregistrerMotDePasse(String h) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOTDEPASSE_FICHIER))) {
            bw.write(h);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkMotDePasse(String mdp) {
        return motDePasseHache(mdp).equals(motDePasseHache);
    }

    private String motDePasseHache(String mdp) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(mdp.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void ajouterMot() {
        String nouveauMot = JOptionPane.showInputDialog(this, "Entrez un nouveau mot :");
        if (nouveauMot != null && !nouveauMot.trim().isEmpty()) {
            dictionnaire.ajouterMot(nouveauMot.trim().toUpperCase());
            JOptionPane.showMessageDialog(this, "Mot ajouté avec succès !");
        }
    }

    private void supprimerMot() {
        String motSupprimer = JOptionPane.showInputDialog(this, "Entrez le mot à supprimer :");
        if (motSupprimer != null && !motSupprimer.trim().isEmpty()) {
            dictionnaire.supprimerMot(motSupprimer.trim().toUpperCase());
            JOptionPane.showMessageDialog(this, "Mot supprimé avec succès !");
        }
    }

    private void afficherMots() {
        JOptionPane.showMessageDialog(this, "Mots du dictionnaire : " + String.join(", ", dictionnaire.getMots()));
    }

    private void changerMotDePasse() {
        String nouveauMotDePasse = JOptionPane.showInputDialog(this, "Entrez le nouveau mot de passe :");
        if (nouveauMotDePasse != null && !nouveauMotDePasse.trim().isEmpty()) {
            motDePasseHache = motDePasseHache(nouveauMotDePasse);
            enregistrerMotDePasse(motDePasseHache);
            JOptionPane.showMessageDialog(this, "Mot de passe mis à jour !");
        }
    }
}
