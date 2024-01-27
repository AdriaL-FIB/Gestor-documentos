package Junit;

import domini.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IndexDocumentTest {

    IndexDocumentTesteado i;

    @Before
    public void setUp(){
        i = new IndexDocumentTesteado();
    }

    @Test
    public void getIds() {
        int[] a = new int[]{};
        int [] b;
        b = i.getIds();
        assertArrayEquals(a,b);

        i.afegirDocument(0,"test1.","Joan","test1");
        a = new int[]{0};
        b = i.getIds();
        assertArrayEquals(a,b);

        i.afegirDocument(2,"test2.","Joan","test2");
        a = new int[]{0,2};
        b = i.getIds();
        assertArrayEquals(a,b);

        i.afegirDocument(1,"test3.","Joan","test3");
        a = new int[]{0,1,2};
        b = i.getIds();
        assertArrayEquals(a,b);

        i.eliminarDocument(2);
        a = new int[]{0,1};
        b = i.getIds();
        assertArrayEquals(a,b);

        i.eliminarDocument(1);
        a = new int[]{0};
        b = i.getIds();
        assertArrayEquals(a,b);

        i.eliminarDocument(0);
        a = new int[]{};
        b = i.getIds();
        assertArrayEquals(a,b);
    }

    @Test
    public void numDocs() {
        int n;
        n = i.numDocs();
        assertEquals(0,n);

        i.afegirDocument(0,"test1.","Joan","test1");
        n = i.numDocs();
        assertEquals(1,n);

        i.afegirDocument(2,"test2.","Joan","test2");
        n = i.numDocs();
        assertEquals(2,n);

        i.afegirDocument(1,"test3.","Joan","test3");
        n = i.numDocs();
        assertEquals(3,n);

        i.eliminarDocument(1);
        n = i.numDocs();
        assertEquals(2,n);

        i.afegirDocument(1,"test3.","Joan","test3");
        n = i.numDocs();
        assertEquals(3,n);

        i.eliminarDocument(1);
        n = i.numDocs();
        assertEquals(2,n);

        i.eliminarDocument(2);
        n = i.numDocs();
        assertEquals(1,n);

        i.eliminarDocument(0);
        n = i.numDocs();
        assertEquals(0,n);
    }

    @Test
    public void afegirDocument() {
        int n;
        int[] a = new int[]{0};
        int [] b;

        i.afegirDocument(0,"test1.","Joan","test1");
        n = i.numDocs();
        assertEquals(1,n);
        b = i.getIds();
        assertArrayEquals(a,b);

        i.afegirDocument(2,"test2.","Joan","test2");
        n = i.numDocs();
        assertEquals(2,n);
        a = new int[]{0,2};
        b = i.getIds();
        assertArrayEquals(a,b);

        i.afegirDocument(1,"test3.","Joan","test3");
        n = i.numDocs();
        assertEquals(3,n);
        a = new int[]{0,1,2};
        b = i.getIds();
        assertArrayEquals(a,b);

    }

    @Test
    public void eliminarDocument() {
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");
        i.afegirDocument(2,"test3.","Joan","test3");
        int n;
        int[] a = new int[]{1,2};
        int [] b;

        i.eliminarDocument(0);
        n = i.numDocs();
        assertEquals(2,n);
        b = i.getIds();
        assertArrayEquals(a,b);

        i.eliminarDocument(2);
        n = i.numDocs();
        assertEquals(1,n);
        a = new int[]{1};
        b = i.getIds();
        assertArrayEquals(a,b);

        i.eliminarDocument(1);
        n = i.numDocs();
        assertEquals(0,n);
        a = new int[]{};
        b = i.getIds();
        assertArrayEquals(a,b);
    }

    @Test
    public void modificarDocument() {
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");
        i.afegirDocument(2,"test3.","Joan","test3");
        String s;

        i.modificarDocument(1,"noutest2.");
        s = i.getContingutDocument(1);
        assertEquals("noutest2.",s);
        s = i.getContingutDocument(0);
        assertEquals("test1.",s);
        s = i.getContingutDocument(2);
        assertEquals("test3.",s);

        i.modificarDocument(0,"noutest1.");
        s = i.getContingutDocument(0);
        assertEquals("noutest1.",s);
        s = i.getContingutDocument(1);
        assertEquals("noutest2.",s);
        s = i.getContingutDocument(2);
        assertEquals("test3.",s);


        i.modificarDocument(1,"nounoutest2.");
        s = i.getContingutDocument(1);
        assertEquals("nounoutest2.",s);
        s = i.getContingutDocument(0);
        assertEquals("noutest1.",s);
        s = i.getContingutDocument(2);
        assertEquals("test3.",s);
    }

    @Test
    public void getPath() {
        //Temporal no toquem el path dels documents encara
        i.afegirDocument(0,"test1.","Joan","test1");
        String s = i.getPath(0);
        String aux = null;
        assertEquals(aux,s);
    }

    @Test
    public void getTfParaulaDocument() {
        double d;
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");
        d = i.getTfParaulaDocument(0,"hola");
        assertEquals(4,d,0.00001);
        d = i.getTfParaulaDocument(1,"test2");
        assertEquals(5,d,0.00001);
        d = i.getTfParaulaDocument(1,"");
        assertEquals(0,d,0.00001);
    }

    @Test
    public void getParaulesDocument() {
        String[] s = new String[]{"getParaules","correcte"};
        String[] aux;
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");
        aux = i.getParaulesDocument(0);
        assertArrayEquals(s,aux);
        aux = i.getParaulesDocument(1);
        assertArrayEquals(s,aux);
    }

    @Test
    public void getParaulesFiltradesDocument() {
        String[] s = new String[]{"getParaulesFiltrades","correcte"};
        String[] aux;
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");
        aux = i.getParaulesFiltradesDocument(1);
        assertArrayEquals(s,aux);
        aux = i.getParaulesFiltradesDocument(0);
        assertArrayEquals(s,aux);
    }

    /*
    //No se com testejar; es una redireccio igualment
     //funciona bé si ho fa setAllTfIdf de document(ja testejada)
    @Test
    public void setAllTfIdfsDocument() {

    }
     */

    @Test
    public void getPesosParaulaDocument() {
        double d;
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");
        d = i.getPesosParaulaDocument(0,"holaadeu",0);
        assertEquals(8,d,0.00001);
        d = i.getPesosParaulaDocument(1,"test23",0);
        assertEquals(6,d,0.00001);
        d = i.getPesosParaulaDocument(1,"",1);
        assertEquals(1,d,0.00001);
    }

    @Test
    public void estaStringEnFraseDocument() {
        i.afegirDocument(0,"test1.test3.","Joan","test3");
        i.afegirDocument(1,"test2.","Joan","test4");
        boolean b;

        b = i.estaStringEnFraseDocument(1,0,"test1");
        assertTrue(b);
        b = i.estaStringEnFraseDocument(0,0,"test1");
        assertTrue(b);
        b = i.estaStringEnFraseDocument(0,1,"test1");
        assertTrue(b);
    }

    @Test
    public void llistaFrasesParaulaDocument() {
        int[] a = new int[]{0,1,2,3};
        int[] res;
        i.afegirDocument(0,"test1.test3.","Joan","test3");
        i.afegirDocument(1,"test2.","Joan","test4");

        res = i.llistaFrasesParaulaDocument(0,"test1");
        assertArrayEquals(a,res);

        res = i.llistaFrasesParaulaDocument(1,"test1");
        assertArrayEquals(a,res);

        res = i.llistaFrasesParaulaDocument(0,"test2");
        assertArrayEquals(a,res);
    }

    @Test
    public void getNumFrasesDocument() {
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");
        int aux = i.getNumFrasesDocument(0);
        assertEquals(0,aux);
        aux = i.getNumFrasesDocument(1);
        assertEquals(0,aux);
        i.modificarDocument(1,"test3");
        aux = i.getNumFrasesDocument(1);
        assertEquals(0,aux);
        aux = i.getNumFrasesDocument(0);
        assertEquals(0,aux);
    }

    @Test
    public void getContingutDocument() {
        String s;
        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");

        s = i.getContingutDocument(1);
        assertEquals("test2.",s);

        s = i.getContingutDocument(0);
        assertEquals("test1.",s);

        i.modificarDocument(1, "test3.");
        s = i.getContingutDocument(1);
        assertEquals("test3.",s);

        s = i.getContingutDocument(0);
        assertEquals("test1.",s);

        i.modificarDocument(1, "");
        s = i.getContingutDocument(1);
        assertEquals("",s);


    }

    @Test
    public void convertirid() {
        ArrayList<Pair<String,String>> s=new ArrayList<>();
        Pair<String,String> p;
        ArrayList<Pair<String,String>> aux;
        int[] ids = new int[]{};
        aux = i.convertirid(ids);
        assertArrayEqualsS(s,aux);

        i.afegirDocument(0,"test1.","Joan","test1");
        i.afegirDocument(1,"test2.","Joan","test2");

        ids = new int[]{1};
        p = new Pair<>("test2","Joan");
        s.add(p);
        aux = i.convertirid(ids);
        assertArrayEqualsS(s,aux);

        ids = new int[]{0};
        s.remove(p);
        p = new Pair<>("test1","Joan");
        s.add(p);
        aux = i.convertirid(ids);
        assertArrayEqualsS(s,aux);

        ids = new int[]{0,1};
        p = new Pair<>("test2","Joan");
        s.add(p);
        aux = i.convertirid(ids);
        assertArrayEqualsS(s,aux);

        ids = new int[]{1,0};
        s=new ArrayList<>();
        p = new Pair<>("test2","Joan");
        s.add(p);
        p = new Pair<>("test1","Joan");
        s.add(p);
        aux = i.convertirid(ids);
        assertArrayEqualsS(s,aux);

    }

    private void assertArrayEqualsS(ArrayList<Pair<String, String>> s, ArrayList<Pair<String, String>> aux) {
        assertEquals(s.size(),aux.size());

        for (int i=0; i<s.size();i++) {
            assertEquals(s.get(i).getFirst(),aux.get(i).getFirst());
            assertEquals(s.get(i).getSecond(),aux.get(i).getSecond());
        }
    }
}