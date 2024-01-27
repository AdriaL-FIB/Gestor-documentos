package Junit;

import org.junit.Before;
import org.junit.Test;
import domini.clases.ExprBool;

import static org.junit.Assert.*;

public class ConjuntExprBoolTest {

    ConjuntExprBoolTesteado c;

    @Before
    public void setUp() {
        c = new ConjuntExprBoolTesteado();
    }

    @Test
    public void agregarExprBool() {
        c.agregarExprBool("test1","!hola");
        boolean b = c.pertanyExprBool("test1");
        assertTrue(b);

        c.agregarExprBool("test2","test1 & test2");
        b = c.pertanyExprBool("test2");
        assertTrue(b);

        c.agregarExprBool("test3","test1 & test2 | test3 & test4");
        b = c.pertanyExprBool("test3");
        assertTrue(b);
    }

    @Test
    public void pertanyExprBool() {
        c.agregarExprBool("test1","!hola");
        boolean b = c.pertanyExprBool("test1");
        assertTrue(b);

        c.agregarExprBool("test2","test1 & test2");
        b = c.pertanyExprBool("test2");
        assertTrue(b);

        c.eliminarExprBool("test2");
        b = c.pertanyExprBool("test1");
        assertTrue(b);
        b = c.pertanyExprBool("test2");
        assertFalse(b);

        c.agregarExprBool("test3","test1 & test2 | test3 & test4");
        b = c.pertanyExprBool("test3");
        assertTrue(b);

        c.eliminarExprBool("test3");
        b = c.pertanyExprBool("test1");
        assertTrue(b);
        b = c.pertanyExprBool("test3");
        assertFalse(b);

        c.eliminarExprBool("test1");
        b = c.pertanyExprBool("test1");
        assertFalse(b);
        b = c.pertanyExprBool("test2");
        assertFalse(b);
    }

    @Test
    public void eliminarExprBool() {
        c.agregarExprBool("test1","!hola");
        c.agregarExprBool("test2","test1 & test2");
        c.agregarExprBool("test3","test1 & test2 | test3 & test4");


        c.eliminarExprBool("test2");
        boolean b = c.pertanyExprBool("test1");
        assertTrue(b);
        b = c.pertanyExprBool("test2");
        assertFalse(b);

        c.eliminarExprBool("test3");
        b = c.pertanyExprBool("test1");
        assertTrue(b);
        b = c.pertanyExprBool("test2");
        assertFalse(b);

        c.eliminarExprBool("test1");
        b = c.pertanyExprBool("test1");
        assertFalse(b);
        b = c.pertanyExprBool("test3");
        assertFalse(b);
    }

    @Test
    public void modificarExprBool() {
        c.agregarExprBool("test1","!hola");
        c.agregarExprBool("test2","test1 & test2");
        ExprBool e = new ExprBool("!test");
        ExprBool aux;
        c.modificarExprBool("test1","!test");
        aux = c.getExpBool("test1");
        assertEquals(e.toString(),aux.toString());
        e = new ExprBool("test1 & test2");
        aux = c.getExpBool("test2");
        assertEquals(e.toString(),aux.toString());
        e = new ExprBool("!test1 & test2");
        c.modificarExprBool("test2","!test1 & test2");
        aux = c.getExpBool("test2");
        assertEquals(e.toString(),aux.toString());
    }

    @Test
    public void getExpBool() {
        c.agregarExprBool("test1","!hola");
        c.agregarExprBool("test2","test1 & test2");
        c.agregarExprBool("test3","test1 & test2 | test3 & test4");
        ExprBool e = new ExprBool("!hola");
        ExprBool aux;
        aux = c.getExpBool("test1");
        assertEquals(e.toString(),aux.toString());
        e = new ExprBool("test1 & test2");
        aux = c.getExpBool("test2");
        assertEquals(e.toString(),aux.toString());
        e = new ExprBool("test1 & test2 | test3 & test4");
        aux = c.getExpBool("test3");
        assertEquals(e.toString(),aux.toString());
    }

    @Test
    public void llistarExprBooleanas() {
        String [] aux;
        String[] exp = new String[]{};

        aux = c.llistarExprBooleanas();
        assertArrayEquals(exp,aux);
        c.agregarExprBool("aNoApareixHola","!hola");
        exp = new String[]{"aNoApareixHola"};
        aux = c.llistarExprBooleanas();
        assertArrayEquals(exp,aux);
        c.agregarExprBool("bProvaAnd","test1 & test2");
        exp = new String[]{"aNoApareixHola","bProvaAnd"};
        aux = c.llistarExprBooleanas();
        assertArrayEquals(exp,aux);
        c.agregarExprBool("cTestOR","test1 & test2 | test3 & test4");
        exp = new String[]{"aNoApareixHola","bProvaAnd","cTestOR"};
        aux = c.llistarExprBooleanas();
        assertArrayEquals(exp,aux);
        c.eliminarExprBool("bProvaAnd");
        exp = new String[]{"aNoApareixHola","cTestOR"};
        aux = c.llistarExprBooleanas();
        assertArrayEquals(exp,aux);
    }
}