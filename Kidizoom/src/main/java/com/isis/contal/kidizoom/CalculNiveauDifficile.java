package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CalculNiveauDifficile extends JPanel {
    private JLabel labelCalcul;
    private JTextField champReponse;
    private JButton boutonVerifier, boutonSolution, boutonNouveau, boutonSupprimer;
    private JSlider tailleSlider;
    private int resultatAttendu;

    public CalculNiveauDifficile() {
        setLayout(new BorderLayout());

        // Panel principal (fond noir)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Panel pour le calcul et la réponse
        JPanel calculPanel = new JPanel();
        calculPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        labelCalcul = new JLabel();
        labelCalcul.setForeground(Color.BLACK);
        labelCalcul.setFont(new Font("Arial", Font.BOLD, 24)); // Taille par défaut

        champReponse = new JTextField(5);
        champReponse.setFont(new Font("Arial", Font.BOLD, 24)); // Taille par défaut

        boutonVerifier = new JButton("Vérifier");
        boutonSolution = new JButton("Solution");
        boutonNouveau = new JButton("Nouveau");
        boutonSupprimer = new JButton("Supprimer");

        // Slider pour régler la taille du texte et du champ de réponse
        tailleSlider = new JSlider(12, 48, 24);
        tailleSlider.setMajorTickSpacing(6);
        tailleSlider.setPaintTicks(true);
        tailleSlider.setPaintLabels(true);
        tailleSlider.addChangeListener(e -> ajusterTailleTexte());

        // Ajout des composants avec GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        calculPanel.add(labelCalcul, gbc);

        gbc.gridy = 1;
        calculPanel.add(champReponse, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        calculPanel.add(boutonVerifier, gbc);

        gbc.gridx = 1;
        calculPanel.add(boutonSolution, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        calculPanel.add(boutonNouveau, gbc);

        gbc.gridx = 1;
        calculPanel.add(boutonSupprimer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        calculPanel.add(new JLabel("Taille du texte :"), gbc);

        gbc.gridy = 5;
        calculPanel.add(tailleSlider, gbc);

        // Ajout du panneau de calcul au panneau principal
        mainPanel.add(calculPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(780, 500));

        // Ajout du panneau principal à l'interface
        add(mainPanel, BorderLayout.CENTER);

        // Actions des boutons
        boutonVerifier.addActionListener(e -> verifierReponse());
        boutonSolution.addActionListener(e -> montrerSolution());
        boutonNouveau.addActionListener(e -> genererCalcul());
        boutonSupprimer.addActionListener(e -> champReponse.setText(""));

        // Génération initiale d'un calcul
        genererCalcul();
    }

    // Ajuste la taille du texte du calcul ET du champ de réponse
    private void ajusterTailleTexte() {
        int nouvelleTaille = tailleSlider.getValue();

        // Mettre à jour la taille du texte du calcul
        labelCalcul.setFont(new Font("Arial", Font.BOLD, nouvelleTaille));

        // Mettre à jour la taille du champ de réponse
        champReponse.setFont(new Font("Arial", Font.BOLD, nouvelleTaille));
        champReponse.setPreferredSize(new Dimension(nouvelleTaille * 3, nouvelleTaille + 10));

        // Rafraîchir l'affichage
        revalidate();
        repaint();
    }

    // Générer un calcul aléatoire (niveau difficile)
    private void genererCalcul() {
        Random rand = new Random();
        int typeOperation = rand.nextInt(3); // 0 = addition, 1 = soustraction, 2 = multiplication
        int a, b;

        switch (typeOperation) {
            case 0: // Addition (2 nombres à 3 chiffres)
                a = rand.nextInt(900) + 100; // 100 à 999
                b = rand.nextInt(900) + 100; // 100 à 999
                resultatAttendu = a + b;
                labelCalcul.setText(a + " + " + b + " = ?");
                break;
            case 1: // Soustraction (2 nombres à 3 chiffres, résultat négatif possible)
                a = rand.nextInt(900) + 100; // 100 à 999
                b = rand.nextInt(900) + 100; // 100 à 999
                resultatAttendu = a - b;
                labelCalcul.setText(a + " - " + b + " = ?");
                break;
            case 2: // Multiplication (2 nombres à 1 chiffre)
                a = rand.nextInt(9) + 1; // 1 à 9
                b = rand.nextInt(9) + 1; // 1 à 9
                resultatAttendu = a * b;
                labelCalcul.setText(a + " × " + b + " = ?");
                break;
        }

        champReponse.setText("");
        champReponse.setBackground(Color.WHITE);
        champReponse.requestFocus();
    }

    // Vérifier la réponse de l'utilisateur
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

    // Afficher la solution
    private void montrerSolution() {
        JOptionPane.showMessageDialog(this, "La solution est : " + resultatAttendu);
    }
}
