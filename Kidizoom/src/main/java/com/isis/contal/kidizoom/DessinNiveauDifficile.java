package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;

public class DessinNiveauDifficile extends JPanel {
    private DessinView view;
    private DessinController controller;

    public DessinNiveauDifficile() {
        // Création du modèle et du contrôleur
        DessinModel model = new DessinModel(780, 500);
        controller = new DessinController(model, view);
        view = new DessinView(controller);

        // Association des actions aux boutons
        view.saveButton.addActionListener(e -> controller.saveDrawing());
        view.loadButton.addActionListener(e -> controller.loadDrawing());
        view.deleteButton.addActionListener(e -> controller.deleteDrawing());

        // Effacer tout quand le slider est à 100
        view.eraserSlider.addChangeListener(e -> {
            if (view.eraserSlider.getValue() == 100) {
                controller.clearDrawing(); // Utiliser la méthode du contrôleur
                view.eraserSlider.setValue(0);
            }
        });

        // Layout
        setLayout(new BorderLayout());
        add(view, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Créer un JFrame pour contenir le JPanel
            JFrame frame = new JFrame("Dessin Niveau Difficile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            // Ajouter le JPanel DessinNiveauDifficile
            DessinNiveauDifficile panel = new DessinNiveauDifficile();
            frame.add(panel);

            // Rendre la fenêtre visible
            frame.setVisible(true);
        });
    }
}
