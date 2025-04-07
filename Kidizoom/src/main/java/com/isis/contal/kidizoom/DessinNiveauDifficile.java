package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

public class DessinNiveauDifficile extends JPanel {

    private PanneauDessinDifficile drawPanel;
    private JSlider eraserSlider, brushSizeSlider, eraserSizeSlider;
    private Color currentColor = Color.BLACK;
    private boolean erasing = false;
    private int brushSize = 10;
    private int eraserSize = 20;
    public String selectedShape = "Libre";

    public DessinNiveauDifficile() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        drawPanel = new PanneauDessinDifficile(this);
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(780, 500));

        // Bas : sauvegarder, charger, supprimer
        JPanel controlPanel = new JPanel();
        JButton saveButton = new JButton("Sauvegarder");
        JButton loadButton = new JButton("Charger");
        JButton deleteButton = new JButton("Supprimer");

        saveButton.addActionListener(e -> saveDrawing());
        loadButton.addActionListener(e -> loadDrawing());
        deleteButton.addActionListener(e -> deleteDrawing());

        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(deleteButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // Palette de couleurs
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.GRAY);

        JButton paletteButton = new JButton("Sélectionner une couleur");
        paletteButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(this, "Choisir une couleur", currentColor);
            if (chosenColor != null) {
                currentColor = chosenColor;
                erasing = false;
            }
        });

        JButton eraseButton = new JButton("Gomme");
        eraseButton.addActionListener(e -> {
            erasing = !erasing;
            eraseButton.setText(erasing ? "Dessiner" : "Gomme");
        });

        colorPanel.add(paletteButton);
        colorPanel.add(eraseButton);

        mainPanel.add(colorPanel, BorderLayout.NORTH);

        // Panel formes
        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new GridLayout(4, 1, 5, 5));
        shapePanel.setBackground(Color.LIGHT_GRAY);

        ShapeButtonDifficile freeDrawButton = new ShapeButtonDifficile("✏", "Libre", this);
        ShapeButtonDifficile circleButton = new ShapeButtonDifficile("O", "Cercle", this);
        ShapeButtonDifficile squareButton = new ShapeButtonDifficile("■", "Carré", this);
        ShapeButtonDifficile triangleButton = new ShapeButtonDifficile("▲", "Triangle", this);

        shapePanel.add(freeDrawButton);
        shapePanel.add(circleButton);
        shapePanel.add(squareButton);
        shapePanel.add(triangleButton);

        mainPanel.add(shapePanel, BorderLayout.WEST);

        // Sliders en bas
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

    public void saveDrawing() {
        String name = JOptionPane.showInputDialog(this, "Nom du dessin :");
        if (name != null && !name.trim().isEmpty()) {
            try (FileOutputStream fos = new FileOutputStream(name + ".ser"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(drawPanel.getImage());
                JOptionPane.showMessageDialog(this, "Dessin sauvegardé !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadDrawing() {
        File dir = new File(".");
        String[] files = dir.list((d, name) -> name.endsWith(".ser"));
        if (files != null && files.length > 0) {
            String selectedFile = (String) JOptionPane.showInputDialog(this, "Choisissez un dessin :", "Charger un dessin",
                    JOptionPane.PLAIN_MESSAGE, null, files, files[0]);
            if (selectedFile != null) {
                try (FileInputStream fis = new FileInputStream(selectedFile); ObjectInputStream ois = new ObjectInputStream(fis)) {
                    drawPanel.setImage((Image) ois.readObject());
                    JOptionPane.showMessageDialog(this, "Dessin chargé !");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun dessin enregistré.");
        }
    }

    public void deleteDrawing() {
        File dir = new File(".");
        String[] files = dir.list((d, name) -> name.endsWith(".ser"));
        if (files != null && files.length > 0) {
            String selectedFile = (String) JOptionPane.showInputDialog(this, "Choisissez un dessin à supprimer :",
                    "Supprimer un dessin", JOptionPane.PLAIN_MESSAGE, null, files, files[0]);
            if (selectedFile != null) {
                File file = new File(selectedFile);
                if (file.delete()) {
                    JOptionPane.showMessageDialog(this, "Dessin supprimé !");
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de la suppression.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun dessin enregistré.");
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
