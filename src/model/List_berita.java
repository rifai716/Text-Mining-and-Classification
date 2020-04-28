package model;

import java.util.List;

public class List_berita {
    String kategori;
    List<String> files;

    public List_berita(String kategori, List<String> files) {
        this.kategori = kategori;
        this.files = files;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
