package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;

public class MenuGeneral extends JFrame {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private boolean ardoiseActive = false;
    private boolean calculActive = false;
    private DessinNiveauFacile dessinFacile;
    private DessinNiveauDifficile dessinDifficile;
    private CalculNiveauFacile calculFacile;
    private CalculNiveauDifficile calculDifficile;

    public MenuGeneral() {
        setTitle("Application Multi-Activités");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barre de menu
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(getWidth(), 30));
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Bouton "Activités"
        JButton activitesButton = new JButton("Activités");
        JPopupMenu activitesMenu = new JPopupMenu();
        JMenuItem ardoiseItem = new JMenuItem("Ardoise Magique");
        JMenuItem calculItem = new JMenuItem("Activité Calcul"); // Ajout activité calcul

        activitesMenu.add(ardoiseItem);
        activitesMenu.add(calculItem);
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

        // Ajout des boutons à la barre de menu
        menuPanel.add(activitesButton);
        menuPanel.add(niveauButton);
        menuPanel.add(adminButton);
        add(menuPanel, BorderLayout.NORTH);

        // Zone principale
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // Actions des menus
        ardoiseItem.addActionListener(e -> setArdoiseMagique());
        calculItem.addActionListener(e -> setActiviteCalcul());
        facileItem.addActionListener(e -> setNiveauFacile());
        difficileItem.addActionListener(e -> setNiveauDifficile());
    }

    private void setArdoiseMagique() {
        ardoiseActive = true;
        calculActive = false;
        setNiveauFacile();
    }

    private void setActiviteCalcul() {
        calculActive = true;
        ardoiseActive = false;
        setNiveauFacile();
    }

    private void setNiveauFacile() {
        if (ardoiseActive) {
            dessinFacile = new DessinNiveauFacile();
            updateMainPanel(dessinFacile);
        } else if (calculActive) {
            calculFacile = new CalculNiveauFacile();
            updateMainPanel(calculFacile);
        }
    }

    private void setNiveauDifficile() {
        if (ardoiseActive) {
            dessinDifficile = new DessinNiveauDifficile();
            updateMainPanel(dessinDifficile);
        } else if (calculActive) {
            calculDifficile = new CalculNiveauDifficile();
            updateMainPanel(calculDifficile);
        }
    }

    private void updateMainPanel(JPanel newPanel) {
        mainPanel.removeAll();
        mainPanel.add(newPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuGeneral().setVisible(true));
    }
}
