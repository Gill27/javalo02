import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bot extends Joueur implements StrategyJouer {
    protected StrategyJouer strategy;
    public Bot(String nomJoueur) {
        super(nomJoueur);
        this.strategy =  new Fort();

    }
    public void test()
    {
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

        System.out.println("test");
        strategy.jouer(this);
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

    public int choisirjoueur() {
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
            Random random = new Random();
            identJoueur = random.nextInt(Partie.getleGroupeJoueur().size()-1);;
            if (noj.contains(identJoueur)) {
                break;
            }
            System.out.println("Numéro incorrect, réessayez : ");
        }

        return identJoueur;
    }

    public Carte choisircarte() {
        Random random = new Random();
        int numeroCarte;
        boolean reponseValide = false ;

        System.out.println(this.getNomJoueur() + " choisisez une carte dans votre main : ");

        for (int j =0; j <this.mainJoueur.size();j++){
            System.out.printf("%n" + j + " : " + this.mainJoueur.get(j));
        }
        System.out.println();
        do {
            numeroCarte = random.nextInt(this.mainJoueur.size());
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

    @Override
    public void jouer(Bot cbot) {

    }
}
