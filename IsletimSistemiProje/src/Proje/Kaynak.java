import java.util.*;

public class KaynakTahsisi {
    private int yaziciSayisi = 2;
    private int tarayiciSayisi = 1;
    private int modemSayisi = 1;
    private int cdSurucuSayisi = 2;
    private int bellekBoyutu = 1024;

    private int kullanilanYazici = 0;
    private int kullanilanTarayici = 0;
    private int kullanilanModem = 0;
    private int kullanilanCdSurucu = 0;
    private int kullanilanBellek = 0;

    private Queue<Process> dusukOncelikliKuyruk = new LinkedList<>();
    private List<Process> gercekZamanliList = new ArrayList<>();

    public void prosesTalebi(Process process) {
        if (process.getOncelik() > 0) { // Gerçek zamanlı proses
            if (process.getcalismaZamani() <= 64 && kullanilanBellek + process.getcalismaZamani() <= bellekBoyutu) {
                gercekZamanliList.add(process);
                kullanilanBellek += process.getcalismaZamani();
            } else {
                System.out.println("Gerçek zamanlı proses için yeterli bellek yok veya boyutu fazla.");
            }
        } else { // Düşük öncelikli proses
            dusukOncelikliKuyruk.add(process);
        }
    }

    public void prosesTamamlandi(Process process) {
        if (process.getOncelik() > 0) { // Gerçek zamanlı proses
            gercekZamanliList.remove(process);
            kullanilanBellek -= process.getcalismaZamani();
        } else { // Düşük öncelikli proses
            dusukOncelikliKuyruk.remove();
        }
    }

    public boolean kaynakKontrol(Process process) {
        if (process.getOncelik() > 0) { // Gerçek zamanlı proses
            return process.getcalismaZamani() <= 64 && kullanilanBellek + process.getcalismaZamani() <= bellekBoyutu;
        } else { // Düşük öncelikli proses
            // Kaynakları kontrol et ve talep edilen kaynakları serbestse işlemi gerçekleştir
            if (process.getcalismaZamani() <= 1024 - kullanilanBellek) {
                return true;
            } else {
                System.out.println("Yetersiz bellek!");
                return false;
            }
        }
    }

    // Diğer kaynakları kontrol etmek ve işlem yapmak için metotlar eklenebilir

    public static void main(String[] args) {
        KaynakTahsisi kaynakTahsisi = new KaynakTahsisi();

        
        Process yeniIslem = new Process(new int[]{1, 0, 0, 100, 0}); 
        if (kaynakTahsisi.kaynakKontrol(yeniIslem)) {
            kaynakTahsisi.prosesTalebi(yeniIslem);
            // İşlem başarıyla eklendi, gerekli kaynaklar ayrıldı
        } else {
            // İşlem kaynaklar nedeniyle eklenemedi
        }

        // İşlem tamamlandıktan sonra serbest bırakılabilir
        kaynakTahsisi.prosesTamamlandi(yeniIslem);
    }
}
