package domini.clases;

import domini.utils.BooleanParser;

import java.io.Serializable;

public class ExprBool implements Serializable {
    String rawExpression;
    ExprBoolNode arrel;

    /**
     * Constructor que pren l'expressió original en format String com a paràmetre
     * i crea l'arbre d'expressió utilitzant la classe BooleanParser.
     * @param rawExpression -> String; l'expressió booleana original.
     */
    public ExprBool(String rawExpression) {
        this.rawExpression = rawExpression;
        arrel = BooleanParser.crearExpresion(rawExpression);
    }

    /**
     * Getter per al node arrel de l'arbre d'expressió.
     * @return ExprBoolNode, el node arrel de l'arbre d'expressió.
     */
    public ExprBoolNode getArrel() {
        return arrel;
    }

    /**
     * Getter per a l'expressió original en format String.
     * @return string, l'expressió original.
     */
    public String getRawExpression() {
        return rawExpression;
    }
}