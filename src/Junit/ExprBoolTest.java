package Junit;

import domini.clases.ExprBool;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExprBoolTest {

    ExprBool e;

    @Test
    public void testToString() {
        e = new ExprBool("{hola}");
        String s = "{\"value\": \"{hola}\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("!hola");
        s = "{\"value\": \"!hola\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("test1 | test2");
        s = "{\"value\": \"test1 | test2\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("(test1 & test2) | test3");
        s = "{\"value\": \"(test1 & test2) | test3\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("test1 | test2 & test3");
        s = "{\"value\": \"test1 | test2 & test3\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("test1 & test4 | test2 & test3");
        s = "{\"value\": \"test1 & test4 | test2 & test3\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());
    }
}