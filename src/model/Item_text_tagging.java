package model;

public class Item_text_tagging {
    private String kata;
    private int jumlah;

    public Item_text_tagging(String kata, int jumlah) {
        this.kata = kata;
        this.jumlah = jumlah;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
