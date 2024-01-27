package domini.clases;
import java.io.Serializable;
import java.util.*;

import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomInvalidException;
import domini.utils.TST;

import javax.naming.InvalidNameException;

public class IndexAutors implements Serializable {

    //atributos
    private final Map<String, Autor> indxAutors;
    private final TST autorsPerPrefix;

    //Metodos

    /**
     * Constructora per defecte
     */
    public IndexAutors(){
        this.indxAutors = new HashMap<>();
        this.autorsPerPrefix = new TST();
    }


    /**
     * Retorna cert si existeix l’autor amb l’identificador passat per referència.
     * @param autor -> String; nom autor.
     * @return booleà, cert si existeix autor, fals altrament.
     */
    public boolean estaAutor(String autor){
        autor = autor.toLowerCase();
        return indxAutors.containsKey(autor);
    }

    /**
     * Retorna la quantitat total d’autors donats d’alta.
     * @return enter, número d'autors.
     */
    public int numAutors(){
        return indxAutors.size();
    }

    /**
     * Donat un autor retorna el número de títols que té aquest.
     * @param autor -> String;  nom del autor.
     * @return enter, número de títols del autor {@code autor}.
     * @throws NoExisteixException Si no existeix cap autor amb el nom {@code autor}.
     */
    public int numTitols(String autor) throws NoExisteixException{
        autor = autor.toLowerCase();
        if(!indxAutors.containsKey(autor)) throw new NoExisteixException("No existeix un autor amb nom " + autor);
        return indxAutors.get(autor).numTitols();
    }

    /**
     * Retorna una array de strings amb tots els autors.
     * @return array de strings, array amb tots els autors.
     */
    public String[] llistarAutors(){
        ArrayList<String> s= new ArrayList<>();
        for (Map.Entry<String, Autor> set : indxAutors.entrySet()) {
            s.add(set.getKey());
        }
        return s.toArray(String[]::new);
    }

    /**
     * Retorna un array de Strings amb tots els autors que tenen com a prefix l’string indicat.
     * @param prefix -> String; prefix desitjat pels autors del return.
     * @return array de strings, array d'autors que tenen {@code prefix} com a prefix.
     */
    public String[] llistarAutorsPrefix(String prefix){
        //if(indxAutors.isEmpty()) throw new EstaBuitException("La llista d'autors esta buida");
        return autorsPerPrefix.obtenirParaulesPerPrefix(prefix);
    }

    /**
     * Retorna una array de Strings amb tots els títols d’un autor donat per paràmetre.
     * @param autor -> String; nom autor.
     * @return array de strings, array de títols del autor {@code autor}.
     * @throws NoExisteixException Si no existeix cap autor amb el nom {@code autor}.
     */
    public String[] llistarTitols(String autor) throws NoExisteixException{
        autor = autor.toLowerCase();
        if(!indxAutors.containsKey(autor)) throw new NoExisteixException("No existeix un autor amb nom " + autor);
        Autor a = indxAutors.get(autor);
        //if (a == null) throw exception
        return a.llistarTots();
    }

    /**
     * Crea i agrega un títol nou a un autor ja existent, en cas de que l’autor no existís el crea també.
     * @param autor -> String; nom autor.
     * @param titol -> String; nom document.
     * @param id -> Integer; identificador del document.
     * @throws JaExisteixException Si ja existeix un document de l'autor {@code autor} amb títol {@code titol}.
     * @throws NomInvalidException Si el nom de l'autor o del títol no és vàlid, és un string buit.
     */
    public void afegirTitol(String autor,String titol, int id) throws JaExisteixException, NomInvalidException {
        autor = autor.toLowerCase();
        if (autor.strip().equals("")) throw new NomInvalidException("Nom d'autor no pot ser buit");
        Autor a;
        if (!estaAutor(autor)) { //Nuevo autor
            a = new Autor();
            indxAutors.put(autor, a);
            autorsPerPrefix.insertParaula(autor);
        }
        else {
            a = indxAutors.get(autor);
        }
        a.afegirTitol(titol, id);
    }

    /**
     * Donat un identificador, esborra un títol d’un autor, a més si l'autor es queda sense títol s'esborra també l’autor.
     * @param autor -> String; nom autor.
     * @param titol -> String; nom document.
     * @throws NoExisteixException Si no existeix l'autor {@code autor} o el títol {@code titol}.
     */
    public void eliminarTitol(String autor, String titol) throws NoExisteixException {
        autor = autor.toLowerCase();
        if(!indxAutors.containsKey(autor)) throw new NoExisteixException("No existeix un autor amb nom " + autor);
        Autor a = indxAutors.get(autor);
        if (a != null) {
            a.eliminarTitol(titol);
            if(indxAutors.get(autor).numTitols()<1){
                indxAutors.remove(autor);
                autorsPerPrefix.eliminarParaula(autor);
            }
        }
    }

    /**
     * Donats un autor, un títol i un nou títol desitjat modifica el títol del document indenificat per l’autor i el primer títol, assignant-li el nou títol desitjat.
     * @param autor -> String; nom autor.
     * @param oldTitol -> String; titol actual del document.
     * @param newTitol -> String; nou títol al qual es vol canviar.
     * @throws NoExisteixException Si no existeix el títol amb nom {@code oldTitol} o l'autor amb nom {@code autor}.
     * @throws JaExisteixException Si ja existeix un títol amb el nom {@code newTitol}.
     */
    public void modificarTitol(String autor, String oldTitol, String newTitol) throws NoExisteixException, JaExisteixException, NomInvalidException {
        autor = autor.toLowerCase();
        if(!indxAutors.containsKey(autor)) throw new NoExisteixException("No existeix un autor amb nom " + autor);
        Autor a = indxAutors.get(autor);
        if (a != null) {
            a.modificarTitol(oldTitol, newTitol);
        }
    }

    /**
     * Donat un autor, i el seu nou nom desitjat, modifica el nom de l’autor pel desitjat.
     * @param oldName -> String; nom actual de l'autor.
     * @param newName -> String; nou nom de l'autor.
     * @throws NoExisteixException Si no existeix l'autor amb el nom {@code oldName}.
     * @throws JaExisteixException Si ja existeix un autor amb el nom {@code newName}.
     */
    public void modificarAutor(String oldName, String newName) throws NoExisteixException, JaExisteixException, NomInvalidException {
        oldName = oldName.toLowerCase();
        newName = newName.toLowerCase();
        if (newName.strip().equals("")) throw new NomInvalidException("Nom d'autor no pot ser buit");
        if(!indxAutors.containsKey(oldName)) throw new NoExisteixException("No existeix un autor amb nom " + oldName);
        if(indxAutors.containsKey(newName)) throw new JaExisteixException("Ja existeix un autor amb nom " + newName);
        Autor a = indxAutors.get(oldName);
        indxAutors.put(newName, a);
        indxAutors.remove(oldName);
        autorsPerPrefix.eliminarParaula(oldName);
        autorsPerPrefix.insertParaula(newName);
    }

    /**
     * Retorna cert si existeix un document amb el nom de l’autor i el títol proporcionats.
     * @param autor -> String; nom autor.
     * @param titol -> String; nom document.
     * @return booleà, cert si l'autor té un document amb títol {@code titol}, fals altrament.
     * @throws NoExisteixException Si no existeix l'autor {@code autor}.
     */
    public boolean estaTitol(String autor,String titol) throws NoExisteixException {
        autor = autor.toLowerCase();
        if(!indxAutors.containsKey(autor)) throw new NoExisteixException("No existe el autor: "+ autor);
        return indxAutors.get(autor).pertanyTitol(titol);
    }


    /**
     * Retorna l'identificador del document amb l’autor i títol proporcionats.
     * @param autor -> String; nom del autor.
     * @param titol -> String; nom del document.
     * @return integer, identificador del document.
     * @throws NoExisteixException Si no existeix l'autor {@code autor}.
     */
    public int obtenirId(String autor, String titol)throws NoExisteixException {
        autor = autor.toLowerCase();
        if(!indxAutors.containsKey(autor)) throw new NoExisteixException("No existeix un autor amb nom " + autor);
        return indxAutors.get(autor).obtenirId(titol);
    }

}