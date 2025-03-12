package gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Herbert Caffarel
 */
public class CalculetteFrame extends JFrame {
    private final CalcMenuBar menuBar;
    private int caseSize;  // Variable pour stocker la taille des cases

    public CalculetteFrame() throws HeadlessException {
        this("Ma calculette");
    }

    public CalculetteFrame(String title) throws HeadlessException {
        super(title);
        menuBar = new CalcMenuBar();
        initGui();
    }

    private void initGui() {
        // Demander la taille des cases à l'utilisateur et vérifier la validité
        boolean validInput = false;
        while (!validInput) {
            String input = JOptionPane.showInputDialog(this, "Entrez la taille des cases en pixels (minimum 49px):", "Taille des cases", JOptionPane.QUESTION_MESSAGE);
            
            // Vérifier si l'utilisateur a entré une valeur et la convertir en entier
            try {
                caseSize = Integer.parseInt(input);
                if (caseSize >= 49) {
                    validInput = true; // Si la taille est valide, on sort de la boucle
                } else {
                    // Si la taille est trop petite, afficher un message d'erreur
                    JOptionPane.showMessageDialog(this, "La taille des cases doit être d'au moins 49 pixels.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                // Si l'entrée n'est pas un nombre valide, afficher un message d'erreur
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide supérieur ou égal à 49.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Créer un conteneur global avec un BorderLayout
        JPanel root = new JPanel();
        BorderLayout bl = new BorderLayout(5, 5);
        root.setLayout(bl);

        // Créer 3 conteneurs pour mes 3 espaces
        DisplayPanel display = new DisplayPanel();
        DigitsPanel digits = new DigitsPanel(display, caseSize);  // Passer la taille des cases
        OperationsPanel ops = new OperationsPanel(display, caseSize);  // Passer la taille des cases

        // Les ajouter à mon conteneur de base
        root.add(display, BorderLayout.NORTH);
        root.add(digits, BorderLayout.WEST);
        root.add(ops, BorderLayout.CENTER);

        // Ajouter root à la fenêtre
        this.add(root);
        this.setJMenuBar(menuBar);

        // Configurer le comportement de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
