package domini.clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Paraula implements Serializable {
    ArrayList<Integer> paraulaDocs;
    int ocurrenciesDoc;
    double idf;
    String valor;

    //pasamos numDocs por parametro para evitar acoplamiento (?)
    /**
     * Creadora Paraula
     * @param valor -> String; la paraula en qüestió.
     * @param id -> Integer; identificador del document on ha aparegut la paraula.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf -> Boolean; si volem que s'actualitzi el valor idf al crear la Paraula.
     */
    public Paraula(String valor, int id, int numDocs, boolean actualitzarIdf) {
        this.valor = valor;
        this.ocurrenciesDoc = 1;


        paraulaDocs = new ArrayList<>();

        paraulaDocs.add(id);
        if (actualitzarIdf) actualitzarIdf(numDocs);
        else idf = -1d;
    }

    /**
     * Donats l’identificador d'un document, el número de documents totals i si cal actualitzar el valor idf.
     * Afegeix a les ocurrències de la paraula, una nova al document identificat per id, i actualtiza el valor idf si així s'ha indicat.
     * @param id -> Integer; identificador del document.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf -> Boolean; ens indica si s'ha d'acutalitzar el valor idf.
     */
    public void novaOcurrencia(int id, int numDocs, boolean actualitzarIdf) {
        ++ocurrenciesDoc;
        paraulaDocs.add(id);
        if (actualitzarIdf) actualitzarIdf(numDocs);
    }

    /**
     * Elimina l'ocurrència de la paraula al document indicat i acutalitza el valor idf si és necessari.
     * @param id -> Integer; identificador d'un document.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf -> Boolean; ens indica si s'ha d'acutalitzar el valor idf.
     * @return booleà, cert si no queden ocurrències, fals altrament.
     */
    //Devuelve cierto si ya no aparece en ningun documento, falso en caso contrario
    public boolean eliminarOcurrencia(int id, int numDocs, boolean actualitzarIdf) {
        --ocurrenciesDoc;
        if (ocurrenciesDoc <= 0) return true;
        paraulaDocs.removeIf(doc -> doc.equals(id));
        if (actualitzarIdf) actualitzarIdf(numDocs);
        return false;
    }

    /**
     * Recalcula el valor idf a partir del nombre total de documents.
     * @param numDocs -> Integer; número de documents al sistema.
     */
    public void actualitzarIdf(int numDocs) {
        idf = Math.log(numDocs/(double)paraulaDocs.size());
    }

    /**
     * Getter Idf.
     * @return double, el valor Idf.
     */
    public double getIdf() {
        return idf;
    }

    /**
     * Retorna una llista amb els identificadors de tots els documents on apareix la paraula.
     * @return array d'enters, llista amb els identificadors de documents.
     */
    public int[] llistaDocs() {
        return paraulaDocs.stream().mapToInt(Integer::intValue).toArray();
    }
}
