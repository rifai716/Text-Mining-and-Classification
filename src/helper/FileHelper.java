package helper;

import java.io.File;
import java.util.ArrayList;

public class FileHelper {
    public static ArrayList<String> getDir(String parentDir){
        ArrayList<String> out = new ArrayList<>();
        File folder = new File(parentDir);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                out.add(listOfFiles[i].getName());
            }
        }
        return out;
    }

    public static ArrayList<String> getFile(String parentDir){
        ArrayList<String> out = new ArrayList<>();
        File folder = new File(parentDir);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                out.add(listOfFiles[i].getName());
            }
        }
        return out;
    }
}
