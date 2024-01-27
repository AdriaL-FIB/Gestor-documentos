package presentacio.clases;

import presentacio.controladores.ControladorPresentacion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class VistaAlta extends JFrame {

    private final ControladorPresentacion cp;
    VistaAltaPanel vap;

    private final JMenuBar menuBarVista = new JMenuBar();
    private final JMenu menuFile = new JMenu("File");


    private final JMenuItem exportar = new JMenuItem("Exportar");

    private final JMenuItem Sortir = new JMenuItem("Sortir");

    /**
     * Creadora per defecte.
     * @param cpi -> ControladorPresentacion; controlador de presentació amb que volem crear la vista.
     */
    public VistaAlta(ControladorPresentacion cpi, String autor, String titol){
        cp = cpi;
        inicializar(autor, titol);
    }

    /**
     * S’encarrega d'inicialitzar la vista i preparar tots els valors predefinits que pugui necessitar per a que l’usuari pugui usarla.
     */
    public void inicializar(String autor, String titol){
        setSize(1280,720);
        vap = new VistaAltaPanel(cp, autor, titol);
        getContentPane().add(vap.getPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBarVista);
        menuBarVista.add(menuFile);
        menuFile.add(exportar);
        menuFile.add(new JSeparator());
        menuFile.add(Sortir);



        exportar.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(".txt","txt");
            FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(".xml","xml");
            FileNameExtensionFilter propFilter = new FileNameExtensionFilter(".prop","prop");
            jFileChooser.addChoosableFileFilter(txtFilter);
            jFileChooser.addChoosableFileFilter(xmlFilter);
            jFileChooser.addChoosableFileFilter(propFilter);
            jFileChooser.setAcceptAllFileFilterUsed(false);
            if (jFileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
                return;

            String path = jFileChooser.getSelectedFile().getPath();


            String selectedExtension = jFileChooser.getFileFilter().getDescription();

            String[] aux = path.split("\\.");
            String extension = aux[aux.length-1];

            if (!selectedExtension.equals("." + extension))
                path += selectedExtension;

            try {
                cp.exportarDocument(vap.getAutor(), vap.getTitol(), path);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error al exportar fitxer", JOptionPane.ERROR_MESSAGE);
            }
        });

        Sortir.addActionListener(e -> {
            int s= JOptionPane.showOptionDialog(Sortir,
                    "Estas segur",
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
                vap.tornarVP();
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });

    }



    public void consultadoc(){
        vap.consultadoc();
    }


}
