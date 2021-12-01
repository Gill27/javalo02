import java.util.*;


public class Joueur  implements Input {

    protected final String nomJoueur;
    public enum Identite {Sorciere,Villageois,Default} // enumeration du type Identite
    protected Identite id ; // id de type Identite
    protected boolean idRevele;
    protected boolean premierAppel = true;
    protected ArrayList<Carte> mainJoueur = new ArrayList<>();
    protected final LinkedList<Carte> carteRevele = new LinkedList<>();
    protected Joueur joueurInterdit;
    protected int iAccusateur;

    public String toString() {
        return "Joueur : " + nomJoueur + " main du joueur : " + mainJoueur;
    }

    //////////////Constructeur//////////////
    public Joueur (String nomJoueur){
        this.nomJoueur = nomJoueur;
        this.idRevele = false;
        this.id = Identite.Default;

    }




    public String getNomJoueur() {
        return nomJoueur;
    }


    public Identite getId() {
        return id;
    }

    public boolean isIdRevele() {
        return idRevele;
    }


    //////////////Méthode de base //////////////

    public void reset(){
        this.id= Identite.Default;
        this.idRevele = false ;
        this.mainJoueur.clear();
        this.carteRevele.clear();
        this.iAccusateur= 0;
    }

    public void choisirIdentite(){
        if ( this.premierAppel) {   // permet de ne pas pouvoir appeler 2 fois la méthode pour un meme joueur
            System.out.print(this.nomJoueur + " entrer votre identite ");
            do {
                System.out.println("(Sorciere ou Villageois)");
                String identite = Input.inputString();
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

    public void jouer() { /*      this = joueur Accusateur      */
        String reponse1;
        int reponse2;
        Joueur joueurAccuse;
        ArrayList<Joueur> groupeJoueur;
        groupeJoueur = Partie.getleGroupeJoueur();
        Partie.finRound(groupeJoueur);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("C'est a " + this.getNomJoueur() + " de jouer");
        System.out.println(this.getNomJoueur() + " voulez vous accuser un autre joueur d'être une sorcière ? (oui ou non)");

        reponse1 = Input.inputString();

        if (reponse1.equalsIgnoreCase("oui" ) || this.mainJoueur.isEmpty()) {
            reponse2 = this.choisirjoueur();
            joueurAccuse = groupeJoueur.get(reponse2);

            while (true) {
                if (this.joueurInterdit != joueurAccuse || !joueurAccuse.idRevele || Partie.getleGroupeJoueur().size() <= 2) { // Attention
                    this.accuser(joueurAccuse);
                    break;
                }
                System.out.println("Vous ne pouvez pas accuser ce joueur !");
                reponse2 = this.choisirjoueur();
                joueurAccuse = groupeJoueur.get(reponse2);
            }

        }else if (reponse1.equalsIgnoreCase("non")) {

            System.out.println("Vous avez décidé d'utiliser une carte Rumeur ");
            Effet.effethunt(choisircarte().effeth, this);
        }
    }

    public void accuser ( Joueur joueurAccuse){
        System.out.println(this.nomJoueur + " Accuse " + joueurAccuse.nomJoueur + " d'être une sorciere");


        joueurAccuse.iAccusateur = Partie.getleGroupeJoueur().indexOf(this);


            System.out.println(joueurAccuse.nomJoueur + " Voulez vous reveler votre identité ? (oui ou non) ");
            String reponse = Input.inputString();
            if (reponse.equalsIgnoreCase("oui") || joueurAccuse.mainJoueur.isEmpty()) {
                if(joueurAccuse.mainJoueur.size() == 0){
                    System.out.println("Vous n'avez plus de carte rumeur dans votre main vous devez revelez votre identité !");
                }
                joueurAccuse.idRevele = true;

                if (joueurAccuse.id == Identite.Sorciere) {
                    System.out.println(joueurAccuse.nomJoueur + " est une " + joueurAccuse.id);
                    Partie.addPoint(this, 1);
                    System.out.println(this.nomJoueur + " Vous avez découvert une sorciere");
                    Partie.eliminerJoueur(joueurAccuse);
                    this.jouer();
                } else if (joueurAccuse.id == Identite.Villageois) {
                    System.out.println(joueurAccuse.nomJoueur + " est un " + joueurAccuse.id);
                    joueurAccuse.jouer();
                }
            } else if (reponse.equalsIgnoreCase("non")) {
                System.out.println("Vous avez choisi de ne pas réveler votre identité ");
                System.out.println("vous devez a présent résoudre l'effet witch d'une de vos carte");

                Effet.effetwitch( joueurAccuse.choisircarte().effetw,joueurAccuse);
            }

    }

    protected int choisirjoueur() {

        int identJoueur;
        List<Integer> noj = new LinkedList<>();

        for (Joueur j : Partie.getleGroupeJoueur()) {
            if (j != this) {
                System.out.print(j.getNomJoueur() + " : " + Partie.getleGroupeJoueur().indexOf(j) + "  |  ");
                noj.add(Partie.getleGroupeJoueur().indexOf(j));
            }
        }
        System.out.println();

        while (true) {
            identJoueur = Input.inputInt();
            if (noj.contains(identJoueur)) {
                break;
            }
            System.out.println("Numéro incorrect, réessayez : ");
        }

        return identJoueur;
    }

    public Carte choisircarte() {
        int numeroCarte;
        boolean reponseValide = false ;

        System.out.println(this.getNomJoueur() + " choisisez une carte dans votre main : ");

        for (int j =0; j <this.mainJoueur.size();j++){
            System.out.printf("%n" + j + " : " + this.mainJoueur.get(j));
        }
        System.out.println();
        do {
            numeroCarte = Input.inputInt();
            if (numeroCarte <= this.mainJoueur.size() && numeroCarte >= 0) {
                reponseValide = true;
            }
            else System.out.printf("Vous ne disposez pas de cette carte dans votre main. Veuillez recommencer %n");
        }while(!reponseValide);

        this.carteRevele.add(this.mainJoueur.get(numeroCarte));

        this.mainJoueur.remove(numeroCarte);


        //System.out.print(cartetest);

        return this.carteRevele.getLast();

    }


}

