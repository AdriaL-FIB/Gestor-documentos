package Junit;



import org.junit.Before;
import org.junit.Test;
import domini.utils.Pair;

import static org.junit.Assert.*;

public class DocumentTest {

    DocumentTesteado d;

    @Before
    public void setUp() {
        d = new DocumentTesteado("test correcte.","Albert","Test");
    }

    @Test
    public void actualitzarInfo() {
        String[] aux;
        String[] exp = new String[]{"test","correcte"};
        aux = d.getParaules();
        assertArrayEquals(exp,aux);
        double ex;
        double b = d.getTf("test");
        ex = 0.2;
        assertEquals(ex,b,0.0001);
        b = d.getPesosParaula("test",0);
        ex = -1.0;
        assertEquals(ex,b,0.0001);
        b = d.getTf("correcte");
        ex = 0.8;
        assertEquals(ex,b,0.0001);
        b = d.getPesosParaula("correcte",0);
        ex = -1.0;
        assertEquals(ex,b,0.0001);
    }


    @Test
    public void getTf() {
        double aux;
        double exp;

        aux = d.getTf("Casa");
        exp = 0.0;
        assertEquals(exp,aux,0.0001);
        aux = d.getTf("test");
        exp = 0.2;
        assertEquals(exp,aux,0.0001);
        aux = d.getTf("correcte");
        exp = 0.8;
        assertEquals(exp,aux,0.0001);
        aux = d.getTf("");
        exp = 0.0;
        assertEquals(exp,aux,0.0001);
    }

    @Test
    public void getParaules() {
        String[] aux;
        String[] exp = new String[]{"test","correcte"};
        aux = d.getParaules();
        assertArrayEquals(exp,aux);
    }

    @Test
    public void getParaulesFiltrades() {
        String[] aux;
        String[] exp = new String[]{"test","correcte","correcte","correcte","correcte"};
        aux = d.getParaulesFiltrades();
        assertArrayEquals(exp,aux);
    }

    @Test
    public void setAllTfIdf() {
        double aux;
        double[] exp = new double[]{5.0/7,20.0,3.0,1.333,1.0};
        d.setAllTfIdf(exp);

        aux = d.getPesosParaula("test",0);
        assertEquals(exp[0],aux,0.0001);
        aux = d.getPesosParaula("correcte",0);
        assertEquals(exp[4],aux,0.0001); //Es queda amb el ultim per les repeticions

    }

    @Test
    public void getPesosParaula() {
        double aux;
        double[] exp = new double[]{19.0/3,12.1,3.0,1.333,13.36};
        //Fan falta 5 perk agafa paraules filtrades sense Stop Words i el stub torna
        //correcte 4 cops, per tant el ultim es queda.
        // En un cas normal com coordenades no te repetits no hi haruia problema.
        d.setAllTfIdf(exp);

        aux = d.getPesosParaula("test",0);
        assertEquals(exp[0],aux,0.0001);
        aux = d.getPesosParaula("correcte",0);
        assertEquals(exp[4],aux,0.0001);
        aux = d.getPesosParaula("correcte",1);
        assertEquals(20,aux,0.0001);
        aux = d.getPesosParaula("test",1);
        assertEquals(5,aux,0.0001);
    }

    @Test
    public void estaStringEnFrase() {
        boolean b = d.estaStringEnFrase(0,"test");
        assertTrue(b);

        //Frase 0 es "Test" y les 1-4 "correcte"
        b = d.estaStringEnFrase(0,"correcte");
        assertTrue(b);

        DocumentTesteado  d2 = new DocumentTesteado ("test. correcte.","Joan","test2");

        b = d2.estaStringEnFrase(1,"correcte");
        assertTrue(b);

        b = d2.estaStringEnFrase(1,"test");
        assertFalse(b);
    }



    @Test
    public void llistaFrasesParaula() {
        //Funciona aixi per el stub al separar la frase "test" en paraules amb el filtrador
        // tenim "test","correcte"x4
        int[] aux;
        int [] exp = new int[]{0,1,2,3,4};
        aux = d.llistaFrasesParaula("test");
        assertArrayEquals(exp,aux);

        exp = new int[]{0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4};
        aux = d.llistaFrasesParaula("correcte");
        assertArrayEquals(exp,aux);
    }

    /*
    @Test
    public void getPath(){}

     */

    @Test
    public void getTitolAutor(){
        Pair<String,String> p = new Pair<>("Test","Albert");
        Pair<String,String> aux;
        aux = d.getTitolAutor();
        assertEquals(p.getFirst(),aux.getFirst());
        assertEquals(p.getSecond(),aux.getSecond());

        DocumentTesteado d2 = new DocumentTesteado("test2","Albert","test2");
        p = new Pair<>("test2","Albert");
        aux = d2.getTitolAutor();
        assertEquals(p.getFirst(),aux.getFirst());
        assertEquals(p.getSecond(),aux.getSecond());

        DocumentTesteado d3 = new DocumentTesteado("test3","Joan","");
        p = new Pair<>("","Joan");
        aux = d3.getTitolAutor();
        assertEquals(p.getFirst(),aux.getFirst());
        assertEquals(p.getSecond(),aux.getSecond());
    }

    @Test
    public void getNumFrases() {
        int aux = d.getNumFrases();
        assertEquals(1,aux);

        d = new DocumentTesteado("Hola. Adeu.","Joan","test34");
        aux = d.getNumFrases();
        assertEquals(2,aux);

        d = new DocumentTesteado("","Joan","test34");
        aux = d.getNumFrases();
        assertEquals(0,aux);

        d = new DocumentTesteado("Hola. Adeu.Moltes.Frases.","Joan","test34");
        aux = d.getNumFrases();
        assertEquals(4,aux);
    }

    @Test
    public void getContingut() {
        String s;
        s = d.getContingut();
        assertEquals("test correcte.",s);

        DocumentTesteado  d2 = new DocumentTesteado ("test.","Manel","test3");
        s = d2.getContingut();
        assertEquals("test.",s);

        DocumentTesteado  d3 = new DocumentTesteado ("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.","Gabriel","test4");
        s = d3.getContingut();
        assertEquals("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",s);
    }
}