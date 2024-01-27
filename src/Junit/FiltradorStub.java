package Junit;


import domini.utils.Pair;
import java.util.Map;

public class FiltradorStub {


    public static String[] filtrarStopWords(String[] paraules) {
        String[] s = new String[]{"test","correcte","correcte","correcte","correcte"};
        return s;
    }


    public static String[] filtrarContingut(String contingut, String regex) {
        String[] s = new String[]{"test","correcte","correcte","correcte","correcte"};
        return s;
    }


    public static String[] filtrarMapStopWords(Map<String, Pair<Double, Double>> map) {
        String[] s = new String[]{"test","correcte","correcte","correcte","correcte"};
        return s;
    }
}
