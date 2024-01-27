package domini.utils;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {
    T1 first;
    T2 second;

    /**
     * Donats 2 valors de tipus T1 i T2 creem un pair.
     */
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Getter primer element de la parella.
     * @return T1, retorna el primer valor de la parella.
     */
    public T1 getFirst() {return first;}

    /**
     * Getter del segon element de la parella.
     * @return T2, retorna el segon valor de la parella.
     */
    public T2 getSecond() {return second;}

    /**
     * Setter del primer valor de la parella.
     * @param v -> T1; valor a assignar al primer element de la parella.
     */
    public void setFirst(T1 v) {first = v;}

    /**
     * Setter del segon valor de la parella.
     * @param v -> T2; valor a assignar al segon element de la parella.
     */
    public void setSecond(T2 v) {second = v;}
}
