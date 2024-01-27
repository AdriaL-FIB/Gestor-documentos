package domini.clases;

import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndexDocument implements Serializable {
    Map<Integer, Document> documents;

    int lastId;

    /**
     * Constructora per defecte de IndexDocument.
     */
    public IndexDocument() {
        documents = new HashMap<>();
        lastId = 0;
    }

    /**
     * Retorna cert si existeix el document amb l’identificador passat per referència.
     * @param id -> Integer; identificador del document.
     * @return booleà, cert si existeix un Document amb identificador {@code id}, altrament fals;
     */
    private boolean estaDocument(int id) {
        return documents.containsKey(id);
    }

    /**
     * Retorna un array amb els identificadors de tots els documents.
     * @return array d'enters, array amb identificadors de tots els Documents.
     */
    public int[] getIds() {
        return documents.keySet().stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Getter l'última id de document.
     * @return integer, retorna l'última id de document.
     */
    public int getLastId() {
        return lastId;
    }

    /**
     * Setter l'última id de document.
     * @param value -> Integer; nou valor última id.
     */
    public void setLastId(int value) {
        lastId = value;
    }

    /**
     * Retorna el número total de documents donats d’alta.
     * @return integer, el número de documents existents.
     */
    public int numDocs() {
        return documents.size();
    }

    /**
     * Crea i afegeix un document nou amb el paràmetres indicats.
     * @param id -> Integer; identificador del document.
     * @param contingut -> String; contingut del document.
     * @param autor -> String; nom del autor del document.
     * @param titol -> String; títol del document.
     * @throws JaExisteixException Si el document amb identificador {@code id} ja existeix.
     */
    public void afegirDocument(int id, String contingut, String autor, String titol) throws JaExisteixException {
        if (estaDocument(id)) throw new JaExisteixException("Ja existeix un document amb id " + id);
        Document infoDoc = new Document(contingut, autor, titol);
        documents.put(id, infoDoc);
    }

    /**
     * Donat un identificador esborra el document respectiu.
     * @param id -> Integer; identificador del document.
     * @throws NoExisteixException Si no existeix un document amb identificador {@code id}.
     */
    public void eliminarDocument(int id) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        documents.remove(id);
    }

    /**
     * Canvia el contingut del Document amb identificador id a nouContingut.
     * @param id -> Integer; identificador del document.
     * @param nouContingut -> String; Contingut nou desitjat.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public void modificarDocument(int id, String nouContingut) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        documents.get(id).actualitzarInfo(nouContingut);
    }

    /**
     * Obtenir el valor TF de la paraula al document.
     * @param id -> Integer; identificador del document.
     * @param paraula -> String; paraula de què volem el valor TF.
     * @return integer, el valor Tf de la paraula {@code paraula} del document amb identificador {@code id}.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public double getTfParaulaDocument(int id, String paraula) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getTf(paraula);
    }

    /**
     * Obtenir totes les paraules que apareixen al document.
     * @param id -> Integer; identificador del document.
     * @return array de strings, array amb totes les paraules del document amb identificador {@code id}.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public String[] getParaulesDocument(int id) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getParaules();
    }

    /**
     * Obtenir totes les paraules que apareixen al document, sense stopWords.
     * @param id -> Integer; identificador del document.
     * @return array de strings, array amb totes les paraules, excepte stopWords, del document amb identificador {@code id}.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public String[] getParaulesFiltradesDocument(int id) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getParaulesFiltrades();
    }

    /**
     * Assigna un valor Tf-Idf per a cada paraula del document amb l’identificar proporcionat.
     * @param id -> Integer; identificador del document.
     * @param tfIdfs -> double[]; arrat de valors Tf-Idf desitjats per les paraules del document.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public void setAllTfIdfsDocument(int id, double[] tfIdfs) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        documents.get(id).setAllTfIdf(tfIdfs);
    }

    /**
     * Retorna el pes de la paraula indicada al document amb l’identificador indicat, seguint l’estratègia indicada.
     * @param id -> Integer; identificador del document.
     * @param paraula -> String; paraula de què volem el pes.
     * @param estrategia -> Integer; indica de quina estratègia es vol el pes de la paraula.
     * @return double, el pes de la paraula {@code paraula} al document amb identificador {@code id}, seguint l'estratègia {@code estrategia}.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public double getPesosParaulaDocument(int id, String paraula, int estrategia) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getPesosParaula(paraula, estrategia);
    }

    /**
     * Donat un identificador del document, un identificador de frase i una string, confirma que la string formi part de la frase en concret.
     * @param idDoc -> Integer; identificador del document.
     * @param idFrase -> Integer; identificador de la frase.
     * @param str -> String; paraula/expressió que volem saber si està a la frase.
     * @return booleà, cert si la frase amb identificador {@code idFrase} del document amb identificador
     * {@code idDoc} conté l'expressió {@code str}.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public boolean estaStringEnFraseDocument(int idDoc, int idFrase, String str) throws NoExisteixException {
        if (!estaDocument(idDoc)) throw new NoExisteixException("No existeix el document amb id " + idDoc);
        return documents.get(idDoc).estaStringEnFrase(idFrase, str);
    }

    /**
     * Donats un identificador d'un document i una paraula, retorna una array amb els identificadors de totes les frases del document que contenen la paraula en qüestió.
     * @param idDoc -> Integer; identificador del document.
     * @param paraula -> String; paraula de què volem saber les frases del document on apareix.
     * @return array d'enters, array amb els identificadors de les frases del document amb identificador {@code idDoc}
     * on apareix la paraula.
     * @throws NoExisteixException Si no existeix el document amb identificador {@code id}.
     */
    public int[] llistaFrasesParaulaDocument(int idDoc, String paraula) throws NoExisteixException {
        if (!estaDocument(idDoc)) throw new NoExisteixException("No existeix el document amb id " + idDoc);
        return documents.get(idDoc).llistaFrasesParaula(paraula);
    }

    /**
     * Retorna el número de frases que hi ha al document indicat amb l’identificador.
     * @param id -> Integer; identificador del document.
     * @return  integer, número de frases al document amb identificador {@code id}.
     */
    public int getNumFrasesDocument(int id) {
        return documents.get(id).getNumFrases();
    }

    /**
     * Converteix els identificadors dels documents en parelles de títol i autor.
     * @param ids -> int[]; array amb els identificadors de document a convertir.
     * @return llista de parelles de strings, llista de parells(títol,autor) corresponents als documents amb
     * identificadors del paràmetre {@code ids}.
     */
    public ArrayList<Pair<String,String>> convertirid(int[] ids){
        ArrayList<Pair<String,String>> s=new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            s.add(documents.get(ids[i]).getAutorTitol());
        }
        return s;
    }
}

