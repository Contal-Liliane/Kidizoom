package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CalculNiveauDifficile extends JPanel {
    private JLabel labelCalcul;
    private JTextField champReponse;
    private JButton boutonVerifier, boutonSolution, boutonNouveau, boutonSupprimer;
    private int resultatAttendu;

    public CalculNiveauDifficile() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        labelCalcul = new JLabel();
        champReponse = new JTextField(5);
        boutonVerifier = new JButton("Vérifier");
        boutonSolution = new JButton("Solution");
        boutonNouveau = new JButton("Nouveau");
        boutonSupprimer = new JButton("Supprimer");

        // Ajout des composants au layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelCalcul, gbc);

        gbc.gridy = 1;
        add(champReponse, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(boutonVerifier, gbc);

        gbc.gridx = 1;
        add(boutonSolution, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(boutonNouveau, gbc);

        gbc.gridx = 1;
        add(boutonSupprimer, gbc);

        // Actions des boutons
        boutonVerifier.addActionListener(e -> verifierReponse());
        boutonSolution.addActionListener(e -> montrerSolution());
        boutonNouveau.addActionListener(e -> genererCalcul());
        boutonSupprimer.addActionListener(e -> champReponse.setText(""));

        // Génération initiale d'un calcul
        genererCalcul();
    }

    // Méthode pour générer un calcul (niveau difficile)
    private void genererCalcul() {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        resultatAttendu = a * b;
        labelCalcul.setText(a + " × " + b + " = ?");
        champReponse.setText("");
        champReponse.setBackground(Color.WHITE);
        champReponse.requestFocus();
    }

    // Méthode pour vérifier la réponse de l'utilisateur
    private void verifierReponse() {
        try {
            int reponse = Integer.parseInt(champReponse.getText());
            if (reponse == resultatAttendu) {
                champReponse.setBackground(Color.GREEN);
                JOptionPane.showMessageDialog(this, "Bravo !");
            } else {
                champReponse.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "Incorrect !");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrez un nombre valide.");
        }
    }

    // Méthode pour afficher la solution
    private void montrerSolution() {
        JOptionPane.showMessageDialog(this, "La solution est : " + resultatAttendu);
    }
}
