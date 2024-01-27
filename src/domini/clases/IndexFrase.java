package domini.clases;

import domini.exceptions.NoExisteixException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class IndexFrase implements Serializable {
    Map<String, Map<Integer, int[]>> frases;

    /**
     * Creadora per defecte
     */
    public IndexFrase() {
        frases = new HashMap<>();
    }

    /**
     * Donades una paraula, l’identificador del document i els identificadors de les frases que conté aquest; afegeix ocurrencies de les paraules.
     * @param paraula -> String; paraula de què volem afegir ocurrències.
     * @param idDoc -> Integer; identificador del document.
     * @param idFrases -> int[]; frases del document en què apareix la paraula.
     */
    public void afegirOcurrenciesDocument(String paraula, int idDoc, int[] idFrases) {
        if (!frases.containsKey(paraula)) {
            Map<Integer, int[]> indxFrases = new LinkedHashMap<>();
            indxFrases.put(idDoc, idFrases);
            frases.put(paraula, indxFrases);
        }
        else {
            frases.get(paraula).put(idDoc, idFrases);
        }

    }

    /**
     * Donades una paraula i l’identificador del document elimina les ocurrencies de la paraula en aquell document.
     * @param paraula -> String; paraula de què volem esborrar ocurrències al document.
     * @param idDoc -> Integer; identificador del document.
     * @throws NoExisteixException Si no hi ha cap ocurrència de la paraula {@code paraula} al document.
     */
    public void eliminarOcurrenciesDocument(String paraula, int idDoc) throws NoExisteixException {
        if (!frases.containsKey(paraula)) throw new NoExisteixException("No existeix cap ocurrencia de la paraula " + paraula);
        frases.get(paraula).remove(idDoc); //Eliminar todas las frases del document idDoc
        if (frases.get(paraula).keySet().size() == 0) frases.remove(paraula); //La palabra no esta en ninguna frase (== en ningun documento)
    }

    /**
     * Donada una paraula obté la id de les frases en què apareix i les ids dels documents on apareix.
     * @param paraula -> String; paraula de què volem les frases i documents on apareix.
     * @return  mapa amb enters com a clau i arrays d'enters com a valors, keys -> ids del documents on apareix la paraula {@code paraula}, value -> array dels ids
     * de les frases del document on apareix la paraula {@code paraula}.
     */
    public Map<Integer, int[]> getIndexFraseParaula(String paraula) {
        Map<Integer, int[]> ret = frases.get(paraula.toLowerCase());
        if (ret == null) ret = new LinkedHashMap<>();
        return ret;
    }
}
