import java.util.*;

// Soyut süreç sınıfı
public abstract class Process {
    private int calismaZamani;

    public Process(int calismaZamani) {
        this.calismaZamani = calismaZamani;
    }

    public int getCalismaZamani() {
        return calismaZamani;
    }
}

// Gerçek zamanlı işlem sınıfı, süreç sınıfından türetilmiştir
class GercekZamanliIslem extends Process {
    private boolean gercekZamanli;

    public GercekZamanliIslem(int calismaZamani, boolean gercekZamanli) {
        super(calismaZamani);
        this.gercekZamanli = gercekZamanli;
    }

    public boolean isGercekZamanli() {
        return gercekZamanli;
    }
}

// Bellek yönetim sınıfı
public class BellekYonetimi {
    private int toplamBellekBoyutu;
    private int gercekZamanliBellekBoyutu;
    private LinkedList<BellekBlok> bellekBloklari;

    public BellekYonetimi(int bellekBoyutu) {
        this.toplamBellekBoyutu = bellekBoyutu;
        this.gercekZamanliBellekBoyutu = 64; // Gerçek zamanlı işlemler için ayrılan bellek boyutu
        bellekBloklari = new LinkedList<>();
        bellekBloklari.add(new BellekBlok(0, bellekBoyutu)); // Bellek başlangıcında tek bir blok oluşturulur
    }

    // Bellek yönetimi için gerçek zamanlı işlemlerin eklenmesi
    public void islemEkle(GercekZamanliIslem process) {
        int boyut = process.getCalismaZamani();
        int kullanilabilirBellek = process.isGercekZamanli() ? gercekZamanliBellekBoyutu : toplamBellekBoyutu - gercekZamanliBellekBoyutu;

        boolean eklendi = bellekTahsisi(boyut, kullanilabilirBellek, process);

        if (!eklendi) {
            System.out.println("Yeterli bellek bloğu bulunamadı, işlem eklenemedi.");
        }
    }

    // Bellek tahsisini gerçekleştiren yardımcı metod
    private boolean bellekTahsisi(int boyut, int kullanilabilirBellek, GercekZamanliIslem process) {
        for (BellekBlok blok : bellekBloklari) {
            if (!blok.isDolu() && blok.getBoyut() >= boyut && kullanilabilirBellek >= boyut) {
                blok.islemEkle(process);
                kullanilabilirBellek -= boyut;
                return true;
            }
        }
        return false;
    }

    // Bellekten işlem kaldıran metod
    public void islemKaldir(Process process) {
        for (BellekBlok blok : bellekBloklari) {
            if (blok.islemVar(process)) {
                blok.islemKaldir(process);
                break;
            }
        }
    }
}

// Bellek bloğu sınıfı
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

    // Bellek bloğuna işlem ekleyen metod
    public void islemEkle(Process process) {
        this.islem = process;
        this.dolu = true;
    }

    // Bellek bloğundan işlem kaldıran metod
    public void islemKaldir(Process process) {
        if (this.islem != null && this.islem.equals(process)) {
            this.islem = null;
            this.dolu = false;
        }
    }

    // Belirtilen işlemin bu bellek bloğunda olup olmadığını kontrol eden metod
    public boolean islemVar(Process process) {
        return this.islem != null && this.islem.equals(process);
    }
}
