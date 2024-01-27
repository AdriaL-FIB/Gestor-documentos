package Junit;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.ArrayList;

import domini.exceptions.NoExisteixException;
import domini.utils.Pair;


public class DocumentTesteado {
    //static int numDocs;
    /**
     * A cada {@code String} li correspon un {@code Pair<Double, Double>} on el primer Double és el Term-Frequency (Tf) i el segon es el Tf-Idf
     */
    Map<String, Pair<Double, Double>> paraulaInfo;
    //temporal
    String path;

    String titol;

    String autor;

    //private String[] frases;
    private String contingut;
    private ArrayList<Pair<Integer, Integer>> indexFrases;

    private Map<String,ArrayList<Integer>> paraulaFrases;

    /*
    private static class InfoParaulaDoc {
        public double tfIdf;
        public double tf;
        public double count;
    }
    */

    /**
     * Creadora Document.
     * @param contingut Contingut del document
     * @param a autor
     * @param t titol
     *
     */
    public DocumentTesteado(String contingut, String a, String t) {
        paraulaFrases = new HashMap<>();
        paraulaInfo = new LinkedHashMap<>();
        indexFrases = new ArrayList<>();
        actualitzarInfo(contingut);
        titol = t;
        autor = a;
    }

    /**
     *
     * @param contingut
     * @return Array del paràmtre separat per paraules.
     */
    private String[] obtenirParaules(String contingut){
        return FiltradorStub.filtrarContingut(contingut, "[.,!? \\r\\n\\t\\f\\v]");
    }


    /**
     *
     * @param frases Array de frases.
     * Per cada paraula que apareix a alguna de les frases, guarda en quines
     *   frases apareix.
     */
    private void crearParaulaFrase(String[] frases){
        for (int i = 0; i < frases.length; i++){
            String[] paraulesDeFrase = obtenirParaules(frases[i]);
            for (String paraula : paraulesDeFrase) {
                paraula = paraula.toLowerCase();
                if (!paraulaFrases.containsKey(paraula)) {
                    paraulaFrases.put(paraula, new ArrayList<>());
                }
                paraulaFrases.get(paraula).add(i);
            }
        }
    }

    /**
     *
     * @param contingut
     * Crea els indexos de les frases i de les paraules a partir del contingut.
     */
    private void indexarFrasesIParaules(String contingut){
        String[] frases = FiltradorStub.filtrarContingut(contingut, "[.!?]+[\\r\\n\\t\\f\\v ]*");

        Pattern pattern = Pattern.compile("[.!?]+[\\r\\n\\t\\f\\v ]*");
        Matcher matcher = pattern.matcher(contingut);
        int iniciFrase = 0;
        int finalFrase;
        while (matcher.find()) {
            finalFrase = matcher.start();
            indexFrases.add(new Pair<>(iniciFrase, finalFrase));
            iniciFrase = matcher.end();
        }


        crearParaulaFrase(frases);
    }

    /**
     * actualitza tota la informació que es guarda del Document, a partir de contingut.
     * @param contingut Contingut del document
     */
    public void actualitzarInfo(String contingut) {
        //Palabras quitando las stopWords
        this.contingut = contingut;
        String[] paraules = obtenirParaules(contingut.toLowerCase());
        int numParaules = paraules.length;

        //Antes de filtrar por StopWords, meter todas las palabras en el map
        for (String paraula : paraules) {
            paraulaInfo.put(paraula, new Pair<>(-1d, -1d));
        }

        paraules = FiltradorStub.filtrarStopWords(paraules);
        indexarFrasesIParaules(contingut);

        //Eliminar y contar repetidos
        final Map<String, Long> countMap = Arrays.stream(paraules).collect(Collectors.groupingBy(p -> p, LinkedHashMap::new, Collectors.counting()));

        //Calcular el Tf de cada palabra
        for (Map.Entry<String, Long> pInfo : countMap.entrySet()) {
            paraulaInfo.get(pInfo.getKey()).setFirst(pInfo.getValue()/(double)numParaules);
        }
    }

    /**
     *
     * @return el Path del document.
     */
    public String getPath(){
        return path;
    }

    /**
     *
     * @param paraula Paraula de la que obtenir el tf (term-frequency)
     * @return Devuelve el valor de TF de la palabra. Si no esta presente en el documento, devuelve 0
     */
    public double getTf(String paraula) {
        var pInfo = paraulaInfo.get(paraula);
        return (pInfo != null) ?  pInfo.getFirst() : 0d;
    }

    /**
     *
     * @return Array de totes les paraules del document.
     */
    public String[] getParaules() {
        return paraulaInfo.keySet().toArray(new String[0]);
    }

    /**
     *
     * @return Retornar paraules ja filtrades (sin stopWords)
     */
    public String[] getParaulesFiltrades() {
        return FiltradorStub.filtrarMapStopWords(paraulaInfo);
    }

    /**
     *
     * @param tfIdfs -> Array de números reals.(Tants com paraules al document)
     * Posa el valor del TfIdf de cada paraula al valor a la mateixa posició
     * al array del paràmetre.
     */
    public void setAllTfIdf(double[] tfIdfs) {
        int i = 0;
        for (String paraula : FiltradorStub.filtrarMapStopWords(paraulaInfo)) {
            paraulaInfo.get(paraula).setSecond(tfIdfs[i++]);
        }
    }

    /**
     *
     * @param paraula Paraula de la que obtenir els pesos
     * @param estrategia Estrategia per els pesos de les paraules. 0 -> Tf-Idf, 1 -> Bag of Words
     * @return Retorna el valor del pes de la paraula segons la estratègia escollida, si no està al document retorna 0.
     */
    public double getPesosParaula(String paraula, int estrategia) {
        if (estrategia == 0) {
            var pInfo = paraulaInfo.get(paraula);
            return (pInfo != null) ?  pInfo.getSecond() : 0d;
        }
        var frasesParaula = paraulaFrases.get(paraula);
        return (frasesParaula != null) ?  frasesParaula.size() : 0d; //Nombre de ocurrències de la paraula
    }

    /**
     *
     * @param idFrase id de la Frase (Natural menor que el número de frases del document).
     * @param str Paraula/Expressió que volem veure si està a alguna frase.
     * @return Cert si str està contingut dins la frase amb id idFrase.
     * @throws NoExisteixException si idFrase està fora del conjunt de ids de frases del document.
     */
    public boolean estaStringEnFrase(int idFrase, String str) throws NoExisteixException {
        if (idFrase >= indexFrases.size()) throw new NoExisteixException("No existeix la frase id " + idFrase);
        int iniciFrase = indexFrases.get(idFrase).getFirst();
        int finalFrase = indexFrases.get(idFrase).getSecond();
        return contingut.substring(iniciFrase, finalFrase).toLowerCase().contains(str.toLowerCase());
    }

    /**
     *
     * @param paraula
     * @return Array dels ids de les frases que contenen la paraula.
     */
    public int[] llistaFrasesParaula(String paraula) {
        return paraulaFrases.get(paraula.toLowerCase()).stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     *
     * @return El número de frases al document.
     */
    public int getNumFrases() {
        return indexFrases.size();
    }

    /**
     *
     * @return Una parella amb primer element el titol del Document i segon el seu autor.
     */
    public Pair<String,String > getTitolAutor(){
        //Pair<String,String> hola = new Pair<>(titol,autor);
        return new Pair<>(titol,autor);
    }

    /**
     *
     * @return El contingut del Document en format String.
     */
    public String getContingut() {
        return contingut;
    }
}
