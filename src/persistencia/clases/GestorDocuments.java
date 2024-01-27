package persistencia.clases;

import domini.exceptions.NoExisteixException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestorDocuments {

    /**
     * Donat un identificador i un contingut, guarda els canvis efectuats en el document en el path que li pertoca.
     * @param id -> Integer; identificador del document.
     * @param contingut -> String; contingut del document.
     */
    public void guardarDocument(int id, String contingut) {
        try {
            Files.createDirectories(Paths.get("files"));
            Files.writeString(Paths.get(String.valueOf("files/" + id)), contingut);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Donats autor, títol, contingut i path; guarda el document en el format corresponent al path proporcionat.
     * @param autor nom de l'autor del document.
     * @param titol títol del document.
     * @param contingut contingut del document.
     * @param path path a on exportar el document.
     */
    public void exportarDocument(String autor, String titol, String contingut, String path) throws IOException {
        String text;
        String[] aux = path.split("\\.");
        String extension = aux[aux.length-1];
        if (contingut == null) contingut = "";
        switch (extension) {
            case "xml":
                text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<document>" +
                        "<autor>" + autor + "</autor>\n" +
                        "<titol>" + titol + "</titol>\n" +
                        "<contingut>" + contingut + "</contingut>" +
                        "</document>";
                break;
            case "prop":
                text = "autor 👉" + autor + "\n" +
                        "titol 👉" + titol + "\n" +
                        "contingut 👉" + contingut;
                break;
            default:
                text = autor + "\n" + titol + "\n" + contingut;
        }
        Files.writeString(Paths.get(path), text);
    }

    /**
     * Donat un identificador esborra el path, eliminant així el document.
     * @param id -> Integer; identificador del document.
     */
    public void eliminarDocument(int id) {
        try {
            Files.createDirectories(Paths.get("files"));
            Files.delete(Paths.get(String.valueOf("files/" + id)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Donat un identificador retorna tot el contingut del document indicat.
     * @param id -> Integer; identificador del document.
     * @return string, el contingut del document amb identificador {@code id}.
     */
    public String llegirDocument(int id) {
        try {
            Files.createDirectories(Paths.get("files"));
            return Files.readString(Paths.get(String.valueOf("files/" + id)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Donat un path, retorna tot el contingut del document guardat en el path indicat.
     * @param path -> String; path d'on importar el document.
     * @return string, contingut del document guardat al path {@code path} indicat.
     */
    public String importarDocument(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
