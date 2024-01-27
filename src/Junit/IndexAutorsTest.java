package Junit;

import domini.clases.IndexAutors;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class IndexAutorsTest {

    //No stub Autor són tot getters i setter; el stub seria igual a la classe

    IndexAutors i;

    @Before
    public void setUp(){
        i = new IndexAutors();
    }

    @Test
    public void estaAutor() {
        boolean b;

        b = i.estaAutor("Joan");
        assertFalse(b);

        i.afegirTitol("Joan","ciencia ficcion",0);
        b = i.estaAutor("Joan");
        assertTrue(b);
        b = i.estaAutor("Albert");
        assertFalse(b);
        i.afegirTitol("Albert","Documental",5);
        b = i.estaAutor("Albert");
        assertTrue(b);
        i.afegirTitol("Joan","ciencia ficcion2",2);
        b = i.estaAutor("Joan");
        assertTrue(b);

        i.eliminarTitol("Joan","ciencia ficcion");
        b = i.estaAutor("Joan");
        assertTrue(b);
        i.eliminarTitol("Joan","ciencia ficcion2");
        b = i.estaAutor("Joan");
        assertFalse(b);
        b = i.estaAutor("Albert");
        assertTrue(b);
        i.eliminarTitol("Albert","Documental");
        b = i.estaAutor("Albert");
        assertFalse(b);

    }

    @Test
    public void numAutors() {
        int aux;

        aux = i.numAutors();
        assertEquals(0,aux);
        i.afegirTitol("Joan","ciencia ficcion",0);
        aux = i.numAutors();
        assertEquals(1,aux);
        i.afegirTitol("Albert","Documental",5);
        aux = i.numAutors();
        assertEquals(2,aux);
        i.afegirTitol("Joan","ciencia ficcion2",2);
        aux = i.numAutors();
        assertEquals(2,aux);

        i.eliminarTitol("Joan","ciencia ficcion");
        aux = i.numAutors();
        assertEquals(2,aux);
        i.eliminarTitol("Joan","ciencia ficcion2");
        aux = i.numAutors();
        assertEquals(1,aux);
        i.eliminarTitol("Albert","Documental");
        aux = i.numAutors();
        assertEquals(0,aux);
    }

    @Test
    public void llistarAutors() {
        String[] aux;
        String[] exp = new String[]{};
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
        i.afegirTitol("Joan","ciencia ficcion",0);
        exp = new String[]{"Joan"};
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
        i.afegirTitol("Joan","ciencia ficcion2",1);
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
        i.afegirTitol("Albert","Documental",5);
        exp = new String[]{"Joan","Albert"};
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
        i.eliminarTitol("Joan","ciencia ficcion2");
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
        i.eliminarTitol("Joan","ciencia ficcion");
        exp = new String[]{"Albert"};
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
        i.eliminarTitol("Albert","Documental");
        exp = new String[]{};
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
    }


    @Test
    public void llistarComencatsPer() {
        String[] aux;
        String[] exp = new String[]{};
        aux = i.llistarAutorsPrefix("");
        assertArrayEquals(exp,aux);
        i.afegirTitol("Joan","ciencia ficcion",0);
        aux = i.llistarAutorsPrefix("A");
        assertArrayEquals(exp,aux);
        i.afegirTitol("Albert","Documental",5);
        exp = new String[]{"Joan"};
        aux = i.llistarAutorsPrefix("J");
        assertArrayEquals(exp,aux);
        i.afegirTitol("Jordi","docu",1);
        exp = new String[]{"Joan","Jordi"};
        aux = i.llistarAutorsPrefix("J");
        assertArrayEquals(exp,aux);
        i.eliminarTitol("Joan","ciencia ficcion");
        aux = i.llistarAutorsPrefix("J");
        exp = new String[]{"Jordi"};
        assertArrayEquals(exp,aux);
        i.eliminarTitol("Jordi","docu");
        aux = i.llistarAutorsPrefix("J");
        exp = new String[]{};
        assertArrayEquals(exp,aux);
    }

    @Test
    public void llistarTitols() {
        String[] aux;
        String[] exp;
        i.afegirTitol("Joan","bbbb",0);
        exp = new String[]{"bbbb"};
        aux = i.llistarTitols("Joan");
        assertArrayEquals(exp,aux);

        i.afegirTitol("Albert","Documental",5);
        aux = i.llistarTitols("Joan");
        assertArrayEquals(exp,aux);
        exp = new String[]{"Documental"};
        aux = i.llistarTitols("Albert");
        assertArrayEquals(exp,aux);
        i.afegirTitol("Joan","aaaa",2);
        aux = i.llistarTitols("Albert");
        assertArrayEquals(exp,aux);
        exp = new String[]{"aaaa","bbbb"};
        aux = i.llistarTitols("Joan");
        assertArrayEquals(exp,aux);
        i.eliminarTitol("Joan","aaaa");
        exp = new String[]{"bbbb"};
        aux = i.llistarTitols("Joan");
        assertArrayEquals(exp,aux);

    }

    @Test
    public void numTitols() {
        int aux;
        i.afegirTitol("Joan","bbbb",0);
        aux = i.numTitols("Joan");
        assertEquals(1,aux);

        i.afegirTitol("Albert","Documental",5);
        aux = i.numTitols("Joan");
        assertEquals(1,aux);
        aux = i.numTitols("Albert");
        assertEquals(1,aux);
        i.afegirTitol("Joan","aaaa",2);
        aux = i.numTitols("Albert");
        assertEquals(1,aux);
        aux = i.numTitols("Joan");
        assertEquals(2,aux);
        i.eliminarTitol("Joan","aaaa");
        aux = i.numTitols("Joan");
        assertEquals(1,aux);
    }

    @Test
    public void afegirTitol() {
        String[] aux;
        String[] exp;
        String[] aux2;
        String[] exp2;

        i.afegirTitol("Joan","Document1",0);
        aux = i.llistarAutors();
        exp = new String[]{"Joan"};
        assertArrayEquals(exp,aux);
        aux2 = i.llistarTitols("Joan");
        exp2 = new String[]{"Document1"};
        assertArrayEquals(exp2,aux2);

        i.afegirTitol("Albert","Document2",1);
        aux = i.llistarAutors();
        exp = new String[]{"Joan","Albert"};
        assertArrayEquals(exp,aux);
        aux2 = i.llistarTitols("Joan");
        assertArrayEquals(exp2,aux2);
        aux2 = i.llistarTitols("Albert");
        exp2 = new String[]{"Document2"};
        assertArrayEquals(exp2,aux2);

        i.afegirTitol("Joan","Document3",2);
        aux = i.llistarAutors();
        assertArrayEquals(exp,aux);
        aux2 = i.llistarTitols("Joan");
        exp2 = new String[]{"Document3","Document1"};
        assertArrayEquals(exp2,aux2);

    }

    @Test
    public void eliminarTitol() {
        String[] aux;
        String[] exp;
        String[] aux2;
        String[] exp2;

        i.afegirTitol("Joan","Document1",0);
        i.afegirTitol("Manel","Document2",1);
        i.afegirTitol("Joan","Document3",2);

        i.eliminarTitol("Joan","Document1");
        aux = i.llistarAutors();
        aux2 = i.llistarTitols("Joan");
        exp = new String[]{"Joan","Manel"};
        exp2 = new String[]{"Document3"};
        assertArrayEquals(exp,aux);
        assertArrayEquals(exp2,aux2);

        i.eliminarTitol("Joan","Document3");
        aux = i.llistarAutors();
        exp = new String[]{"Manel"};
        assertArrayEquals(exp,aux);

        i.eliminarTitol("Manel","Document2");
        aux = i.llistarAutors();
        exp = new String[]{};
        assertArrayEquals(exp,aux);
    }

    @Test
    public void estaTitol() {
         //Pre el autor existeix.
        boolean b;
        i.afegirTitol("Joan","Document1",0);
        i.afegirTitol("Manel","Document2",1);
        i.afegirTitol("Joan","Document3",2);

        b = i.estaTitol("Manel","Document1");
        assertFalse(b);
        b = i.estaTitol("Manel","Document2");
        assertTrue(b);
        b = i.estaTitol("Joan","Document3");
        assertTrue(b);
        b = i.estaTitol("Joan","avnjdfvna");
        assertFalse(b);
        b = i.estaTitol("Joan","Document1");
        assertTrue(b);
    }

    @Test
    public void obtenirId() { //Pre ha d'eexistir la parella autor títol
        int aux;
        i.afegirTitol("Joan","Document1",0);
        i.afegirTitol("Manel","Document2",1);
        i.afegirTitol("Joan","Document3",2);
        aux = i.obtenirId("Joan","Document3");
        assertEquals(2,aux);
        aux = i.obtenirId("Manel","Document2");
        assertEquals(1,aux);
        aux = i.obtenirId("Joan","Document1");
        assertEquals(0,aux);
    }
}