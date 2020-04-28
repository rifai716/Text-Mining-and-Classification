package model;

import java.util.ArrayList;
import java.util.List;

public class Text_tagging {
    private String kategori;
    private String judul;
    private ArrayList<String> words;
    private ArrayList<Integer> values;
    private List<Item_text_tagging> items;

    public Text_tagging(String kategori, String judul, ArrayList<String> words, ArrayList<Integer> values, List<Item_text_tagging> items) {
        this.kategori = kategori;
        this.judul = judul;
        this.words = words;
        this.values = values;
        this.items = items;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    public List<Item_text_tagging> getItems() {
        return items;
    }

    public void setItems(List<Item_text_tagging> items) {
        this.items = items;
    }
}
