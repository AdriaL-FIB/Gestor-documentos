package domini.clases;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.ArrayList;

import domini.exceptions.NoExisteixException;
import domini.utils.Filtador;
import domini.utils.Pair;


public class Document implements Serializable {
    //static int numDocs;
    /**
     * A cada {@code String} li correspon un {@code Pair<Double, Double>} on el primer Double és el Term-Frequency (Tf) i el segon es el Tf-Idf
     */
    Map<String, Pair<Double, Double>> paraulaInfo;

    String titol;
    String autor;

    //private String[] frases;
    //private String contingut;
    private ArrayList<String[]> indexFrases;

    private Map<String,ArrayList<Pair<Integer, Integer>>> paraulaFrases;

    /*
    private static class InfoParaulaDoc {
        public double tfIdf;
        public double tf;
        public double count;
    }
    */

    /**
     * Creadora Document.
     * @param contingut -> String; contingut del document.
     * @param a -> String; autor del document.
     * @param t -> String; títol del document.
     */
    public Document(String contingut, String a, String t) {
        paraulaFrases = new HashMap<>();
        paraulaInfo = new LinkedHashMap<>();
        indexFrases = new ArrayList<>();
        titol = t;
        autor = a;
        actualitzarInfo(contingut);
    }

    /**
     * Donat el contingut d'un document, el separa per paraules.
     * @param contingut -> String; contingut del document.
     * @return array de strings, paraules del contingut {@code contingut}.
     */
    private String[] obtenirParaules(String contingut){
        return Filtador.filtrarContingut(contingut, "[(){}\\[\\]\"'.,!? \\r\\n\\t\\f\\v]");
    }


    /**
     * Donades totes les frases, per cada paraula que apareix en una de les frases, guarda en quines frases apareix.
     * @param frases -> String[]; array de frases.
     */
    private void crearParaulaFrase(String[] frases){
        for (int i = 0; i < frases.length; ++i) {
            String[] paraulesDeFrase = obtenirParaules(frases[i]);
            for (int j = 0; j < paraulesDeFrase.length; ++j) {
                String paraula = paraulesDeFrase[j].toLowerCase();
                //String paraulaSeg = (j+1 < paraulesDeFrase.length) ? paraulesDeFrase[j+1].toLowerCase() : null;
                if (!paraulaFrases.containsKey(paraula)) {
                    paraulaFrases.put(paraula, new ArrayList<>());
                }
                paraulaFrases.get(paraula).add(new Pair<>(i, j));
            }
        }
    }

    /**
     * Donat el contingut d’un document, indexa totes les frases i les paraules.
     * @param contingut -> String; contingut del document.
     *
     */
    private void indexarFrasesIParaules(String contingut){
        String[] frases = Filtador.filtrarContingut(contingut, "[.!?\\r\\n\\t\\f\\v]+[\\r\\n\\t\\f\\v ]*");

        for (int i = 0; i < frases.length; ++i) {
            String[] paraulesDeFrase = obtenirParaules(frases[i]);
            indexFrases.add(paraulesDeFrase);
            for (int j = 0; j < paraulesDeFrase.length; ++j) {
                String paraula = paraulesDeFrase[j].toLowerCase();
                if (!paraulaFrases.containsKey(paraula)) {
                    paraulaFrases.put(paraula, new ArrayList<>());
                }
                paraulaFrases.get(paraula).add(new Pair<>(i, j));
            }
        }

       // crearParaulaFrase(frases);
    }

    /**
     * A partir del contingut actualitza tota la informació que guarda document.
     * @param contingut -> String; contingut del document.
     */
    public void actualitzarInfo(String contingut) {

        //Primero añadimos el autor y el títol <<-- NO
        //contingut = autor + "\n" + titol + "\n" + contingut;

        //Palabras quitando las stopWords
        //this.contingut = contingut;
        String[] paraules = obtenirParaules(contingut.toLowerCase());
        int numParaules = paraules.length;

        //Antes de filtrar por StopWords, meter todas las palabras en el map
        for (String paraula : paraules) {
            paraulaInfo.put(paraula, new Pair<>(-1d, -1d));
        }

        paraules = Filtador.filtrarStopWords(paraules);
        indexarFrasesIParaules(contingut);

        //Eliminar y contar repetidos
        final Map<String, Long> countMap = Arrays.stream(paraules).collect(Collectors.groupingBy(p -> p, LinkedHashMap::new, Collectors.counting()));

        //Calcular el Tf de cada palabra
        for (Map.Entry<String, Long> pInfo : countMap.entrySet()) {
            paraulaInfo.get(pInfo.getKey()).setFirst(pInfo.getValue()/(double)numParaules);
        }
    }

    /**
     * Retorna el valor tf de la paraula indicada, i 0 si aquesta no apareix al document.
     * @param paraula -> String; paraula de què volem el Tf.
     * @return double, retorna el valor TF de la paraula. Si no està al document, retorna 0.
     */
    public double getTf(String paraula) {
        var pInfo = paraulaInfo.get(paraula);
        return (pInfo != null) ?  pInfo.getFirst() : 0d;
    }

    /**
     * Retorna un array amb totes les paraules que apareixen al document.
     * @return array de strings, array amb totes les paraules del document.
     */
    public String[] getParaules() {
        return paraulaInfo.keySet().toArray(new String[0]);
    }

    /**
     * Retorna un array amb totes les paraules que apareixen al document, sense StopWords.
     * @return array de strings, array amb totes les paraules del document ja filtrades (sense stopWords)
     */
    public String[] getParaulesFiltrades() {
        return Filtador.filtrarMapStopWords(paraulaInfo);
    }

    /**
     * Donat un array de doubles amb els valors de tf-idf, posa aquests al valor amb la mateixa posició a l'array del paràmetre.
     * @param tfIdfs -> double[]; valors de TfIdf desitjats, un per cada paraula al document.
     */
    public void setAllTfIdf(double[] tfIdfs) {
        int i = 0;
        for (String paraula : Filtador.filtrarMapStopWords(paraulaInfo)) {
            paraulaInfo.get(paraula).setSecond(tfIdfs[i++]);
        }
    }

    /**
     * Retorna el valor del pes de la paraula segons l'estratègia escollida, retorna 0 si no està.
     * @param paraula -> String; paraula de què volem obtenir els pesos.
     * @param estrategia -> Integer; estratègia per els pesos de les paraules. 0 -> Tf-Idf, 1 -> Bag of Words.
     * @return double, retorna el valor del pes de la paraula segons l'estratègia escollida, si no està al document retorna 0.
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
     * Donats un identificador de frase i una String, dona cert si aquesta apareix a la frase indicada.
     * @param idFrase -> Integer; identificador de la Frase (Natural menor que el número de frases del document).
     * @param str -> String; paraula/expressió que volem veure si està a alguna frase.
     * @return booleà, cert si str apareix a la frase amb identificador idFrase.
     * @throws NoExisteixException Si al document no existeix cap frase amb l'identificador indicat.
     */
    public boolean estaStringEnFrase(int idFrase, String str) throws NoExisteixException {
        if (idFrase >= indexFrases.size()) throw new NoExisteixException("No existeix la frase id " + idFrase);
        /*
        int iniciFrase = indexFrases.get(idFrase).getFirst();
        int finalFrase = indexFrases.get(idFrase).getSecond();
        return contingut.substring(iniciFrase, finalFrase).toLowerCase().contains(str.toLowerCase());
        */
        String[] paraules = obtenirParaules(str);
        String[] frase = indexFrases.get(idFrase);
        if (paraules.length > frase.length) return false;
        int j = 0;
        while (j < frase.length) {
            int i = 0;
            while (!frase[j].equals(paraules[i])) {
                ++j; //Avançar en la frase fins que trobem la primera paraula.
                if (j >= frase.length) return false;
            }
            while (i < paraules.length && paraules[i].equals(frase[j])) {
                if (i == paraules.length - 1) return true;
                ++i; ++j;
            }
        }
        return false;
    }

    /**
     * Donada una paraula, retorna els identificadors de les frases que contenen aquesta paraula.
     * @param paraula -> String; paraula de què volem les frases on apareix.
     * @return array d'enters, array amb els identificadors de les frases que contenen la paraula.
     */
    public int[] llistaFrasesParaula(String paraula) {
        return paraulaFrases.get(paraula.toLowerCase()).stream().map(Pair::getFirst).mapToInt(Integer::intValue).toArray();
    }

    /**
     * Retorna el número de frases al document.
     * @return integer, el número de frases al document.
     */
    public int getNumFrases() {
        return indexFrases.size();
    }

    /**
     * Retorna una parella, on el primer String és l’autor del document, i el segon el seu títol.
     * @return parella de strings, una parella amb primer element l'autor del Document i segon el seu títol.
     */
    public Pair<String,String > getAutorTitol(){
        //Pair<String,String> hola = new Pair<>(titol,autor);
        return new Pair<>(autor,titol);
    }


    /*
    public String getContingut() {
        return contingut;
    }
    */
}
