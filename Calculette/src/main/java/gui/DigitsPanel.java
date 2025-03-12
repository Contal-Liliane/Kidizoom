package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DigitsPanel extends JPanel {
    private DisplayPanel digitsPanel;
    private int caseSize;
    private double firstOperand = 0;  // Premier opérande
    private String currentOperation = ""; // Opération en cours

    public DigitsPanel(DisplayPanel digitsPanel, int caseSize) {
        GridLayout gl = new GridLayout(0, 3, 3, 3);
        this.setLayout(gl);
        this.digitsPanel = digitsPanel;
        this.caseSize = caseSize;

        String[] numbers = {
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            "0", ".", "="
        };

        for (String value : numbers) {
            JButton jb = new JButton(value);
            
            // Définir la taille des boutons avec la taille spécifiée
            jb.setPreferredSize(new Dimension(caseSize, caseSize));  // Utiliser la taille passée
            
            this.add(jb);
            jb.addActionListener((e) -> {
                if (!"=".equals(value)) {
                    String currentText = digitsPanel.getJLabel().getText();
                    if ("0".equals(currentText)) {
                        currentText = "";
                    }
                    if (".".equals(value)) {
                        if (!currentText.contains(".")) {
                            digitsPanel.getJLabel().setText(currentText + value);
                        }
                    } else {
                        digitsPanel.getJLabel().setText(currentText + value);
                    }
                } else {
                    // Calculer le résultat lors du clic sur "="
                    double currentValue = Double.parseDouble(digitsPanel.getJLabel().getText());
                    if ("=".equals(currentOperation)) {
                        firstOperand += currentValue; // Effectuer l'addition
                    }
                    // Implémenter les autres opérations ici comme la soustraction, multiplication, etc.
                    digitsPanel.getJLabel().setText(String.valueOf(firstOperand)); // Afficher le résultat
                    currentOperation = ""; // Réinitialiser l'opération
                }
            });
        }
    }
}
