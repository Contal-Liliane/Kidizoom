package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;

public class DessinView extends JPanel {

    protected DrawPanel drawPanel;
    protected JButton saveButton, loadButton, deleteButton, colorChooserButton, eraseButton;
    protected JSlider brushSizeSlider, eraserSizeSlider, eraserSlider;
    protected JPanel colorPanel; // Assure-toi que le panel existe bien

    public DessinView(DessinController controller) {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Panneau de dessin
        drawPanel = new DrawPanel();
        mainPanel.add(drawPanel, BorderLayout.CENTER);

        // Barre de tâches (sauvegarde, chargement, suppression)
        JPanel controlPanel = new JPanel();
        saveButton = new JButton("Sauvegarder");
        loadButton = new JButton("Charger");
        deleteButton = new JButton("Supprimer");

        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(deleteButton);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        //Création du panneau pour les couleurs
        colorPanel = new JPanel(); // Initialisation correcte
        colorPanel.setLayout(new FlowLayout()); // Ajoute une disposition pour voir les boutons

        //Bouton "Choisir une couleur"
        colorChooserButton = new JButton("Choisir une couleur");
        colorChooserButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Sélectionner une couleur", Color.BLACK);
            if (newColor != null) {
                controller.setColor(newColor); // Appliquer la couleur choisie
            }
        });

        // Bouton gomme
        eraseButton = new JButton("🧽 Gomme");

        //Ajout des boutons au panneau des couleurs
        colorPanel.add(colorChooserButton);
        colorPanel.add(eraseButton);

        //Ajout du panneau des couleurs en haut
        mainPanel.add(colorPanel, BorderLayout.NORTH);

        // Sliders (taille crayon, gomme, effacement)
        brushSizeSlider = new JSlider(1, 50, 10);
        eraserSizeSlider = new JSlider(5, 50, 20);
        eraserSlider = new JSlider(0, 100, 0);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        bottomPanel.add(new JLabel("Taille du crayon:"));
        bottomPanel.add(brushSizeSlider);
        bottomPanel.add(new JLabel("Taille de la gomme:"));
        bottomPanel.add(eraserSizeSlider);
        bottomPanel.add(new JLabel("Effacer tout:"));
        bottomPanel.add(eraserSlider);

        //Ajout des composants à la fenêtre principale
        setPreferredSize(new Dimension(780, 500));
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //Met à jour l'affichage pour éviter les erreurs de rendu
        revalidate();
        repaint();
    }
}
