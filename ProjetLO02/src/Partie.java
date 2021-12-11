import java.awt.desktop.SystemEventListener;
import java.util.*;

public class Partie implements Input  {

    public int nombreJrs;
    public static ArrayList<Joueur> groupeJoueur = new ArrayList<Joueur>();
    public static ArrayList<Joueur> groupeJoueurP = new ArrayList<>();
    private int [] score;
    public static Partie partie = null;  // variable nécessaire au mecanisme du singleton
    private static ArrayList<String> saveNomsJoueursManche = new ArrayList<String>();
    // add 27/11
    private static boolean premierAppelDistMain = true;
    private static boolean premierAppelGet1erJoueur = true;
    private static int premierJoueur;
    private int NbrBot;
    /*----------------------------------------------------------------------------------------------------*/


    private Partie (){
        this.nombreJrs = 0;
        score = new int [nombreJrs]; // réservation en mémoire d'un tableau de nombre de joueurs
    }

    public static Partie getInstance(){
        if(partie == null){
            partie = new Partie();
        }
        return partie;
    }

    public int getNombreJrs() {
        return nombreJrs;
    }

    public static ArrayList<Joueur> getleGroupeJoueur (){
        return groupeJoueur;
    }

    public static void addPoint(Joueur j,int p) {
        partie.score[groupeJoueur.indexOf(j)] += p;
        System.out.println(j.getNomJoueur() +" gagne "+ p + " points" );
    }

    public static void subPoint(Joueur j, int p) {
        if (partie.score[groupeJoueur.indexOf(j)] < p){
            partie.score[groupeJoueur.indexOf(j)] = 0;
        }
        else partie.score[groupeJoueur.indexOf(j)] -= p;
        System.out.println(j.getNomJoueur() +" perd " + p + " points" );
    }

    public Joueur getPremierJoueur(){
        System.out.println("----------------------------------------------------------------------------------------------------");
        if ( premierAppelGet1erJoueur) {
            System.out.println("Quel est le plus jeune joueur ? Taper le numéro correspondant au joueur : ");

            for (Joueur j : groupeJoueur) {
                System.out.print(j.getNomJoueur() + " : " + groupeJoueur.indexOf(j));
                System.out.print(" | ");
            }
            System.out.printf("%n");

            premierJoueur = Input.inputInt(); // premierJoueur est un variable globale
            System.out.println(groupeJoueur.get(premierJoueur).getNomJoueur() + " est le joueur le plus jeune");
            premierAppelGet1erJoueur = false;
        }
        return groupeJoueur.get(premierJoueur);

    }

    public void distribuerMain(){
        Carte.InitCartes();
        Collections.shuffle(Carte.Pioche);
        Iterator<Carte> it1 = Carte.Pioche.iterator();

            while (Carte.Pioche.size() >= nombreJrs) {
                for (int i = 0; i < nombreJrs; i++) {
                    groupeJoueur.get(i).mainJoueur.add(it1.next());
                    it1.remove();
                }
            }

        System.out.println("Pioche : " + Carte.Pioche);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void creerJoueurs (){
        String name;
        System.out.print("Entrer le nombre de joueur dans la partie");
        System.out.println(" (nombre compris entre 1 et 6) ");
        nombreJrs = Input.inputInt();
        do {

            if (nombreJrs < 1 || nombreJrs > 6){
                System.out.println("Il y a trop ou pas assez de joueur dans la partie veuillez recommencer !");
                nombreJrs = Input.inputInt();
            }


            if (nombreJrs >= 3 && nombreJrs <= 6)
            {
                for (int i = 0; i < nombreJrs; i++) {
                    System.out.println("Entrer le nom du joueur " + i);
                    name = Input.inputString();
                    groupeJoueurP.add(new Joueur(name));
                    groupeJoueur.add(new Joueur(name));
                }
                System.out.println("Combien de Bot voulez vous ajouter ? (0 à "+(6-nombreJrs) +" bot(s).");
                NbrBot = Input.inputInt();
                for (int i = 0;i <NbrBot ; i++)
                {
                        name = "Bot"+(i+1);
                        groupeJoueurP.add(new Bot(name));
                        groupeJoueur.add(new Bot(name));
                        nombreJrs +=1;
                }
            }

            if (nombreJrs > 0|| nombreJrs < 3) {
                System.out.println("Vous avez choisi un nombre de joueur inferieur à 3 : ");
                System.out.println( (3-nombreJrs) + " Bot(s) vont être ajouté");

                for (int i = 0; i < nombreJrs; i++) {
                    System.out.println("Entrer le nom du joueur " + i);
                    name = Input.inputString();
                    groupeJoueurP.add(new Joueur(name));
                    groupeJoueur.add(new Joueur(name));
                    System.out.println(groupeJoueur);
                }
                int nbraajouter  = (3-nombreJrs) ;

                for (int i = 0;i <nbraajouter ; i++)
                {
                    name = "Bot"+(i+1);
                    groupeJoueurP.add(new Bot(name));
                    groupeJoueur.add(new Bot(name));
                    nombreJrs +=1;
                }
            }
        }while (nombreJrs < 1 || nombreJrs > 6);

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Affichage des scores : ");
        score = new int[nombreJrs];
        for (int i = 0 ; i < nombreJrs ; i ++){
            score[i] = 0; // initialisation du score de tous le jouers de la partie a 0
            System.out.println(groupeJoueur.get(i).getNomJoueur() + " Score : " + score[i]);
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public static void eliminerJoueur (Joueur jrASupp){
        groupeJoueur.remove(jrASupp);
        System.out.println(jrASupp.getNomJoueur()+" Votre identité est révelé vous etes éliminé de la manche !");
    }

    private static void printScore(){
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("Affichage des scores des joueurs : %n");
        for(Joueur j : groupeJoueur){
            System.out.println(j.getNomJoueur() + " score : " + partie.score[groupeJoueur.indexOf(j)]);
        }
    }

    public static void finPartie (){
        ArrayList<Joueur> gagnants = new ArrayList<>(); // la liste de vainceurs !
        for (Joueur j : groupeJoueur){
            if ( partie.score[groupeJoueur.indexOf(j)] >= 5) {
                gagnants.add(j); // je stock les gagnants dans un tableau car il peut y en avoir plusieurs !
            }
            if (!gagnants.isEmpty()){ // il y a au moins un gagnant
                if(gagnants.size() > 1) {
                    System.out.println("Il y a plusieurs gagnants le vainceur serra choisit aléatoirement :( ");
                    int rdm = (int) (Math.random() * (gagnants.size()));
                    System.out.printf("Le gagnant de la partie est donc " + gagnants.get(rdm).getNomJoueur() +" %n");

                }else {
                    System.out.println(gagnants.get(0).getNomJoueur() + " a plus de 5 points, il a gagné la partie !");
                    /*System.out.println("Voulez voues relancer une partie ? ( oui ou non )");
                    String reponse = Input.inputString();
                    if (reponse.equalsIgnoreCase("oui")) {
                        lancerPartie();
                    }
                    if (reponse.equalsIgnoreCase("non")) {
                        System.out.println("La partie est terminée");
                        System.exit(0);
                    }*/
                    printScore();
                    System.out.println("----------------------------------------------------------------------------------------------------");
                    System.exit(0);
                }

            }
        }
    }

    public static void finRound (ArrayList<Joueur> groupeJoueur){
        boolean fin;
        int nbJIdUnReveal = 0;
        fin = false;
        for(Joueur j : groupeJoueur){
            if(j.isIdRevele() == false){
                nbJIdUnReveal += 1;

            }
        }


        if ( nbJIdUnReveal == 1){
            for(Joueur j : groupeJoueur){
                if(j.isIdRevele() == false){

                    System.out.println("----------------------------------------------------------------------------------------------------");
                    System.out.printf(j.getNomJoueur() + " est le dernier joueur à ne pas être révélé !");
                    System.out.println("Il était " + j.getId()+ " !");
                    if(j.getId() == Joueur.Identite.Sorciere) {

                        Partie.addPoint(j,2);
                    }
                    else Partie.addPoint(j,1);
                    System.out.println(" Fin de la manche");

                }
            }

            System.out.println("----------------------------------------------------------------------------------------------------");
            finPartie();
            nouvelleManche();
        }
    }

    public static void lancerPartie (){
        partie.creerJoueurs();
        nouvelleManche();
    }

    public static void nouvelleManche(){
        Joueur premierJoueur;

        System.out.printf("NOUVELLE MANCHE : **********************************************************************************%n");
        System.out.println("----------------------------------------------------------------------------------------------------");
        groupeJoueur.clear();
        groupeJoueurP.forEach(Joueur::reset);
        groupeJoueur.addAll(groupeJoueurP);
        partie.distribuerMain();

        for (Joueur i : groupeJoueur)
        {
            System.out.println(i);
        }
        partie.printScore();
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (Joueur j : groupeJoueur)
            j.choisirIdentite();

        premierJoueur = partie.getPremierJoueur();
        premierJoueur.jouer();
    }
    public static void main (String[] args){
        Partie partie = Partie.getInstance();
        lancerPartie();

    }
}