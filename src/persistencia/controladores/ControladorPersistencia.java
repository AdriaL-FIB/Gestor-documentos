package persistencia.controladores;


import persistencia.clases.GestorDocuments;
import persistencia.clases.GestorEstat;

import java.io.IOException;

public class ControladorPersistencia {

    GestorDocuments gd = new GestorDocuments();
    GestorEstat ge = new GestorEstat();

    /**
     * Creadora per defecte.
     */
    public ControladorPersistencia() {

    }

    /**
     * Donat un identificador i un contingut, guarda els canvis efectuats en el document en el path que li pertoca.
     * @param id -> Integer; identificador del document.
     * @param contingut -> String; contingut del document.
     */
    public void guardarDocument(int id, String contingut) {
        gd.guardarDocument(id, contingut);
    }

    /**
     * Donats autor, títol, contingut, format i path; guarda el document en el format corresponent al path proporcionat.
     * @param autor -> String; nom de l'autor del document.
     * @param titol -> String; títol del document.
     * @param contingut -> String; contingut del document.
     * @param path -> String; path a on exportar el document.
     */
    public void exportarDocument(String autor, String titol, String contingut, String path) throws IOException {
        gd.exportarDocument(autor, titol, contingut, path);
    }

    /**
     * Donat un identificador esborra el path, eliminant així el document.
     * @param id -> Integer; identificador del document.
     */
    public void eliminarDocument(int id) {
        gd.eliminarDocument(id);
    }

    /**
     * Donat un identificador retorna tot el contingut del document indicat.
     * @param id -> Integer; identificador del document.
     * @return string, el contingut del document amb identificador {@code id}.
     */
    public String llegirDocument(int id) {
        return gd.llegirDocument(id);
    }

    /**
     * Donat un path, retorna tot el contingut del document guardat en el path indicat.
     * @param path -> String; path d'on importar el document.
     * @return string, contingut del document guardat al path {@code path} indicat.
     */
    public String importarDocument(String path) throws IOException {
        return gd.importarDocument(path);
    }

    /**
     * Donat un nom i un objecte, el serialitza i el guarda en format binari.
     * @param nom -> nom de l'objecte.
     * @param o -> objecte a serialitzar i guardar.
     * @return booleà, cert si s'ha guardat correctament, i fals altrament.
     */
    public boolean guardarEstat(String nom, Object o) {
        return ge.guardarEstatDisc(nom, o);
    }

    /**
     * Donat un nom carrega l'estat del disc i el retorna en un objecte.
     * @param nom -> String; nom de l'objecte a carregar.
     * @return Object, estat carregat del disc.
     */
    public Object carregarEstat(String nom) {
        return ge.carregarEstatDisc(nom);
    }
}
