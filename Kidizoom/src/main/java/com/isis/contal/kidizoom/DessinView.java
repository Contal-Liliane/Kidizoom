package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DessinView extends JPanel {

    protected DrawPanel drawPanel;
    protected JButton saveButton, loadButton, deleteButton, colorChooserButton, eraseButton;
    protected JSlider brushSizeSlider, eraserSizeSlider, eraserSlider;
    protected JPanel colorPanel;
    protected JButton roundButton, squareButton, triangleButton;

    // Définir une couleur par défaut pour le dessin
    private Color currentColor = Color.BLACK;

    // Référence au contrôleur
    private DessinController controller;

    public DessinView(DessinController controller) {
        this.controller = controller;

        // Layout principal du panel
        setLayout(new BorderLayout());

        // Initialisation du DrawPanel (zone de dessin)
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        // Panneau des contrôles (en bas)
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1)); // 3 lignes : boutons de formes, couleurs et sliders

        // Partie des formes (rond, carré, triangle)
        JPanel shapesPanel = new JPanel();
        roundButton = new JButton("⭕");
        squareButton = new JButton("◼");
        triangleButton = new JButton("🔺");

        shapesPanel.add(roundButton);
        shapesPanel.add(squareButton);
        shapesPanel.add(triangleButton);

        // Partie des couleurs (sélectionner la couleur)
        colorPanel = new JPanel();
        colorChooserButton = new JButton("Sélectionner une couleur");
        colorChooserButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Sélectionner une couleur", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                controller.setColor(currentColor);  // Passer la nouvelle couleur au modèle
            }
        });

        // Bouton gomme
        eraseButton = new JButton("🧽 Gomme");

        colorPanel.add(colorChooserButton);
        colorPanel.add(eraseButton);

        // Sliders pour la taille du pinceau, de la gomme et effacer tout
        JPanel sliderPanel = new JPanel(new GridLayout(3, 2));
        brushSizeSlider = new JSlider(1, 50, 10);
        eraserSizeSlider = new JSlider(5, 50, 20);
        eraserSlider = new JSlider(0, 100, 0);

        sliderPanel.add(new JLabel("Taille du pinceau:"));
        sliderPanel.add(brushSizeSlider);
        sliderPanel.add(new JLabel("Taille de la gomme:"));
        sliderPanel.add(eraserSizeSlider);
        sliderPanel.add(new JLabel("Effacer tout:"));
        sliderPanel.add(eraserSlider);

        // Ajouter les panneaux au contrôle principal
        controlPanel.add(shapesPanel);
        controlPanel.add(colorPanel);
        controlPanel.add(sliderPanel);

        // Ajouter le panneau de contrôle (formulaire, couleurs et sliders)
        add(controlPanel, BorderLayout.SOUTH);

        // Action pour les boutons de forme (cercle, carré, triangle)
        roundButton.addActionListener(e -> controller.setShape("Cercle"));
        squareButton.addActionListener(e -> controller.setShape("Carré"));
        triangleButton.addActionListener(e -> controller.setShape("Triangle"));

        // Action pour la gomme (réinitialiser la forme à "effacer")
        eraseButton.addActionListener(e -> controller.setShape("Effacer"));

        // Gestion de l'effacement (slider effacer tout à 100)
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                controller.clearDrawing();
                eraserSlider.setValue(0);
            }
        });
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JSlider getBrushSizeSlider() {
        return brushSizeSlider;
    }

    public JSlider getEraserSizeSlider() {
        return eraserSizeSlider;
    }

    public JSlider getEraserSlider() {
        return eraserSlider;
    }
}
