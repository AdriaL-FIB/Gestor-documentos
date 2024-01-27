package domini.clases;

import domini.exceptions.ExprBoolIncorrectaException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomInvalidException;
import domini.utils.BooleanParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class ConjuntExprBool implements Serializable {

    //Atributs

    private Map<String, ExprBool> cb;

    //metodes

    /**
     * Creadora per defecte
     */
    public ConjuntExprBool(){
        this.cb = new HashMap<>();
    }

    /**
     * Donats un identificador i una expressió booleana, en format string, crea l'expressió booleana i li assigna l'identificador.
     * @param nomEbool -> String; identificador de l'expressió booleana(nom).
     * @param ebool -> String; expressió booleana en format string.
     * @throws JaExisteixException Si ja existeix una expressió booleana amb l'identificador {@code nomEbool}.
     * @throws ExprBoolIncorrectaException Si l'expressió booleana {@code ebool} és incorrecta.
     * @throws NomInvalidException Si l'identificador no és vàlid, donat que és un string buit.
     */
    public void agregarExprBool(String nomEbool, String ebool) throws JaExisteixException, ExprBoolIncorrectaException, NomInvalidException {
        if (nomEbool.strip().equals("")) throw new NomInvalidException("Nom de la expressió booleana no pot ser buida");
        if(cb.containsKey(nomEbool)) throw new JaExisteixException("Ja existeix la expresio "+ ebool);
        ExprBool newbool = new ExprBool(ebool);
        cb.put(nomEbool,newbool);
    }

    /**
     * Retorna cert si existeix l’expressió booleana amb l’identificador indicat, fals altrament.
     * @param nomEbool -> String; identificador de l'expressió booleana(nom).
     * @return booleà, cert si existeix l’expressió booleana amb l’identificador indicat, fals altrament.
     */
    public boolean pertanyExprBool(String nomEbool){
        return cb.containsKey(nomEbool);
    }

    /**
     * S’elimina l'expressió booleana indicada.
     * @param nomEbool -> String; identificador de l'expressió booleana(nom).
     * @throws NoExisteixException Si no existeix l'expressió booleana amb identificador {@code nomEbool}.
     */
    public void eliminarExprBool(String nomEbool) throws NoExisteixException{
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        cb.remove(nomEbool);
    }

    /**
     * Donat un identificador i una expressió booleana, modifica l'expressió indicada pel primer, fent que el seu nou valor sigui el segon.
     * @param nomEbool -> String; identificador de l'expressió booleana(nom).
     * @param ebool -> String; expressió booleana en format string.
     * @throws NoExisteixException Si no existeix l'expressió booleana amb identificador {@code nomEbool}.
     * @throws ExprBoolIncorrectaException Si l'expressió booleana {@code ebool} és incorrecta.
     */
    public void modificarExprBool(String nomEbool, String ebool) throws NoExisteixException, ExprBoolIncorrectaException {
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        ExprBool newbool = new ExprBool(ebool);
        cb.put(nomEbool,newbool);
    }

    /**
     * Retorna l’expressió booleana amb l'identificador indicat.
     * @param nomEbool -> String; identificador de l'expressió booleana(nom).
     * @return ExprBool, node de l'arbre que representa l'expressió booleana.
     * @throws NoExisteixException Si no existeix l'expressió booleana amb identificador {@code nomEbool}.
     */
    public ExprBool getExpBool(String nomEbool) throws NoExisteixException{
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        return cb.get(nomEbool);
    }

    /**
     * Retorna l’expressió booleana amb l’identificador indicat, en format string.
     * @param nomEbool -> String; identificador de l'expressió booleana(nom).
     * @return string, l'expressió booleana en format string.
     * @throws NoExisteixException si no existeix la id
     */
    public String getStringExpBool(String nomEbool) throws NoExisteixException{
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        return cb.get(nomEbool).rawExpression;
    }

    /**
     * Retorna una llista amb els identificadors de totes les expressions booleanes.
     * @return array de strings, llista amb els identificadors de totes les expressions booleanes.
     */
    public String[] llistarExprBooleanas() {
        ArrayList<String> s= new ArrayList<>();
        for (Map.Entry<String, ExprBool> set : cb.entrySet()) {
            s.add(set.getKey());
        }
        return s.toArray(String[]::new);
    }

}