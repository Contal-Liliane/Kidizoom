package com.isis.contal.kidizoom;

import java.awt.Color;

public class DessinController {

    private DessinModel model;
    private DessinView view;
    private String selectedShape = "Cercle";  // Forme par défaut (Cercle)

    public DessinController(DessinModel model, DessinView view) {
        this.model = model;
        this.view = view;
    }

    // Définir la couleur actuelle
    public void setColor(Color color) {
        model.setCurrentColor(color);
    }

    // Définir la forme sélectionnée
    public void setShape(String shape) {
        this.selectedShape = shape;
    }

    // Sauvegarder le dessin
    public void saveDrawing() {
        // Implémentez la logique de sauvegarde ici
    }

    // Charger un dessin
    public void loadDrawing() {
        // Implémentez la logique de chargement ici
    }

    // Supprimer un dessin
    public void deleteDrawing() {
        // Implémentez la logique de suppression ici
    }

    // Effacer le dessin
    public void clearDrawing() {
        model.clear();
        view.repaint();  // Redessiner la vue après avoir effacé le dessin
    }

    // Obtenir la forme sélectionnée
    public String getSelectedShape() {
        return selectedShape;
    }

    // Accéder au modèle (si nécessaire, par exemple pour récupérer l'image actuelle)
    public DessinModel getModel() {
        return model;
    }

    // Accéder à la vue si nécessaire
    public DessinView getView() {
        return view;
    }
}
