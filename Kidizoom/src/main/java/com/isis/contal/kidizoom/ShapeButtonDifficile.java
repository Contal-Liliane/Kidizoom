package com.isis.contal.kidizoom;
//Liliane
import javax.swing.*;
import java.awt.event.*;

public class ShapeButtonDifficile extends JButton {
    private String shapeName;
    private DessinNiveauDifficile dessinNiveauDifficile;

    public ShapeButtonDifficile(String label, String shape, DessinNiveauDifficile parent) {
        super(label);
        this.shapeName = shape;
        this.dessinNiveauDifficile = parent;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dessinNiveauDifficile.setSelectedShape(shapeName);
            }
        });
    }
}
