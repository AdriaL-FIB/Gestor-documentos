package Junit;

import domini.clases.ExprBool;
import domini.utils.BooleanParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanParserTest {


    @Test
    public void crearExpresion() {

        //No utilitzo stub perque deixar ExprBool per defecte m'ajuda a saber si s'ha creat bé l'expressió

        ExprBool e;
        e = new ExprBool("hola");
        String s = "{\"value\": \"hola\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("(hola)");
        s = "{\"value\": \"hola\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("{hola}");
        s = "{\"value\": \"hola\", \"left\": [], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("!hola");
        s = "{\"value\": \"!\", \"left\": [{\"value\": \"hola\", \"left\": [], \"right\": []}], \"right\": []}";
        assertEquals(s,e.toString());

        e = new ExprBool("test1 | test2");
        s = "{\"value\": \"|\", \"left\": [{\"value\": \"test1\", \"left\": [], \"right\": []}], \"right\": [{\"value\": \"test2\", \"left\": [], \"right\": []}]}";
        assertEquals(s,e.toString());

        e = new ExprBool("(test1 & test2) | test3");
        s = "{\"value\": \"|\", \"left\": [{\"value\": \"&\", \"left\": [{\"value\": \"test1\", \"left\": [], \"right\": []}], \"right\": [{\"value\": \"test2\", \"left\": [], \"right\": []}]}], \"right\": [{\"value\": \"test3\", \"left\": [], \"right\": []}]}";
        assertEquals(s,e.toString());

        e = new ExprBool("test1 | test2 & test3");
        s = "{\"value\": \"|\", \"left\": [{\"value\": \"test1\", \"left\": [], \"right\": []}], \"right\": [{\"value\": \"&\", \"left\": [{\"value\": \"test2\", \"left\": [], \"right\": []}], \"right\": [{\"value\": \"test3\", \"left\": [], \"right\": []}]}]}";
        assertEquals(s,e.toString());

        e = new ExprBool("test1 & test4 | test2 & test3");
        s = "{\"value\": \"|\", \"left\": [{\"value\": \"&\", \"left\": [{\"value\": \"test1\", \"left\": [], \"right\": []}], \"right\": [{\"value\": \"test4\", \"left\": [], \"right\": []}]}], \"right\": [{\"value\": \"&\", \"left\": [{\"value\": \"test2\", \"left\": [], \"right\": []}], \"right\": [{\"value\": \"test3\", \"left\": [], \"right\": []}]}]}";
        assertEquals(s,e.toString());
    }
}