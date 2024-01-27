package Junit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexParaulaTest {

    IndexParaulaTesteado i;

    @Before
    public void setUp(){
        i = new IndexParaulaTesteado();
    }

    @Test
    public void afegirParaula() {
        double aux;
        int[] a;
        int[] b = new int[]{0};

        i.afegirParaula("test",0,1,true);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);

        i.afegirParaula("test",1,2,false);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
        b = new int[]{0,1};
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);

        i.afegirParaula("hola",1,2,false);
        aux = i.getIdfParaula("hola");
        assertEquals(-1d,aux,0.00001);
        b = new int[]{1};
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);


    }

    @Test
    public void afegirParaules() {
        double aux;
        int[] a;
        int[] b = new int[]{0};
        String[] s = new String[]{"hola","adeu"};

        i.afegirParaules(s,0,1,true);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);
        aux = i.getIdfParaula("adeu");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("adeu");
        assertArrayEquals(b,a);

        s = new String[]{"hola","test"};
        b = new int[]{0,1};
        i.afegirParaules(s,1, 2,false);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);
        b = new int[]{1};
        aux = i.getIdfParaula("test");
        assertEquals(-1d,aux,0.00001);
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);
    }
/*
    @Test
    public void eliminarParaula() {


    }

    No hi ha cap getter per comprovar-ho tots funciones sobre paraules existent
    I amb que la paraula existeixi com a precondició

 */

    @Test
    public void eliminarOcurrencies() {
        double aux;
        int[] a;
        int[] b = new int[]{0,2};
        String[] s = new String[]{"hola","adeu","test"};
        i.afegirParaules(s,0,1,false);
        i.afegirParaules(s,1,2,false);
        i.afegirParaules(s,2,3,false);

        i.eliminarOcurrencies(s,1,2,false);
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);
        a = i.llistaDocsParaula("adeu");
        assertArrayEquals(b,a);
        aux = i.getIdfParaula("hola");
        assertEquals(-1d,aux,0.00001);
        aux = i.getIdfParaula("test");
        assertEquals(-1d,aux,0.00001);
        aux = i.getIdfParaula("adeu");
        assertEquals(-1d,aux,0.00001);

        s = new String[]{"hola","adeu"};
        i.eliminarOcurrencies(s,0,2,true);
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);
        b = new int[]{2};
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);
        a = i.llistaDocsParaula("adeu");
        assertArrayEquals(b,a);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        aux = i.getIdfParaula("test");
        assertEquals(-1d,aux,0.00001);
        aux = i.getIdfParaula("adeu");
        assertEquals(1d,aux,0.00001);

        s = new String[]{"test"};
        i.eliminarOcurrencies(s,2,2,true);
        b = new int[]{0};
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);
        b = new int[]{2};
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);
        a = i.llistaDocsParaula("adeu");
        assertArrayEquals(b,a);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
        aux = i.getIdfParaula("adeu");
        assertEquals(1d,aux,0.00001);
    }

    @Test
    public void getIdfParaula() {
        double aux;
        i.afegirParaula("test",0,2,true);
        i.afegirParaula("hola",1,2,false);
        aux = i.getIdfParaula("hola");
        assertEquals(-1d,aux,0.00001);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
    }

    @Test
    public void recalcularTotsIdf() {
        double aux;
        i.afegirParaula("test",0,2,true);
        i.afegirParaula("hola",1,2,false);
        i.recalcularTotsIdf(2);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
    }

    @Test
    public void llistaDocsParaula() {
        //Nou hi pot haver dues ocurrencies de la mateixa paraula al mateix document.
        int[] a;
        int[] b = new int[]{0};
        i.afegirParaula("test",0,3,true);
        i.afegirParaula("hola",1,3,false);
        i.afegirParaula("hola",2,3,false);
        i.afegirParaula("hola",3,4,false);
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);
        b = new int[]{1,2,3};
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);
        i.eliminarOcurrenciaParaula("hola",2,3,false);
        b = new int[]{1,3};
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);

        b = new int[]{3};
        i.eliminarOcurrenciaParaula("hola",1,2,false);
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);
    }

    @Test
    public void novaOcurrenciaParaula() {
        double aux;
        int[] a;
        int[] b = new int[]{0,1};
        i.afegirParaula("test",0,3,true);
        i.afegirParaula("hola",1,3,false);

        i.novaOcurrenciaParaula("test",1,3,false);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("test");
        assertArrayEquals(b,a);

        i.novaOcurrenciaParaula("test",2,3,true);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("test");
        b = new int[]{0,1,2};
        assertArrayEquals(b,a);

        i.novaOcurrenciaParaula("hola",0,3,true);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("hola");
        b = new int[]{1,0};
        assertArrayEquals(b,a);

        i.novaOcurrenciaParaula("hola",3,4,false);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("hola");
        b = new int[]{1,0,3};
        assertArrayEquals(b,a);
    }

    //Un altre cop la part de eliminar paraula no es pot testejar sense getter
    @Test
    public void eliminarOcurrenciaParaula() {
        double aux;
        int[] a;
        int[] b = new int[]{0,3};
        i.afegirParaula("test",0,3,true);
        i.afegirParaula("hola",1,3,false);
        i.novaOcurrenciaParaula("test",1,3,false);
        i.novaOcurrenciaParaula("test",2,3,false);
        i.novaOcurrenciaParaula("hola",0,3,false);
        i.novaOcurrenciaParaula("hola",3,4,false);

        i.eliminarOcurrenciaParaula("hola",1,3,false);
        aux = i.getIdfParaula("hola");
        assertEquals(-1d,aux,0.00001);
        a = i.llistaDocsParaula("hola");
        assertArrayEquals(b,a);

        i.eliminarOcurrenciaParaula("hola",3,2,true);
        aux = i.getIdfParaula("hola");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("hola");
        b = new int[]{0};
        assertArrayEquals(b,a);

        i.eliminarOcurrenciaParaula("test",0,5,true);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("test");
        b = new int[]{1,2};
        assertArrayEquals(b,a);

        i.eliminarOcurrenciaParaula("test",1,4,false);
        aux = i.getIdfParaula("test");
        assertEquals(1d,aux,0.00001);
        a = i.llistaDocsParaula("test");
        b = new int[]{2};
        assertArrayEquals(b,a);
    }
}