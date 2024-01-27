package presentacio.clases;

import javax.swing.*;

import presentacio.controladores.ControladorPresentacion;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


//
public class VistaPrincipal extends JFrame {
    private final ControladorPresentacion cp;

    private final JMenuBar menuBarVista = new JMenuBar();
    private final JMenu menuFile = new JMenu("File");

    private final JMenuItem crearDocument = new JMenuItem("Crear Document");
    private final JMenuItem importar = new JMenuItem("Importar");


    private final JMenuItem sortir = new JMenuItem("Sortir");

    /**
     * Creadora per defecte
     * @param cpi -> ControladorPresentacion; controlador de presentació amb que volem crear la vista.
     */
    public VistaPrincipal(ControladorPresentacion cpi){
        cp = cpi;
        inicializar();
    }

    /**
     * S’encarrega d'inicialitzar la vista i preparar tots els valors predefinits que pugui necessitar per a que l’usuari pugui usarla.
     */
    void inicializar(){
        vistaPrincipalPanel vpp = new vistaPrincipalPanel(cp);
        setSize(1280,720);
        getContentPane().add(vpp.getPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBarVista);
        menuBarVista.add(menuFile);
        menuFile.add(crearDocument);
        menuFile.add(importar);
        menuFile.add(new JSeparator());
        menuFile.add(sortir);

        crearDocument.addActionListener(e -> cp.cambiarVPrincipalVAlta("",""));

        importar.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setMultiSelectionEnabled(true);
            jFileChooser.showOpenDialog(this);
            File[] files = jFileChooser.getSelectedFiles();
            if (files.length == 0)
                return;

            int importedFiles = 0;
            for (File file : files) {
                try {
                    String path = file.getPath();
                    cp.importarDocument(path);
                    ++importedFiles;
                }
                catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), file.getName(), JOptionPane.ERROR_MESSAGE);
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Fitxer no vàlid", file.getName(), JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(null, "S'han importat " + importedFiles + "/" + files.length + " documents amb éxit", "Documents importats", JOptionPane.INFORMATION_MESSAGE);

            vpp.refresh();
        });

        sortir.addActionListener(e -> {
            int s= JOptionPane.showOptionDialog(sortir,
                    "Esta segur que desitja sortir",
                    "Sortir",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null);
            if (s==0) {
                cp.sortir();
                ImageIcon icon = new ImageIcon("doc\\santajoseado.png");
                JOptionPane.showMessageDialog(null," \uD83C\uDF85 que pasi unes bones festes  \uD83C\uDF85",
                        "Bon nadal",JOptionPane.INFORMATION_MESSAGE,icon);
                System.exit(0);
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cp.sortir();
                System.exit(0);
            }
        });


    }

}
