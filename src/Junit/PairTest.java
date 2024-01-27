package Junit;

import domini.utils.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    Pair<Double, Double> p;
    Pair<String ,String > s;

    @Before
    public void setUp()  {
        p = new Pair<>(0.0,0.0);
        s = new Pair<>("Manel","Document1");
    }

    @Test
    public void getFirst() {

        double aux;

        assertEquals(s.getFirst(),"Manel");

        s = new Pair<>("","Document1");
        assertEquals(s.getFirst(),"");

        s = new Pair<>("Giovanni dos Santos","Document1");
        assertEquals(s.getFirst(),"Giovanni dos Santos");

        aux = p.getFirst();
        assertEquals(0.0,aux,0.00001);

        p = new Pair<>(5.0/7,-47.0);
        aux = p.getFirst();
        assertEquals(5.0/7,aux,0.00001);

        p = new Pair<>(-1.33,21.0);
        aux = p.getFirst();
        assertEquals(-1.33,aux,0.00001);

        p = new Pair<>(-1.33,10.0);
        aux = p.getFirst();
        assertEquals(-1.33,aux,0.00001);
    }

    @Test
    public void getSecond() {
        double aux;

        assertEquals(s.getSecond(),"Document1");

        s = new Pair<>("","");
        assertEquals(s.getSecond(),"");

        s = new Pair<>("Document1","Giovanni dos Santos");
        assertEquals(s.getSecond(),"Giovanni dos Santos");

        aux = p.getSecond();
        assertEquals(0.0,aux,0.00001);

        p = new Pair<>(0.0,127.3);
        aux = p.getSecond();
        assertEquals(127.3,aux,0.00001);

        p = new Pair<>(5.0,-3.0/19);
        aux = p.getSecond();
        assertEquals(-3.0/19,aux,0.00001);

        p = new Pair<>(10.0,-8.0/2.0);
        aux = p.getSecond();
        assertEquals(-8.0/2,aux,0.00001);
    }

    @Test
    public void setFirst() {
        double aux;

        s.setFirst("");
        assertEquals(s.getFirst(),"");

        s.setFirst("Giovanni dos Santos");
        assertEquals(s.getFirst(),"Giovanni dos Santos");

        aux = p.getFirst();
        assertEquals(0.0,aux,0.00001);

        p.setFirst(6.5/12);
        aux = p.getFirst();
        assertEquals(6.5/12,aux,0.00001);

        p.setFirst(-1.6666);
        aux = p.getFirst();
        assertEquals(-1.6666,aux,0.00001);

        p.setFirst(10.0);
        aux = p.getFirst();
        assertEquals(10.0,aux,0.00001);
    }

    @Test
    public void setSecond() {
        double aux;

        s.setSecond("");
        assertEquals(s.getSecond(),"");

        s.setSecond("Giovanni dos Santos");
        assertEquals(s.getSecond(),"Giovanni dos Santos");

        aux = p.getSecond();
        assertEquals(0.0,aux,0.00001);

        p.setSecond(12345.749);
        aux = p.getSecond();
        assertEquals(12345.749,aux,0.00001);

        p.setSecond(-17.0/21);
        aux = p.getSecond();
        assertEquals(-17.0/21,aux,0.00001);

        p.setSecond(4.333361);
        aux = p.getSecond();
        assertEquals(4.333361,aux,0.00001);
    }
}