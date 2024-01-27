package Junit;

import org.junit.Before;
import org.junit.Test;
import domini.utils.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FiltradorTest {

    Filtador f;

    @Before
    public void setUp() {
        f = new Filtador();
    }

    @Test
    public void filtrarStopWords() {
        //Mix
        String[] s = new String[]{"ell","i","jo","som","caçadors"};
        String[] exp = new String[]{"som","caçadors"};
        String[] aux;
        aux = f.filtrarStopWords(s);
        assertArrayEquals(exp,aux);

        //Tot stopWords
        s = new String[]{"ell","i","jo"};
        exp = new String[]{};
        aux = f.filtrarStopWords(s);
        assertArrayEquals(exp,aux);

        //buit
        s = new String[]{};
        exp = new String[]{};
        aux = f.filtrarStopWords(s);
        assertArrayEquals(exp,aux);

        //Cap stop word
        s = new String[]{"Casa","Manel","esdeve","reliquia"};
        exp = new String[]{"Casa","Manel","esdeve","reliquia"};
        aux = f.filtrarStopWords(s);
        assertArrayEquals(exp,aux);

    }

    @Test
    public void filtrarContingut() {
        //Mix
        String s = "ell i jo som som caçadors";
        String r = " ";
        String[] exp = new String[]{"ell","i","jo","som","som","caçadors"};
        String[] aux;
        aux = f.filtrarContingut(s,r);
        assertArrayEquals(exp,aux);

        //Tot stopWords
        s = "ell,i,jo,som,caçadors";
        r = ",";
        exp = new String[]{"ell","i","jo","som","caçadors"};
        aux = f.filtrarContingut(s,r);
        assertArrayEquals(exp,aux);

        s = "ell, i, jo , som, caçadors ";
        r = ",.!?";
        exp = new String[]{"ell","i","jo","som","caçadors"};
        aux = f.filtrarContingut(s,r);
        assertArrayEquals(exp,aux);

        //buit
        s = "";
        r = "";
        exp = new String[]{};
        aux = f.filtrarContingut(s,r);
        assertArrayEquals(exp,aux);

        s = "";
        r = " ";
        exp = new String[]{};
        aux = f.filtrarContingut(s,r);
        assertArrayEquals(exp,aux);

        s = "ell,i,jo,som,caçadors";
        r = " ";
        exp = new String[]{"ell,i,jo,som,caçadors"};
        aux = f.filtrarContingut(s,r);
        assertArrayEquals(exp,aux);

        //Cap stop word
        s = "Casa Manel esdeve reliquia";
        r = "";
        exp = new String[]{"C","a","s","a","","M","a","n","e","l","","e","s","d","e","v","e","","r","e","l","i","q","u","i","a"};
        aux = f.filtrarContingut(s,r);
        assertArrayEquals(exp,aux);

    }

    @Test
    public void filtrarMapStopWords() {
        Map<String, Pair<Double, Double>> map = new LinkedHashMap<>();
        String[] aux;
        String[] exp = new String[]{};
        Pair<Double,Double> p1 = new Pair<>(-1d,0d);
        Pair<Double,Double> p2 = new Pair<>(2d,3d);
        aux = f.filtrarMapStopWords(map);
        assertArrayEquals(exp,aux);

        map.put("Hola",p1);
        aux = f.filtrarMapStopWords(map);
        assertArrayEquals(exp,aux);

        map.put("Casa",p2);
        exp = new String[]{"Casa"};
        aux = f.filtrarMapStopWords(map);
        assertArrayEquals(exp,aux);

        map.put("molt",p1);
        aux = f.filtrarMapStopWords(map);
        assertArrayEquals(exp,aux);

        map.put("",p2);
        exp = new String[]{"Casa",""};
        aux = f.filtrarMapStopWords(map);
        assertArrayEquals(exp,aux);

        map.put("curar",p2);
        exp = new String[]{"Casa","","curar"};
        aux = f.filtrarMapStopWords(map);
        assertArrayEquals(exp,aux);
    }
}