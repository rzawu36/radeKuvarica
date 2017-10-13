package model;

/**
 * Created by rza on 11.10.17..
 */

public class Recept {
    private String ime;
    private String sadrzaj;

    /*
    ovo ti je samo klasa recept, ako ovo moram da ti objasnjavam onda jebiga :D
     */

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recept{");
        sb.append("ime='").append(ime).append('\'');
        sb.append(", sadrzaj='").append(sadrzaj).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
