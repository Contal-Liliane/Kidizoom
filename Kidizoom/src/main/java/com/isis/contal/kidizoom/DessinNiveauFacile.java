import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DessinNiveauFacile extends JFrame {
    private DrawPanel drawPanel;
    private JSlider eraserSlider;
    private Color currentColor = Color.BLACK; // Couleur du pinceau
    private boolean erasing = false; // Indicateur pour savoir si on utilise la gomme

    public DessinNiveauFacile() {
        setTitle("Ardoise Magique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Conteneur principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        // Zone de dessin blanche
        drawPanel = new DrawPanel();
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        
        // Palette de couleurs
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.GRAY);

        JButton blackButton = new JButton("Noir");
        blackButton.setBackground(Color.BLACK);
        blackButton.addActionListener(e -> currentColor = Color.BLACK);
        colorPanel.add(blackButton);

        JButton redButton = new JButton("Rouge");
        redButton.setBackground(Color.RED);
        redButton.addActionListener(e -> currentColor = Color.RED);
        colorPanel.add(redButton);

        JButton greenButton = new JButton("Vert");
        greenButton.setBackground(Color.GREEN);
        greenButton.addActionListener(e -> currentColor = Color.GREEN);
        colorPanel.add(greenButton);

        JButton blueButton = new JButton("Bleu");
        blueButton.setBackground(Color.BLUE);
        blueButton.addActionListener(e -> currentColor = Color.BLUE);
        colorPanel.add(blueButton);

        // Bouton gomme
        JButton eraseButton = new JButton("Gomme");
        eraseButton.addActionListener(e -> {
            erasing = !erasing; // Alterner entre gomme et dessin
            if (erasing) {
                eraseButton.setText("Dessiner"); // Changer le texte du bouton
            } else {
                eraseButton.setText("Gomme");
            }
        });
        colorPanel.add(eraseButton);

        mainPanel.add(colorPanel, BorderLayout.NORTH);
        
        // Slider pour effacer
        eraserSlider = new JSlider(0, 100, 0);
        eraserSlider.setOrientation(JSlider.HORIZONTAL);
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                drawPanel.clear();
                eraserSlider.setValue(0);
            }
        });
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.YELLOW);
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
                        erase(e.getX(), e.getY()); // Gomme
                    } else {
                        draw(e.getX(), e.getY()); // Dessiner
                    }
                }
            });
            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (erasing) {
                        erase(e.getX(), e.getY()); // Gomme
                    } else {
                        draw(e.getX(), e.getY()); // Dessiner
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
                g2.setColor(currentColor); // Utiliser la couleur actuelle
                g2.fillOval(x, y, 10, 10); // Dessiner un petit cercle pour chaque mouvement de souris
                repaint();
            }
        }

        private void erase(int x, int y) {
            if (g2 != null) {
                g2.setColor(Color.WHITE); // Utiliser le blanc pour effacer
                g2.fillRect(x - 5, y - 5, 20, 20); // Effacer une zone de 20x20 autour du point
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
