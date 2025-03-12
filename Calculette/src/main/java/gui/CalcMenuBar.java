/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
/**
 *
 * @author lcontal
 */
public class CalcMenuBar extends JMenuBar{
    public CalcMenuBar(){
        JMenu calculation = new JMenu("Calcul");
        JMenuItem help = new JMenuItem("Aide");
        help.addActionListener( e -> {
            String answer = JOptionPane.showInputDialog(calculation,"Entrer votre nom", "Question", JOptionPane.QUESTION_MESSAGE);
            System.out.println("Vous avez repondu : " +answer);
        });
        JMenuItem newCalcl = new JMenuItem("Nouveau");
        newCalcl.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Nouveau Calcul", "Information", JOptionPane.INFORMATION_MESSAGE);
        });
        JMenuItem load = new JMenuItem("Charger");
        load.addActionListener(e -> {
            System.out.println("Load");
        });
        JMenuItem save = new JMenuItem("Sauvegarder");
        save.addActionListener(e -> {
            JOptionPane.showMessageDialog(calculation, "Vous avez cliqué pour sauvegarder", "Information", JOptionPane.WARNING_MESSAGE);
        });
        JMenu properties = new JMenu("Propriétés");
            JMenuItem a = new JMenuItem("a");
            JMenuItem b = new JMenuItem("b");
            JMenuItem c = new JMenuItem("c");
            JMenuItem d= new JMenuItem("d");
            properties.add(a);
            properties.add(b);
            properties.add(c);
            properties.add(d);
        
        calculation.add(newCalcl);
        calculation.add(new JSeparator());
        calculation.add(load);
        calculation.add(save);
        calculation.add(new JSeparator());
        calculation.add(properties);
               
        
        this.add(calculation);
        this.add(help);
    }
}