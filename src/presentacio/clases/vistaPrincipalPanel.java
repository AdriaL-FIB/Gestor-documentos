package presentacio.clases;

import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomInvalidException;
import domini.utils.Pair;
import presentacio.controladores.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class vistaPrincipalPanel {

    private final ControladorPresentacion cp;

    private JComboBox filtro;
    private JTextField busqueda;
    private JButton buscarButton;
    private JButton ordenarAButton;
    private JButton ordenarFButton;
    private JButton sortirButton;
    private JPanel panel;
    private JPanel barrabusqueda;
    private JPanel resultado_salir;
    private JPanel busqueda_salir;
    private JTextArea consulta;
    private JList listaResultados;
    private JButton ordenarKDocsButton;
    private JButton eliminarButton;
    private JButton modificarButton;
    private JPanel botonesconsulta;
    private JPanel buscarkdoc;
    private JButton buscarSimilitudButton;
    private JTextField intkdoc;
    private JRadioButton bagOfWordsRadioButton;
    private JRadioButton TFIDFRadioButton;
    private JLabel ksim;
    private JButton guardarExprBoolButton;
    private JRadioButton nameExprRadioButton;
    private JRadioButton rawExprRadioButton;
    private JPanel opcionsBusquedaExprBool;
    private JLabel resultadosLabel;
    private JPanel optionsPanel;

    private String ultimaBusqueda;

    private String ultimAutor;

    private String ultimTitol;

    private String uId;

    //private final ButtonGroup bg;

    /**
     * Creadora.
     * @param cpi -> ControladorPresentacion; controlador de presentació amb que volem crear la vista.
     */
    public vistaPrincipalPanel(ControladorPresentacion cpi) {
        cp=cpi;
        //inicilitzarlisteners();
        ButtonGroup bg = new ButtonGroup();
        bg.add(TFIDFRadioButton);
        bg.add(bagOfWordsRadioButton);
        busqueda.setVisible(true);
        buscarkdoc.setVisible(false);
        ordenarKDocsButton.setVisible(false);
        eliminarButton.setVisible(false);
        opcionsBusquedaExprBool.setVisible(false);
        ordenarFButton.setVisible(true);
        buscarButton.setVisible(false);
        busquedaAutor("");

        filtro.addActionListener(e -> {
            System.out.println(e.getActionCommand());
            if (e.getActionCommand().equals("comboBoxChanged")) {
                ordenarKDocsButton.setVisible(false);
                modificarButton.setVisible(false);
                eliminarButton.setVisible(false);
                buscarkdoc.setVisible(false);
                opcionsBusquedaExprBool.setVisible(false);
                buscarButton.setVisible(true);
                busqueda.setVisible(true);
                busqueda.setText(null);
                ordenarFButton.setVisible(false);
                String selected = (String) filtro.getSelectedItem();
                switch (selected) {
                    case "Autor":
                        buscarButton.setVisible(false);
                        ordenarFButton.setVisible(true);
                        busquedaAutor("");
                        break;
                    case "Expresions Guardades":
                        try{
                            crearListaPalabra(cp.llistarExprBooleanas());
                            ultimaBusqueda = "Expresions Guardades";
                            resultadosLabel.setText("Resultats busqueda");
                        }

                        catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        //Habilitar y desabilitar botons
                        busqueda.setVisible(false);
                        buscarButton.setVisible(false);
                        ordenarKDocsButton.setVisible(false);
                        modificarButton.setVisible(false);
                        eliminarButton.setVisible(false);
                        buscarkdoc.setVisible(false);
                        //consulta.setText(null);
                        break;

                    case "Expresio Booleana":
                        //consulta.setText(null);
                        opcionsBusquedaExprBool.setVisible(true);
                        //CardLayout cl = (CardLayout) optionsPanel.getLayout();
                        //JPanel testPanel = new JPanel();
                        //cl.show(testPanel, "test");
                        break;
                    case "Paraula":
                        break;
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerBusqueda();
            }
        });


        sortirButton.addActionListener(e -> {
            int s= JOptionPane.showOptionDialog(null,
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

        listaResultados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList lt = (JList) e.getSource();

                modificarButton.setEnabled(lt.getSelectedValue() != null);

                if(ultimaBusqueda.equals("Autor") && !listaResultados.isSelectionEmpty()){
                    modificarButton.setVisible(true);
                    modificarButton.setText("Modificar Autor");
                }
                else if (ultimaBusqueda.equals("Expresions Guardades") && !listaResultados.isSelectionEmpty()){
                    String S = (String) lt.getSelectedValue();
                    uId = S;
                    //consulta.setText("La expresion Booleana de: " + S +"    es: " + cp.getStringExpBool(S));
                    modificarButton.setVisible(true);
                    modificarButton.setText("Modificar ExprBool");
                    eliminarButton.setVisible(true);
                }
                else if (ultimaBusqueda.equals("Titols Autor")) {
                    ultimTitol = (String) lt.getSelectedValue();
                    buscarkdoc.setVisible(true);
                    modificarButton.setVisible(true);
                    modificarButton.setText("Consultar Document");
                    eliminarButton.setVisible(true);
                }

                if(e.getClickCount() == 2){
                    switch (ultimaBusqueda) {
                        case "Expresions Guardades": {
                            //consulta.setText("La expresion Booleana de: " + S +"    es: " + cp.getStringExpBool(S));
                            filtro.setSelectedIndex(3);
                            busqueda.setText(uId);
                            nameExprRadioButton.setSelected(true);

                            break;
                        }
                        case "Autor": {
                            ultimAutor = (String) lt.getSelectedValue();
                            busquedaTitulosAutor(ultimAutor);

                            //Cambiar a busqueda de titols autor
                            ultimaBusqueda = "Titols Autor";
                            filtro.setSelectedIndex(1);
                            busqueda.setText(ultimAutor);

//                            ordenarKDocsButton.setVisible(false);
//                            modificarButton.setVisible(true);
//                            eliminarButton.setVisible(false);


                            break;
                        }
                        case "Paraula":
                        case "Similitud":
                        case "Expresio Bool": {
                            String separar = (String) lt.getSelectedValue();
                            // String[] separat = separar.split("Autor: ")
                            String[] paraules = separar.split("[:-]");
                            //Elimina palabras initulies
                            paraules = Arrays.stream(paraules).filter(p -> !p.equals("Autor") && !p.equals(" Titol")).map(String::strip).toArray(String[]::new);
                            ultimAutor = paraules[0];
                            ultimTitol = paraules[1];
                            //consulta.setText(cp.obtenirContingut(ultimAutor, ultimTitol));
                            buscarkdoc.setVisible(true);
                            modificarButton.setVisible(true);
                            modificarButton.setText("Consultar Document");
                            eliminarButton.setVisible(true);
                            break;
                        }
                    }
                }
            }
        });

        ordenarAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ordre;
                if (ordenarAButton.getText().equals("Alfabèticament ↓")) {
                    ordre = 0;
                    ordenarAButton.setText("Alfabèticament ↑");
                } else {
                    ordre = 1;
                    ordenarAButton.setText("Alfabèticament ↓");
                }

                if(ultimaBusqueda.equals("Autor") || ultimaBusqueda.equals("Titols Autor") || ultimaBusqueda.equals(
                        "Expresions Guardades")){
                    crearListaPalabra(cp.ordenararray(ordre));
                }
                else {
                    crearListaPairs(cp.ordenarLlistaTitolAutor(ordre));
                }
            }
        });

        ordenarFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ultimaBusqueda.equals("Autor")){
                    crearListaPalabra(cp.ordenararray(3));
                }
                else {
                    crearListaPairs(cp.ordenarndocs());
                }
            }
        });

        ordenarKDocsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearListaPairs(cp.ordenarkdocs());
            }
        });

        buscarSimilitudButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(intkdoc.getText().equals("") || Integer.parseInt(intkdoc.getText().toString()) < 0) {
                        JOptionPane.showMessageDialog(null, "introdueixi un valor valid", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else{
                    if(TFIDFRadioButton.isSelected()){
                        int k = Integer.parseInt(intkdoc.getText().toString());

                        try {
                            crearListaPairs(cp.llistarDocumentsSimilars(ultimAutor,ultimTitol,k,0));
                        }
                        catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    else if(bagOfWordsRadioButton.isSelected()){
                        int k = Integer.parseInt(intkdoc.getText().toString());
                        try {
                            crearListaPairs(cp.llistarDocumentsSimilars(ultimAutor,ultimTitol,k,1));
                        }
                        catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    ultimaBusqueda = "Similitud";
                    ordenarKDocsButton.setVisible(true);
                    modificarButton.setVisible(false);
                    eliminarButton.setVisible(false);
                    buscarkdoc.setVisible(false);
                    ordenarFButton.setVisible(true);
                    //buscarSimilitudButton.setVisible(false);
                    resultadosLabel.setText("Els documents semblants a "+ ultimAutor + " , "+ ultimTitol + " son:");
                }
                }

        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ultimaBusqueda.equals("Expresions Guardades")) {
                    try {
                        String expBool = cp.getStringExpBool(uId);
                        String eb = JOptionPane.showInputDialog("Introdueix la nova expressió per " + uId + " : " + expBool);
                        if (eb == null) return;
                        cp.modificarExprBool(uId, eb);
                        JOptionPane.showMessageDialog(modificarButton, "S'ha modificat correctament"
                                , "Modificar", JOptionPane.INFORMATION_MESSAGE);
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }


                } else if (ultimaBusqueda.equals("Paraula") || ultimaBusqueda.equals("Similitud")
                        || ultimaBusqueda.equals("Expresio Bool") || ultimaBusqueda.equals("Titols Autor")) {
                    cp.cridaConsultaDocument(ultimAutor, ultimTitol);
                } else if (ultimaBusqueda.equals("Autor")) {
                    if (listaResultados.getSelectedValue() == null) return;
                    String oldAutor = listaResultados.getSelectedValue().toString();
                    if (oldAutor == null) return;
                    String newAutor = JOptionPane.showInputDialog("Introdueix nou nom per a l'autor: " + oldAutor);
                    if (newAutor != null) {
                        try {
                            cp.modificarAutor(oldAutor, newAutor);
                        } catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    crearListaPalabra(cp.LlistarAutorsPrefix(busqueda.getText()));
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ultimaBusqueda.equals("Expresions Guardades")) {
                    int s = JOptionPane.showOptionDialog(eliminarButton,
                            "Eliminar EXPBOOL",
                            "Esta Seguro que desea eliminar la expresion Booleana",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            null,
                            null);
                    if (s == 0) {
                        try {
                            cp.donarBaixaExprBool(uId);
                            listaResultados.setModel(new DefaultListModel());
                            JOptionPane.showMessageDialog(eliminarButton, "Se ha borrado correctamente"
                                    , "Borrar", JOptionPane.WARNING_MESSAGE);
                        } catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
                else if (ultimaBusqueda.equals("Paraula") || ultimaBusqueda.equals("Similitud")
                        || ultimaBusqueda.equals("Expresio Bool") || ultimaBusqueda.equals("Titols Autor")){
                    int s = JOptionPane.showOptionDialog(eliminarButton,
                            "Eliminar Document",
                            "Esta Seguro que desea eliminar el document",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            null,
                            null);

                    if (s == 0) {
                        try {
                            cp.donarBaixaDocument(ultimAutor, ultimTitol);
                            listaResultados.setModel(new DefaultListModel());
                            filtro.setSelectedIndex(0);
                            busqueda.setText("");
                            JOptionPane.showMessageDialog(eliminarButton, "Se ha borrado correctamente"
                                    , "Borrar", JOptionPane.WARNING_MESSAGE);
                        } catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }

            }
        });

        guardarExprBoolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rawExprRadioButton.isSelected()) {
                    String ebool = busqueda.getText();
                    String nom = JOptionPane.showInputDialog("Introdueixi un nom per l'expresió \"" + ebool + "\"");
                    if (nom == null) return;
                    try {
                        cp.donarAtaExprBool(nom, ebool);
                        JOptionPane.showMessageDialog(null, "S'ha guardat correctament", "Guardar", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        busqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println(e.paramString());
                //super.keyTyped(e);
                String filtros = (String) filtro.getSelectedItem();
                if (filtros.equals("Autor"))
                    busquedaAutor(busqueda.getText());
            }
        });
        busqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
            }
        });
        nameExprRadioButton.addItemListener(new ItemListener() {
            /**
             * Invoked when an item has been selected or deselected by the user.
             * The code written for this method performs the operations
             * that need to occur when an item is selected (or deselected).
             *
             * @param e the event to be processed
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    guardarExprBoolButton.setEnabled(false);
                }
                else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    guardarExprBoolButton.setEnabled(true);
                }
            }
        });
    }




    private void crearListaPalabra(String[] lista){
        listaResultados.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return lista.length;
            }
            @Override
            public String getElementAt(int index) {
                return lista[index];
            }
        });
    }

    /**
     * Donada una llista de pairs ens permet visualitzar-la.
     * @param llista -> ArrayList<Pair<String,String>>; llista de parelles de strings que volem visualitzar.
     */
    private void crearListaPairs(ArrayList<Pair<String,String>> llista){

        DefaultListModel<String> lmodel = new DefaultListModel<String>();
        for (Pair<String, String> stringStringPair : llista) {
            String elem = "Autor: " + stringStringPair.getFirst() +
                    " - Titol: " + stringStringPair.getSecond();
            lmodel.addElement(elem);
        }
        listaResultados.setModel(lmodel);
    }

    private void hacerBusqueda() {
        String filtros = (String) filtro.getSelectedItem();
        String paraula_busqueda = busqueda.getText();

        ordenarAButton.setText("Alfabèticament ↓");

        switch (filtros) {
            case "Autor":
                busquedaAutor(paraula_busqueda);
                break;
            case "Titols Autor":
                busquedaTitulosAutor(paraula_busqueda);
                break;
            case "Paraula":
                try{
                    crearListaPairs(cp.llistarDocumentsParaula(paraula_busqueda));
                    ultimaBusqueda = "Paraula";
                    ordenarKDocsButton.setVisible(false);
                    ordenarFButton.setVisible(true);
                    modificarButton.setVisible(false);
                    eliminarButton.setVisible(false);
                    buscarkdoc.setVisible(false);
                }
                catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                //consulta.setText(null);
                break;
            case "Expresio Booleana":
                busquedaExprBool(paraula_busqueda);
                break;
            default:
                busquedaExprBoolGuardades();
                break;
        }
    }

    private void busquedaAutor(String prefix) {
        crearListaPalabra(cp.LlistarAutorsPrefix(prefix));
        ordenarAButton.setText("Alfabèticament ↓");
        ultimaBusqueda = "Autor";
        resultadosLabel.setText("Autors començats per '" + prefix + "'");

        ordenarKDocsButton.setVisible(false);
        modificarButton.setVisible(false);
        eliminarButton.setVisible(false);
        buscarkdoc.setVisible(false);
        //consulta.setText(null);
    }

    private void busquedaTitulosAutor(String autor) {
        try{
            ordenarAButton.setText("Alfabèticament ↓");
            crearListaPalabra(cp.llistarTitolsAutor(autor));
            ultimAutor = autor;
            ultimaBusqueda = "Titols Autor";
            resultadosLabel.setText("Llista de títols de l'autor " + autor);

            ordenarKDocsButton.setVisible(false);
            modificarButton.setVisible(false);
            eliminarButton.setVisible(false);
            buscarkdoc.setVisible(false);
        }
        catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }



        //consulta.setText(null);
    }

    private void busquedaExprBool(String text) {
        if (nameExprRadioButton.isSelected()) {
            try{
                crearListaPairs(cp.llistarDocumentsExprBool(text));
                ordenarFButton.setVisible(true);
            }
            catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
        else {
            try{
                crearListaPairs(cp.llistarDocumentsRawExprBool(text));
                ordenarFButton.setVisible(true);
            }
            catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

        ultimaBusqueda = "Expresio Bool";
        resultadosLabel.setText("Llista de documents que compleixen la expressió booleana " + text);

        ordenarKDocsButton.setVisible(false);
        modificarButton.setVisible(false);
        eliminarButton.setVisible(false);
        buscarkdoc.setVisible(false);
        //consulta.setText(null);
    }

    private void llistarExprBoolGuardades() {
        crearListaPalabra(cp.llistarExprBooleanas());
        ultimaBusqueda = "Expresions Guardades";
        resultadosLabel.setText("Resultats busqueda");
        ordenarKDocsButton.setVisible(false);
        modificarButton.setVisible(false);
        eliminarButton.setVisible(false);
        buscarkdoc.setVisible(false);
    }

    private void busquedaExprBoolGuardades() {

        crearListaPalabra(cp.llistarExprBooleanas());
        ultimaBusqueda = "Expresions Guardades";
        resultadosLabel.setText("Resultats busqueda");

        ordenarKDocsButton.setVisible(false);
        modificarButton.setVisible(false);
        eliminarButton.setVisible(false);
        buscarkdoc.setVisible(false);
    }

    public void refresh() {
        hacerBusqueda();
    }


    /**
     * Retorna el panel sencer.
     * @return JPanel, retorna el panel sencer.
     */
    public JPanel getPanel(){
        return panel;
    }
}
