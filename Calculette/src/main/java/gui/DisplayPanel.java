package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Herbert Caffarel
 */
public class DisplayPanel extends JPanel {
    private JLabel displayLabel;
    public DisplayPanel() {
        this.setLayout(new BorderLayout());
        // Créer un label pour l'afficheur
        displayLabel = new JLabel("0", JLabel.TRAILING);
        // Créer une bordure pour l'afficheur
        // Bordure composée d'une bordure pleine de 1 pixel en noir
        // à l'intérieure de laquelle on met une bordure vide de 3px
        Border border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(3, 6, 3, 6)
        );
        displayLabel.setBorder(border);
        this.add(displayLabel, BorderLayout.NORTH);
    }
    public JLabel getJLabel(){
        return this.displayLabel;
    }

}
