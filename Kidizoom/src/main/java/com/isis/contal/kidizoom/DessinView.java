package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;

public class DessinView extends JPanel {

    protected DrawPanel drawPanel;
    protected JButton saveButton, loadButton, deleteButton, colorChooserButton, eraseButton;
    protected JSlider brushSizeSlider, eraserSizeSlider, eraserSlider;

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

        // Panneau des couleurs
        JPanel colorPanel = new JPanel();
        // Création d'une palette de couleurs
        JColorChooser colorChooser = new JColorChooser(Color.BLACK);
        colorChooser.getSelectionModel().addChangeListener(e -> {
            Color newColor = colorChooser.getColor();
            controller.setColor(newColor); // Met à jour la couleur dans le modèle
        });

        JDialog colorDialog = JColorChooser.createDialog(this, "Choisir une couleur", true, colorChooser, null, null);

        colorChooserButton = new JButton("Palette de Couleurs");
        colorChooserButton.addActionListener(e -> colorDialog.setVisible(true));

        eraseButton = new JButton("Gomme");

        colorPanel.add(colorChooserButton);
        colorPanel.add(eraseButton);
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

        setPreferredSize(new Dimension(780, 500));
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
