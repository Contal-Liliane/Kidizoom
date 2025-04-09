/*
Liliane
*/
package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

public class DessinNiveauFacile extends JPanel {

    private PanneauDessin drawPanel;
    private JSlider eraserSlider, brushSizeSlider, eraserSizeSlider;
    private Color currentColor = Color.BLACK;
    private boolean erasing = false;
    private int brushSize = 10;
    private int eraserSize = 20;
    public String selectedShape = "Libre";  // Forme par défaut
    private ShapeButton shapeButton;  // Déclaration de ShapeButton

    public DessinNiveauFacile() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        drawPanel = new PanneauDessin(this);  // Passer l'instance de DessinNiveauFacile à DrawPanel
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(780, 500));
        JPanel controlPanel = new JPanel();
        JButton saveButton = new JButton("Sauvegarder");

        saveButton.addActionListener(e -> saveDrawing());

        controlPanel.add(saveButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // Panel des couleurs
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.GRAY);

        JButton blackButton = createColorButton("Noir", Color.BLACK);
        JButton redButton = createColorButton("Rouge", Color.RED);
        JButton greenButton = createColorButton("Vert", Color.GREEN);
        JButton blueButton = createColorButton("Bleu", Color.BLUE);

        colorPanel.add(blackButton);
        colorPanel.add(redButton);
        colorPanel.add(greenButton);
        colorPanel.add(blueButton);

        JButton eraseButton = new JButton("Gomme");
        eraseButton.addActionListener(e -> {
            erasing = !erasing;
            eraseButton.setText(erasing ? "Dessiner" : "Gomme");
        });
        colorPanel.add(eraseButton);

        mainPanel.add(colorPanel, BorderLayout.NORTH);

        // Panneau des formes
        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new GridLayout(4, 1, 5, 5));
        shapePanel.setBackground(Color.LIGHT_GRAY);

        // Création de chaque bouton ShapeButton en passant l'instance de DessinNiveauFacile
        ShapeButton freeDrawButton = new ShapeButton("✏", "Libre", this);
        ShapeButton circleButton = new ShapeButton("O", "Cercle", this);
        ShapeButton squareButton = new ShapeButton("■", "Carré", this);
        ShapeButton triangleButton = new ShapeButton("▲", "Triangle", this);

        shapePanel.add(freeDrawButton);
        shapePanel.add(circleButton);
        shapePanel.add(squareButton);
        shapePanel.add(triangleButton);

        mainPanel.add(shapePanel, BorderLayout.WEST);

        // Panneau des sliders en bas
        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.setPreferredSize(new Dimension(780, 100));

        brushSizeSlider = new JSlider(1, 50, 10);
        brushSizeSlider.addChangeListener(e -> brushSize = brushSizeSlider.getValue());

        eraserSizeSlider = new JSlider(5, 50, 20);
        eraserSizeSlider.addChangeListener(e -> eraserSize = eraserSizeSlider.getValue());

        eraserSlider = new JSlider(0, 100, 0);
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                drawPanel.clear();
                eraserSlider.setValue(0);
            }
        });

        bottomPanel.add(new JLabel("Taille du crayon:"));
        bottomPanel.add(brushSizeSlider);
        bottomPanel.add(new JLabel("Taille de la gomme:"));
        bottomPanel.add(eraserSizeSlider);
        bottomPanel.add(new JLabel("Effacer tout:"));
        bottomPanel.add(eraserSlider);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createColorButton(String name, Color color) {
        JButton button = new JButton(name);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> {
            currentColor = color;
            erasing = false;
        });
        return button;
    }

    public void saveDrawing() {
        String name = JOptionPane.showInputDialog(this, "Nom du dessin :");
        if (name != null && !name.trim().isEmpty()) {
            File file = new File(name + ".ser");
            if (file.exists()) {
                int result = JOptionPane.showConfirmDialog(this,
                        "Un dessin avec ce nom existe déjà. Voulez-vous l’écraser ?",
                        "Confirmer l'écrasement",
                        JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    return; // l’utilisateur a refusé d’écraser, on quitte
                }
            }
            try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(drawPanel.getImage());
                JOptionPane.showMessageDialog(this, "Dessin sauvegardé avec succès !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public boolean isErasing() {
        return erasing;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public int getEraserSize() {
        return eraserSize;
    }

    public String getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(String selectedShape) {
        this.selectedShape = selectedShape;
    }
}
