import helper.Helper;
import model.Text_tagging;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataMaker {
    public static void run(Set<String> allWords, List<Text_tagging> text_taggings, String filename){
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();

        // add header
        sb.append("title;");
        sb.append(allWords.stream().collect(Collectors.joining(";")));
        sb.append(";label\n");

        for(Text_tagging tg : text_taggings){
            ArrayList<String> v = new ArrayList<>();
            v.add(tg.getJudul());
            for(String s : allWords){
                boolean isThere = false;
                for(String tgs : tg.getWords()){
                    if(s.equalsIgnoreCase(tgs)){
                        isThere = true;
                        break;
                    }
                }

                int idx = Helper.getIndex(tg.getWords(), s);
                if(idx == 999999){
                    // data not found
                    v.add("0");
                } else {
                    v.add(String.valueOf(tg.getValues().get(idx)));
                }
            }
            v.add(tg.getKategori());

            // add body
            String collect = v.stream().collect(Collectors.joining(";"));
            sb.append(collect);
            sb.append("\n");
        }

        try {
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
