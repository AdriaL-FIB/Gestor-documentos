package Junit;

import domini.exceptions.ExprBoolIncorrectaException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.clases.ExprBool;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class ConjuntExprBoolTesteado {

    //Atributs

    private Map<String, ExprBool> cb;

    //metodes

    public ConjuntExprBoolTesteado(){
        this.cb = new HashMap<>();
    }

    public void agregarExprBool(String nomEbool, String ebool) throws JaExisteixException, ExprBoolIncorrectaException {
        if(cb.containsKey(nomEbool)) throw new JaExisteixException("Ja existeix la expresio "+ ebool);
        ExprBool newbool = BooleanParserStub.crearExpresion(ebool);
        cb.put(nomEbool,newbool);
    }

    public boolean pertanyExprBool (String nomEbool){
        return cb.containsKey(nomEbool);
    }

    public void eliminarExprBool(String nomEbool) throws NoExisteixException{
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        cb.remove(nomEbool);
    }
    public void modificarExprBool(String nomEbool, String ebool) throws NoExisteixException, ExprBoolIncorrectaException {
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        ExprBool newbool = BooleanParserStub.crearExpresion(ebool);
        cb.put(nomEbool,newbool);
    }

    public ExprBool getExpBool(String nomEbool) throws NoExisteixException{
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        return cb.get(nomEbool);
    }

    public String getStringExpBool(String nomEbool) throws NoExisteixException{
        if(!cb.containsKey(nomEbool)) throw new NoExisteixException("No existeix la expresio "+ nomEbool);
        return cb.get(nomEbool).getRawExpression();
    }

    public String[] llistarExprBooleanas() {
        ArrayList<String> s= new ArrayList<>();
        for (Map.Entry<String, ExprBool> set : cb.entrySet()) {
            s.add(set.getKey());
        }
        return s.toArray(String[]::new);
    }

}