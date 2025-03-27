/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author louis
 */
// Classe qui gère l'interface graphique et l'interaction avec l'utilisateur
class FenetreCalcul extends JFrame {
    private ZoneDessin zoneDessin; // Zone de dessin pour afficher le calcul
    private JTextField champReponse; // Permet à l'utilisateur d'entrer sa réponse
    private JButton boutonVerifier, boutonSolution, boutonNouveau, boutonSupprimer;
    private JRadioButton niveauFacile, niveauDifficile; // Boutons pour choisir le niveau de difficulté
    private Calculateur calculateur;

    public FenetreCalcul() {
        // Configuration de la fenêtre
        setTitle("Activité de Calcul");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        calculateur = new Calculateur();
        zoneDessin = new ZoneDessin();

        // Création des composants
        champReponse = new JTextField(10);
        boutonVerifier = new JButton("Vérifier");
        boutonSolution = new JButton("Solution");
        boutonNouveau = new JButton("Nouveau");
        boutonSupprimer = new JButton("Supprimer");

        // Boutons radio pour choisir le niveau de difficulté
        niveauFacile = new JRadioButton("Facile", true);
        niveauDifficile = new JRadioButton("Difficile");
        ButtonGroup groupNiveau = new ButtonGroup();
        groupNiveau.add(niveauFacile);
        groupNiveau.add(niveauDifficile);

        // Ajout des composants à la fenêtre avec un layout flexible
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(zoneDessin, gbc);

        gbc.gridy = 1;
        add(champReponse, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(niveauFacile, gbc);
        gbc.gridx = 1;
        add(niveauDifficile, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(boutonVerifier, gbc);
        gbc.gridx = 1;
        add(boutonSolution, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(boutonNouveau, gbc);
        gbc.gridx = 1;
        add(boutonSupprimer, gbc);

        // Génération initiale d'un calcul
        genererCalcul();

        // Ajout des actions aux boutons
        boutonVerifier.addActionListener(e -> verifierReponse());
        boutonSolution.addActionListener(e -> montrerSolution());
        boutonNouveau.addActionListener(e -> genererCalcul());
        boutonSupprimer.addActionListener(e -> champReponse.setText(""));

        setVisible(true);
        
        zoneDessin.setPreferredSize(new Dimension(250, 80)); // Plus d'espace pour le calcul

    }

    // Méthode qui génère un calcul en fonction du niveau sélectionné
    private void genererCalcul() {
    calculateur.genererCalcul(niveauFacile.isSelected());
    zoneDessin.setCalcul(calculateur.getCalculAffiche()); // Met à jour l'affichage
    champReponse.setText("");
    champReponse.setBackground(Color.WHITE);
    champReponse.requestFocus();
    zoneDessin.repaint();  // Force la mise à jour de l'affichage
}


    // Méthode qui compare la réponse entrée par l'utilisateur avec la solution correcte
    private void verifierReponse() {
        try {
            int reponse = Integer.parseInt(champReponse.getText());
            if (calculateur.verifierReponse(reponse)) {
                champReponse.setBackground(Color.GREEN); // Met le champ en vert si la réponse est correcte
                JOptionPane.showMessageDialog(this, "Bravo ! Bonne réponse.");
            } else {
                champReponse.setBackground(Color.RED); // Met le champ en rouge si la réponse est incorrecte
                JOptionPane.showMessageDialog(this, "Faux. Essayez encore !");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
        }
    }

    // Méthode qui affiche la solution correcte
    private void montrerSolution() {
        JOptionPane.showMessageDialog(this, "La solution est : " + calculateur.getResultat());
    }
}

// Classe qui gère l'affichage du calcul avec drawString()
class ZoneDessin extends JPanel {
    private String calculAffiche = "";

    public ZoneDessin() {
        setPreferredSize(new Dimension(200, 50)); // Ajuste la taille pour un affichage correct
    }

    public void setCalcul(String calcul) {
        this.calculAffiche = calcul;
        repaint(); // Met à jour l'affichage
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Active l'anti-aliasing pour un meilleur rendu
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Définit la police et récupère les dimensions du texte
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(calculAffiche);
        int textHeight = fm.getAscent();

        // Centre le texte dans la zone de dessin
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2;

        // Affiche le calcul
        g2.drawString(calculAffiche, x, y);
    }
}


