package helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Helper {
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

    public static int getIndex(List<String> datas, String keyword){
        int idx = 999999;
        for(int i = 0; i < datas.size(); i++){
            if(datas.get(i).equalsIgnoreCase(keyword)){
                idx = i;
            }
        }
        return idx;
    }
}
