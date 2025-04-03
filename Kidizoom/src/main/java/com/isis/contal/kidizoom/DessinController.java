package com.isis.contal.kidizoom;

import java.awt.Color;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class DessinController {
    private DessinModel model;
    private DessinView view;

    public DessinController(DessinModel model, DessinView view) {
        this.model = model;
        this.view = view;
    }

    // Sauvegarde du dessin sous format PNG
    public void saveDrawing() {
        String name = JOptionPane.showInputDialog(view, "Nom du dessin :");
        if (name != null && !name.trim().isEmpty()) {
            File file = new File(name + ".png");
            try {
                ImageIO.write(model.getImage(), "png", file);
                JOptionPane.showMessageDialog(view, "Dessin sauvegardé !");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Erreur lors de la sauvegarde.");
            }
        }
    }

    // Chargement d'un dessin
    public void loadDrawing() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Charger un dessin");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images PNG", "png"));

        int returnValue = fileChooser.showOpenDialog(view);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedImage loadedImage = ImageIO.read(file);
                model.setImage(loadedImage);
                view.repaint();
                JOptionPane.showMessageDialog(view, "Dessin chargé !");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Erreur lors du chargement.");
            }
        }
    }

    // Suppression d'un dessin
    public void deleteDrawing() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Supprimer un dessin");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images PNG", "png"));

        int returnValue = fileChooser.showOpenDialog(view);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.delete()) {
                JOptionPane.showMessageDialog(view, "Dessin supprimé !");
            } else {
                JOptionPane.showMessageDialog(view, "Échec de la suppression.");
            }
        }
    }

    // Effacer tout
    public void clearDrawing() {
        model.clear();
        view.repaint();
    }

    // Changer la couleur du crayon
    public void setColor(Color color) {
        model.setCurrentColor(color);
    }
}
