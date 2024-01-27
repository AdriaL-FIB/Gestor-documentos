package presentacio.clases;

import presentacio.controladores.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaAltaPanel {


    private ControladorPresentacion cp;
    private JLabel autorCabecera;
    private JPanel titolPanel;
    private JPanel autorPanel;
    private JLabel TitolCabezera;
    private JTextField titolField;
    private JTextField autorField;
    private JLabel contingutCapcelera;
    private JPanel contingutPanel;
    private JButton tornar;
    private JButton guardar;
    private JPanel Botones;
    private JPanel panel;
    private JTextArea contingut;
    private JButton modificarButton;
    private JScrollPane scrollPane;


    //indica si estem modificant
    private boolean modificant = false;

    private boolean consultant = false;

    private String autor, titol;

    public VistaAltaPanel(ControladorPresentacion cpi, String a, String t) {
        cp = cpi;
        modificarButton.setVisible(false);
        autor = a;
        titol = t;

        //contingut.setFont(new Font("Serif", Font.ITALIC, 16));
        contingut.setLineWrap(true);
        contingut.setWrapStyleWord(true);

        try {
            if(!autor.isBlank()) contingut.setText(cp.obtenirContingut(autor,titol));
        }
        catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        autorField.setText(autor);
        titolField.setText(titol);

        scrollPane.getVerticalScrollBar().setValue(0);

        tornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tornarVP();
            }

        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCanvis();
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titolField.setEditable(true);
                contingut.setEditable(true);
                guardar.setVisible(true);
                modificarButton.setVisible(false);
                modificant = true;
                consultant = false;
            }
        });
    }

    public void tornarVP() {
        if(consultant){
            consultant = false;
            cp.cambiarVAltaVPrincipal();
            return;
        }

        int guardar = JOptionPane.showOptionDialog(null,
                "Desitja guardar abans de tornar",
                "tornar",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        if (guardar==0) {
            guardarCanvis();
        }
        else if(guardar==1){
            cp.cambiarVAltaVPrincipal();
        }
    }

    private void guardarCanvis() {
        autor = autorField.getText();
        if(titolField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Titol no pot ser null", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nouTitol = titolField.getText();

        String Contingut = contingut.getText();
        if (!modificant) {
            try{
                cp.donarAltaDocument(autor, nouTitol, Contingut);
                titol = nouTitol;
                JOptionPane.showMessageDialog(null, "S'ha guardat correctament",
                        "Guardar", JOptionPane.INFORMATION_MESSAGE);
                cp.cambiarVAltaVPrincipal();
            }
            catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            try {
                if(!titol.equals(nouTitol)){
                    cp.modificarTitol(autor,titol,nouTitol);
                }
                cp.modificarDocument(autor,nouTitol,Contingut);
                JOptionPane.showMessageDialog(null, "S'ha modificat correctament"
                        , "Modificar", JOptionPane.INFORMATION_MESSAGE);
                titol = nouTitol;
                cp.cambiarVAltaVPrincipal();
            }
            catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void consultadoc(){
        consultant = true;

        modificarButton.setVisible(true);
        guardar.setVisible(false);
        contingut.setEditable(false);
        autorField.setEditable(false);
        titolField.setEditable(false);

    }

    /**
     * Retorna el panel sencer.
     * @return Jpanel, el panel sencer.
     */
    public JPanel getPanel(){
        return panel;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitol() {
        return titol;
    }
}
