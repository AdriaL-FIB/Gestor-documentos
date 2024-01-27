package presentacio.controladores;
import domini.controladores.ControladorDomini;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomInvalidException;
import domini.exceptions.ExprBoolIncorrectaException;
import domini.utils.Pair;
import presentacio.clases.*;

import java.io.IOException;
import java.util.*;

public class ControladorPresentacion {

    private final ControladorDomini cd;
    private VistaAlta va;
    private final VistaPrincipal vp;

    //ordre 0 creixent
    //ordre 1 decreixent
    int ordre = 0;
    int ordrepairs = 0;
    //Metodes

    /**
     * Creadora per defecte.
     */
    public ControladorPresentacion(){
        cd = new ControladorDomini();
        vp = new VistaPrincipal(this);
        //va = new VistaAlta(this);

    }

    /**
     * S’encarrega d'inicialitzar el controlador i preparar tots els valors predefinits d’aquest.
     */
    public void inicialitza(){
        //cd.inicialitzar();

        /*
        cd.donarAltaDocument("arbol","libro 1","arbol");
        cd.donarAltaDocument("arboleda","libro 1","arbol");
        cd.donarAltaDocument("arbolazo","libro 1","arbol");
        cd.donarAltaDocument("arbolito","libro 1","arbol");
        cd.donarAltaDocument("arboliso","libro 1","arbol");
        cd.donarAltaDocument("arbolindo","libro 1","arbol");
        cd.donarAltaDocument("zeta","libro 1","arbol");
        cd.donarAltaDocument("arbol","libro 2","lapiz");
        cd.donarAltaDocument("miguel","libro 3","goma");
        cd.donarAltaExprBool("expbool1", "a & b");
        cd.donarAltaExprBool("expbool2", "arbol | lapiz");
        */

        vp.setVisible(true);
    }

    /**
     * Donat un autor, títol i contingut, aquest es dona d’alta i es guarda.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @param contingut -> String; contingut del document.
     * @throws JaExisteixException Si ja existeix el document de l'autor {@code autor} amb títol {@code titol}.
     * @throws NomInvalidException Si el nom del document {@code titol} no és vàlid.
     */
    public void donarAltaDocument(String autor, String titol, String contingut) throws JaExisteixException, NomInvalidException {
        cd.donarAltaDocument(autor, titol, contingut);
        cd.guardarDocument(autor, titol, contingut);
    }

    /**
     * Donat un autor, títol i contingut, aquest es dona de baixa i s’elimina.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     */
    public void donarBaixaDocument(String autor, String titol) throws NoExisteixException {
        cd.donarBaixaDocument(autor, titol);
    }

    /**
     * Donat un autor, titol i contingut, modifica el contingut antic del document per el nou.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @param contingut -> String; contingut del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     * @throws JaExisteixException Si ja existeix un document amb el títol {@code titol} al disc.
     */
    public void modificarDocument(String autor, String titol, String contingut) throws NoExisteixException {
        cd.modificarDocument(autor, titol, contingut);
    }

    public void exportarDocument(String autor, String titol, String path) throws NoExisteixException, IOException {
        cd.exportarDocument(autor, titol, path);
    }
    public void importarDocument(String path) throws JaExisteixException, NomInvalidException, IOException {
        cd.importarDocument(path);
    }

    /**
     * Modifica el títol d'un document existent d'un autor
     * @param autor nom autor
     * @param oldTitol titol actual del document
     * @param newTitol nou títol al qual es vol canviar
     * @throws NoExisteixException si no es troba el titol amb el nom {@code oldTitol} o l'autor amb nom {@code autor}
     * @throws JaExisteixException si ja existeix un títol amb el nom {@code newTitol}
     */
    public void modificarTitol(String autor, String oldTitol, String newTitol) throws NoExisteixException, JaExisteixException, NomInvalidException {
        cd.modificarTitol(autor, oldTitol, newTitol);
    }

    /**
     * Modifica nom d'un autor existent
     * @param oldName nom actual de l'autor
     * @param newName nou nom de l'autor
     * @throws NoExisteixException si no es troba l'autor amb el nom {@code oldName}
     * @throws JaExisteixException si ja existeix un autor amb el nom {@code newName}
     */
    public void modificarAutor(String oldName, String newName) throws NoExisteixException, JaExisteixException, NomInvalidException {
        cd.modificarAutor(oldName, newName);
    }

    public void cambiarVPrincipalVAlta(String autor, String titol){
        vp.setVisible(false);
        va = new VistaAlta(this, autor, titol);
        va.setVisible(true);
    }

    /**
     * Cambia la vista de alta a la vista principal.
     */
    public void cambiarVAltaVPrincipal(){
        va.setVisible(false);
        va = null;
        vp.setVisible(true);
    }



    /**
     * Crida el cambi de vista, a mes cridem a funcio que ens permetra modifcar document.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     */


    public void cridaConsultaDocument(String autor, String titol){
        cambiarVPrincipalVAlta(autor, titol);
        va.consultadoc();
    }

    /**
     * Donat un prefix, retorna la llista de tots els autors que el contenen.
     * @param s -> String; el prefix que volem que tinguin els autors.
     * @return array de strings, retorna un array amb tots els autors que tenen com a prefix {@code s}.
     */
    public String[] LlistarAutorsPrefix(String s){
        return cd.llistarAutorsPrefix(s);
    }

    /**
     * Donat el nom d’un autor retorna la llista de tots els seus títols.
     * @param autor -> String; nom de l'autor.
     * @return array de strings, array amb tots els títols de l'autor {@code autor}.
     * @throws NoExisteixException Si no existeix l'autor {@code autor}.
     */
    public String[] llistarTitolsAutor(String autor) throws NoExisteixException {
        return cd.llistarTitolsAutor(autor);
    }

    /**
     * Llista totes les ids d'expressions booleanes donades d’alta.
     * @return array de strings, array amb totes les expressions booleanes donades d'alta.
     */
    public String[] llistarExprBooleanas() {
        return cd.llistarExprBooleanas();
    }

    /**
     * Donada una expressió booleana retorna la llista de tots els documents que contenen una frase que la compleix.
     * @param rawExprBool -> String; expressió booleana.
     * @return llista de parelles d'enters, llista amb les parelles d'autor i títol dels documents que compleixen l'expressió booleana {@code rawExprBool}.
     * @throws NoExisteixException Si no existeix l'expressió booleana.
     */
    public ArrayList<Pair<String,String>> llistarDocumentsRawExprBool(String rawExprBool) throws NoExisteixException{
        return cd.llistarDocumentsRawExprBool(rawExprBool);
    }

    /**
     * Donat l'identificador d'una expressió booleana retorna la llista de tots els documents que contenen una frase que la compleix.
     * @param expbool -> String; identificador d'una expressió booleana.
     * @return llista de parelles d'enters, llista amb les parelles d'autor i títol dels documents que compleixen l'expressió booleana amb identificador {@code expbool}.
     * @throws NoExisteixException Si no existeix l'expressió booleana.
     */
    public ArrayList<Pair<String,String>> llistarDocumentsExprBool(String expbool) throws NoExisteixException {
        return cd.llistarDocumentsExprBool(expbool);
    }

    /**
     * Donat el nom d'una expressió booleana, retorna l'expressió booleana en format string.
     * @param nomEbool -> nom d'una expressió booleana.
     * @return string, l'expressió booleana amb el nom indicat, en format string.
     * @throws NoExisteixException Si no existeix l'expressió booleana amb nom {@code nomEbool}.
     */
    public String getStringExpBool(String nomEbool) throws NoExisteixException{
        return cd.getStringExpBool(nomEbool);
    }

    /**
     * Donada una paraula retorna la llista de documents que la contenen.
     * @param paraula -> String; la paraula de la qual volem saber els documents on apareix.
     * @return llista de parelles de strings, llista amb els parells d'autor i títol dels documents on apareix la paraula {@code paraula}.
     */
    public ArrayList<Pair<String,String>> llistarDocumentsParaula(String paraula){
        return cd.llistarDocumentsParaula(paraula);
    }

    /**
     * Donat un document, retorna la llista de documents que són similars.
     * @param autor -> String; nom de l'autor,
     * @param titol -> String; títol del document.
     * @param k -> Integer; nombre de documents més similars que volem.
     * @param estrategia -> Integer; indica quina estratègia de similitud de document utilitzar.
     *                   0 -> Tf-Idf
     *                   1 -> Bag of Words
     * @return llista de parelles de strings, llista amb els parells d'autor i títol dels {@code k} documents més similars a l'indicat amb {@code autor} i {@code titol}.
     * @throws NoExisteixException
     */
    public ArrayList<Pair<String,String>> llistarDocumentsSimilars(String autor, String titol, int k, int estrategia) throws NoExisteixException {
        return cd.llistarDocumentsSimilars(autor,titol,k,estrategia);
    }

    /**
     * Dona d'alta una expressió booleana.
     * @param ebool -> String; nom de l'expressió booleana.
     * @param raw -> String; valor de l'expressió booleana en format string.
     */
    public void donarAtaExprBool(String ebool, String raw){
        cd.donarAltaExprBool(ebool,raw);
    }

    /**
     * Donada l’antiga expressió booleana i la nova, permet modificar l'expressió booleana.
     * @param eboolold -> String; nom de l'expressió booleana.
     * @param ebool -> String; nou valor desitjat de l'expressió booleana en format string.
     * @throws JaExisteixException Si ja existeix una expressió booleana amb el valor {@code ebool}.
     * @throws NoExisteixException Si no existeix cap expressió booleana amb el nom {@code eboolold}.
     */
    public void modificarExprBool(String eboolold,String ebool) throws JaExisteixException,NoExisteixException{
        cd.modificarExprBool(eboolold,ebool);
    }

    /**
     * Donat el nom d’una expressió booleana, la dona de baixa.
     * @param ebool -> String; nom de l'expressió booleana.
     * @throws NoExisteixException Si no existeix cap expressió booleana amb el nom {@code ebool}.
     */
    public void donarBaixaExprBool(String ebool) throws NoExisteixException{
        cd.donarBaixaExprBool(ebool);
    }

    /**
     * Ordena i retorna els resultats de l'última cerca amb resulta un array de strings, en ordre alfabètic.
     * @return array de strings, el resultat de l'última cerca que retorna un array de strings, ordenat alfabèticament.
     */
    public String[] ordenararray(int o){
        if (o == 3){
            return cd.ordenararray(2);
        }
        if(ordre == 0) {
            ordre++;
            return cd.ordenararray(0);
        }
        else {
            ordre--;
            return cd.ordenararray(1);
        }

    }

    /**
     * Ordena i retorna els resultats en ordre de l'autor amb més documents.
     * @return llista de parelles de strings, la llista de parelles (autor,títol), resultat de l'última cerca, ordenant els autors per nombre de títols.
     */
    public ArrayList<Pair<String,String>> ordenarndocs (){
        return cd.ordenarLlistaTitolAutor(0);
    }

    /**
     * Ordena i retorna els resultats de l'última busca per similitud, amb l'última estratègia utilitzada.
     * @return llista de parelles de strings, la llista de parelles (autor,títol), resultat de l'última cerca per similitud, i en ordre per similitud decreixent a l'indicat a la cerca.
     * (primer els més similars).
     */
    public ArrayList<Pair<String,String>> ordenarkdocs (){
        return cd.ordenarLlistaTitolAutor(1);
    }

    /**
     *  Ordena i retorna, el resultat de l'última cerca que donen com a resultat parelles de títols i autors, en ordre alfabètic.
     * @return llista de parelles de strings, la llista de parelles (autor,títol), resultat de l'última cerca en ordre alfabètic.
     */
    public ArrayList<Pair<String,String>> ordenarLlistaTitolAutor(int ordre){
        return cd.ordenarLlistaTitolAutor(ordre + 2);
    }

    /**
     * Donat un autor i un títol retorna el contingut del seu document.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del contingut.
     * @return string, el contingut del document amb títol {@code titol} de l'autor {@code autor}.
     */
    public String obtenirContingut(String autor,String titol){
        return cd.obtenirContingut(autor, titol);
    }

    /**
     * Guarda al disc l’estat actual dels índexs i de les expressions booleanes.
     */
    public void sortir(){
        cd.guardarEstatDisc();
    }
}
