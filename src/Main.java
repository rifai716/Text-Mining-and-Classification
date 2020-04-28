import ALI.*;
import helper.FileHelper;
import model.Item_text_tagging;
import model.List_berita;
import model.Text_tagging;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        /**
         * -----------------------------------------------------
         */
        int threshold = 5;
        /**
         * -----------------------------------------------------
         */

        /**
         * --------------------------------------
         * LOAD LIBRARY
         * --------------------------------------
         */
        VectorLib vectorLib = new VectorLib();
        TextMiningLib textMiningLib = new TextMiningLib();

        /** (1)
         * --------------------------------------
         * GET ALL DIRECTORY AND FILE
         * --------------------------------------
         */
        String parentDir = "berita/";                                       // parent directory
        ArrayList<String> kategoriDir = FileHelper.getDir(parentDir);       // get child directory (category of news)
        ArrayList<List_berita> list_beritas = new ArrayList<>();            // get all file
        for(String s : kategoriDir){
            list_beritas.add(new List_berita(s, FileHelper.getFile(parentDir+s+"/")));
        }

        for(List_berita lb : list_beritas){
            System.out.println("\nKATEGORI\t: "+lb.getKategori());
            System.out.println(Arrays.toString(lb.getFiles().toArray(new String[0])));
        }


        /** (2)
         * --------------------------------------
         * DATA SPLITING
         * --------------------------------------
         */
        Pemisahan_data pemisahanData = new Pemisahan_data(list_beritas, 3);

        for(int i = 0; i < list_beritas.size(); i++){
            System.out.println("\n-----------------------------");
            System.out.println("KATEGORI\t: "+pemisahanData.getTraining().get(i).getKategori());
            System.out.println("data train\t: " +pemisahanData.getTraining().get(i).getFiles().size());
            for(String judul : pemisahanData.getTraining().get(i).getFiles()){
                System.out.println("\t\t- "+judul);
            }
            System.out.println("");
            System.out.println("data test\t: " +pemisahanData.getTesting().get(i).getFiles().size());
            for(String judul : pemisahanData.getTesting().get(i).getFiles()){
                System.out.println("\t\t- "+judul);
            }
        }

        /** (3)
         * --------------------------------------
         * TEXT MINING
         * --------------------------------------
         */

        List<Text_tagging> textTaggings = new ArrayList<>();

        String txt;
        String[] words, keywords;
        Map m = null;
        ArrayList<String> keyList, tmpKeyList;
        ArrayList<Integer> valueList, tmpValueList;
        for(List_berita lb : list_beritas){
            for(int i = 0; i < lb.getFiles().size(); i++){
                txt = textMiningLib.readFile(parentDir+lb.getKategori()+"/"+lb.getFiles().get(i));
                words = textMiningLib.Tokenizing(txt);
                words = textMiningLib.Filtering(words);
                keywords = textMiningLib.StemmingTagging(words);
                // temp list
                List<Item_text_tagging> litt = new ArrayList<>();
                tmpKeyList = new ArrayList<>();
                tmpValueList = new ArrayList<>();

                m = textMiningLib.Scoring(keywords);
                //System.out.println(m[i]);
                keyList = new ArrayList<>(m.keySet());
                valueList = new ArrayList<>(m.values());

                for(int j = 0; j < keyList.size(); j++){
                    if(valueList.get(j) >= threshold){
                        litt.add(new Item_text_tagging(keyList.get(j), valueList.get(j)));
                        tmpKeyList.add(keyList.get(j));
                        tmpValueList.add(valueList.get(j));
                    }
                }
                textTaggings.add(new Text_tagging(
                        lb.getKategori(),
                        lb.getFiles().get(i),
                        tmpKeyList,
                        tmpValueList,
                        litt)
                );
            }
        }

        for(Text_tagging tt : textTaggings){
            System.out.println("-----------------------------------\nJUDUL \t\t : " +tt.getJudul());
            System.out.println("KATEGORI \t : " +tt.getKategori());
            System.out.println(Arrays.toString(tt.getWords().toArray(new String[0])));
            System.out.println(Arrays.toString(tt.getValues().toArray(new Integer[0])));
        }
    }
}