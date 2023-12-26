package Proje;
import java.util.Random;

public class Process {

    int Id;
    int varisZamani;
    int calismaZamani;
    int oncelik;
    int asimZamani;

    public int getAsimZamani() {
        return asimZamani;
    }

    public void setAsimZamani(int asimZamani) {
        this.asimZamani = asimZamani;
    }


    public int getVarisZamani() {
        return varisZamani;
    }

    @Override
    public String toString() {
        return "process [Id=" + Id + ", varisZamani=" + varisZamani + ", calismaZamani=" + calismaZamani + ", oncelik="
                + oncelik + ", asimZamani="+ asimZamani + "]";
    }
    public void setVarisZamani(int varisZamani) {
        this.varisZamani = varisZamani;
    }

    public int getcalismaZamani() {
        return calismaZamani;
    }

    public void setcalismaZamani(int calismaZamani) {
        this.calismaZamani = calismaZamani;
    }

    public int getOncelik() {
        return oncelik;
    }

    public void setOncelik(int oncelik) {
        this.oncelik = oncelik;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Process (int[] dizi ){

        this.Id=dizi[0];
        this.varisZamani=dizi[1];
        this.oncelik=dizi[2];
        this.calismaZamani=dizi[3];
        this.asimZamani = dizi[4];


    }

}
