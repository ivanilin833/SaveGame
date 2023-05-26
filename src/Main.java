import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static String basePath = "D:\\Games\\savegames\\";

    public static void main(String[] args) {


       Map<String, GameProgress> gameProgresses = new HashMap<>();
       gameProgresses.put("save1.dat", new GameProgress(100, 2, 12, 12));
       gameProgresses.put("save2.dat", new GameProgress(50, 1, 19, 11));
       gameProgresses.put("save3.dat", new GameProgress(150, 11, 119, 21));

       gameProgresses.forEach((x,y) -> saveGame(basePath + x, y));

       zipFiles(basePath + "save.zip", gameProgresses.keySet());

        for (String path : gameProgresses.keySet()) {
            new File(basePath + path).delete();
        }
    }

    private static void saveGame(String path, GameProgress progress){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            oos.writeObject(progress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void zipFiles(String pathZip, Set<String> listPathToSave){
        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for (String path : listPathToSave) {
                try (FileInputStream fis = new FileInputStream(basePath + path)){
                    File file = new File(basePath + path);
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
