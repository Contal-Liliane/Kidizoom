package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OperationsPanel extends JPanel {

    private String[] operations = {
        "C", "CE",
        "+", "-", 
        "x", "/"
    };
    private DisplayPanel operationsPanel;
    private int caseSize;
    private double total = 0; // Variable pour stocker le total de l'addition
    private String currentOperation = "";
    private boolean isNewOperation = false;  // To know if the next number starts after an operation

    public OperationsPanel(DisplayPanel operationsPanel, int caseSize) {
        this.operationsPanel = operationsPanel;
        this.caseSize = caseSize;
        setLayout(new GridLayout(0, 2, 5, 3));

        for (String operation : operations) {
            JButton jb2 = new JButton(operation);

            // Définir la taille des boutons
            jb2.setPreferredSize(new Dimension(caseSize, caseSize));

            this.add(jb2);
            jb2.addActionListener((f) -> {
                String currentText = operationsPanel.getJLabel().getText();
                
                if ("C".equals(operation)) {
                    operationsPanel.getJLabel().setText("0");
                    total = 0; // Reset the total
                    currentOperation = "";
                    isNewOperation = false;
                }
                
                if ("CE".equals(operation)) {
                    operationsPanel.getJLabel().setText("0");
                }

                if ("+".equals(operation)) {
                    if (!isNewOperation) {
                        total = Double.parseDouble(currentText); // Set the first operand
                        operationsPanel.getJLabel().setText(currentText + " + ");
                    } else {
                        total += Double.parseDouble(currentText); // Add current value to total
                        operationsPanel.getJLabel().setText(String.valueOf(total) + " + ");
                    }
                    currentOperation = "+";
                    isNewOperation = true; // Ready for the next number
                }

                if ("-".equals(operation)) {
                    if (!isNewOperation) {
                        total = Double.parseDouble(currentText); // Set the first operand
                        operationsPanel.getJLabel().setText(currentText + " - ");
                    } else {
                        total -= Double.parseDouble(currentText); // Subtract current value from total
                        operationsPanel.getJLabel().setText(String.valueOf(total) + " - ");
                    }
                    currentOperation = "-";
                    isNewOperation = true;
                }

                if ("x".equals(operation)) {
                    if (!isNewOperation) {
                        total = Double.parseDouble(currentText);
                        operationsPanel.getJLabel().setText(currentText + " x ");
                    } else {
                        total *= Double.parseDouble(currentText); // Multiplication
                        operationsPanel.getJLabel().setText(String.valueOf(total) + " x ");
                    }
                    currentOperation = "x";
                    isNewOperation = true;
                }

                if ("/".equals(operation)) {
                    if (!isNewOperation) {
                        total = Double.parseDouble(currentText);
                        operationsPanel.getJLabel().setText(currentText + " / ");
                    } else {
                        total /= Double.parseDouble(currentText); // Division
                        operationsPanel.getJLabel().setText(String.valueOf(total) + " / ");
                    }
                    currentOperation = "/";
                    isNewOperation = true;
                }

                if ("=".equals(operation)) {
                    double currentValue = Double.parseDouble(currentText);
                    if ("+".equals(currentOperation)) {
                        total += currentValue; // Perform final addition
                    } else if ("-".equals(currentOperation)) {
                        total -= currentValue; // Perform final subtraction
                    } else if ("x".equals(currentOperation)) {
                        total *= currentValue; // Perform final multiplication
                    } else if ("/".equals(currentOperation)) {
                        total /= currentValue; // Perform final division
                    }
                    operationsPanel.getJLabel().setText(String.valueOf(total)); // Display the result
                    total = 0; // Reset after calculation
                    currentOperation = "";
                    isNewOperation = false;
                }
            });

            jb2.setBackground(Color.WHITE); // Set white for all buttons

            if ("CE".equals(operation)) {
                jb2.setForeground(Color.RED); // Red for the CE button
            }
        }
    }
}
