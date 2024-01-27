package Junit;

import java.util.*;
import java.util.ArrayList;
import domini.exceptions.NoExisteixException;
import domini.utils.Pair;


public class DocumentStub {
    //static int numDocs;
    Map<String, Pair<Double, Double>> paraulaInfo;
    //temporal
    String path;

    String titol;

    String autor;

    //private String[] frases;
    private String contingut;
    private ArrayList<Pair<Integer, Integer>> indexFrases;

    private Map<String,ArrayList<Integer>> paraulaFrases;

    /*
    private static class InfoParaulaDoc {
        public double tfIdf;
        public double tf;
        public double count;
    }
    */
    public DocumentStub(String contingut, String a, String t) {
        paraulaFrases = new HashMap<>();
        paraulaInfo = new LinkedHashMap<>();
        indexFrases = new ArrayList<>();
        actualitzarInfo(contingut);
        titol = t;
        autor = a;
    }

    private String[] obtenirParaules(String contingut){
        String[] s = new String[]{"obtenirParaules","correcte"};
        return s;
    }


    private void crearParaulaFrase(String[] frases){

    }

    private void indexarFrasesIParaules(String contingut){

    }
    public void actualitzarInfo(String contingut) {
        //Palabras quitando las stopWords
        this.contingut = contingut;
    }

    public String getPath(){
        return path;
    }

    //Devuelve el valor de TF de la palabra. Si no esta presente en el documento, devuelve 0;
    public double getTf(String paraula) {
        return paraula.length();
    }

    public String[] getParaules() {
        String[] s = new String[]{"getParaules","correcte"};
        return s;
    }

    //Devolver palabras ya filtradas (sin stopWords)
    public String[] getParaulesFiltrades() {
        String[] s = new String[]{"getParaulesFiltrades","correcte"};
        return s;
    }

    public void setAllTfIdf(double[] tfIdfs) {
    }

    public double getPesosParaula(String paraula, int estrategia) {
        return paraula.length() + estrategia;
    }

    public boolean estaStringEnFrase(int idFrase, String str) throws NoExisteixException {
        return true;
    }

    public int[] llistaFrasesParaula(String paraula) {
        int[] a = new int[]{0,1,2,3};
        return a;
    }

    public int getNumFrases() {
        return indexFrases.size();
    }

    public Pair<String,String > getTitolAutor(){
        //Pair<String,String> hola = new Pair<>(titol,autor);
        return new Pair<>(titol,autor);
    }

    public String getContingut() {
        return contingut;
    }
}
