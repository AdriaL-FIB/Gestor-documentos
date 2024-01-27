package domini.clases;

import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomInvalidException;

import java.io.Serializable;
import java.util.*;

public class Autor implements Serializable {
    Map<String, Integer> idTitols;


    /**
     * Constructora per defecte
     */
    public Autor() {
        idTitols = new HashMap<>();
    }

    /**
     * Donats un títol i un identificador afegeix el títol amb aquest identificador.
     * @param titol -> String; títol del document.
     * @param id -> Enter; identificador del document.
     * @throws JaExisteixException Si ja existeix un document amb el títol {@code titol}.
     * @throws NomInvalidException Si el títol no és vàlid, donat que és un string buit.
     */
    public void afegirTitol(String titol, Integer id) throws JaExisteixException, NomInvalidException {
        if (titol.strip().equals("")) throw new NomInvalidException("Nom del títol no pot ser buit");
        if (idTitols.containsKey(titol)) throw new JaExisteixException("Ja existeix un document amb titol " + titol);
        idTitols.put(titol, id);
    }


    /**
     * Donat un títol, si existeix, l'elimina.
     * @param titol -> String; títol del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     */
    public void eliminarTitol(String titol) throws NoExisteixException {
        if(!idTitols.containsKey(titol)) throw new NoExisteixException("No existeix un document amb nom " + titol);
        idTitols.remove(titol);
    }

    /**
     * Donats un títol i un nou títol desitjat, modifica el primer, que passa a ser el segon.
     * @param oldTitol -> String; títol actual del document.
     * @param newTitol -> String; nou títol al qual es vol canviar.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code oldTitol}.
     * @throws JaExisteixException Si ja existeix un document amb el títol {@code newTitol}.
     * @throws NomInvalidException El nom del titol no pot ser buit.
     */
    public void modificarTitol(String oldTitol, String newTitol) throws NoExisteixException, JaExisteixException, NomInvalidException {
        if (newTitol.strip().equals("")) throw new NomInvalidException("Nom del títol no pot ser buit");
        if(!idTitols.containsKey(oldTitol)) throw new NoExisteixException("No existeix un document amb nom " + oldTitol);
        if(idTitols.containsKey(newTitol)) throw new NoExisteixException("Ja existeix un document amb nom " + newTitol);
        idTitols.put(newTitol, idTitols.get(oldTitol));
        idTitols.remove(oldTitol);
    }


    /**
     * Donat un títol retorna cert si l'autor té un document amb aquest títol.
     * @param titol -> String; títol del document.
     * @return boolean, cert si està el títol, fals altrament.
     */
    public boolean pertanyTitol(String titol){
        return idTitols.containsKey(titol);
    }

    /**
     *  Retorna l'identificador del títol proporcionat.
     * @param titol -> String; títol del document.
     * @return integer, amb l'identificador del document.
     * @throws NoExisteixException Si no existeix cap document amb el títol {@code titol}.
     */
    public int obtenirId(String titol) throws NoExisteixException{
        if(!idTitols.containsKey(titol)) throw new NoExisteixException("No existeix un document amb nom " + titol);
        return idTitols.get(titol);
    }

    /**
     * Retorna el nombre de títols d’aquest autor.
     * @return integer, número de títols.
     */
    public Integer numTitols(){
        return idTitols.size();
    }


    /**
     * Retorna una array amb tots els títols d’aquest autor.
     * @return array de strings, array amb els títols de l'autor.
     */
    public String[] llistarTots(){
        ArrayList<String> tit = new ArrayList<>();
        for (Map.Entry<String, Integer> set : idTitols.entrySet()) {
            tit.add(set.getKey());
        }
        return tit.toArray(String[]::new);
    }

}