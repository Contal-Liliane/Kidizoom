package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;

public class MenuGeneral extends JFrame {

    private JPanel menuPanel;
    private boolean ardoiseActive = false;
    private boolean calculActive = false;
    private boolean penduActive = false;
    private DessinNiveauFacile dessinFacile;
    private DessinNiveauDifficile dessinDifficile;
    private CalculNiveauFacile calculFacile;
    private CalculNiveauDifficile calculDifficile;
    private BackgroundPanel mainPanel;

    public MenuGeneral() {
        setTitle("Application Multi-Activités");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fond animé par défaut
        mainPanel = new BackgroundPanel("background.jpg"); // Assurez-vous que cette image existe
        mainPanel.setLayout(new BorderLayout());

        // Barre de menu
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(getWidth(), 30));
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Bouton "Activités"
        JButton activitesButton = new JButton("Activités");
        JPopupMenu activitesMenu = new JPopupMenu();
        JMenuItem ardoiseItem = new JMenuItem("Ardoise Magique");
        JMenuItem calculItem = new JMenuItem("Activité Calcul");
        JMenuItem penduItem = new JMenuItem("Jeu du Pendu");

        activitesMenu.add(ardoiseItem);
        activitesMenu.add(calculItem);
        activitesMenu.add(penduItem);
        activitesButton.addActionListener(e -> activitesMenu.show(activitesButton, 0, activitesButton.getHeight()));

        // Bouton "Niveau"
        JButton niveauButton = new JButton("Niveau");
        JPopupMenu niveauMenu = new JPopupMenu();
        JMenuItem facileItem = new JMenuItem("Niveau Facile");
        JMenuItem difficileItem = new JMenuItem("Niveau Difficile");

        niveauMenu.add(facileItem);
        niveauMenu.add(difficileItem);
        niveauButton.addActionListener(e -> niveauMenu.show(niveauButton, 0, niveauButton.getHeight()));

        // Bouton "Administration"
        JButton adminButton = new JButton("Administration");
        adminButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Section administration en développement..."));

        // Bouton "Retour à la base"
        JButton retourButton = new JButton("Retour au menu");
        retourButton.addActionListener(e -> resetToBase());

        // Ajout des boutons à la barre de menu
        menuPanel.add(activitesButton);
        menuPanel.add(niveauButton);
        menuPanel.add(adminButton);
        menuPanel.add(retourButton);
        add(menuPanel, BorderLayout.NORTH);

        // Ajout du fond animé
        add(mainPanel, BorderLayout.CENTER);

        // Actions des menus
        ardoiseItem.addActionListener(e -> setArdoiseMagique());
        calculItem.addActionListener(e -> setActiviteCalcul());
        penduItem.addActionListener(e -> setJeuPendu());
        facileItem.addActionListener(e -> setNiveauFacile());
        difficileItem.addActionListener(e -> setNiveauDifficile());

        setVisible(true); // Rendre la fenêtre visible
    }

    private void setArdoiseMagique() {
        ardoiseActive = true;
        calculActive = false;
        penduActive = false;
        setNiveauFacile();
    }

    private void setActiviteCalcul() {
        calculActive = true;
        ardoiseActive = false;
        penduActive = false;

        mainPanel.setBackgroundImage("creatif.jpg"); // Change l'image de fond pour le calcul
        setNiveauFacile(); // Charge le niveau facile par défaut
    }

    private void setJeuPendu() {
        penduActive = true;
        ardoiseActive = false;
        calculActive = false;
        Pendu pendu = new Pendu();
        updateMainPanel(pendu);
    }

    private void setNiveauFacile() {
        if (ardoiseActive) {
            dessinFacile = new DessinNiveauFacile();
            updateMainPanel(dessinFacile);
        } else if (calculActive) {
            calculFacile = new CalculNiveauFacile();
            mainPanel.setBackgroundImage("creatif.jpg"); // Change l'image de fond pour le calcul
            updateMainPanel(calculFacile);
        }
    }

    private void setNiveauDifficile() {
        if (ardoiseActive) {
            dessinDifficile = new DessinNiveauDifficile();
            updateMainPanel(dessinDifficile);
        } else if (calculActive) {
            calculDifficile = new CalculNiveauDifficile();
            mainPanel.setBackgroundImage("creatif.jpg"); // Change l'image de fond pour le calcul
            updateMainPanel(calculDifficile);
        }
    }

    private void updateMainPanel(JPanel newPanel) {
        mainPanel.removeAll();
        mainPanel.add(newPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void resetToBase() {
        mainPanel.setBackgroundImage("background.jpg"); // Remet l'image de base
        mainPanel.removeAll(); // Supprime tous les composants affichés
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuGeneral()); // Lancer l'application
    }
}
