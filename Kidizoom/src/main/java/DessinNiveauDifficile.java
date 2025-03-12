import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DessinNiveauDifficile extends JFrame {
    private DrawPanel drawPanel;
    private JSlider eraserSlider, brushSizeSlider, eraserSizeSlider;
    private Color currentColor = Color.BLACK; // Couleur du pinceau
    private boolean erasing = false; // Indicateur pour savoir si on utilise la gomme
    private int brushSize = 10; // Taille du pinceau par défaut
    private int eraserSize = 20; // Taille de la gomme par défaut

    public DessinNiveauDifficile() {
        setTitle("Ardoise Magique - Niveau Difficile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Conteneur principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        // Zone de dessin blanche
        drawPanel = new DrawPanel();
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        
        // Barre du haut : Sélection de couleur et gomme
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.GRAY);

        // Bouton pour choisir une couleur personnalisée
        JButton colorChooserButton = new JButton("Choisir La Couleur");
        colorChooserButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choisissez une couleur", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                erasing = false; // Assure que le pinceau est actif après le choix de couleur
            }
        });
        topPanel.add(colorChooserButton);

        // Bouton gomme
        JButton eraseButton = new JButton("Gomme");
        eraseButton.addActionListener(e -> {
            erasing = !erasing; // Alterner entre gomme et dessin
            eraseButton.setText(erasing ? "Dessiner" : "Gomme");
        });
        topPanel.add(eraseButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Barre du bas : Sliders pour la taille du pinceau et gomme
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.YELLOW);
        bottomPanel.setLayout(new GridLayout(3, 2, 10, 5));

        brushSizeSlider = new JSlider(1, 50, 10);
        brushSizeSlider.setOrientation(JSlider.HORIZONTAL);
        brushSizeSlider.addChangeListener(e -> brushSize = brushSizeSlider.getValue());

        eraserSizeSlider = new JSlider(5, 50, 20);
        eraserSizeSlider.setOrientation(JSlider.HORIZONTAL);
        eraserSizeSlider.addChangeListener(e -> eraserSize = eraserSizeSlider.getValue());

        eraserSlider = new JSlider(0, 100, 0);
        eraserSlider.setOrientation(JSlider.HORIZONTAL);
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                drawPanel.clear();
                eraserSlider.setValue(0);
            }
        });

        bottomPanel.add(new JLabel("Taille du crayon:"));
        bottomPanel.add(brushSizeSlider);
        bottomPanel.add(new JLabel("Taille de la gomme:"));
        bottomPanel.add(eraserSizeSlider);
        bottomPanel.add(new JLabel("Effacer tout:"));
        bottomPanel.add(eraserSlider);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    // Panneau de dessin
    private class DrawPanel extends JPanel {
        private Image image;
        private Graphics2D g2;
        
        public DrawPanel() {
            setPreferredSize(new Dimension(600, 400));
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (erasing) {
                        erase(e.getX(), e.getY());
                    } else {
                        draw(e.getX(), e.getY());
                    }
                }
            });
            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (erasing) {
                        erase(e.getX(), e.getY());
                    } else {
                        draw(e.getX(), e.getY());
                    }
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image == null) {
                image = createImage(getWidth(), getHeight());
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                clear();
            }
            g.drawImage(image, 0, 0, null);
        }

        private void draw(int x, int y) {
            if (g2 != null) {
                g2.setColor(currentColor);
                g2.fillOval(x, y, brushSize, brushSize);
                repaint();
            }
        }

        private void erase(int x, int y) {
            if (g2 != null) {
                g2.setColor(Color.WHITE);
                g2.fillRect(x - eraserSize / 2, y - eraserSize / 2, eraserSize, eraserSize);
                repaint();
            }
        }

        public void clear() {
            if (g2 != null) {
                g2.setPaint(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                repaint();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DessinNiveauDifficile().setVisible(true));
    }
}
