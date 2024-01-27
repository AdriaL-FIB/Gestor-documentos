package domini.controladores;

import domini.clases.*;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomInvalidException;
import domini.utils.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

public class ControladorIndex implements Serializable {
    private IndexAutors a;
    private IndexDocument d;
    private IndexParaula p;
    private IndexFrase f;

    private String[] ultimalista;

    private ArrayList<Pair<String,String>> ulAutorTtitol;

    private ArrayList<Pair<String,String>> ultimkdocuments;

    /**
     * Creadora per defecte
     */
    public ControladorIndex() {
        a = new IndexAutors();
        d = new IndexDocument();
        p = new IndexParaula();
        f = new IndexFrase();
    }


    /**
     * Creadora amb paràmetres
     * @param a -> IndexAutors; índex d'autors que desitgem tenir.
     * @param d -> IndexDocument; índex de documents que desitgem tenir.
     * @param f -> IndexParaula; índex de paraules que desitgem tenir.
     * @param p -> IndexFrase; índex de frases que desitgem tenir.
     */
    public ControladorIndex(IndexAutors a, IndexDocument d, IndexParaula p, IndexFrase f) {
        if (a != null) this.a = a;
        else this.a = new IndexAutors();
        if (a != null) this.d = d;
        else this.d = new IndexDocument();
        if (a != null) this.p = p;
        else this.p = new IndexParaula();
        if (a != null) this.f = f;
        else this.f = new IndexFrase();
    }

    /**
     * Getter IndexAutors.
     * @return IndexAutors, l'índex d'autors del controlador.
     */
    public IndexAutors getIndexAutors() {
        return a;
    }

    /**
     * Getter IndexDocument.
     * @return IndexDocument, l'índex de documents del controlador.
     */
    public IndexDocument getIndexDocument() {
        return d;
    }

    /**
     * Getter IndexParaula.
     * @return IndexParaula, l'índex de paraules del controlador.
     */
    public IndexParaula getIndexParaula() {
        return p;
    }

    /**
     * Getter IndexFrase.
     * @return IndexFrase, l'índex de frases del controlador.
     */
    public IndexFrase getIndexFrase() {
        return f;
    }

    /**
     * Donats un autor, un títol i un contingut en format string, es dona d’alta un document format per aquests tres.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @param contingut -> String; contingut del document.
     * @throws JaExisteixException Si ja existeix un document de l'autor {@code autor} amb títol {@code titol}.
     * @throws NomInvalidException Si el títol no és vàlid, donat que és un string buit.
     */
    public void altaDocument(String autor, String titol, String contingut) throws JaExisteixException, NomInvalidException {

        int lastId = d.getLastId();

        a.afegirTitol(autor,titol,lastId);

        d.afegirDocument(lastId, contingut, autor, titol);

        String[] paraules = d.getParaulesDocument(lastId);
        p.afegirParaules(paraules, lastId, numDocs(), false);

        //No actualizamos Idf al insertar palabras porque ya lo hacemos aqui
        p.recalcularTotsIdf(numDocs());
        recalcularTfIdf();

        for (String paraula : paraules) {
            f.afegirOcurrenciesDocument(paraula, lastId, d.llistaFrasesParaulaDocument(lastId, paraula));
        }

        d.setLastId(lastId + 1);
    }

    /**
     * Donats un autor i un títol, es dona de baixa el document identificat per aquests.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     */
    public void baixaDocument(String autor, String titol) throws NoExisteixException {
        int id = a.obtenirId(autor,titol);

        String[] paraules = d.getParaulesDocument(id);
        p.eliminarOcurrencies(paraules, id, numDocs(), false);
        a.eliminarTitol(autor,titol);


        for (String paraula : paraules) {
            f.eliminarOcurrenciesDocument(paraula, id);
        }

        d.eliminarDocument(id);

        //No actualizamos Idf al insertar palabras porque ya lo hacemos aqui
        p.recalcularTotsIdf(numDocs());
        recalcularTfIdf();

    }

    /**
     * Donats un autor, un títol i un contingut en format string, es substitueix el contingut que tenia el document identificat per l’autor i el títol donats, pel nou contingut indicat.
     * @param autor -> String; autor del document.
     * @param titol -> String; títol del document.
     * @param nouContingut -> String; nou contingut desitjat pel document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     */
    public void modificarDocument(String autor, String titol, String nouContingut) throws NoExisteixException {
        int id = obtenirId(autor, titol);

        String[] paraulesAbans = d.getParaulesDocument(id);

        d.modificarDocument(id,nouContingut);

        String[] paraulesDespres = d.getParaulesDocument(id);


        for (String paraula : paraulesAbans) {
            f.eliminarOcurrenciesDocument(paraula, id);
        }
        for (String paraula : paraulesDespres) {
            f.afegirOcurrenciesDocument(paraula, id, d.llistaFrasesParaulaDocument(id, paraula));
        }

        if (!sonLesMateixesParaules(paraulesAbans, paraulesDespres)) {
            //Recalcular solo el documento si cambian solo las freq.
            //Recalcular todos si se añade o quita una palabra totalmente
            p.eliminarOcurrencies(paraulesAbans, id, numDocs(), false);
            p.afegirParaules(paraulesDespres, id, numDocs(), false);
            p.recalcularTotsIdf(numDocs());
            recalcularTfIdf();
        }
    }

    /**
     * Donats un autor, un títol, i el nou títol desitjat, canvia el títol del document identificat pels dos primers paràmetres, pel tercer paràmetre.
     * @param autor -> String; nom de l'autor.
     * @param oldTitol -> String; títol actual del document.
     * @param newTitol -> String; nou títol al qual es vol canviar.
     * @throws NoExisteixException Si no existeix un document amb el títol {@code oldTitol} o l'autor amb nom {@code autor}.
     * @throws JaExisteixException Si ja existeix un document amb el títol {@code newTitol}.
     */
    public void modificarTitol(String autor, String oldTitol, String newTitol) throws NoExisteixException, JaExisteixException, NomInvalidException {
        a.modificarTitol(autor, oldTitol, newTitol);
    }

    /**
     * Donats un autor i un nou nom d’autor, canvia el nom del primer autor pel del segon.
     * @param oldName -> String; nom actual de l'autor.
     * @param newName -> String; nou nom desitjat de l'autor.
     * @throws NoExisteixException Si no existeix l'autor amb el nom {@code oldName}.
     * @throws JaExisteixException Si ja existeix un autor amb el nom {@code newName}.
     */
    public void modificarAutor(String oldName, String newName) throws NoExisteixException, JaExisteixException, NomInvalidException {
        a.modificarAutor(oldName, newName);
    }

    /**
     * Donats un autor i un títol retorna l’identificador(Integer) d’aquest document.
     * @param autor -> String; nom de l'autor.
     * @param titol -> String; títol del document.
     * @return integer, l'identificador del document de l'autor {@code autor} amb títol {@code titol}.
     * @throws NoExisteixException Si no existeix un document amb el títol {@code titol} o l'autor amb nom {@code autor}.
     */
    public int obtenirId(String autor, String titol) throws NoExisteixException{
        if(!a.estaAutor(autor)) throw new NoExisteixException("No existeix un autor amb nom " + autor);
        return a.obtenirId(autor, titol);
    }

    /**
     * Retorna el número de documents al sistema.
     * @return integer, número de documents.
     */
    public int numDocs(){
        return d.numDocs();
    }

    /**
     * Retorna un array amb els identificadors (Integer) de tots els documents.
     * @return array d'enters, array amb els identificadors de tots els documents.
     */
    public int[] getIdsDocs() {
        return d.getIds();
    }


    /**
     * Donat dos arrays de paraules retorna cert si aquests arrays conten les mateixes paraules.
     * @param p1 -> String[]; primer array de paraules.
     * @param p2 -> String[]; segon array de paraules
     * @return booleà, cert si {@code p1} i {@code p2} tenen les mateixes paraules, i fals altrament.
     */
    private boolean sonLesMateixesParaules(String[] p1, String[] p2) {
        //Si son iguals, no cal recalcular res
        if (Arrays.equals(p1, p2)) return true;

        //Si son del mateix tamany i tenen les mateixes paraules, no cal recalcular
        if (p1.length == p2.length)  {
            Arrays.sort(p1);
            Arrays.sort(p2);
            return Arrays.deepEquals(p1, p2);
        }
        //Sino, recalcular
        return false;
    }

    /**
     * Donada una paraula, retorna una llista de les parelles (autor, títol) dels documents als quals apareix la paraula indicada.
     * @param paraula -> String; paraula de què volem la llista de documents on apareix.
     * @throws NoExisteixException Si la paraula {@code paraula} no apareix a cap document del sistema.
     */
    public void llistaDocsParaula(String paraula)throws NoExisteixException{
        convertirIdsEnTitolsAutor(p.llistaDocsParaula(paraula));
    }

    /**
     * Assigna a ultimalista un array amb tots els autors amb algun document al sistema.
     */
    public void llistarAutors(){
        ultimalista = a.llistarAutors();
    }

    /**
     * Assigna a ultimalista un array amb tots els autors que tenen com a prefix l’string indicat.
     * @param prefix -> String; prefix desitjat.
     */
    public void llistarAutorsPrefix(String prefix){
        ultimalista = a.llistarAutorsPrefix(prefix);
    }

    /**
     * Donat un autor retorna un array amb tots els títols dels documents dels quals n’és autor.
     * @param autor -> String; nom de l'autor.
     * @throws NoExisteixException Si no existeix cap autor amb nom {@code autor}.
     */
    public void llistarTitolsAutor(String autor) throws NoExisteixException {
        ultimalista = a.llistarTitols(autor);
    }

    /**
     * Donat un array amb identificadors (Integer) de documents, els converteix en una llista de parelles (autor,títol).
     * I l'assigna a ulAutorTtitol.
     * @param ids -> int[]; array d'identificadors de documents.
     */
    private void convertirIdsEnTitolsAutor(int[]ids){
        //ArrayList<Pair<String,String>> listaSimilars(d.convertirid(ids);
        ulAutorTtitol = d.convertirid(ids);
    }

    /**
     * Assigna a ultimkdocuments una llista de parelles autor i títol representant els k documents més similars a l’identificat per l’autor i el títol, seguint l’estratègia indicada.
     * @param autor -> String; nom de l'autor.
     * @param titol -> Stirng; títol del document.
     * @param k -> Integer; nombre de documents més similars desitjat.
     * @param estrategia  -> Integer; indica l'estratègia a usar.
     *       0 -> Tf-Idf
     *       Altrament -> Bag of Words.
     * @throws NoExisteixException Si no existeix l'autor {@code autor} o aquest no té cap títol {@code titol}.
     */
    public void llistarDocumentsSimilars(String autor, String titol, int k, int estrategia) throws NoExisteixException{
        convertirIdsEnTitolsAutor(obtenirIdsKdocumentsSimilars(a.obtenirId(autor,titol),k, estrategia));
        ultimkdocuments = (ArrayList<Pair<String, String>>) ulAutorTtitol.clone();;
    }


    /**
     * Donats identificadors de documents, guarda una llista de les parelles (autor, títol) corresponents.
     * @param ids -> int[]; array d'identificadors de documents.
     */
    public void llistarDocumentsExprBool(int[] ids) {
        convertirIdsEnTitolsAutor(ids);
    }

    /**
     * Donats un string, un identificador de document (Integer) i un identificador de frase (Integer), retorna cert si el string està a la frase indicada del document indicat, i fals altrament.
     * @param idDoc -> Integer; identificador del document.
     * @param idFrase -> Integer; identificador de la frase.
     * @param str -> String; paraula/expressió que volem saber si apareix a la frase.
     * @return booleà, cert si {@code str} apareix a la frase identificada del document identificat, i fals altrament.
     */
    public boolean estaStringEnFraseDocument(int idDoc, int idFrase, String str) {
        return d.estaStringEnFraseDocument(idDoc, idFrase, str);
    }

    /**
     * Donats un identificador de document (Integer) i una paraula, retorna un array dels identificadors de les frases del document indicat on apareix la paraula indicada.
     * @param idDoc -> Integer; identificador del document.
     * @param paraula -> String; paraula de què volem saber les frases del document on apareix.
     * @return array d'enters, array amb els identificadors de les frases del document indicat on apareix la paraula {@code paraula}.
     */
    public int[] llistaFrasesParaulaDocument(int idDoc, String paraula) {
        return d.llistaFrasesParaulaDocument(idDoc, paraula);
    }

    /**
     * Donat un document, un número i una estratègia ens retorna un array d'integers amb els documents més semblants al document del paràmetre.
     * @param idDoc -> Integer; identificador del document.
     * @param k -> Integer; nombre de documents més similars que volem.
     * @param estrategia -> Integer; indica quina estratègia volem utilitzar.
     *            0 -> Tf-Idf
     *            Altrament -> Bag of Words.
     * @return array d'enters, array amb els identificadors dels k documents més similars a l'indicat.
     */
    public int[] obtenirIdsKdocumentsSimilars(int idDoc, int k, int estrategia) {

        //Resultats mes grans primers
        PriorityQueue<Pair<Integer, Double>> results = new PriorityQueue<>((o1, o2) -> o2.getSecond().compareTo(o1.getSecond()));


        String[] paraules =  d.getParaulesFiltradesDocument(idDoc);
        int[] idsDocs = d.getIds();

        double modul1 = 0;
        for (String paraula : paraules) {
            double pes = d.getPesosParaulaDocument(idDoc, paraula, estrategia);
            modul1 += pes*pes;
        }
        modul1 = Math.sqrt(modul1);

        for (int id : idsDocs) {
            if (id == idDoc) continue; //skip

            double dotProduct = 0;
            double modul2 = 0;
            String[] paraules2 = d.getParaulesFiltradesDocument(id);
            if (paraules2.length != 0) {
                //hay que calcularlo con todas las palabras de el documeto 2, no solo con las del documento1!
                for (String paraula : paraules2) {
                    double pes1 = d.getPesosParaulaDocument(idDoc, paraula, estrategia);
                    double pes2 = d.getPesosParaulaDocument(id, paraula, estrategia);
                    dotProduct += pes1*pes2;
                    modul2 += pes2*pes2;
                }
                modul2 = Math.sqrt(modul2);

                double cosineSimilarity = (modul2 != 0) ? dotProduct/(modul1*modul2) : 0d;
                results.add(new Pair<Integer, Double>(id, cosineSimilarity));
            }
            else results.add(new Pair<Integer, Double>(id, 0d));

        }

        k = Math.min(k, results.size());
        int[] result = new int[k];
        int i = 0;
        while (i < k && results.size() > 0) {
            result[i++] = results.poll().getFirst();
        }
        return result;
    }


    /**
     * Calcula el Tf-Idf de les paraules indicades en el document indicat.
     * @param paraules -> String[]; array de les paraules de què volem calcular el Tf-Idf.
     * @param idDoc -> Integer; identificador del document.
     * @return array de reals, els valors Tf-Idf de cadascuna de les paraules indicades al document indicat.
     */
    private double[] calculTfIdf(String[] paraules, int idDoc) {
        double[] tfIdfs = new double[paraules.length];
        for (int i = 0; i < paraules.length; ++i) {
            double tf = d.getTfParaulaDocument(idDoc, paraules[i]);
            double idf = p.getIdfParaula(paraules[i]);
            tfIdfs[i] = tf*idf;
        }
        return tfIdfs;
    }

    /**
     * Recalcula els valors Tf-Idf de tots els documents.
     */
    private void recalcularTfIdf() {
        int[] ids = d.getIds();
        for (int id : ids) {
            String[] paraules = d.getParaulesFiltradesDocument(id);
            d.setAllTfIdfsDocument(id, calculTfIdf(paraules, id));
        }
    }

    /**
     * Ordena l'última llista alfabèticament en funció del paràmetre.
     * @param ordre -> Integer; indica en quin ordre alfabètic ordenar la llista.
     *              0 -> Creixent.
     *              Altrament -> Decreixent.
     */
    public void ordenarLlistaAlfabeticamente(int ordre){
        if(ordre == 0) Arrays.sort(ultimalista);
        else if (ordre == 1)Arrays.sort(ultimalista,Collections.reverseOrder());
        else {
            Arrays.sort(ultimalista, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (a.numTitols(o1) >  a.numTitols(o2)) return -1;
                    else if (a.numTitols(o1) ==  a.numTitols(o2)) return 0;
                    else return 1;
                }
            });
        }
    }


    /**
     * Ordena l'última llista de parelles d'autors i títols alfabèticament en funció d’un paràmetre.
     * @param ordre -> Integr; indica quin ordre seguir.
     *              2 -> Creixent.
     *              Altrament -> Decreixent.
     */
    public void ordenarLlistaAlfabeticamentepairs(int ordre){
        if(ordre==2) Collections.sort(ulAutorTtitol, Comparator.comparing((Pair<String, String> o) -> o.getFirst()).thenComparing(Pair::getSecond));
        else {
            Comparator<Pair<String, String>> c = Collections.reverseOrder(Comparator.comparing((Pair<String, String> o) -> o.getFirst()).thenComparing(Pair::getSecond));
            Collections.sort(ulAutorTtitol, c);
        }
    }

    /**
     * Ordena l'última llista de parelles d'autors i títols en funció dels autors que tenen més títols.
     * @throws NoExisteixException Si hi ha algun autor sense cap títol.
     */
    public void ordenarLlistaPairsNdocs()throws NoExisteixException{
        ulAutorTtitol.sort(new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                if(a.numTitols(o1.getFirst()) > a.numTitols(o2.getFirst())) return -1;
                else if(a.numTitols(o1.getFirst()) == a.numTitols(o2.getFirst())) return 0;
                else return 1;
            }
        });
    }

    /**
     * Posa com a ulAutorTitol una còpia de ultimkdocuments.
     */
    public void ordenarKdoc(){
        ulAutorTtitol = (ArrayList<Pair<String, String>>) ultimkdocuments.clone();
    }

    /**
     * Getter de l'última llista.
     * @return array de strings, l'última llista de strings guardada.
     */
    public String[] obtenirLlista(){
        return ultimalista;
    }

    /**
     * Setter de l'última llista.
     * @param ebool -> String[]; nova última llista desitjada.
     */
    public void setLlistaExpbool(String[] ebool){
        ultimalista= ebool;
    }

    /**
     * Getter última llista de parelles d'autors i títols.
     * @return llista de parelles d'autors i títols, l'última llista guardada de parelles d'autors i títols.
     */
    public ArrayList<Pair<String,String>> obtenirLlistaAutorTitol(){
        return ulAutorTtitol;
    }

    /**
     * Donada una paraula retorna un mapa amb key les ids dels documents on apareix i value un array de les ids de les frases del document en que apareix.
     * @param paraula -> String; paraula de què volem saber en quins documents apareix i en quines frases d'aquests.
     * @return mapa de key enters i de value arrays d'enters, un mapa amb key els identificadors dels documents on apareix la paraula {@code paraula}  i de value un array dels
     * identificadors de les frases del document on apareix.
     */
    public Map<Integer, int[]> getIndexFraseParaula(String paraula) {
        return f.getIndexFraseParaula(paraula);
    }

    /**
     * Retorna un map que té com a key l’identificador d’un document (Integer) i com a value un array d’identificadors de frases.
     * On afegim cada document amb l'array contenint els identificadors de totes les seves frases.
     * @return mapa de key enters i de value arrays d'enters, un mapa amb key els identificadors de tots els documents i de value un array amb els identificadors de totes les frases del document.
     */
    public Map<Integer, int[]> getIdsFrases() {
        Map<Integer, int[]> map = new HashMap<>();
        int[] idsDoc = d.getIds();
        for (int id : idsDoc) {
            int[] idsFrase = IntStream.range(0, d.getNumFrasesDocument(id)).toArray();
            map.put(id, idsFrase);
        }
        return map;
    }
}

