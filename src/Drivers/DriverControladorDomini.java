package Drivers;

import domini.controladores.ControladorDomini;
import domini.exceptions.*;
import domini.utils.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverControladorDomini {

    private static ControladorDomini c = new ControladorDomini();

    private static Scanner s;


    private static void testdonarAltaDocument() {
        System.out.println("Test donarAltaDocument\n");
        System.out.println("Introdueix: ");
        System.out.println("Autor");
        String a = s.nextLine();
        System.out.println("Titol");
        String t = s.nextLine();
        System.out.println("Contingut");
        String cont = s.nextLine();

        try {
            c.donarAltaDocument(a, t, cont);
        } catch (JaExisteixException e) {
            System.out.println("Error el document que has intentat donar d'alta ja existeix.");
        } catch (NomInvalidException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
    }

    private static void testdonarBaixaDocument() {
        System.out.println("Test donarBaixaDocument\n");
        System.out.println("Introdueix: ");
        System.out.println("Autor ");
        String a = s.nextLine();
        System.out.println("Titol ");
        String t = s.nextLine();

        try {
            c.donarBaixaDocument(a, t);
        } catch (NoExisteixException e) {
            System.out.println("Error el document que has intentat donar de baixa no existeix.");
        }
        System.out.println();
    }

    private static void testmodificarDocument() {
        System.out.println("Test modificarDocument\n");
        System.out.println("Introdueix: ");
        System.out.println("Autor ");
        String a = s.nextLine();
        System.out.println("Titol ");
        String t = s.nextLine();
        System.out.println("Nou Contingut ");
        String cont = s.nextLine();

        try {
            c.modificarDocument(a, t, cont);
        } catch (NoExisteixException e) {
            System.out.println("Error el document que has intentat modificar no existeix.");
        }
        /*
        catch (JaExisteixException e) {
            System.out.println("Error el document que has intentat modificar ja existeix.");
        }

         */

        System.out.println();
    }

    private static void testnumDocs() {
        System.out.println("Test numDocs");
        System.out.println("Número de Documents :" + c.numDocs() + "\n");
        System.out.println();
    }

    /*
    private static void testobtenirPathDocument() {
        System.out.println("Test obtenirPathDocument\n");
        System.out.println("Introdueix: ");
        System.out.println("Autor ");
        String a = s.nextLine();
        System.out.println("Titol ");
        String t = s.nextLine();
        try {
            System.out.println("Path del Document: " + c.obtenirPathDocument(a, t) + "");
        }
        catch (NoExisteixException e) {
            System.out.println("Error el document indicat no existeix.");
        }
        System.out.println();
    }

     */


    private static void testllistarAutors() {
        System.out.println("Test llistarAutors");
        String[] aux = c.llistarAutors();
        System.out.println("Autors:");
        for (int i = 0; i < aux.length; i++) {
            System.out.println((i + 1) + " : " + aux[i]);
        }
        System.out.println();
    }

    private static void testllistarTitolsAutor() {
        System.out.println("Test llistarTitolsAutor\n");
        System.out.println("Introdueix: ");
        System.out.println("Autor ");
        String a = s.nextLine();
        try {
            String[] aux = c.llistarTitolsAutor(a);
            System.out.println("Titols:");
            for (int i = 0; i < aux.length; i++) {
                System.out.println((i + 1) + " : " + aux[i]);
            }
        } catch (NoExisteixException e) {
            System.out.println("Error l'autor indicat no existeix.");
        }
        System.out.println();
    }

    private static void testllistarDocumentsSimilars() {
        System.out.println("Test llistarDocumentsSimilars\n");
        System.out.println("Introdueix: ");
        System.out.println("Autor ");
        String a = s.nextLine();
        System.out.println("Titol ");
        String t = s.nextLine();
        System.out.println("Número de Documents Similars ");
        int k = 0;
        String str = s.nextLine();
        try {
            k = Integer.parseInt(str);
        }
        catch (NumberFormatException ex) {
            System.out.println("Error no has introduit un número.");
        }
        System.out.println("Estratègia: 1. Tf-Idf Altrament. Bag of words");
        str = s.nextLine();
        int e = 1;
        try {
            e = Integer.parseInt(str);
        }
        catch (NumberFormatException ex) {
            System.out.println("Error no has introduit un número.");
        }

        if (e == 1) e = 0;
        else e = 1;

        try {
            ArrayList<Pair<String, String>> aux = c.llistarDocumentsSimilars(a,t,k,e);


            for (int i = 0; i < aux.size(); i++) {
                System.out.println("Document " + (i + 1) + " Autor " + aux.get(i).getFirst() + " Titol " + aux.get(i).getSecond());
            }
        } catch (NoExisteixException ex) {
            System.out.println("Error el document indicat no existeix.");
        }
        System.out.println();
    }

    private static void testllistarDocumentsParaula() {
        System.out.println("Test llistarDocumentsParaula\n");
        System.out.println("Introdueix: ");
        System.out.println("Paraula ");
        String p = s.nextLine();

        String[] w = p.split("[(){}\\[\\]\"'.,!? \\r\\n\\t\\f\\v]");
        if (w.length != 1) {
            p = w[0];
            System.out.println("Has entrat més d'una paraula, utilitzant la primera" + p);
        }

        try {
            ArrayList<Pair<String, String>> aux = c.llistarDocumentsParaula(p);

            for (int i = 0; i < aux.size(); i++) {
                System.out.println("Document " + (i + 1) + " Autor " + aux.get(i).getFirst() + " Titol " + aux.get(i).getSecond());
            }
        } catch (NoExisteixException ex) {
            System.out.println("La paraula indicada no apareix a cap document.");
        }
        System.out.println();
    }

    private static void testobtenirContingut() {
        System.out.println("Test obtenirContingut\n");
        System.out.println("Introdueix: ");
        System.out.println("Autor ");
        String a = s.nextLine();
        System.out.println("Titol ");
        String t = s.nextLine();

        try {
            System.out.println("Contingut del Document: " + c.obtenirContingut(a, t));
        } catch (NoExisteixException ex) {
            System.out.println("Error el document indicat no existeix.");
        }
        System.out.println();
    }

    private static void testdonarAltaExprBool() {
        System.out.println("Test donarAltaExprBool\n");
        System.out.println("Introdueix nom de la Expressio Booleana: ");
        String nom = s.nextLine();
        System.out.println("Introdueix el valor de la Expressio Booleana: ");
        String e = s.nextLine();

        try {
            c.donarAltaExprBool(nom, e);
        } catch (JaExisteixException ex) {
            System.out.println("Error la expressio booleana ja existeix.");
        } catch (ExprBoolIncorrectaException ex) {
            System.out.println("Error la expressio booleana no es correcta.");
        } catch (NomInvalidException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    private static void testdonarBaixaExprBool() {
        System.out.println("Test donarBaixaExprBool\n");
        System.out.println("Introdueix nom de la Expressio Booleana: ");
        String e = s.nextLine();

        try {
            c.donarBaixaExprBool(e);
        } catch (NoExisteixException ex) {
            System.out.println("Error la expressio booleana indicada no existeix.");
        }
        System.out.println();
    }

    private static void testmodificarExprBool() {
        System.out.println("Test modificarExprBool\n");
        System.out.println("Introdueix: ");
        System.out.println("El nom de la expressio Booleana Antiga");
        String ea = s.nextLine();
        System.out.println("El nou valor de la expressio Booleana");
        String en = s.nextLine();

        try {
            c.modificarExprBool(ea, en);
        } catch (NoExisteixException ex) {
            System.out.println("Error la expressio booleana antiga indicada no existeix.");
        } catch (ExprBoolIncorrectaException ex) {
            System.out.println("Error la expressio booleana nova indicada no es correcta.");
        }
        System.out.println();
    }


    private static void testllistarExprBooleanas() {
        System.out.println("Test llistarExprBooleanas\n");
        String[] aux = c.llistarExprBooleanas();
        for (int i = 0; i < aux.length; i++) {
            System.out.println((i + 1) + " : " + aux[i]);
        }
        System.out.println();
    }


    private static void testllistarDocumentsExprBool() {
        System.out.println("Test llistarDocumentsExprBool\n");
        System.out.println("Introdueix: ");
        System.out.println("Expressio Booleana");
        String e = s.nextLine();

        try {
            ArrayList<Pair<String, String>> aux = c.llistarDocumentsExprBool(e);


            for (int i = 0; i < aux.size(); i++) {
                System.out.println("Document " + (i + 1) + " Autor " + aux.get(i).getFirst() + " Titol " + aux.get(i).getSecond());
            }
        } catch (NoExisteixException ex) {
            System.out.println("Error la expressio booleana indicada no existeix.");
        }
        System.out.println();
    }



    private static void testordenarAlfabeticament() {
        System.out.println("Test ordenarAlfabeticament\n");
        System.out.println("Introdueix: ");
        System.out.println("0 Si vols ordre creixent, o un altre número si vols ordre decreixent:");
        String str = s.nextLine();
        int au = 0;
        try {
            au = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            System.out.println("Error no has introduit un número.");
        }
        String s = "creixent";
        if (au != 0) s = "decreixent";
        String[] aux = c.ordenararray(1);
        for (int i = 0; i < aux.length; i++) {
            System.out.println((i + 1) + " : " + aux[i]);
        }
        System.out.println();
    }

    private static void testordenarLlistaAlfabeticamentepairs() {
        System.out.println("Test ordenarLlistaAlfabeticamentepairs\n");
        System.out.println("Introdueix: ");
        System.out.println("0 Si vols ordre creixent, 1 si vols ordre per similitud o qualsevol altre si vols ordre per pes:");
        String str = s.nextLine();
        int au = 0;
        try {
            au = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            System.out.println("Error no has introduit un número.");
        }
        String s = "creix";
        if (au  == 1) s = "kdocs";
        else if (au != 0) s = "pes";
        ArrayList<Pair<String, String>> aux = c.ordenarLlistaTitolAutor(0);
        for (int i = 0; i < aux.size(); i++) {
            System.out.println("Document " + (i + 1) + " Autor " + aux.get(i).getFirst() + " Titol " + aux.get(i).getSecond());
        }
        System.out.println();
    }

    private static void llistarAutorsPrefix() {
        System.out.println("Test llistarAutorsPrefix\n");
        System.out.println("Introdueix: ");
        System.out.println("Prefix");
        String pre = s.nextLine();
        String[] aux = c.llistarAutorsPrefix(pre);
        for (int i = 0; i < aux.length; i++) {
            System.out.println((i + 1) + " : " + aux[i]);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        //c.inicialitzar();
        s = new Scanner(System.in);
        System.out.println("Driver del Controlador de Domini:\n");
        System.out.println("Quin mètode vols provar?");
        System.out.println("-1. res");
        System.out.println("0. donarAltaDocument");
        System.out.println("1. donarBaixaDocument");
        System.out.println("2. modificardocument");
        System.out.println("3. numDocs");
        System.out.println("4. llistarAutorsPrefix");
        System.out.println("5. llistarAutors");
        System.out.println("6. llistarTitolsAutor");
        System.out.println("7. llistarDocumentsSimilars");
        System.out.println("8. llistaDocumentsParaula");
        System.out.println("9. obtenirContingut");
        System.out.println("10.donarAltaExprBool");
        System.out.println("11.donarBaixaExprBool");
        System.out.println("12.modificarExprBool");
        System.out.println("13.llistarExprBooleanas");
        System.out.println("14.ordenarAlfabeticament");
        System.out.println("15.ordenarLlistaAlfabeticamentepairs");
        System.out.println("16.llistarDocumentsExprBool");
        System.out.println("17.sortir");
        String aux = s.nextLine();
        int opt = -1;
        try {
            opt = Integer.parseInt(aux);
        }
        catch (NumberFormatException e) {
            System.out.println("Error no has introduit un número.");
        }

        while(opt != -1) {
            switch (opt) {
                case 0:
                    testdonarAltaDocument();
                    break;
                case 1:
                    testdonarBaixaDocument();
                    break;
                case 2:
                    testmodificarDocument();
                    break;
                case 3:
                    testnumDocs();
                    break;
                case 4:
                    llistarAutorsPrefix();
                    break;
                case 5:
                    testllistarAutors();
                    break;
                case 6:
                    testllistarTitolsAutor();
                    break;
                case 7:
                    testllistarDocumentsSimilars();
                    break;
                case 8:
                    testllistarDocumentsParaula();
                    break;
                case 9:
                    testobtenirContingut();
                    break;
                case 10:
                    testdonarAltaExprBool();
                    break;
                case 11:
                    testdonarBaixaExprBool();
                    break;
                case 12:
                    testmodificarExprBool();
                    break;
                case 13:
                    testllistarExprBooleanas();
                    break;
                case 14:
                    testordenarAlfabeticament();
                    break;
                case 15:
                    testordenarLlistaAlfabeticamentepairs();
                    break;
                case 16:
                    testllistarDocumentsExprBool();
                    break;
                default:
                    break;
            }
            opt = -1;
            aux = s.nextLine();
            try {
                opt = Integer.parseInt(aux);
            }
            catch (NumberFormatException e) {
                System.out.println("Error no has introduit un número.");
            }
        }
        s.close();

    }
}
