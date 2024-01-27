package Junit;

import domini.clases.IndexFrase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class IndexFraseTest {

    IndexFrase i;

    @Before
    public void setUp(){
        i = new IndexFrase();
    }

    @Test
    public void afegirOcurrenciesDocument() {
        int[] a = new int[]{0,2,3};
        i.afegirOcurrenciesDocument("test",0,a);
        Map<Integer, int[]> aux;
        Map<Integer, int[]> exp = new LinkedHashMap<>();
        exp.put(0,a);
        aux = i.getIndexFraseParaula("test");
        assertEqualsMap(exp,aux);

        a = new int[]{3,4};
        i.afegirOcurrenciesDocument("test",1,a);
        exp.put(1,a);
        aux = i.getIndexFraseParaula("test");
        assertEqualsMap(exp,aux);

        a = new int[]{0,1};
        i.afegirOcurrenciesDocument("paraula",1,a);
        exp = new LinkedHashMap<>();
        exp.put(1,a);
        aux = i.getIndexFraseParaula("paraula");
        assertEqualsMap(exp,aux);
    }

    private void assertEqualsMap(Map<Integer,int[]> exp, Map<Integer,int[]> aux) {
        assertEquals(exp.size(),aux.size());
        int i;

        for (Map.Entry<Integer,int[]> entry : exp.entrySet()){
            i = entry.getKey();
            assertTrue(aux.containsKey(i));
            assertArrayEquals(exp.get(i),aux.get(i));
        }

    }

    @Test
    public void eliminarOcurrenciesDocument() {
        int[] a = new int[]{0,2,3};
        Map<Integer, int[]> aux;
        Map<Integer, int[]> exp = new LinkedHashMap<>();
        i.afegirOcurrenciesDocument("test",0,a);
        a = new int[]{0};
        i.afegirOcurrenciesDocument("test",1,a);
        a = new int[]{1};
        i.afegirOcurrenciesDocument("paraula",0,a);

        i.eliminarOcurrenciesDocument("test",0);
        exp.put(0,a);
        aux = i.getIndexFraseParaula("paraula");
        assertEqualsMap(exp,aux);
        exp = new LinkedHashMap<>();
        a = new int[]{0};
        exp.put(1,a);
        aux = i.getIndexFraseParaula("test");
        assertEqualsMap(exp,aux);

        i.eliminarOcurrenciesDocument("paraula",0);
        aux = i.getIndexFraseParaula("test");
        assertEqualsMap(exp,aux);


    }

    @Test
    public void getIndexFraseParaula() {
        int[] a = new int[]{0,2,3};

        i.afegirOcurrenciesDocument("test",0,a);
        Map<Integer, int[]> aux;
        Map<Integer, int[]> exp = new LinkedHashMap<>();
        exp.put(0,a);
        aux = i.getIndexFraseParaula("test");
        assertEqualsMap(exp,aux);

        a = new int[]{0,1};
        i.afegirOcurrenciesDocument("paraula",1,a);
        exp = new LinkedHashMap<>();
        exp.put(1,a);
        aux = i.getIndexFraseParaula("paraula");
        assertEqualsMap(exp,aux);

        a = new int[]{0,1};
        i.afegirOcurrenciesDocument("test",1,a);
        a = new int[]{0,2,3};
        exp = new LinkedHashMap<>();
        exp.put(0,a);
        a = new int[]{0,1};
        exp.put(1,a);
        aux = i.getIndexFraseParaula("test");
        assertEqualsMap(exp,aux);

        i.eliminarOcurrenciesDocument("test",0);
        exp = new LinkedHashMap<>();
        exp.put(1,a);
        aux = i.getIndexFraseParaula("paraula");
        assertEqualsMap(exp,aux);
        exp = new LinkedHashMap<>();
        a = new int[]{0,1};
        exp.put(1,a);
        aux = i.getIndexFraseParaula("test");
        assertEqualsMap(exp,aux);





    }
}