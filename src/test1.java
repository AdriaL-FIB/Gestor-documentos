import domini.controladores.ControladorDomini;

import java.util.Arrays;

public class test1 {

    public static void main(String[] args) {
        ControladorDomini cd = new ControladorDomini();

        //cd.inicialitzar();


        cd.donarAltaDocument("AE", "T", "asd");
        cd.donarAltaDocument("AB", "T", "asd");
        cd.donarAltaDocument("MC", "T", "asd");
        cd.donarAltaDocument("AA", "T", "asd");
        cd.donarAltaDocument("AD", "T", "asd");
        cd.donarAltaDocument("BE", "T", "asd");
        cd.donarAltaDocument("BA", "T", "asd");
        cd.donarAltaDocument("BI", "T", "asd");
        cd.donarAltaDocument("JI", "T", "asd");


        cd.donarAltaDocument("call", "T", "asd");
        cd.donarAltaDocument("me", "T", "asd");
        cd.donarAltaDocument("mind", "T", "asd");
        cd.donarAltaDocument("mid", "T", "asd");


        System.out.println(Arrays.toString(cd.llistarAutorsPrefix("M")));

        cd.donarBaixaDocument("me", "T");
        System.out.println(Arrays.toString(cd.llistarAutorsPrefix("m")));
    }
}
