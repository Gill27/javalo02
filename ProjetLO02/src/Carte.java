import java.util.*;
import java.lang.*;

public class Carte  {

    public String nom;

    public static ArrayList<Carte> Pioche = new ArrayList<Carte>();
    public static ArrayList<Carte> Set = new ArrayList<Carte>();
    public String effeth,effetw;
    public Carte(String nom) {
        this.nom = nom;
    }

    public Carte(String nom, String effetw, String effeth) {
        this.nom = nom;
        this.effeth = effeth;
        this.effetw = effetw;
    }

    public String toString() {
        return this.nom ;
    }

    public static void InitCartes(){

        Carte carte1 = new Carte("angry_mob" , "takenext" , "reveal");

        Carte carte2 = new Carte("the_inquisition" , "disc&takenext", "lookid&choosenextp");
        Carte carte3 = new Carte("pointed_hat","getbackcard&takenext","takcardrfp&choosenextp");
        Carte carte4 = new Carte("ducking_stool","choosenextp","qwitch");
        Carte carte5 = new Carte("hooked_nose","takecarday&takenext","takcardfp&choosenextp");
        Carte carte6 = new Carte("broomstick","takenext","choosenextp");
        Carte carte7 = new Carte("wart","takenext","choosenextp");
        Carte carte8 = new Carte("cauldron","discardcay&takenext","sreveal");
        Carte carte9 = new Carte("evil_eye","cantaccy&choosenextp","cantaccy&choosenextp");
        Carte carte10 = new Carte("toad","takenext","sreveal");
        Carte carte11 = new Carte("black_cat","takenext","discardc");
        Carte carte12 = new Carte("pet_newt","takenext","takcardrfp&choosenextp");
        Set.add(carte1);
        Set.add(carte2);
        Set.add(carte3);
        Set.add(carte4);
        Set.add(carte5);
        Set.add(carte6);
        Set.add(carte7);
        Set.add(carte8);
        Set.add(carte9);
        Set.add(carte10);
        Set.add(carte11);
        Set.add(carte12);

        System.out.println("Set de Carte : "+ Set);

        Pioche.addAll(Set);

    }
    public static void main(String[] args) {
        // write your code here

    }

}