import ALI.TextMiningLib;
import ALI.VectorLib;
import model.Item_text_tagging;
import model.List_berita;
import model.Text_tagging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextMining {
    public static List<Text_tagging> stemmingTagging(List<List_berita> data, String parentDir, int threshold){
        TextMiningLib textMiningLib = new TextMiningLib();
        List<Text_tagging> textTaggings = new ArrayList<>();
        String txt;
        String[] words, keywords;
        Map m = null;
        ArrayList<String> keyList, tmpKeyList;
        ArrayList<Integer> valueList, tmpValueList;
        for(List_berita lb : data){
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
        return textTaggings;
    }
}
