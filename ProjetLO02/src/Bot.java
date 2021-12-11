import java.util.*;

public class Bot extends Joueur/* implements StrategyJouer*/{
    protected StrategyJouer strategy;

    public Bot(String nomJoueur) {
        super(nomJoueur);
       this.strategy =  new Fort();

    }



    public void jouer() {
        strategy.jouer(this); // this = jouerAccusateur
    }

    public void etreaccuse(Joueur joueurAccusateur){
        strategy.etreaccuse(joueurAccusateur,this); // this = joueurAccusateur
    }


    public void choisirIdentite(){
        if ( this.premierAppel) {
            System.out.print(this.nomJoueur + " entrer votre identite ");
            do {
                System.out.println("(Sorciere ou Villageois)");
                String identite = chooseRandomChoiceSV();
                System.out.println(identite);
                if (identite.equalsIgnoreCase("Sorciere")) {
                    id = Identite.Sorciere;
                } else if (identite.equalsIgnoreCase("Villageois")) {
                    id = Identite.Villageois;
                }
                this.premierAppel = false;
            } while (id == Identite.Default);
        }else{
            System.out.println(this.nomJoueur + " a deja choisit son identite");
        }
    }

    public int choisirjoueur() {
        int identJoueur;
        Random random = new Random();
        List<Integer> noj = new LinkedList<>();

        for (Joueur j : Partie.getleGroupeJoueur()) {
            if (j != this) {
                System.out.print(j.getNomJoueur() + " : " + Partie.getleGroupeJoueur().indexOf(j) + "  |  ");
                noj.add(Partie.getleGroupeJoueur().indexOf(j));
            }
        }
        System.out.println();

        while (true) {
            identJoueur = random.nextInt(Partie.getleGroupeJoueur().size()-1);;
            System.out.println(identJoueur);
            if (noj.contains(identJoueur)) {
                break;
            }
        }
        return identJoueur;
    }

    public Carte choisircarte() {
        Random random = new Random();
        int numeroCarte;
        boolean reponseValide = false ;

        System.out.println(this.getNomJoueur() + " choisis une carte de sa main : ");

        for (int j =0; j <this.mainJoueur.size();j++){
            System.out.printf("%n" + j + " : " + this.mainJoueur.get(j));
        }

        System.out.println();
        do {
            numeroCarte = random.nextInt(this.mainJoueur.size());
            System.out.println(numeroCarte);
            if (numeroCarte <= this.mainJoueur.size() && numeroCarte >= 0) {
                reponseValide = true;
            }
        }while(!reponseValide);

        this.carteRevele.add(this.mainJoueur.get(numeroCarte));

        this.mainJoueur.remove(numeroCarte);

        return this.carteRevele.getLast();
    }

    public String chooseRandomChoiceSV(){
        ArrayList<String> choix = new ArrayList<>();
        choix.add("Sorciere");
        choix.add("Villageois");
        Collections.shuffle(choix);
        return choix.get(0);
    }
    public static String chooseRandomChoiceON(){
        ArrayList<String> choix = new ArrayList<>();
        choix.add("oui");
        choix.add("non");
        Collections.shuffle(choix);
        return choix.get(0);
    }
}
