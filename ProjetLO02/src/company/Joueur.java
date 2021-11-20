package company;

import java.util.*;


public class Joueur {

    private String nomJoueur;
    private enum Identite {Sorciere,Villageois,Default}; // enumeration du type Identite
    private Identite id ; // id de type Identite
    private boolean idRevele;
    private boolean premierAppel = true;
    private static int nombreJoueurs = 0;
    private int score;
    public ArrayList<Carte> mainJoueur = new ArrayList<Carte>();


    public String toString() {
        return "com.company.Joueur : " + nomJoueur + " mainJoueur = " + mainJoueur;
    }

    //////////////Constructeur//////////////
    public Joueur (String nomJoueur){
        this.nomJoueur = nomJoueur;
        this.idRevele = false;
        this.score = 0;
        this.id = Identite.Default;
        nombreJoueurs += 1;

    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    //////////////Méthodes Private//////////////
    private String read () {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        return str;
    }
    //////////////Méthodes Public//////////////

    public void choisirIdentite(){
        // permet de ne pas pouvoir appeler 2 fois la méthode pour un meme joueur
        if ( this.premierAppel == true ) {
            System.out.print(this.nomJoueur + " entrer votre identite ");
            do {
                System.out.println("(Sorciere ou Villageois)");
                String identite = read();
                if (identite.equalsIgnoreCase("Sorciere") || identite.equalsIgnoreCase("S")) {
                    id = Identite.Sorciere;
                } else if (identite.equalsIgnoreCase("Villageois") || identite.equalsIgnoreCase("V")) {
                    id = Identite.Villageois;
                } else {
                    System.out.print("Erreur lors du choix de l'identite veuillez recommencer ");
                }
                this.premierAppel = false;
            } while (id == Identite.Default);
        }else{
            System.out.println(this.nomJoueur + " a deja choisit son identite");
        }
    }

    public void jouer(){
        System.out.println("C'est " + this.getNomJoueur() );
    }

    public void accuser ( Joueur joueur){
        System.out.println(this.nomJoueur + " Accuse " + joueur.nomJoueur);
        etreAccuse(joueur);
    }

    public void etreAccuse (Joueur joueur){
        if (idRevele = false) {
            System.out.println(joueur.nomJoueur + " Voulez vous reveler votre identité ? (oui ou non) ");
            String reponse = read();
            if (reponse.equalsIgnoreCase("oui")) {
                joueur.revelerIdentite();
                if (joueur.id == Identite.Sorciere) {
                    this.score += 1;
                    joueur.idRevele = true;
                } else if (joueur.id == Identite.Villageois) {
                    // Le joueur doit jouer au tour suivant
                }
            } else if (reponse.equalsIgnoreCase("non")) {
                // jouer carte rumeur avec effet witch ?
            }
        }else{
            System.out.println(" Vous ne pouvez pas accuser quelqu'un dont l'identité est deja connue !");
        }
    }

    private void revelerIdentite (){
        if (this.id == Identite.Sorciere) {
            System.out.println(this.nomJoueur + " est une " + id);
        }else{
            System.out.println(this.nomJoueur + " est un " + id);
        }
    }

    public static void main (String[] args){

    }
}

