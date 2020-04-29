import ALI.*;
import helper.Helper;
import model.List_berita;
import model.Text_tagging;

import java.io.FileWriter;
import java.io.IOException;
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
        ArrayList<String> kategoriDir = Helper.getDir(parentDir);       // get child directory (category of news)
        ArrayList<List_berita> list_beritas = new ArrayList<>();            // get all file
        for(String s : kategoriDir){
            list_beritas.add(new List_berita(s, Helper.getFile(parentDir+s+"/")));
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

        // ALL DATA
        // GABUNGKAN SELURUH "KATA" MENJADI SATU KESATUAN
        List<Text_tagging> textTaggingAllData = TextMining.stemmingTagging(list_beritas, parentDir, threshold);

        Stream combined = null;
        int i = 0;
        for(Text_tagging tt : textTaggingAllData){
            System.out.println("-----------------------------------\nJUDUL \t\t : " +tt.getJudul());
            System.out.println("KATEGORI \t : " +tt.getKategori());
            System.out.println(Arrays.toString(tt.getWords().toArray(new String[0])));
            System.out.println(Arrays.toString(tt.getValues().toArray(new Integer[0])));
            if(i == 0) {
                combined = tt.getWords().stream();
            } else {
                combined = Stream.concat(combined, tt.getWords().stream());
            }
            i++;
        }
        System.out.println("\n\n--------------------\n HASIL PENGGABUNGAN \n--------------------\n");
        List<String> lstr = (List<String>) combined.map(s -> s.toString()).collect(Collectors.toList());
        Map<String, Long> wordsAllArticles = lstr.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println("TOTAL KATA \t"+ wordsAllArticles.keySet().size());
        System.out.println(wordsAllArticles.keySet());

        // MEMBUAT DATA TRAINING
        List<Text_tagging> textTaggingsTraining = TextMining.stemmingTagging(pemisahanData.getTraining(), parentDir, threshold);

        DataMaker.run(wordsAllArticles.keySet(), textTaggingsTraining, "training.csv");
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter("training.csv");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        StringBuffer sb = new StringBuffer();
//
//        // add header
//        sb.append("title;");
//        sb.append(wordsAllArticles.keySet().stream().collect(Collectors.joining(";")));
//        sb.append(";label\n");
//
//        for(Text_tagging tg : textTaggingsTraining){
//            ArrayList<String> v = new ArrayList<>();
//            v.add(tg.getJudul());
//            for(String s : wordsAllArticles.keySet()){
//                boolean isThere = false;
//                for(String tgs : tg.getWords()){
//                    if(s.equalsIgnoreCase(tgs)){
//                        isThere = true;
//                        break;
//                    }
//                }
//
//                int idx = Helper.getIndex(tg.getWords(), s);
//                if(idx == 999999){
//                    // data not found
//                    v.add("0");
//                } else {
//                    v.add(String.valueOf(tg.getValues().get(idx)));
//                }
//            }
//            v.add(tg.getKategori());
//
//            // add body
//            String collect = v.stream().collect(Collectors.joining(";"));
//            sb.append(collect);
//            sb.append("\n");
//        }
//
//        try {
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // MEMBUAT DATA TESTING
        List<Text_tagging> textTaggingsTesting = TextMining.stemmingTagging(pemisahanData.getTesting(), parentDir, threshold);

    }
}