package persistencia.clases;

import java.io.*;

public class GestorEstat {

    /**
     * Donat un nom i un objecte, el serialitza i el guarda en format binari.
     * @param nom -> nom de l'objecte.
     * @param o -> objecte a serialitzar i guardar.
     * @return booleà, cert si s'ha guardat correctament, i fals altrament.
     */
    public boolean guardarEstatDisc(String nom, Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nom));
            oos.writeObject(o);
            oos.flush();
            oos.close();
            return true;
        }
        catch (IOException e) {
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * Donat un nom carrega l'estat del disc i el retorna en un objecte.
     * @param nom -> String; nom de l'objecte a carregar.
     * @return Object, estat carregat del disc.
     */
    public Object carregarEstatDisc(String nom) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nom));
            Object o = ois.readObject();
            ois.close();
            return o;
        }
        catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
