package domini.clases;

import java.io.Serializable;

public class ExprBoolNode implements Serializable {
    String value;
    ExprBoolNode l, r;

    /**
     * Creadora per defecte
     * @param value -> String; valor desitjat del node.
     */
    public ExprBoolNode(String value) {
        this.value = value;
        this.l = null;
        this.r = null;
    }

    /**
     * Getter del valor del node.
     * @return string, valor del node.
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter del valor del node.
     * @param value -> String; el nou valor del node.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter del successor esquerre del node.
     * @return ExprBoolNode, successor esquerre del node.
     */
    public ExprBoolNode getL() {
        return l;
    }

    /**
     * Setter del successor esquerre del node.
     * @param l -> ExprBoolNode; nou successor esquerre del node.
     */
    public void setL(ExprBoolNode l) {
        this.l = l;
    }

    /**
     * Getter del successor dret del node.
     * @return ExprBoolNode, successor dret del node.
     */
    public ExprBoolNode getR() {
        return r;
    }

    /**
     * Setter del successor dret del node.
     * @param r -> ExprBoolNode; nou successor dret del node.
     */
    public void setR(ExprBoolNode r) {
        this.r = r;
    }

    /**
     * Obtenir l'arbre amb arrel el node en format string.
     * @return String, l'arbre en format string.
     */
    @Override
    public String toString() {
        return "{\"value\": \"" + value.replace("\"", "\\\"") + "\", \"left\": [" + ((l == null) ? "" : l.toString()) + "], \"right\": [" + ((r == null) ? "" : r.toString()) + "]}";
    }
}

