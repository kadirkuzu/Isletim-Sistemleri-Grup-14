package Proje;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static Proje.Kuyruk.satirsayisi;

public class Uygulama {
    public void Program() throws FileNotFoundException {

    	System.out.print("Okutulacak dosya adını giriniz:");
    	Scanner Dosya=new Scanner(System.in);
    	String dosyaYolu=Dosya.nextLine();
        Kuyruk kuyruk=new Kuyruk();
        int dongusayisi =kuyruk.donguSayisi(dosyaYolu);
        int[] baslangicDizi = {-1,-1,-1,-1,0};
        ArrayList<Process> realTimeLastList = new ArrayList<Process>();
        ArrayList<Process> userJobLastList = new ArrayList<Process>();
        ArrayList<ArrayList> lastListFull = new ArrayList<ArrayList>();
        Process activeProcess = new Process(baslangicDizi);
        ArrayList<Process> al = kuyruk.nesneolustur(dosyaYolu);

        for(int saniye=0;saniye<dongusayisi ; saniye++){        //Okunan .txt verisindeki proses zamanı hesaplanarak dongu optimize şekilde çalıştırılır.

            lastListFull = kuyruk.kuyrukCheck(kuyruk.varisZamaniBelirle(al,saniye), realTimeLastList, userJobLastList);
            realTimeLastList = lastListFull.get(0);     //Kapsülleme işlemi açılarak realTime fonksiyona gelen saniyedeki veriler eklenir
            userJobLastList = lastListFull.get(1);      //Kapsülleme işlemi açılarak userJob fonksiyona gelen saniyedeki veriler eklenir

            if (!realTimeLastList.isEmpty()){
                activeProcess = kuyruk.realTime(realTimeLastList,activeProcess,saniye);
            }
            if (realTimeLastList.isEmpty()){
                activeProcess = kuyruk.UserjobQueue(userJobLastList,activeProcess,saniye);
                userJobLastList.clear();
            }
        }
    }
}