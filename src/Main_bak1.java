import ALI.TextMiningLib;
import ALI.VectorLib;
import helper.FileHelper;
import model.List_berita;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main_bak1 {
    public static void coba() {
        int threshold = 4;

        VectorLib vectorLib = new VectorLib();
        TextMiningLib textMiningLib = new TextMiningLib();

        // parent directory
        String parentDir = "berita/";
        // get child directory (category of news)
        ArrayList<String> kategoriDir = FileHelper.getDir(parentDir);
        // get all file
        ArrayList<List_berita> list_beritas = new ArrayList<>();
        for(String s : kategoriDir){
            list_beritas.add(new List_berita(s, FileHelper.getFile(parentDir+s+"/")));
        }

        for(List_berita lb : list_beritas){
            System.out.println("\nKATEGORI\t: "+lb.getKategori());
            System.out.println(Arrays.toString(lb.getFiles().toArray(new String[0])));
        }


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


        String berEkonomi;
        String[] words, keywords;
        Map[] map = new Map[list_beritas.get(0).getFiles().size()];
        for (int i=0; i<list_beritas.get(0).getFiles().size(); i++){
            berEkonomi = textMiningLib.readFile(parentDir+list_beritas.get(0).getKategori()+"/"+list_beritas.get(0).getFiles().get(i));
            //System.out.println(berEkonomi);
            words = textMiningLib.Tokenizing(berEkonomi);
            words = textMiningLib.Filtering(words);
            keywords = textMiningLib.StemmingTagging(words);
            map[i] = textMiningLib.Scoring(keywords);
        }
//        textMiningLib.viewMap(map[0]);
//        System.out.println(map[0]);
//        System.out.println(map[0].get("risiko"));

        /**
         * https://www.baeldung.com/java-merge-maps
         */
        Stream combined = null;
        for(int i = 0; i < 10-1; i++){
            if(i == 0) combined = Stream.concat(map[i].entrySet().stream(), map[i+1].entrySet().stream());
            else combined = Stream.concat(combined, map[i+1].entrySet().stream());
        }

        /**
         * Ref :
         * https://www.geeksforgeeks.org/stream-map-java-examples/
         * https://www.geeksforgeeks.org/collectors-tomap-method-in-java-with-examples/
         */
        System.out.println("\n\nDATA GABUNGAN : ");
        List<String> lstr = (List<String>) combined.map(s -> s.toString()).collect(Collectors.toList());
        // System.out.println(combined.map(s -> s).collect(Collectors.toList()));
        System.out.println("Total kata : "+ lstr.size());
        System.out.println("\n-------------------------------------");

        /**
         * Proses mengambil kata (word) yang memiliki nilai lebih dari 4 (value_count > 4)
         */
        String[] tmp;
        Map<String, Integer> newData = new HashMap<>();
        for(String s : lstr){
            tmp = s.split("=");
            //System.out.println(tmp[0]+"\t\t\t\t\t\t"+tmp[1]);
            if(Integer.parseInt(tmp[1]) >= threshold){
                newData.put(tmp[0], Integer.parseInt(tmp[1]));
            }
        }
        System.out.println(newData);
        System.out.println("---------------------------------------\n");


        /**
         * ----------------------------------------
         * QUERY
         * ----------------------------------------
         */

        String query;
        String[] querylist;
        Map results;
        query = "Jakarta Bangkrut";
        querylist = textMiningLib.Tokenizing(query);
        querylist = textMiningLib.Filtering(querylist);
        querylist = textMiningLib.StemmingTagging(querylist);

        vectorLib.view(querylist);

        String[] dokumens = list_beritas.get(0).getFiles().toArray(new String[0]);
        System.out.println(Arrays.toString(dokumens));
        results = textMiningLib.Search(querylist, map, dokumens);
        textMiningLib.viewMap(results);

        String[] rankdocs = textMiningLib.getRetrievedDocs(results);
        int[] values = textMiningLib.getRetrievedValues(results);
        vectorLib.view(rankdocs);
        vectorLib.view(values);
    }
}