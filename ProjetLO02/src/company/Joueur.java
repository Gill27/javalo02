package company;

import java.util.*;


public class Joueur implements EffetHunter,EffetWitch {

    private String nomJoueur;
    private enum Identite {Sorciere,Villageois,Default}; // enumeration du type Identite
    private Identite id ; // id de type Identite
    private boolean idRevele=false;
    private boolean premierAppel = true;
    private static int nombreJoueurs = 0;
    private int score;
    public ArrayList<Carte> mainJoueur = new ArrayList<Carte>();


    public String toString() {
        return "company.Joueur : " + nomJoueur + " mainJoueur = " + mainJoueur;
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
    public boolean getIdRevele() {
        return idRevele;
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
            System.out.println();
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

    public void jouer(){ /*      this = joueur Accusateur      */
        String reponse1;
        int reponse2;
        Joueur joueurAccuse;
        ArrayList<Joueur> groupeJoueur;
        boolean reponseValide = false ;
        int ncarte;

        groupeJoueur = Partie.getleGroupeJoueur();
        System.out.println("C'est " + this.getNomJoueur() + " qui joue");
        System.out.println(this.getNomJoueur() + " voulez vous accuser un autre joueur d'être une sorcière ? (oui ou non)");
        Scanner sc = new Scanner(System.in);
        reponse1 = sc.nextLine();
        if (reponse1.equalsIgnoreCase("oui")){
            do {
                System.out.println("Quel joueur voulez vous accuser ? Tapez le numéro correspondant au joueur :");
                for (Joueur j : groupeJoueur) {
                    System.out.print(j.getNomJoueur() + " : " + groupeJoueur.indexOf(j) + "  |  ");
                }
                reponse2 = sc.nextInt();
                sc.nextLine();
                joueurAccuse = groupeJoueur.get(reponse2);
                if (joueurAccuse.nomJoueur.equalsIgnoreCase(this.nomJoueur)) {
                    System.out.println("Vous ne pouvez pas vous accuser vous même ! Veuillez choisir un autre joueur");
                } else {
                    this.accuser(joueurAccuse);
                }
            }while(joueurAccuse.nomJoueur.equalsIgnoreCase(this.nomJoueur));
        }else if (reponse1.equalsIgnoreCase("non")){

            System.out.println("Vous avez décidé d'utiliser une carte rumeur : ");
            System.out.println("Choisir une carte dans votre main : " + this.mainJoueur);


            for (int j =0; j <this.mainJoueur.size(); j++){
                System.out.printf("%n" + j + " : " + this.mainJoueur.get(j));
            }
            do {
                ncarte = sc.nextInt();
                sc.nextLine();
                if (ncarte <= this.mainJoueur.size() && ncarte >= 0) {
                    reponseValide = true;

                }
                else System.out.printf("Vous ne disposez pas de cette carte dans votre main %n Veuillez recommencer %n");

            }while(reponseValide == false);

            this.effethunt(this.mainJoueur.get(ncarte).effeth);
        }
    }


    public void accuser ( Joueur joueurAccuse){
        System.out.println(this.nomJoueur + " Accuse " + joueurAccuse.nomJoueur + " d'être une sorciere");
        etreAccuse(joueurAccuse);
    }

    public void etreAccuse (Joueur joueurAccuse){
        Scanner sc = new Scanner(System.in);
        String reponseNomCarte;
        String [] tab;
        tab = new String[joueurAccuse.mainJoueur.size()];
        boolean reponseValide = false;
        int ncarte;


        if (joueurAccuse.idRevele == false) {

            System.out.println(joueurAccuse.nomJoueur + " Voulez vous reveler votre identité ? (oui ou non) ");
            String reponse = read();
            if (reponse.equalsIgnoreCase("oui")) {
                joueurAccuse.idRevele = true;
                joueurAccuse.revelerIdentite();
                if (joueurAccuse.id == Identite.Sorciere) {
                    this.score += 1;
                    System.out.println(this.nomJoueur + " Vous avez dévouvert une sorcier vous gagné un point");
                } else if (joueurAccuse.id == Identite.Villageois) {
                    joueurAccuse.jouer();
                }
            } else if (reponse.equalsIgnoreCase("non")) {
                System.out.println("Vous avez choisi de ne pas réveler votre identité ");
                System.out.println("vous devez a présent résoudre l'effet witch d'une de vos carte");
                System.out.println(joueurAccuse.nomJoueur + " voici votre main " + joueurAccuse.mainJoueur + " veuillez choisir une carte");

                for (int j =0; j <this.mainJoueur.size(); j++){
                    System.out.printf("%n" + j + " : " + this.mainJoueur.get(j));
                }
                do {
                    ncarte = sc.nextInt();
                    sc.nextLine();
                    if (ncarte <= this.mainJoueur.size() && ncarte >= 0) {
                        reponseValide = true;

                    }
                    else System.out.printf("Vous ne disposez pas de cette carte dans votre main %n Veuillez recommencer %n");

                }while(reponseValide == false);

                this.effetwitch(this.mainJoueur.get(ncarte).effetw);
            }
        }else{
            System.out.println(" Vous ne pouvez pas accuser quelqu'un dont l'identité est deja connue !");
        }
    }

    private void revelerIdentite (){
        if (this.id == Identite.Sorciere) {
            System.out.println(this.nomJoueur + " est une " + id);
            Partie.eliminerJoueur(this);
        }else{
            System.out.println(this.nomJoueur + " est un " + id);
        }
    }

    private void discardCard (){

    }

    public static void main (String[] args){
        // corriger cas ou joueur accusé est une sorciere et il y a plus personne qui joue !!
    }
}

