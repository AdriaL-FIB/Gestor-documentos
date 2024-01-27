package Junit;

import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndexDocumentTesteado {
    Map<Integer, DocumentStub> documents;

    /**
     * Constructora per defecte de IndexDocument.
     */
    public IndexDocumentTesteado() {
        documents = new HashMap<>();
    }

    /**
     * @param id -> Enter
     * @return Cert si existeix un Document amb identificador id.
     */
    private boolean estaDocument(int id) {
        return documents.containsKey(id);
    }

    /**
     *
     * @return Array dels identificadors de tots els Documents.
     */
    public int[] getIds() {
        return documents.keySet().stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     *
     * @return El número de Documents existents.
     */
    public int numDocs() {
        return documents.size();
    }

    /**
     * @param id -> Enter
     * @param contingut
     * @param autor
     * @param titol
     * Crea i afegeix un Document nou amb el paràmetres indicats.
     * @throws JaExisteixException Si el Document amb identificador id ja existeix.
     */
    public void afegirDocument(int id, String contingut, String autor, String titol) throws JaExisteixException {
        if (estaDocument(id)) throw new JaExisteixException("Ja existeix un document amb id " + id);
        DocumentStub infoDoc = new DocumentStub(contingut, autor, titol);
        documents.put(id, infoDoc);
    }

    /**
     *
     * @param id -> Enter
     * Elimina un document ja existent.
     * @throws NoExisteixException Si no existeix un Document amb identificador id.
     */
    public void eliminarDocument(int id) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        documents.remove(id);
    }

    /**
     *
     * @param id -> enter
     * @param nouContingut -> Contingut nou desitjat.
     *  Canvia el contingut del Document amb identificador id a nouContingut.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public void modificarDocument(int id, String nouContingut) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        documents.get(id).actualitzarInfo(nouContingut);
    }

    /**
     *
     * @param id -> Enter.
     * @return El path del Document amb identificador id.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public String getPath(int id) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getPath();
    }

    /**
     *
     * @param id -> Enter.
     * @param paraula
     * @return El valor Tf de la paraula del Document amb identificador id.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public double getTfParaulaDocument(int id, String paraula) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getTf(paraula);
    }

    /**
     *
     * @param id -> Enter
     * @return Array de les paraules al Document amb identificador id.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public String[] getParaulesDocument(int id) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getParaules();
    }

    /**
     * @param id -> Enter.
     * @return Array de les paraules sense stopWords al Document amb identificador id.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public String[] getParaulesFiltradesDocument(int id) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getParaulesFiltrades();
    }

    /**
     *
     * @param id -> Enter
     * @param tfIdfs Array de reals (tants com paraules al document)
     * Per cada paraula del document li assigna com a valor TfIdf un del paràmetre tfIdfs.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public void setAllTfIdfsDocument(int id, double[] tfIdfs) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        documents.get(id).setAllTfIdf(tfIdfs);
    }

    /**
     * @param id -> Enter
     * @param paraula
     * @return El valor TfIdf de la paraula al Document amb identificador id.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public double getPesosParaulaDocument(int id, String paraula, int estrategia) throws NoExisteixException {
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getPesosParaula(paraula, estrategia);
    }

    /**
     * @param idDoc -> Enter
     * @param idFrase -> Enter
     * @param str -> Paraula/Expressió
     * @return Cert si la frase amb identificador idFrase del Document amb identificador
     * idDoc conté l'expressió str.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public boolean estaStringEnFraseDocument(int idDoc, int idFrase, String str) throws NoExisteixException {
        if (!estaDocument(idDoc)) throw new NoExisteixException("No existeix el document amb id " + idDoc);
        return documents.get(idDoc).estaStringEnFrase(idFrase, str);
    }

    /**
     * @param idDoc -> Enter
     * @param paraula
     * @return Array dels identificadors de les frases del Document amb identificador idDoc
     * que contenen la paraula.
     * @throws NoExisteixException Si no existeix el Document amb identificador idDoc.
     */
    public int[] llistaFrasesParaulaDocument(int idDoc, String paraula) throws NoExisteixException {
        if (!estaDocument(idDoc)) throw new NoExisteixException("No existeix el document amb id " + idDoc);
        return documents.get(idDoc).llistaFrasesParaula(paraula);
    }

    /**
     *
     * @param id -> enter
     * @return  Número de frases al Document amb identificador id.
     */
    public int getNumFrasesDocument(int id) {
        return documents.get(id).getNumFrases();
    }

    /**
     *
     * @param id -> Enter
     * @return El contingut del Document amb identificador id en format String.
     * @throws NoExisteixException Si no existeix el Document amb identificador id.
     */
    public String getContingutDocument(int id) throws NoExisteixException{
        if (!estaDocument(id)) throw new NoExisteixException("No existeix el document amb id " + id);
        return documents.get(id).getContingut();
    }

    /**
     *
     * @param ids -> Array d'enters
     * @return Llista de parells(títol,autor) corresponents als documents amb
     * identificadors del paràmetre ids.
     */
    public ArrayList<Pair<String,String>> convertirid(int[] ids){
        ArrayList<Pair<String,String>> s=new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            s.add(documents.get(ids[i]).getTitolAutor());
        }
        return s;
    }
}

