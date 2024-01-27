package Junit;

import java.util.ArrayList;

public class ParaulaStub {
    ArrayList<Integer> paraulaDocs;
    int ocurrenciesDoc;
    double idf;
    String valor;

    //pasamos numDocs por parametro para evitar acoplamiento (?)
    public ParaulaStub(String valor, int id, int numDocs, boolean actualitzarIdf) {
        this.valor = valor;
        this.ocurrenciesDoc = 1;


        paraulaDocs = new ArrayList<>();

        paraulaDocs.add(id);
        if (actualitzarIdf) idf = 1d;
        else idf = -1d;
    }

    public void novaOcurrencia(int id, int numDocs, boolean actualitzarIdf) {
        ++ocurrenciesDoc;
        paraulaDocs.add(id);
        if (actualitzarIdf) idf = 1;
    }

    //Devuelve cierto si ya no aparece en ningun documento, falso en caso contrario
    public boolean eliminarOcurrencia(int id, int numDocs, boolean actualitzarIdf) {
        --ocurrenciesDoc;
        if (ocurrenciesDoc <= 0) return true;
        paraulaDocs.removeIf(doc -> doc.equals(id));
        if (actualitzarIdf) idf = 1d;
        return false;
    }

    public void actualitzarIdf(int numDocs) {
        idf = 1d;
    }

    public double getIdf() {
        return idf;
    }

    public int[] llistaDocs() {
        return paraulaDocs.stream().mapToInt(Integer::intValue).toArray();
    }
}
