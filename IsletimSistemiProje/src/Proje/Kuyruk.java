package Proje;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Kuyruk {

    ArrayList<Process> pio1Evrensel;
    ArrayList<Process> pio2Evrensel;
    ArrayList<Process> pio3Evrensel;

    public Kuyruk() {
        pio1Evrensel = new ArrayList<Process>();
        pio2Evrensel = new ArrayList<Process>();
        pio3Evrensel = new ArrayList<Process>();
    }

    public static int satirsayisi(File f) throws FileNotFoundException {  //Programın satır sayısı bulunur.

        Scanner dosya = new Scanner(f);
        int satir = 0;

        while (dosya.hasNextLine()) {
            dosya.nextLine();
            satir++;
        }
        dosya.close();

        return satir;
    }

    public int donguSayisi(String dosyaYolu) throws FileNotFoundException {
        File f = new File(dosyaYolu);

        int sayac= satirsayisi(f);
        int donmeSayaci =0;
        int[][] dizi = new int[sayac][3];
        Scanner dosya = new Scanner(f);
        int satir = 0;

        while (dosya.hasNextLine()) {
            String satirstring = dosya.nextLine();

            String[] arrSplit = satirstring.split(", ");
            int dizisatir = 0;
            for (String arrSplit1 : arrSplit) {

                dizi[satir][dizisatir] = Integer.parseInt(arrSplit1);

                dizisatir++;

            }
            donmeSayaci += dizi[satir][2];
        }
        dosya.close();

        return donmeSayaci;
    }
    public ArrayList<Process> nesneolustur(String dosyaYolu) throws FileNotFoundException {

        File f = new File("giris.txt");
        int donmeSayaci=0;
        Scanner dosyaIslem = new Scanner(f);
        int satir = satirsayisi(f);

        int sayac = 0;
        int[][] dizi = new int[satir][6];

        Process[] stable = new Process[satir];

        while (dosyaIslem.hasNextLine()) {
            String satirstring = dosyaIslem.nextLine();

            String[] arrSplit = satirstring.split(", ");
            int dizisatir = 1;
            for (String arrSplit1 : arrSplit) {

                dizi[sayac][dizisatir] = Integer.parseInt(arrSplit1);
                dizisatir++;
            }
            dizi[sayac][0] = sayac;

            dizi[sayac][4] = 0;
            dizi[sayac][5] = -1;
            Process kukla = new Process(dizi[sayac]);
            stable[sayac] = kukla;
            donmeSayaci += dizi[sayac][3];
            sayac++;
        }
        ArrayList<Process> ver = new ArrayList<Process>(Arrays.asList(stable));
        return ver;
    }

    public ArrayList<Process> varisZamaniBelirle(ArrayList<Process> kuyruk, int saniye) {

        ArrayList<Process> araci = new ArrayList<Process>();

        for (Process eleman : kuyruk) {
            if (eleman.varisZamani == saniye) {
                araci.add(eleman);
            }
        }

        return araci;
    }


    public ArrayList<ArrayList> kuyrukCheck(ArrayList<Process> kuyrukSaniye, ArrayList<Process> realTimeLastList, ArrayList<Process> userJobLastList) {
    // Prosesleri önceliklerine göre kuyruklara yerleştirir.
        for (Process eleman : kuyrukSaniye) {

            switch (eleman.oncelik) {
                case 0:
                    realTimeLastList.add(eleman);
                    break;
                default:
                    userJobLastList.add(eleman);
                    break;
            }
        }
        ArrayList<ArrayList> listeFull = new ArrayList<ArrayList>();   //Process ve ArrayList<Process> i ArrayList<ArrayList> in içinde kapsüllendi
        listeFull.add(realTimeLastList);
        listeFull.add(userJobLastList);
        listeFull.set(0, realTimeLastList);
        listeFull.set(1, userJobLastList);
        return listeFull;                       //Kapsüllenen veri Uygulamaya gönderildi.
    }

    //Ekran fonksiyonu ile yapılan işlemler görüntülenir.
    public void ekran(int saniye, int Statament, Process activeProcess) {
    	//id'ye özgü rengini verir ve prosesin durumunu yazdırır.
        // 0 başladı / 1 yürütülüyor / 2 askıda / 3 sonlandı / 4 zamanaşımı
    	System.out.print( "\u001b[38;5;" + activeProcess.Id + "m");
        switch (Statament) {
            case 0:
                System.out.println(saniye + " sn process başladı\t\t(id:" + activeProcess.Id + "\töncelik:" + activeProcess.oncelik + "\tkalan süre:" + activeProcess.calismaZamani +")");
                break;
            case 1:
                System.out.println(saniye + " sn process yürütülüyor\t(id:" + activeProcess.Id + "\töncelik:" + activeProcess.oncelik + "\tkalan süre:" + activeProcess.calismaZamani+")" );
                break;
            case 2:
                System.out.println(saniye + " sn process askıda\t\t(id:" + activeProcess.Id + "\töncelik:" + activeProcess.oncelik + "\tkalan süre:" + activeProcess.calismaZamani+")");
                break;
            case 3:
                System.out.println(saniye + " sn process sonlandı\t\t(id:" + activeProcess.Id + "\töncelik:" + activeProcess.oncelik + "\tkalan süre:" + activeProcess.calismaZamani+")" );
                break;
            case 4:
                System.out.println(saniye + " sn process zamanaşımı\t(id:" + activeProcess.Id + "\töncelik:" + activeProcess.oncelik + "\tkalan süre:" + activeProcess.calismaZamani+")" );
                break;
        }
    }


    //realTime kuyruğu 0 öncelikli prosesleri dokümanda verildiği gibi işler.
    public Process realTime(ArrayList<Process> realTimeLastList, Process activeProcess, int saniye) {


        if (activeProcess.Id == -1) {                       //Eğer çekilen veri ilk proses ise proses başlatılır.
            activeProcess = realTimeLastList.get(0);
            ekran(saniye, 0, activeProcess);
            activeProcess.calismaZamani -= 1;
        } else if ((activeProcess.oncelik == 0) && (activeProcess.calismaZamani != 0)) {   //Veri çalışma süresi var ise Yürütülür
            ekran(saniye, 1, activeProcess);
            activeProcess.calismaZamani -= 1;
        } else if ((activeProcess.oncelik == 0) && (activeProcess.calismaZamani == 0)) {    //Veri çalışma süresi kalmadı ise Sonlandırılır.
            ekran(saniye, 3, activeProcess);
            realTimeLastList.remove(0);
            if (!realTimeLastList.isEmpty()) {
                activeProcess = realTimeLastList.get(0);
                ekran(saniye, 0, activeProcess);
                activeProcess.calismaZamani -= 1;
            }
        } else {                                                //Proses önceliği 0 olmayan bir prosesten gelirse gelen proses askıya alınır.
            activeProcess.asimZamani = saniye;                  //Ardından Gerçek zamanlı proses Başlatılır.
            ekran(saniye, 2, activeProcess);
            activeProcess = realTimeLastList.get(0);
            ekran(saniye, 0, activeProcess);
            activeProcess.calismaZamani--;
        }

        pio1Evrensel = zamanAsimiKontrol(pio1Evrensel,saniye);              //Öncelik sıralamasına göre dağıtılmış verileri Zamanaşımına uğradığının
        pio2Evrensel = zamanAsimiKontrol(pio2Evrensel,saniye);              //kontrolü yapılır.
        pio3Evrensel = zamanAsimiKontrol(pio3Evrensel,saniye);

        return activeProcess;
    }




    //UserjobQueue kuyruğu 1,2 ve 3 öncelikli prosesleri dökümanda verildiği gibi işler.
    public Process UserjobQueue(ArrayList<Process> userJobList, Process activeProcess, int saniye) {

        for (Process processler : userJobList) {            //Öncelik sırasına göre Kodlar priority kısımlarına dağıtılır.
            if (processler.oncelik == 1) {
                pio1Evrensel.add(processler);
            } else if (processler.oncelik == 2) {
                pio2Evrensel.add(processler);
            } else if (processler.oncelik == 3) {
                pio3Evrensel.add(processler);
            }
        }

        if (!pio1Evrensel.isEmpty()) {              //1 Öncelikli proses var ise ilk önce çalıştırılır.
            if(activeProcess.oncelik!=0 &&activeProcess.oncelik!=-1  && activeProcess.calismaZamani!=0){ekran(saniye, 2, activeProcess);}
            activeProcess.asimZamani=saniye;        //Zaman aşımı kontrolü için güncelleme yapılır.
            activeProcess = pio1Evrensel.get(0);
            pio1Evrensel.remove(0);
            if ((activeProcess.calismaZamani - 1) > 0) {        //Prosesin çalışma zamanına göre 3 satırda işenir
                ekran(saniye, 0, activeProcess);        // eğer proses sonlanma süresi bitmediyse önceliği arttırılarak
                activeProcess.calismaZamani--;                  //priority 2 ye atılır.
                activeProcess.oncelik++;
                pio2Evrensel.add(activeProcess);
            } else if(activeProcess.calismaZamani==1){           //Procesin çalışma zamanı 1 saniye ise proses sonlandırılır.
                ekran(saniye, 0,activeProcess);
                activeProcess.calismaZamani--;
                activeProcess.oncelik++;
                ekran(saniye, 3,activeProcess);
            }else {
                activeProcess.calismaZamani--;
                activeProcess.oncelik++;
                ekran(saniye, 3, activeProcess);
            }

        } else if (!pio2Evrensel.isEmpty()) {       //1 Öncelikli proses var ise bitmesini bekler ve bittiğinde çalıştırılır.
            if(activeProcess.oncelik!=0 &&activeProcess.oncelik!=-1 && activeProcess.calismaZamani!=0){ekran(saniye, 2, activeProcess);}
            activeProcess.asimZamani=saniye;
            activeProcess = pio2Evrensel.get(0);
            pio2Evrensel.remove(0);
            if ((activeProcess.calismaZamani - 1) > 0) {
                ekran(saniye, 0, activeProcess);
                activeProcess.calismaZamani--;
                activeProcess.oncelik++;
                //activeProcess.asimZamani=saniye;
                pio3Evrensel.add(activeProcess);
                //ekran(saniye, 2, activeProcess);
            }else if(activeProcess.calismaZamani==1){
                ekran(saniye, 0,activeProcess);
                activeProcess.calismaZamani--;
                activeProcess.oncelik++;
                ekran(saniye, 3,activeProcess);
            }
            else {
                activeProcess.calismaZamani--;
                activeProcess.oncelik++;
                ekran(saniye, 3, activeProcess);
            }

        } else if (!pio3Evrensel.isEmpty()) {        //1 ve 2 Öncelikli proses var ise bitmesini bekler ve bittiğinde çalıştırılır.
            if(activeProcess.oncelik!=0 &&activeProcess.oncelik!=-1  && activeProcess.calismaZamani!=0){ekran(saniye, 2, activeProcess);}
            activeProcess.asimZamani=saniye;
            activeProcess = pio3Evrensel.get(0);
            pio3Evrensel.remove(0);
            if ((activeProcess.calismaZamani - 1) > 0) {
                ekran(saniye, 0, activeProcess);
                activeProcess.calismaZamani--;
                //activeProcess.asimZamani=saniye;
                pio3Evrensel.add(activeProcess);
                //ekran(saniye, 2, activeProcess);
            }else if(activeProcess.calismaZamani==1){
                ekran(saniye, 0,activeProcess);
                activeProcess.calismaZamani--;
                ekran(saniye+1, 3,activeProcess);
            }
            else {
                activeProcess.calismaZamani--;
                ekran(saniye, 3, activeProcess);
            }
        }

        pio1Evrensel = zamanAsimiKontrol(pio1Evrensel,saniye);
        pio2Evrensel = zamanAsimiKontrol(pio2Evrensel,saniye);
        pio3Evrensel = zamanAsimiKontrol(pio3Evrensel,saniye);

        return activeProcess;
    }

    //zamanAsimiKontrol fonksiyonunda zaman aşımına uğrayan bir  fonksiyon var ise belirlenir ve silinir.
    public ArrayList<Process> zamanAsimiKontrol(ArrayList<Process> list,int saniye) {
        int i = list.size();
            if(i==0) return list;

        for(int a=0;a<i;a++) {
            if (saniye - list.get(a).asimZamani == 20 && list.get(a).asimZamani != -1) {
                ekran(saniye, 4, list.get(a));
                list.remove(a);
                i--;
                if (list.size() == 0) {
                    break;
                }
            }
        }
        return list;
    }

}