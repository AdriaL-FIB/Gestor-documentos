package domini.utils;

import domini.clases.ExprBool;
import domini.clases.ExprBoolNode;
import domini.exceptions.ExprBoolIncorrectaException;

import java.awt.image.Kernel;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanParser {

    /**
     * Retorna la precedència de l’operador.
     * @param op -> String; operador de què en volem la precedència.
     * @return enter, enter indicat la precedència del operador.
     */
    private static int getPrecedence(String op) {
        //if (op.matches("[(){}]")) return 2;
        if (op.equals("!")) return 2;
        if (op.equals("&")) return 1;
        return 0;
    }

    /**
     * Indica si un string conté/és operador.
     * @param s -> String; string que volem saber si conté/és un operador.
     * @return booleà, cert si el string conté/és un operador, i fals altrament.
     */
    private static boolean isOperator(String s) {
        return s.matches("[(){}&!|]");
    }

    /**
     * Funció per passar l'expressió infixa a notació infixa.
     * @param str -> expressó infixa que volem passar.
     * @return llista de strings, l'expressió en notació infixa.
     */
    private static ArrayList<String> toPostfix(String str) {
        Stack<String> ops = new Stack<String>();
        //String[] terms = rawExprBool.split("[(){}\"!&|]");

        ArrayList<String> postfix = new ArrayList<>();
        String[] splitEB = str.split("(?<![^(){}&!|])|(?![^(){}&!|])");
        //System.out.println(Arrays.toString(splitEB));


        //http://csis.pace.edu/~wolf/CS122/infix-postfix.htm
        for (String s : splitEB) {
            if (s.equals(" ")) continue; // Ignore spaces
            s = s.strip(); //Remove white spaces
            if (isOperator(s)) {
                //System.out.println("Operand: " + s);

                if (s.equals("{") || s.equals("}")) continue; //Ignore {}

                if (s.equals(")")) { //Sacar operadores hasta encontrar el parentesis
                    String op = ops.pop();
                    while (!ops.isEmpty() && !op.equals("(")) {
                        postfix.add(op);
                        op = ops.pop();
                    }
                }
                else if (ops.isEmpty() || s.equals("(") || ops.peek().equals("(") || getPrecedence(s) >= getPrecedence(ops.peek())) { //Meter operador en pila
                    ops.push(s);
                }
                else { //Sacar operadores hasta encontrar uno con mayor precedencia
                    while (!ops.isEmpty() && getPrecedence(s) < getPrecedence(ops.peek())) {
                        String op = ops.pop();
                        postfix.add(op);
                    }
                    ops.push(s);
                }
            } else {
                postfix.add(s);
            }
        }

        //acabar de vaciar la pila
        while (!ops.isEmpty()) {
            postfix.add(ops.pop());
        }

        return postfix;
    }

    /**
     * Funció per crear una ExprBool a partir de l'expressió en format string.
     * @param rawExprBool -> String; expressió booleana en format string.
     * @return ExprBoolNode, node arrel de l'arbre que representa l'expressió booleana.
     * @throws ExprBoolIncorrectaException Si l'expressió booleana {@code rawExprBool} no és correcta.
     */
    public static ExprBoolNode crearExpresion(String rawExprBool) throws ExprBoolIncorrectaException { //A + B * C --> A(BC*)+
        try {
            ArrayList<String> postfix = toPostfix(rawExprBool); //Lista de operandos y operadores de la expresion en orden postfijo (Infix to postfix para pasarlo a arbol facilmente)

            List<ExprBoolNode> postfixNodes = postfix.stream().map(ExprBoolNode::new).collect(Collectors.toList()); //Crea una lista de nodos con el valor de cada operando/operador de la lista postfix
            Stack<ExprBoolNode> tmp = new Stack<>(); //Stack temporal para ir construyendo el arbol

            for (ExprBoolNode n : postfixNodes) {
                if (isOperator(n.getValue())) {
                    if (!n.getValue().equals("!")) { //2 operands
                        n.setR(tmp.pop());
                    }
                    n.setL(tmp.pop());
                }
                tmp.push(n);
            }
            return tmp.pop();
        }
        catch (RuntimeException e) {
            throw new ExprBoolIncorrectaException("La expressió introduida no és correcta");
        }
    }
}