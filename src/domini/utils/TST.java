package domini.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TST implements Serializable {
    Node root;

    /**
     * Classe privada que representa un node en un arbre TST (Ternary Search Tree).
     */
    private static class Node implements Serializable{

        // Caràcter emmagatzemat al node
        char data;

        // Punters als fills esquerre, dret i central (low) del node
        Node left, right, low;

        // Indica si el node representa el fi d'una paraula
        boolean endOfString;

        /**
         * Crea una nova instància de la classe {@code Node}.
         *
         * @param data -> char; el caràcter a emmagatzemar al node.
         */
        public Node(char data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.low = null;
            this.endOfString = false;
        }
    }

    /**
     * Afegeix els nodes necessaris dins de l'arbre per a poder guardar una nova paraula.
     * @param node -> Node; node arrel de l'arbre a què afegir la paraula.
     * @param s -> String; paraula a insertar.
     * @param indx -> Integer; pròxima lletra de la paraula a afegir.
     * @return Node, node arrel de l'arbre amb la paraula insertada.
     */
    private Node insertParaula(Node node, String s, int indx) {
        if (indx == s.length())
            return node;

        if (node == null) {
            node = new Node(s.charAt(indx));
        }

        if (node.data == s.charAt(indx)) {
            if (indx == s.length() - 1) { // Ultim caracter del String s. El marquem
                node.endOfString = true;
            }
            else {
                node.low = insertParaula(node.low, s, indx + 1);
            }
        }
        else {
            if (s.charAt(indx) < node.data) {
                node.left = insertParaula(node.left, s, indx);
            } else {
                node.right = insertParaula(node.right, s, indx);
            }
        }

        return node;
    }

    /**
     * Retorna cert si el node passat per referència és fulla d'un altra node.
     * @param node -> Node; node que volem saber si és fulla.
     * @return booleà, cert si el node passat per referència és fulla d'un altra node, i fals altrament.
     */
    private boolean esFulla(Node node) {
        return node.left == null && node.low == null && node.right == null;
    }

    /**
     * Donada una paraula, elimina tots els nodes de l'arbre necessaris per a trobar la paraula en qüestió si aquesta formava part de l'arbre.
     * @param node -> Node; node arrel de l'arbre d'on volem eliminar la paraula.
     * @param s -> String; paraula a eliminar.
     * @param indx -> Integer; pròxima lletra de la paraula a eiliminar.
     * @return enter, retorna -1 si no queden lletres de la paraula a eliminar, i 0 altrament.
     */
    private int eliminarParaula(Node node, String s, int indx) {
        /*
        if (indx == s.length())
            return -1;

        if (node.data == s.charAt(indx)) {
            if (indx == s.length() - 1)
                return 0;
            int nodeAEliminar = eliminarParaula(node.low, s, indx);
            if (nodeAEliminar == 0) {
                if (node.low.left != null) {
                    //node.low.left.
                }
            }


        }
        else {
            Node next = (s.charAt(indx) < node.data) ? node.left : node.right;
            int nodeAEliminar = eliminarParaula(next, s, indx);
            if (nodeAEliminar != -1) {
                if (s.charAt(indx) < node.data) node.left = null;
                else node.right = null;
                return -1; //Si es un node right o left, ja hem acabat, ja que o el node anterior es final, o té altres fills
            }
        }
        */
        return 0;
    }

    /**
     * Donat un node retorna les paraules que s'obtenen a partir d'aquest.
     * @param node -> Node; arrel de l'arbre de què obtenir les paraules.
     * @return llista de strings, llista de paraules del TST.
     */
    ArrayList<StringBuilder> obtenirParaules(Node node) {
        if (node == null)
            return new ArrayList<>();

        ArrayList<StringBuilder> suffixs = obtenirParaules(node.low);
        if (node.endOfString) {
            StringBuilder sb = new StringBuilder();
            sb.append("");
            suffixs.add(sb);
        }

        for (StringBuilder sb : suffixs) {
            sb.append(node.data);
        }

        suffixs.addAll(obtenirParaules(node.left));
        suffixs.addAll(obtenirParaules(node.right));

        return suffixs;
    }

    /**
     * Retorna el node que conté la primera lletra de la paraula que s'està buscant.
     * @param node -> Node; node arrel de l'arbre on buscar la paraula.
     * @param s -> String; paraula a buscar.
     * @param indx -> pròxima lletra de la paraula a buscar.
     * @return Node, node amb la primera lletra de la paraula {@code s}.
     */
    Node obtenirNode(Node node, String s, int indx) {
        if (node == null || s.equals(""))
            return null;

        if (Character.toLowerCase(node.data) == Character.toLowerCase(s.charAt(indx))) {
            if (indx == s.length() - 1)
                return node; //trobat
            else
                return obtenirNode(node.low, s, indx + 1);
        }
        else {
            Node next = (Character.toLowerCase(s.charAt(indx)) < Character.toLowerCase(node.data)) ? node.left : node.right;
            return obtenirNode(next, s, indx);
        }
    }

    /**
     * Donat un prefix retorna totes les paraules dins de TST que contenen aquest prefix.
     * @param prefix -> String; prefix desitjat.
     * @return array de strings, paraules del TST que tenen {@code prefix} com a prefix.
     */
    public String[] obtenirParaulesPerPrefix(String prefix) {
        //prefix = prefix.toLowerCase();
        Node nodeInicial;
        boolean esParaula = false;
        if (prefix.equals("")) nodeInicial = root;
        else {
            nodeInicial = obtenirNode(root, prefix, 0);
            if (nodeInicial != null) {
                esParaula = nodeInicial.endOfString;
                nodeInicial = nodeInicial.low;
            }
        }
        ArrayList<StringBuilder> paraules = obtenirParaules(nodeInicial);

        for (StringBuilder sb : paraules) {
            for (int i = prefix.length() - 1; i >= 0; --i) sb.append(prefix.charAt(i));
            sb.reverse();

        }
        if (esParaula) paraules.add(new StringBuilder(prefix));
        return paraules.stream().map(StringBuilder::toString).toArray(String[]::new);
    }

    /**
     * Donada una paraula afegeix els nodes necessaris per afegir la paraula al TST.
     * @param s -> String; paraula a afegir.
     */
    public void insertParaula(String s) {
        root = insertParaula(root, s.toLowerCase(), 0);
    }

    /**
     * Donada una paraula, elimina tots els nodes de l'arbre necessaris per a trobar la paraula en qüestió si aquesta formava part de l'arbre.
     * @param s -> String; paraula a eliminar.
     */
    public void eliminarParaula(String s) {
        //insertNode(root, s, 0);
        Node node = obtenirNode(root, s.toLowerCase(), 0);
        node.endOfString = false;
    }
}
