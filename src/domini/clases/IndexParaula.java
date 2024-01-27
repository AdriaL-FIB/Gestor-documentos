package domini.clases;

import domini.exceptions.NoExisteixException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
public class IndexParaula implements Serializable {
    Map<String, Paraula> paraules;

    /**
     * Creadora per defecte
     */
    public IndexParaula() {
        paraules = new HashMap<>();
    }

    /**
     * Agrega una paraula, si la paraula ja existeix, afegeix una ocurrència d'aquesta.
     * @param paraula -> String; paraula a afegir.
     * @param idDoc -> Integer; identificador del document.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf -> Boolean; indica si en afegir la paraula o ocurrència, volem actualitzar el valor Idf de la paraula.
     */
    public void afegirParaula(String paraula, int idDoc, int numDocs, boolean actualitzarIdf) {
        if (paraules.containsKey(paraula)) {
            novaOcurrenciaParaula(paraula, idDoc, numDocs, actualitzarIdf);
        }
        else {
            paraules.put(paraula, new Paraula(paraula, idDoc, numDocs, actualitzarIdf));
        }
    }

    /**
     * Agrega diverses paraules al mateix temps, amb el mateix comportament que {@see afegirParaula}.
     * @param paraules -> String[]; array amb les paraules que volem afegir.
     * @param idDoc -> Integer; identificador del document.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf -> Boolean; indica si en afegir la paraula o ocurrència, volem actualitzar el valor Idf de la paraula.
     */
    public void afegirParaules(String[] paraules, int idDoc, int numDocs, boolean actualitzarIdf) {
        for (String p : paraules) {
            afegirParaula(p, idDoc, numDocs, actualitzarIdf);
        }
    }

    /**
     * Elimina una paraula.
     * @param paraula -> String; paraula que volem eliminar.
     * @throws NoExisteixException Si la paraula {@code paraula} no existeix.
     */
    public void eliminarParaula(String paraula)throws NoExisteixException {
        if (!paraules.containsKey(paraula)) throw new NoExisteixException("No existeix la paraula: " + paraula);

        paraules.remove(paraula);
    }

    /**
     * Redueix el nombre de vegades que ha aparegut les paraules.
     * @param paraules -> String[]; paraules a què volem reduir les ocurrències.
     * @param idDoc -> Integer; identificador del document.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf -> Boolean; indica si en afegir la paraula o ocurrència, volem actualitzar el valor Idf de la paraula.
     * @throws NoExisteixException Si la paraula {@code paraula} no existeix.
     */

    public void eliminarOcurrencies(String[] paraules, int idDoc, int numDocs, boolean actualitzarIdf) throws NoExisteixException{
        for (String p : paraules) {
            eliminarOcurrenciaParaula(p, idDoc, numDocs, actualitzarIdf);
        }
    }

    /**
     * Donada una paraula, retorna el seu valor idf.
     * @param paraula -> String; paraula de la qual volem el valor Idf.
     * @return double, el valor Idf de la paraula {@code paraula}.
     * @throws NoExisteixException Si la paraula {@code paraula} no existeix.
     */
    public double getIdfParaula(String paraula)throws NoExisteixException {
        if (!paraules.containsKey(paraula)) throw new NoExisteixException("No existeix la paraula: " + paraula);
        return paraules.get(paraula).getIdf();
    }

    /**
     * Recalcula tots els valors IDF de totes les paraules.
     * @param numDocs -> Integer; número de documents al sistema.
     */
    public void recalcularTotsIdf(int numDocs) {
        for (Paraula p : paraules.values()) {
            p.actualitzarIdf(numDocs);
        }
    }

    /**
     * Donada una paraula ens retorna els ids de tots els documents on apareix.
     * @param paraula -> String; paraula que volem saber els documents on apareix.
     * @return array d'enters, array amb els ids dels documents on apareix paraula.
     * @throws NoExisteixException Si la paraula {@code paraula} no existeix.
     */
    public int[] llistaDocsParaula(String paraula) throws NoExisteixException{
        if (!paraules.containsKey(paraula.toLowerCase())) throw new NoExisteixException("No existeix la paraula: " + paraula);
        return paraules.get(paraula.toLowerCase()).llistaDocs();
    }

    /**
     * Afegeix una ocurrència de la paraula.
     * @param paraula -> String; paraula a què volem afegir una ocurrència.
     * @param idDoc -> Integer; identificador del document.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf ->Boolean; indica si en afegir la paraula o ocurrència, volem actualitzar el valor Idf de la paraula.
     */
    public void novaOcurrenciaParaula(String paraula, int idDoc, int numDocs, boolean actualitzarIdf) {
        paraules.get(paraula).novaOcurrencia(idDoc, numDocs, actualitzarIdf);
    }

    /**
     * Elimina una ocurrència de la paraula.
     * @param paraula -> String; paraula a què volem eliminar una ocurrència.
     * @param idDoc -> Integer; identificador del document.
     * @param numDocs -> Integer; número de documents al sistema.
     * @param actualitzarIdf -> Boolean; indica si en afegir la paraula o ocurrència, volem actualitzar el valor Idf de la paraula.
     */
    public void eliminarOcurrenciaParaula(String paraula, int idDoc, int numDocs, boolean actualitzarIdf) {
        if (paraules.get(paraula).eliminarOcurrencia(idDoc, numDocs, actualitzarIdf)) eliminarParaula(paraula);
    }

}