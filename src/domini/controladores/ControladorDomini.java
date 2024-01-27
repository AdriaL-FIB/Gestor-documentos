package domini.controladores;

import domini.clases.*;
import domini.exceptions.ExprBoolIncorrectaException;
import domini.exceptions.NomInvalidException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.utils.Pair;
import persistencia.controladores.ControladorPersistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ControladorDomini {

    //Atributos

    private ControladorIndex i;
    private ControladorExprBool eb;
    private ControladorPersistencia cp;


    /**
     * Inicialitza el contorlador de dominio per a que pugui ser funcional
     */
    public ControladorDomini(){
        cp = new ControladorPersistencia();
        //IndexAutors IndexDocument IndexParaula IndexFrase
        i = new ControladorIndex((IndexAutors) cp.carregarEstat("IndexAutors"), (IndexDocument) cp.carregarEstat("IndexDocument"),
                                (IndexParaula) cp.carregarEstat("IndexParaula"), (IndexFrase) cp.carregarEstat("IndexFrase"));

        ConjuntExprBool cjtEB = (ConjuntExprBool) cp.carregarEstat("ExprBools");
        if (cjtEB == null) {
            eb = new ControladorExprBool(i);
        }
        else {
            eb = new ControladorExprBool(i, cjtEB);
        }
    }

    /**
     * Donats un autor, un títol i un contingut donem d'alta un document.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @param contingut -> String; contingut del document.
     * @throws JaExisteixException Si ja existeix un document amb el títol {@code titol}.
     * @throws NomInvalidException Si el títol no és vàlid, donat que és un string buit.
     */
    public void donarAltaDocument(String autor, String titol, String contingut) throws JaExisteixException, NomInvalidException {
        i.altaDocument(autor, titol, contingut);
        cp.guardarDocument(i.obtenirId(autor, titol), contingut);
    }

    /**
     * Si existeix el document identificat per l'autor i el títol, l'eliminem.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     */
    public void donarBaixaDocument(String autor, String titol) throws NoExisteixException {
        cp.eliminarDocument(i.obtenirId(autor,titol));
        i.baixaDocument(autor, titol);
    }

    /**
     * Si existeix el títol introduït, modifiquem el seu contingut.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @param nouContingut -> String; nou contingut desitjat pel document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     * @throws JaExisteixException Si ja existeix un document amb el títol {@code titol} al disc.
     */
    public void modificarDocument(String autor, String titol, String nouContingut) throws NoExisteixException/*, JaExisteixException*/ {
        i.modificarDocument(autor, titol, nouContingut);
        cp.guardarDocument(i.obtenirId(autor, titol), nouContingut);
    }

    public void exportarDocument(String autor, String titol, String path) throws NoExisteixException, /*JaExisteixException,*/ IOException {
        String contingut = obtenirContingut(autor, titol);
        cp.exportarDocument(autor, titol, contingut, path);
    }
    public void importarDocument(String path) throws JaExisteixException, NomInvalidException, IOException {
        String autor, titol, contingut;


        String rawContent = cp.importarDocument(path);
        String[] aux = path.split("\\.");
        String extension = aux[aux.length-1];
        switch (extension) {
            case "xml":
                try {
                    aux = rawContent.split("<autor>|</autor>|<titol>|</titol>|<contingut>|</contingut>");
                    autor = aux[1];
                    titol = aux[3];
                    contingut = aux[5];
                } catch (RuntimeException ex) {
                    throw new RuntimeException("Fitxer mal format. L'arxiu ha de contenir els tags <autor> <titol> i <contingut>");
                }
                break;
            case "prop":
                try {
                    String[] lineas = cp.importarDocument(path).split("[\\r\\n]+", 3);
                    autor = lineas[0].split("👉")[1];
                    titol = lineas[1].split("👉")[1];
                    contingut = lineas[2].split("👉")[1];
                } catch (RuntimeException ex) {
                    throw new RuntimeException("Fitxer mal format");
                }
                break;
            default:
                try {
                    String[] lineas = cp.importarDocument(path).split("[\\r\\n]+", 3);
                    autor = lineas[0];
                    titol = lineas[1];
                    contingut = lineas[2];
                } catch (RuntimeException ex) {
                    throw new RuntimeException("Fitxer mal format. La primera i segona linea han de contenir l'autor i el títol respectivament");
                }
                break;

        }
        donarAltaDocument(autor, titol, contingut);
    }

    /**
     * Obtenir el número de documents.
     * @return enter, número de documents.
     */
    public int numDocs(){
        return i.numDocs();
    }


    /**
     * Guarda el document indicat.
     * @param Autor -> String; nom de l'autor.
     * @param Titol -> String; títol del document.
     * @param Contingut -> String; contingut del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     * @throws JaExisteixException Si ja existeix un document amb el títol {@code titol} a disc.
     */
    public void guardarDocument(String Autor, String Titol, String Contingut) throws NoExisteixException {
        int id = i.obtenirId(Autor,Titol);
        cp.guardarDocument(id, Contingut);
    }


    /**
     * Modifica el títol del document indicat de l'autor, si existeix.
     * @param autor -> String; nom de l'autor.
     * @param oldTitol -> String; títol actual del document.
     * @param newTitol -> String; nou títol al qual es vol canviar.
     * @throws NoExisteixException si no es troba el titol amb el nom {@code oldTitol} o l'autor amb nom {@code autor}
     * @throws JaExisteixException si ja existeix un títol amb el nom {@code newTitol}
     */
    public void modificarTitol(String autor, String oldTitol, String newTitol) throws NoExisteixException, JaExisteixException, NomInvalidException {
        i.modificarTitol(autor, oldTitol, newTitol);
    }

    /**
     * Modifica nom d'un autor existent.
     * @param oldName -> String; nom actual de l'autor.
     * @param newName -> String; nou nom desitjat de l'autor.
     * @throws NoExisteixException si no es troba l'autor amb el nom {@code oldName}.
     * @throws JaExisteixException si ja existeix un autor amb el nom {@code newName}.
     */
    public void modificarAutor(String oldName, String newName) throws NoExisteixException, JaExisteixException, NomInvalidException {
        i.modificarAutor(oldName, newName);
    }

    /**
     * Obtenir llista de tots els autors.
     * @return array de strings, llista de tots els autors.
     */
    public String[] llistarAutors() {
         i.llistarAutors();
         return i.obtenirLlista();
    }

    /**
     * Obtenir llista amb tots els autors que comencen pel prefix indicat.
     * @param prefix -> String; prefix que volem que tinguin els autors.
     * @return array de strings, array d'autors que compleix les condicions.
     */
    public String[] llistarAutorsPrefix(String prefix){
        i.llistarAutorsPrefix(prefix);
        return i.obtenirLlista();
    }

    /**
     * Obtenir una llista amb tots els títols de l'autor inidicat.
     * @param autor -> String; nom de l'autor.
     * @return array de strings, llista de tots els titols d'un autor.
     * @throws NoExisteixException Si no existeix l'autor {@code autor}.
     */
    public String[] llistarTitolsAutor(String autor) throws NoExisteixException {
         i.llistarTitolsAutor(autor);
         return i.obtenirLlista();
    }

    /**
     * Donat un document, obtenir una llista amb els k documents més similars a l'indicat
     *  en funció de l'estratègia indicada.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @param k -> Integer; nombre de documents similars que volem.
     * @param estrategia -> Integer; estratègia a seguir per la similitud entre documents.
     *                   0 -> Tf-Idf
     *                   Altrament -> Bag of Words
     * @return llista de parelles de strings, arraylist d'autors i títols dels {@code k} documents més similars a l'indicat.
     * @throws NoExisteixException Si no existeix el document indicat o l'autor indicat.
     */
    public ArrayList<Pair<String,String>> llistarDocumentsSimilars(String autor, String titol, int k, int estrategia) throws NoExisteixException {
        i.llistarDocumentsSimilars(autor,titol,k,estrategia);
        return i.obtenirLlistaAutorTitol();
    }

    /**
     * Donada una paraula, obtenir una llista amb tots els documents en què apareix la paraula.
     * @param paraula -> String; paraula de què volem saber els documents on apareix.
     * @return llista de parelles de strings, arraylist de autor i tiol dels documents on apareix la {@code paraula}.
     * @throws NoExisteixException Si la paraula {@code paraula} no existeix.
     */
    public ArrayList<Pair<String,String>> llistarDocumentsParaula(String paraula) throws NoExisteixException{
        i.llistaDocsParaula(paraula);
        return i.obtenirLlistaAutorTitol();
    }


    /**
     * Donada una expressió booleana, obtenir tots els documents que la compleixin.
     * @param expbool -> String; identificador de l'expressió booleana.
     * @return llista de parelles de strings, arraylist d'autors i títols dels documents que compleixen l'expressió booleana.
     * @throws NoExisteixException Si no existeix l'expressió booleana.
     */
    public ArrayList<Pair<String,String>> llistarDocumentsExprBool(String expbool) throws NoExisteixException {
        int[] ids = eb.booleanSearch(expbool);
        i.llistarDocumentsExprBool(ids);
        return i.obtenirLlistaAutorTitol();
    }


    /**
     * Donada una expressió booleana en format String hem de retornar tots els documents que la compleixin.
     * @param rawExprBool -> String; expressió booleana en format string.
     * @return llista de parelles de strings, arraylist d'autors i títols dels documents que compleixen l'expressió booleana.
     * @throws NoExisteixException Si no existeix l'expressió booleana.
     */
    public ArrayList<Pair<String,String>> llistarDocumentsRawExprBool(String rawExprBool) throws NoExisteixException {
        int[] ids = eb.rawExpressionSearch(rawExprBool);
        i.llistarDocumentsExprBool(ids);
        return i.obtenirLlistaAutorTitol();
    }


    /**
     * Obtenir el contingut del document indicat, si existeix.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @return string, amb el contingut del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol} o l'autor {@code autor}.
     */
    public String obtenirContingut(String autor, String titol) throws NoExisteixException{
        return cp.llegirDocument(i.obtenirId(autor, titol));
    }
    //funciones de controladorExprbool

    /**
     * Donar d'alta l'expressió booleana indicada.
     * @param ebool -> String; expressió booleana
     * @param nomEbool -> String; nom de l'expressió booleana.
     * @throws JaExisteixException Si ja existeix una expressió booleana amb nom {@code nomEbool}.
     * @throws ExprBoolIncorrectaException Si l'expressió booleana {@code ebool} no és vàlida.
     * @throws NomInvalidException Si el nom {@code nomEbool} no és vàlid.
     */
    public void donarAltaExprBool(String nomEbool, String ebool) throws JaExisteixException, ExprBoolIncorrectaException, NomInvalidException {
        eb.agregarExprBool(nomEbool, ebool);
    }

    /**
     * Si l'expresió booleana indicada existeix, es dona de baixa.
     * @param ebool -> String; nom de l'expressió booleana
     * @throws NoExisteixException Si no existeix una expressió booleana amb nom {@code ebool}.
     */
    public void donarBaixaExprBool(String ebool) throws NoExisteixException{
        eb.eliminarExprBool(ebool);
    }

    /**
     * Si l'expresió booleana indicada existeix, es modifica al nou valor indicat.
     * @param eboolold -> String; nom actual de l'expressió booleana.
     * @param ebool -> String; nou valor desitjat de l'expressió booleana.
     * @throws JaExisteixException Si ja existeix una expressió booleana amb el nou valor {@code eboolold}.
     * @throws NoExisteixException Si no existeix una expressió booleana amb el nom indicat {@code ebool}.
     */
    public void modificarExprBool(String eboolold,String ebool) throws JaExisteixException,NoExisteixException{
        eb.modificarExprBool(eboolold, ebool);
    }

    /**
     * Obtenir la llista de totes les expressions booleanes.
     * @return array de strings, array de les ids de les expressions creades.
     */
    public String[] llistarExprBooleanas(){
        String[] s= eb.llistarExprBooleanas();
        i.setLlistaExpbool(s);
        return s;
    }

    /**
     * Obtenir una expressió booleana, en format String, donat el seu nom.
     * @param nomEbool -> String; el nom de l'expressió booleana a cercar.
     * @return string, el string expressió booleana amb el nom especificat.
     * @throws NoExisteixException Si no hi ha una expressió booleana amb el nom especificat {@code nomEbool}.
     */
    public String getStringExpBool(String nomEbool) throws NoExisteixException{
        return eb.getStringExpBool(nomEbool);
    }


    //otras funciones



    /**
     * Ordena i retorna l'última llista de strings amb l'ordre indicat.
     * @param ordre -> Integer; indica l'ordre desitjat de l'array.
     *              0 -> Creixent
     *              1 -> Decreixent
     * @return array de strings, retorna l'últim array de strings ordenat.
     */
    public String[] ordenararray(int ordre){
        i.ordenarLlistaAlfabeticamente(ordre);
        return i.obtenirLlista();
    }

    /**
     * Ordena i retorna l'última llista de parelles de strings amb l'ordre indicat.
     * @param ordre -> Integer; indica l'ordre desitjat de l'array.
     *              0 -> Per nombre de documents de l'autor.
     *              1 -> Similitud segons l’última estratègia utilitzada.
     *              2 -> Alfabètic creixent.
     *              3 -> Alfabètic decreixent.
     * @return array de strings, retorna l'última llista d'autor i títol ordenada.
     */
    public ArrayList<Pair<String,String>> ordenarLlistaTitolAutor(int ordre){
        if(ordre == 0) i.ordenarLlistaPairsNdocs();
        else if(ordre == 1)i.ordenarKdoc();
        else if (ordre == 2) i.ordenarLlistaAlfabeticamentepairs(ordre);
        else i.ordenarLlistaAlfabeticamentepairs(ordre);
        return i.obtenirLlistaAutorTitol();
    }

    /**
     * Guarda al disc l’estat actual dels índexs i de les expressions booleanes.
     */
    public void guardarEstatDisc() {
        cp.guardarEstat("IndexAutors", i.getIndexAutors());
        cp.guardarEstat("IndexDocument", i.getIndexDocument());
        cp.guardarEstat("IndexParaula", i.getIndexParaula());
        cp.guardarEstat("IndexFrase", i.getIndexFrase());
        cp.guardarEstat("ExprBools", eb.getConjuntExprBool());
    }
}

