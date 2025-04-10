package com.isis.contal.kidizoom;
//Liliane
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeButton extends JButton {
    private String shapeName;
    private DessinNiveauFacile dessinNiveauFacile;  // Référence à DessinNiveauFacile

    // Constructeur modifié pour accepter l'instance de DessinNiveauFacile
    public ShapeButton(String label, String shape, DessinNiveauFacile dessinNiveauFacile) {
        super(label);
        this.shapeName = shape;
        this.dessinNiveauFacile = dessinNiveauFacile;

        // ActionListener pour changer la forme sélectionnée
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dessinNiveauFacile.selectedShape = shapeName;  // Utilisation de l'instance de DessinNiveauFacile
            }
        });
    }
}
