package Junit;

import domini.clases.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParaulaTest {

    Paraula p;

    @Before
    public void setUp() {
        p = new Paraula("menjar",0,1,false);
    }

    @Test
    public void novaOcurrencia() {
        int[] exp;
        int[] aux;
        double aux2;

        p.novaOcurrencia(1,2,false);
        aux = p.llistaDocs();
        exp = new int[]{0, 1};
        assertArrayEquals(exp,aux);

        p.novaOcurrencia(2,4,true);
        aux2 = p.getIdf();
        assertEquals(Math.log(4.0/3),aux2,0.00001);
        aux = p.llistaDocs();
        exp = new int[]{0, 1, 2};
        assertArrayEquals(exp,aux);

        p.novaOcurrencia(5,7,true);
        aux2 = p.getIdf();
        assertEquals(Math.log(7.0/4),aux2,0.00001);
        aux = p.llistaDocs();
        exp = new int[]{0,1, 2, 5};
        assertArrayEquals(exp,aux);


    }

    @Test
    public void eliminarOcurrencia() {
        p.novaOcurrencia(1,2,false);
        p.novaOcurrencia(3,4,false);
        p.novaOcurrencia(6,7,false);

        int[] exp;
        int[] aux;
        boolean b;
        double aux2;


        b = p.eliminarOcurrencia(1,6,true);
        aux2 = p.getIdf();
        assertEquals(Math.log(6.0/3),aux2,0.00001);
        aux = p.llistaDocs();
        exp = new int[]{0, 3, 6};
        assertFalse(b);
        assertArrayEquals(exp,aux);

        b = p.eliminarOcurrencia(0,3,false);
        aux = p.llistaDocs();
        exp = new int[]{3, 6};
        assertFalse(b);
        assertArrayEquals(exp,aux);

        b = p.eliminarOcurrencia(6,1,true);
        aux2 = p.getIdf();
        assertEquals(Math.log(1.0),aux2,0.00001);
        aux = p.llistaDocs();
        exp = new int[]{3};
        assertFalse(b);
        assertArrayEquals(exp,aux);

        b = p.eliminarOcurrencia(3,0,false);
        // Sempre que retorni True eliminarOcurrencia s'esborra la paraula
        //No crec que tingui sentit mirar el idf (divisio per 0, al actual)
        assertTrue(b);
    }

    @Test
    public void actualitzarIdf() {
        double aux;

        p.actualitzarIdf(3);
        aux = p.getIdf();
        assertEquals(Math.log(3.0),aux,0.00001);

        p.actualitzarIdf(7);
        aux = p.getIdf();
        assertEquals(Math.log(7.0),aux,0.00001);

        p.novaOcurrencia(3,8,false);
        p.novaOcurrencia(6,9,false);
        p.actualitzarIdf(9);
        aux = p.getIdf();
        assertEquals(Math.log(9.0/3),aux,0.00001);

        p.novaOcurrencia(10,10,false);
        p.actualitzarIdf(10);
        aux = p.getIdf();
        assertEquals(Math.log(10.0/4),aux,0.00001);

        p.eliminarOcurrencia(0,9,false);
        p.eliminarOcurrencia(6,8,false);
        p.actualitzarIdf(6);
        aux = p.getIdf();
        assertEquals(Math.log(6.0/2),aux,0.00001);
    }

    @Test
    public void getIdf() {
        double aux;

        p.actualitzarIdf(3);
        aux = p.getIdf();
        assertEquals(Math.log(3.0),aux,0.00001);

        p.actualitzarIdf(7);
        aux = p.getIdf();
        assertEquals(Math.log(7.0),aux,0.00001);

        p.novaOcurrencia(3,8,false);
        p.novaOcurrencia(6,9,true);
        aux = p.getIdf();
        assertEquals(Math.log(6.0/2),aux,0.00001);
        p.actualitzarIdf(9);
        aux = p.getIdf();
        assertEquals(Math.log(9.0/3),aux,0.00001);

        p.novaOcurrencia(10,10,true);
        p.actualitzarIdf(10);
        aux = p.getIdf();
        assertEquals(Math.log(10.0/4),aux,0.00001);

        p.eliminarOcurrencia(0,9,true);
        aux = p.getIdf();
        assertEquals(Math.log(9.0/3),aux,0.00001);
        p.eliminarOcurrencia(6,8,true);
        aux = p.getIdf();
        assertEquals(Math.log(8.0/2),aux,0.00001);
        p.actualitzarIdf(6);
        aux = p.getIdf();
        assertEquals(Math.log(6.0/2),aux,0.00001);
    }

    @Test
    public void llistaDocs() {
        //De nou si arriba a 0 ocurrencies s'esborra
        int[] exp = {0};
        int[] aux = p.llistaDocs();

        assertArrayEquals(exp,aux);

        p.novaOcurrencia(1,3,false);
        aux = p.llistaDocs();
        exp = new int[]{0,1};
        assertArrayEquals(exp,aux);

        p.novaOcurrencia(5,5,false);
        aux = p.llistaDocs();
        exp = new int[]{0,1,5};
        assertArrayEquals(exp,aux);

        p.eliminarOcurrencia(1,4,true);
        aux = p.llistaDocs();
        exp = new int[]{0,5};
        assertArrayEquals(exp,aux);

        p.novaOcurrencia(2,7,false);
        aux = p.llistaDocs();
        exp = new int[]{0,5,2};
        assertArrayEquals(exp,aux);

        p.eliminarOcurrencia(0,6,true);
        aux = p.llistaDocs();
        exp = new int[]{5,2};
        assertArrayEquals(exp,aux);

        p.eliminarOcurrencia(5,1,true);
        aux = p.llistaDocs();
        exp = new int[]{2};
        assertArrayEquals(exp,aux);

        p.novaOcurrencia(12,20,false);
        aux = p.llistaDocs();
        exp = new int[]{2,12};
        assertArrayEquals(exp,aux);

        p.novaOcurrencia(15,21,false);
        aux = p.llistaDocs();
        exp = new int[]{2,12,15};
        assertArrayEquals(exp,aux);


    }
}