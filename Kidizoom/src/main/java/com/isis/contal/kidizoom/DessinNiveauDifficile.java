package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;

public class DessinNiveauDifficile extends JPanel {
    private DessinView view;
    private DessinController controller;

    public DessinNiveauDifficile() {
        // Création du modèle
        DessinModel model = new DessinModel(780, 500);
        
        // Création du contrôleur et de la vue, et liaison entre eux
        controller = new DessinController(model, null);  // Laisser la vue être initialisée après le contrôleur
        view = new DessinView(controller);  // Passer le contrôleur à la vue après initialisation

        // Initialisation de la vue
        controller.getView();

        // Association des actions aux boutons
        view.saveButton.addActionListener(e -> controller.saveDrawing());
        view.loadButton.addActionListener(e -> controller.loadDrawing());
        view.deleteButton.addActionListener(e -> controller.deleteDrawing());

        // Layout
        setLayout(new BorderLayout());
        add(view, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dessin Niveau Difficile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            DessinNiveauDifficile panel = new DessinNiveauDifficile();
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}
