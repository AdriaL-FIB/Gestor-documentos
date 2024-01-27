package Junit;

import domini.clases.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


public class AutorTest {

    Autor a;

    @Before
    public void setUp() {
        a = new Autor();
    }



    @Test
    public void afegirTitol() {
        a.afegirTitol("Quijote",2);
        a.afegirTitol("",0);
        a.afegirTitol("El senyor dels Anells",1);
        int aux = a.obtenirId("");
        assertEquals(0,aux);


        assertTrue(a.pertanyTitol("Quijote"));
        assertTrue(a.pertanyTitol(""));
        assertTrue(a.pertanyTitol("El senyor dels Anells"));
    }

    @Test
    public void eliminarTitol() {
        a.afegirTitol("",3);
        a.afegirTitol("La inflacio descontrolada",100);
        a.afegirTitol("Títol",1);


        a.eliminarTitol("Títol");
        a.eliminarTitol("");
        a.eliminarTitol("La inflacio descontrolada");

        assertFalse(a.pertanyTitol("Títol"));
        assertFalse(a.pertanyTitol(""));
        assertFalse(a.pertanyTitol("La inflacio descontrolada"));
    }

    @Test
    public void pertanyTitol() {

        assertFalse(a.pertanyTitol("Títol"));

        assertFalse(a.pertanyTitol(""));

        assertFalse(a.pertanyTitol("La inflacio descontrolada"));

        a.afegirTitol("Títol",1);
        assertTrue(a.pertanyTitol("Títol"));

        a.afegirTitol("",3);
        assertTrue(a.pertanyTitol(""));

        a.afegirTitol("La inflacio descontrolada",100);
        assertTrue(a.pertanyTitol("La inflacio descontrolada"));

        a.eliminarTitol("Títol");
        assertFalse(a.pertanyTitol("Títol"));

        a.eliminarTitol("");
        assertFalse(a.pertanyTitol(""));

        a.eliminarTitol("La inflacio descontrolada");
        assertFalse(a.pertanyTitol("La inflacio descontrolada"));
    }

    @Test
    public void obtenirId() {
        a.afegirTitol("Quijote",201);
        a.afegirTitol("",0);
        a.afegirTitol("El senyor dels Anells",15);
        int aux;

        aux = a.obtenirId("Quijote");
        assertEquals(201,aux);

        aux = a.obtenirId("");
        assertEquals(0,aux);

        aux = a.obtenirId("El senyor dels Anells");
        assertEquals(15,aux);
    }

    @Test
    public void numTitols() {
        int aux;

        aux = a.numTitols();
        assertEquals(0,aux);

        a.afegirTitol("Títol",201);
        aux = a.numTitols();
        assertEquals(1,aux);

        a.afegirTitol("",0);
        aux = a.numTitols();
        assertEquals(2,aux);

        a.afegirTitol("El senyor dels Anells",15);
        aux = a.numTitols();
        assertEquals(3,aux);

        a.eliminarTitol("Títol");
        aux = a.numTitols();
        assertEquals(2,aux);

        a.eliminarTitol("");
        aux = a.numTitols();
        assertEquals(1,aux);

        a.eliminarTitol("El senyor dels Anells");
        aux = a.numTitols();
        assertEquals(0,aux);
    }

    @Test
    public void llistarTots() {
        String[] aux;
        String[] tit = new String[]{};

        aux = a.llistarTots();
        assertArrayEquals(tit,aux);

        tit = new String[]{"bTítol"};
        a.afegirTitol("bTítol",201);
        aux = a.llistarTots();
        assertArrayEquals(tit,aux);

        tit = new String[]{"","bTítol"};
        a.afegirTitol("",0);
        aux = a.llistarTots();
        assertArrayEquals(tit,aux);

        //Surt en ordre alfabetic
        tit = new String[]{"","aLa inflacio descontrolada","bTítol"};
        a.afegirTitol("aLa inflacio descontrolada",15);
        aux = a.llistarTots();
        assertArrayEquals(tit,aux);

        a.eliminarTitol("bTítol");
        tit = new String[]{"","aLa inflacio descontrolada"};
        aux = a.llistarTots();
        assertArrayEquals(tit,aux);

        tit = new String[]{"aLa inflacio descontrolada"};
        a.eliminarTitol("");
        aux = a.llistarTots();
        assertArrayEquals(tit,aux);

        tit = new String[]{};
        a.eliminarTitol("aLa inflacio descontrolada");
        aux = a.llistarTots();
        assertArrayEquals(tit,aux);

    }
}