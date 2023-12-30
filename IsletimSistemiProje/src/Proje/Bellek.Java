package Proje;

import java.util.*;

public class BellekYonetimi {
    private int toplamBellekBoyutu;
    private LinkedList<BellekBlok> bellekBloklari;

    public BellekYonetimi(int bellekBoyutu) {
        this.toplamBellekBoyutu = bellekBoyutu;
        bellekBloklari = new LinkedList<>();
        bellekBloklari.add(new BellekBlok(0, bellekBoyutu)); // Bellek başlangıcında tek bir blok oluşturulur
    }

    // Belleğe yeni bir işlem ekleme
    public void islemEkle(Process process) {
        int boyut = process.getcalismaZamani(); // İşlemin boyutu alınır
        boolean eklendi = false;

        for (BellekBlok blok : bellekBloklari) {
            if (!blok.isDolu() && blok.getBoyut() >= boyut) { // Dolu olmayan ve yeterli boyutta blok bulunduysa
                blok.islemEkle(process);
                eklendi = true;
                break;
            }
        }

        if (!eklendi) {
            System.out.println("Yeterli bellek bloğu bulunamadı, işlem eklenemedi.");
        }
    }

    // Bellekten bir işlemi kaldırma
    public void islemKaldir(Process process) {
        for (BellekBlok blok : bellekBloklari) {
            if (blok.islemVar(process)) {
                blok.islemKaldir(process);
                break;
            }
        }
    }
}

class BellekBlok {
    private int baslangicAdresi;
    private int boyut;
    private boolean dolu;
    private Process islem;

    public BellekBlok(int baslangicAdresi, int boyut) {
        this.baslangicAdresi = baslangicAdresi;
        this.boyut = boyut;
        this.dolu = false;
        this.islem = null;
    }

    public int getBoyut() {
        return boyut;
    }

    public boolean isDolu() {
        return dolu;
    }

    public void islemEkle(Process process) {
        this.islem = process; // Bloğa işlem eklenir
        this.dolu = true; // Blok dolu olarak işaretlenir
    }

    public void islemKaldir(Process process) {
        if (this.islem != null && this.islem.equals(process)) {
            this.islem = null; // Bloktan işlem kaldırılır
            this.dolu = false; // Blok boşaltılır
        }
    }

    public boolean islemVar(Process process) {
        return this.islem != null && this.islem.equals(process); // Belirtilen işlemin bu blokta olup olmadığını kontrol eder
    }
}
