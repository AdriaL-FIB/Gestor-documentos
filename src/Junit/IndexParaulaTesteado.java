package Junit;

import domini.exceptions.NoExisteixException;

import java.util.HashMap;
import java.util.Map;
public class IndexParaulaTesteado {
    Map<String, ParaulaStub> paraules;

    public IndexParaulaTesteado() {
        paraules = new HashMap<>();
    }

    public void afegirParaula(String paraula, int idDoc, int numDocs, boolean actualitzarIdf) {
        if (paraules.containsKey(paraula)) {
            novaOcurrenciaParaula(paraula, idDoc, numDocs, actualitzarIdf);
        }
        else {
            paraules.put(paraula, new ParaulaStub(paraula, idDoc, numDocs, actualitzarIdf));
        }
    }

    public void afegirParaules(String[] paraules, int idDoc, int numDocs, boolean actualitzarIdf) {
        for (String p : paraules) {
            afegirParaula(p, idDoc, numDocs, actualitzarIdf);
        }
    }

    public void eliminarParaula(String paraula)throws NoExisteixException {
        if (!paraules.containsKey(paraula)) throw new NoExisteixException("No existeix la paraula: " + paraula);

        paraules.remove(paraula);
    }

    public void eliminarOcurrencies(String[] paraules, int idDoc, int numDocs, boolean actualitzarIdf) throws NoExisteixException{
        for (String p : paraules) {
            eliminarOcurrenciaParaula(p, idDoc, numDocs, actualitzarIdf);
        }
    }

    public double getIdfParaula(String paraula)throws NoExisteixException {
        if (!paraules.containsKey(paraula)) throw new NoExisteixException("No existeix la paraula: " + paraula);
        return paraules.get(paraula).getIdf();
    }

    public void recalcularTotsIdf(int numDocs) {
        for (ParaulaStub p : paraules.values()) {
            p.actualitzarIdf(numDocs);
        }
    }

    public int[] llistaDocsParaula(String paraula) throws NoExisteixException{
        if (!paraules.containsKey(paraula.toLowerCase())) throw new NoExisteixException("No existeix la paraula: " + paraula);
        return paraules.get(paraula.toLowerCase()).llistaDocs();
    }

    public void novaOcurrenciaParaula(String paraula, int idDoc, int numDocs, boolean actualitzarIdf) {
        paraules.get(paraula).novaOcurrencia(idDoc, numDocs, actualitzarIdf);
    }

    public void eliminarOcurrenciaParaula(String paraula, int idDoc, int numDocs, boolean actualitzarIdf) {
        if (paraules.get(paraula).eliminarOcurrencia(idDoc, numDocs, actualitzarIdf)) eliminarParaula(paraula);
    }

}