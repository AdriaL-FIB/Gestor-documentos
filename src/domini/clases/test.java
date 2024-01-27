package domini.clases;

import domini.controladores.ControladorExprBool;
import domini.controladores.ControladorIndex;
import domini.utils.BooleanParser;
import persistencia.controladores.ControladorPersistencia;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        //ExprBool eb = new ExprBool("A|B&C");
        ExprBool eb1 = new ExprBool("{p1 p2 p3} & (\"hola adéu\" | pep) & !joan");
        System.out.println(eb1.arrel.toString());

        ExprBool eb2 = new ExprBool("Hola & adeu | pep");
        System.out.println(eb2.arrel.toString());

        ControladorIndex controladorIndex = new ControladorIndex();
        String s1 = "Hola Hola Hola Hola Hola Hola bon dia, pot estas? Espero pot que molt be. Avui pot m'agradaria anar a jugar a futbol.";
        String s2 = "HOLA BON DIA, com pot ESTAS? ESPERO com pot QUE MOLT BE. AVUI com pot M'AGRADARIA ANAR A JUGAR A FUTBOL.";
        String s3 = "Hola bon dia, com pot esta? Espero que com pot molt malament. Avui com pot m'agradaria anar a jugar a basquet.";
        String s4 = "";
        String s5 = "hola avui jugare com al futbol, i tambe anire a estudiar a la biblioteca. Agradaria.";


        controladorIndex.altaDocument("Pol", "s1", s1);
        controladorIndex.altaDocument("Pol", "s2", s2);
        controladorIndex.altaDocument("Pol", "s3", s3);
        controladorIndex.altaDocument("Pol", "s4", s4);
        controladorIndex.altaDocument("Pol", "s5", s5);
        //controladorIndex.printTfIdfDoc(4);
        //controladorIndex.printTfIdfDoc(0);

        String d1 = "Hola como estas";
        String d2 = "   .";
        controladorIndex.altaDocument("Pol", "d1", d1);
        controladorIndex.altaDocument("Pol", "d2", d2);

        //controladorIndex.baixaDocument("Pol", "s2");

        String rubik = "Ernő Rubik va obtenir la patent HU170062 pel seu cub, encara que no va sol·licitar pas cap patent vàlida a escala internacional. El primer lot de prova va ésser generat a final de 1977 i comercialitzat per les botigues de joguines de Budapest. La popularitat del cub va créixer a Hongria gràcies al boca-orella.\n" +
                "\n" +
                "El setembre de 1979, es feu un pacte amb Ideal Toys per expandir la seva comercialització per tot el món. Més endavant, Ideal Toys va comercialitzar un cub més lleuger, i va decidir rebatejar-lo. S'idearen possibles noms com «Nus Gordià« o «Or Inca», però la companyia finalment va decidir rebatejar-lo «Cub de Rubik» i exportar-lo per primer cop fora d'Hongria. S'inicià la comercialització mundial a finals de gener de 1980, a la Fira de la Joguina de Londres, Nova York, Nuremberg i París. El 1984, Ideal Toys va perdre un plet contra Larry Nichols, que havia registrat el producte amb la patent US3655201. Terutoshi Ishigi el va registrar al Japó amb la patent JP55‒8192. ";

        String ernoRubik = "El 1962 va iniciar els seus estudis d'arquitectura a la Universitat de tecnologia de Budapest i es llicencià el 1967. Continuà amb la seva formació en arquitectura i disseny a l'Acadèmia Hongaresa d'Arts Aplicades. En acabar els seus estudis el 1971, Rubik va començar a treballar com a professor d'arquitectura a la Universitat d'Arts i Disseny Moholy-Nagy. Va ser allà on, el 1974, va dissenyar el primer prototip del que després seria el Cub de Rubik, que en principi tan sols era una eina per a treballar amb els seus estudiants.\n" +
                "A principis dels anys 80, el Cub de Rubik va començar a comercialitzar-se arreu del món. Al llarg d'aquella dècada, Rubik va compaginar la seva tasca de docent amb el desenvolupament de nous jocs. El 1990 fou nomenat president de l'Acadèmia Hongaresa d'Enginyeria. El seu darrer joc, conegut com a Rubik 360, va sortir al mercat en 2009.";

        controladorIndex.altaDocument("Wikipedia", "Rubik", rubik);
        controladorIndex.altaDocument("Wikipedia", "ErnoRubik", ernoRubik);

        //System.out.println("----TFIDF0----");
        //controladorIndex.printTfIdfDoc(0);
        //System.out.println("----TFIDF5----");
        //controladorIndex.printTfIdfDoc(5);

        controladorIndex.modificarDocument("Pol", "d2", rubik);

        System.out.println("Tf-Idf:");
        System.out.println(Arrays.toString(controladorIndex.obtenirIdsKdocumentsSimilars(8, 10, 0)));
        System.out.println("Bag of Words:");
        System.out.println(Arrays.toString(controladorIndex.obtenirIdsKdocumentsSimilars(8, 10, 1)));
        System.out.println("Esta \"Hola\" en doc 0 frase 0? " + controladorIndex.estaStringEnFraseDocument(0, 0, "Hola"));

        var asd = controladorIndex.llistaFrasesParaulaDocument(8, "món");
        System.out.println("\"món\" esta en las frases " + Arrays.toString(asd));
        System.out.println("Esta \"món\" en doc 8 frase " + asd[asd.length-1] + "? " + controladorIndex.estaStringEnFraseDocument(8, asd[asd.length-1], "món"));

        ControladorExprBool ceb = new ControladorExprBool(controladorIndex);

        String eb = "Test1";
        try {
            ceb.agregarExprBool(eb, "(Ho!la | !futbol) & anar");
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(Arrays.toString(ceb.booleanSearch(eb)));

        controladorIndex.baixaDocument("Pol", "s1");
        controladorIndex.baixaDocument("Pol", "s2");
        controladorIndex.baixaDocument("Pol", "s3");
        controladorIndex.baixaDocument("Pol", "s4");
        controladorIndex.baixaDocument("Pol", "s5");

        try {
            System.out.println(Arrays.toString(ceb.booleanSearch(eb)));
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

        ControladorPersistencia cp = new ControladorPersistencia();
        controladorIndex.llistarAutors();
        //cp.guardarDocument();

        String eb3 = "TestAAA";
        try {
            ceb.agregarExprBool(eb3, "\"El 1962 va iniciar  els\"");
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
            System.out.println(Arrays.toString(ceb.booleanSearch(eb3)));
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
