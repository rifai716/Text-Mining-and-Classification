import model.List_berita;

import java.util.ArrayList;
import java.util.List;

public class Pemisahan_data {

    private List<List_berita> datas;
    private List<List_berita> training = new ArrayList<>();
    private List<List_berita> testing = new ArrayList<>();
    private int num_test = 3; // DEFAULT

    public Pemisahan_data(List<List_berita> datas, int num_test) {
        this.datas = datas;
        this.num_test = num_test;
        this.proses();
    }

    private void proses(){
        // testing
        for(List_berita lb : datas){
            List<String> tmpS = new ArrayList<>();
            for(int i = 0; i < num_test; i++){
                tmpS.add(lb.getFiles().get(i));
            }
            testing.add(new List_berita(lb.getKategori(), tmpS));
        }

        // training
        for(List_berita lb : datas){
            List<String> tmpS = new ArrayList<>();
            for(int i = num_test; i < lb.getFiles().size(); i++){
                tmpS.add(lb.getFiles().get(i));
            }
            training.add(new List_berita(lb.getKategori(), tmpS));
        }
    }

    public List<List_berita> getTraining() {
        return training;
    }

    public List<List_berita> getTesting() {
        return testing;
    }
}
