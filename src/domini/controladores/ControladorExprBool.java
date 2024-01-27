package domini.controladores;

import domini.clases.ConjuntExprBool;
import domini.clases.ExprBool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;
import java.util.Map;

import domini.clases.ExprBoolNode;
import domini.exceptions.*;

public class ControladorExprBool implements Serializable {

    //atributs
    private final ConjuntExprBool cb;
    private final ControladorIndex cd;

    //metodes

    /**
     Constructora que crea un nou objecte ControladorExprBool.
     @param cdI -> ControladorIndex; controlador d'índex que gestiona el conjunt d'índexs.
     */
    public ControladorExprBool(ControladorIndex cdI){
        cb = new ConjuntExprBool();
        cd = cdI;
    }

    /**
     Constructora que crea un nou objecte ControladorExprBool.
     @param cdI -> ControladorIndex; controlador d'índex que gestiona el conjunt d'índexs
     @param cjtEB -> ConjuntExprBool; conjunt d'expressions booleanes.
     */
    public ControladorExprBool(ControladorIndex cdI, ConjuntExprBool cjtEB){
        cb = cjtEB;
        cd = cdI;
    }

    /**
     * Getter conjunt d'expressions booleanes.
     * @return ConjuntExprBool, el conjunt d'expressions booleanes.
     */
    public ConjuntExprBool getConjuntExprBool() {
        return cb;
    }

    /**
     * Afegeix una nova expressió booleana al conjunt d'expressions booleanes.
     *
     * @param nomEbool -> String; el nom de l'expressió booleana. Ha de ser únic i vàlid.
     * @param ebool String; l'expressió booleana en si. Ha de ser vàlida.
     *
     * @throws JaExisteixException Si ja hi ha una expressió booleana amb el mateix nom.
     * @throws ExprBoolIncorrectaException Si l'expressió booleana és incorrecta.
     * @throws NomInvalidException Si el nom de l'expressió booleana no és vàlid.
     */
    public void agregarExprBool(String nomEbool, String ebool) throws JaExisteixException, ExprBoolIncorrectaException, NomInvalidException {
        cb.agregarExprBool(nomEbool, ebool);
    }

    /**
     * Donat el nom d’una expressió booleana, retorna cert si aquesta pertany al conjunt d’expressions booleanes, i fals altrament.
     * @param nomEbool -> String; mo de l'expressió booleana.
     * @return booleà, cert si l'expressió booleana pretany al conjunt d’expressions booleanes, i fals altrament.
     */
    public boolean pertanyExprBool(String nomEbool){
        return cb.pertanyExprBool(nomEbool);
    }

    /**
     * Elimina una expressió booleana del conjunt d'expressions booleanes.
     *
     * @param nomEbool -> String; el nom de l'expressió booleana a eliminar.
     *
     * @throws NoExisteixException Si no hi ha una expressió booleana amb el nom especificat.
     */
    public void eliminarExprBool(String nomEbool) throws NoExisteixException{
        cb.eliminarExprBool(nomEbool);
    }

    /**
     * Modifica una expressió booleana existent al conjunt d'expressions booleanes.
     *
     * @param nomEbool -> String; el nom de l'expressió booleana a modificar.
     * @param ebool -> String; la nova expressió booleana. Ha de ser vàlida.
     *
     * @throws NoExisteixException Si no hi ha una expressió booleana amb el nom especificat.
     * @throws ExprBoolIncorrectaException Si la nova expressió booleana és incorrecta.
     */
    public void modificarExprBool(String nomEbool,String ebool) throws NoExisteixException, ExprBoolIncorrectaException {
        cb.modificarExprBool(nomEbool,ebool);
    }

    /**
     * Retorna una llista amb els noms de totes les expressions booleanes del conjunt.
     *
     * @return array de strings, una llista amb els noms de totes les expressions booleanes.
     */
    public String[] llistarExprBooleanas(){
        return cb.llistarExprBooleanas();
    }

    /**
     * Retorna una expressió booleana donat el nom.
     *
     * @param nomEbool -> String; el nom de l'expressió booleana a cercar.
     *
     * @return ExprBool, l'expressió booleana amb el nom especificat.
     *
     * @throws NoExisteixException Si no hi ha una expressió booleana amb el nom especificat.
     */
    public ExprBool getExpBool(String nomEbool) throws NoExisteixException {
        return(cb.getExpBool(nomEbool));
    }


    /**
     * Donat el nom d’una expressió booleana, retorna el valor de l’expressió identificada amb el nom indicat, en format string.
     * @param nomEbool -> String; nom de l'expressió booleana.
     * @return string, l'expressió booleana amb el nom indicat, en format string.
     * @throws NoExisteixException Si no existeix cap expressió booleana amb el nom {@code nomEbool}.
     */
    public String getStringExpBool(String nomEbool) throws NoExisteixException{
        return cb.getStringExpBool(nomEbool);
    }

    /**
     * Realitza una cerca booleana al conjunt de documents utilitzant una expressió booleana donada.
     *
     * @param nomEbool -> String; el nom de l'expressió booleana a utilitzar a la cerca.
     *
     * @return array d'enters, una llista amb els índexs dels documents que compleixen amb l'expressió booleana especificada.
     *
     * @throws NoExisteixException Si no hi ha una expressió booleana amb el nom especificat.
     * @throws ExprBoolIncorrectaException Si l'expressió booleana és incorrecta.
     */
    public int[] booleanSearch(String nomEbool) throws NoExisteixException, ExprBoolIncorrectaException {
        ExprBoolNode searching = getExpBool(nomEbool).getArrel();
        try {
            return evaluar(searching).keySet().stream().mapToInt(Integer::intValue).toArray();
        }
        catch (RuntimeException e) {
            throw new ExprBoolIncorrectaException("La expressió " + nomEbool + " no és correcta");
        }
    }

    /**
     * Realitza una cerca booleana al conjunt de documents utilitzant una expressió booleana donada.
     *
     * @param rawExpression -> Sting; expressió booleana a utilitzar a la cerca.
     *
     * @return array d'enters, una llista amb els índexs dels documents que compleixen amb l'expressió booleana especificada.
     *
     * @throws NoExisteixException Si no hi ha una expressió booleana amb el nom especificat.
     * @throws ExprBoolIncorrectaException Si l'expressió booleana és incorrecta.
     */
    public int[] rawExpressionSearch(String rawExpression) throws NoExisteixException, ExprBoolIncorrectaException {
        ExprBoolNode searching = new ExprBool(rawExpression).getArrel();
        try {
            return evaluar(searching).keySet().stream().mapToInt(Integer::intValue).toArray();
        }
        catch (RuntimeException e) {
            throw new ExprBoolIncorrectaException("La expressió \"" + rawExpression + "\" no és correcta");
        }
    }


    /**
     * Avalua el resultat d'una expressió booleana donada.
     *
     * @param toCalc -> ExprBoolNode; el node de l'expressió booleana a avaluar.
     *
     * @return mapa amb key enters i value arrays d'enters, un mapa on les claus són els documents que contenen alguna frase que compleix l'expressió booleana, i
     * els valors són els números de frases d'aquell document que la compleixen.
     */
    private Map<Integer, int[]> evaluar(ExprBoolNode toCalc){
        if (toCalc.getValue().contains("&")){
            return and(evaluar(toCalc.getL()), evaluar(toCalc.getR()));
        }
        else if(toCalc.getValue().contains("|")){
            return or(evaluar(toCalc.getL()), evaluar(toCalc.getR()));
        }
        else if(toCalc.getValue().contains("!")){
            return not(evaluar(toCalc.getL()));
        }
        else if(toCalc.getValue().contains("\"")){
            return frase(toCalc.getValue().substring(1, toCalc.getValue().length()-1)); //Quitamos las comillas
        }
        else {
            return conjuntParaules(toCalc.getValue());
        }
    }

    /**
     * Realitza una operació AND lògica entre dos maps d'enters, fent la intersecció primer de claus, i si la clau està
     * en els dos maps, realitza la intersecció de les dues arrays.
     *
     * @param palabra1 -> Map<Integer, int[]>; el primer map d'enters.
     * @param palabra2 -> Map<Integer, int[]>; el segon map d'enters.
     *
     * @return mapa amb key enters i value arrays d'enters, la intersecció entre els valors dels dos maps originals.
     */
    private Map<Integer, int[]> and(Map<Integer, int[]> palabra1, Map<Integer, int[]> palabra2) {
        Map<Integer, int[]> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, int[]> set : palabra1.entrySet()) {
            if (palabra2.containsKey(set.getKey())) {
                int[] aux = palabra2.get(set.getKey());
                int [] interseccio = intAnd(set.getValue(), aux);
                if (interseccio.length > 0)
                    result.put(set.getKey(), interseccio);
            }
        }
        return result;
    }

    /**
     * Realitza una operació AND lògica entre dos arrays d'enters. Equivalent a fer la interecció. Els arrays han d'estar
     * ordenats creixentment.
     *
     * @param arr1 -> int[]; el primer array d'enters. Ha d'estar ordenat creixentment.
     * @param arr2 -> int[]; el segon array d'enters. Ha d'estar ordenat creixentment.
     *
     * @return array d'enters, un array d'enters que conté la intersecció entre els dos arrays originals.
     */
    private int[] intAnd(int[] arr1, int[] arr2){
        ArrayList<Integer> result = new ArrayList<>(); //No sabemos a priori el tamaño del resultado
        int i = 0; int j = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] == arr2[j]) {
                result.add(arr1[i]);
                ++i; ++j;
            }
            else if (arr1[i] < arr2[j]) ++i;
            else ++j;
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Realitza una operació OR lògica entre dos maps d'enters, fent la unió primer de claus, i si la clau està
     * en els dos maps, realitza la unió de les dues arrays.
     *
     * @param palabra1 -> Map<Integer, int[]>; el primer map d'enters.
     * @param palabra2 -> Map<Integer, int[]>; el segon map d'enters.
     *
     * @return mapa amb key enters i value arrays d'enters, la unió entre els valors dels dos maps originals.
     */
    private Map<Integer, int[]> or(Map<Integer, int[]> palabra1, Map<Integer, int[]> palabra2){
        Map<Integer, int[]> result = new LinkedHashMap<>();
        int[] unioDocs = intOr(palabra1.keySet().stream().mapToInt(Integer::intValue).toArray(), palabra2.keySet().stream().mapToInt(Integer::intValue).toArray());
        for (int id : unioDocs) {
            if (palabra1.containsKey(id) && palabra2.containsKey(id)) { //hay que hacer union de idsFrase
                result.put(id, intOr(palabra1.get(id), palabra2.get(id)));
            }
            else if (palabra1.containsKey(id)) {
                result.put(id, palabra1.get(id));
            }
            else {
                result.put(id, palabra2.get(id));
            }
        }
        return result;
    }

    /**
     * Realitza una operació OR lògica entre dos arrays d'enters. Equivalent a fer la unió. Els arrays han d'estar
     * ordenats creixentment.
     *
     * @param arr1 -> int[]; el primer array d'enters. Ha d'estar ordenat creixentment.
     * @param arr2 -> int[]; el segon array d'enters. Ha d'estar ordenat creixentment.
     *
     * @return array d'enters, un array d'enters que conté la unió entre els dos arrays originals.
     */
    private int[] intOr(int[] arr1, int[] arr2){
        ArrayList<Integer> result = new ArrayList<>(); //No sabemos a priori el tamaño del resultado
        int i = 0; int j = 0;
        while (i < arr1.length || j  < arr2.length) {
            if (j >= arr2.length || i < arr1.length && arr1[i] < arr2[j]) {
                result.add(arr1[i]);
                ++i;
            }
            else if (i >= arr1.length || arr1[i] > arr2[j]) {
                result.add(arr2[j]);
                ++j;
            }
            else {
                result.add(arr1[i]);
                ++i; ++j;

            }

        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Realitza una operació NOT lògica sobre un mapa d'enters. Equivalent a fer la entre diferència entre el conjunt
     * complet i el conjunt de valors del map original.
     *
     * @param palabra -> Map<Integer, int[]>; el map d'enters sobre el qual es fa l'operació.
     *
     * @return mapa amb key enters i value arrays d'enters, un map d'enters que conté, la diferència entre el conjunt complet de documents i frases de cada document
     * i el conjunt de documents i frases de cada document del map original.
     */
    private Map<Integer, int[]> not(Map<Integer, int[]> palabra){
        Map<Integer, int[]> result = cd.getIdsFrases();
        for (Map.Entry<Integer, int[]> set : palabra.entrySet()) {
            int[] aux = result.get(set.getKey());
            result.replace(set.getKey(),intNot(aux,set.getValue()));
        }
        return result;
    }

    /**
     * Realitza una operació NOT lògica sobre un arrays d'enters. En aquest cas, s'ha de proporcionar l'array amb tots
     * els elements per fer la diferència.
     *
     * @param all -> int[]; el conjunt complet amb tots els elements.
     * @param res -> int[]; el conjunt d'elements a excloure.
     *
     * @return array d'enters, un array d'enters que conté la diferència entre el primer array (tots els elements) i el segon.
     */
    private int[] intNot(int[] all, int[] res){
        ArrayList<Integer> finalIds = new ArrayList<>(); //No sabemos a priori el tamaño del resultado
        int i = 0; int j = 0;
        while (i < all.length) {
            if (j < res.length && all[i] == res[j]) {
                ++j;
            }
            else {
                finalIds.add(all[i]);
            }
            ++i;
        }
        return finalIds.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Realitza una cerca d'una seqüència de paraules específica a l'índex de documents.
     *
     * @param sequencia -> String; Una seqüència de paraules.
     *
     * @return mapa amb key enters i value arrays d'enters, un map d'enters que indica, per a cada document (claus), les frases que contenen la seqüència de paraules cercada (valors).
     */
    private Map<Integer, int[]> frase(String sequencia){
        Map<Integer, int[]> auxiliar = conjuntParaules(sequencia);
        Map<Integer, int[]> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, int[]> set : auxiliar.entrySet()) {
            ArrayList<Integer> resultFrases = new ArrayList<>();
            for(int idFrase: set.getValue()){
                if(cd.estaStringEnFraseDocument(set.getKey(),idFrase,sequencia)){
                    resultFrases.add(idFrase);
                }
            }
            if(!resultFrases.isEmpty()) {
                result.put(set.getKey(),resultFrases.stream().mapToInt(Integer::intValue).toArray());
            }
        }
        return result;
    }


    /**
     * Realitza una cerca d'un conjunt de paraules a l'índex de documents.
     *
     * @param conjParaules -> String; conjunt de paraules a cercar.
     *
     * @return mapa amb key enters i value arrays d'enters, un map d'enters que indica, per a cada document (claus), les frases que contenen el conjunt de paraules (valors).
     */
    private Map<Integer,int[]> conjuntParaules(String conjParaules){
        String[] paraules = conjParaules.split(" +");
        Map<Integer, int[]> docsParaules = cd.getIndexFraseParaula(paraules[0]);
        for(int i = 1; i<paraules.length; ++i){
            docsParaules = and(docsParaules, cd.getIndexFraseParaula(paraules[i]));
            if (docsParaules.size() == 0) return docsParaules; //Early return
        }
        return docsParaules;
    }
}