package company;

import java.util.*;

public class Partie implements EffetHunter {

    public int nombreJrs;
    public static ArrayList<Joueur> groupeJoueur = new ArrayList<Joueur>();
    private int [] score;
    private int numeroManche;

    public static Partie partie = null;  // variable nécessaire au mecanisme du singleton
    private static boolean[] verif;

    public static Partie getInstance(){
        if(partie == null){
            partie = new Partie();
        }
        return partie;
    }

    private Partie (){
        this.nombreJrs=0;
        this.numeroManche =0;
        score = new int [nombreJrs]; // réservation en mémoire d'un tableau de nombre de joueurs
        for (int i = 0 ; i < nombreJrs ; i ++){
            score[i] = 0; // initialisation du score de tous le jouers de la partie a 0
        }
    }
    public Joueur getPremierJoueur(){

        Scanner sc = new Scanner(System.in);

            System.out.println(" Quel est le plus jeune joueur ?  Tapez le numéro correspondant au joueur : ");

            for (Joueur j : groupeJoueur) {
                System.out.print(j.getNomJoueur() + " : " + groupeJoueur.indexOf(j)+ "  |  ");
            }
            System.out.println();

            int idjj = sc.nextInt();
            sc.nextLine();
            System.out.println("Joueur : " + groupeJoueur.get(idjj).getNomJoueur());
           return groupeJoueur.get(idjj);
        }

    public static void joueurSuivant (Joueur joueur){
        joueur.jouer();
    }


    public void distribuerMain(){
        Carte.InitCartes();
        Collections.shuffle(Carte.Pioche);

        Iterator<Carte> it1 = Carte.Pioche.iterator();

       while( Carte.Pioche.size()>= nombreJrs) {



            for(int i = 0 ; i<nombreJrs; i++) {

                groupeJoueur.get(i).mainJoueur.add(it1.next());
                it1.remove();
            }
        }
        System.out.println("Pioche : " + Carte.Pioche);


    }
    public void creerJoueurs (){
        String name;

        Scanner sc = new Scanner(System.in);
        System.out.print(" Entrer le nombre de joueur dans la partie");
        System.out.println(" (nombre compris entre 3 et 6) ");
        nombreJrs = sc.nextInt();
        sc.nextLine(); // voir https://developpement-informatique.com/article/267/lire-les-entrees-clavier-en-java pour recomprendre
        do {
            if (nombreJrs < 3 || nombreJrs > 6){
                System.out.println(" Il y a trop ou pas assez de joueur dans la partie veuillez recommencer !");
                nombreJrs = sc.nextInt();
                sc.nextLine();
            }

            if (nombreJrs >= 3 && nombreJrs <= 6)
            {
                for (int i = 0; i < nombreJrs; i++) {
                    System.out.println(" Entrer le nom du joueur " + i);
                    name = sc.nextLine();

                    groupeJoueur.add(new Joueur(name));
                    System.out.println(" Le nom du jouer" + i + " est " + name);
                }
            }
        }while (nombreJrs < 3 || nombreJrs > 6);

    }

    public void eliminerJoueur (int jrASupp){

        groupeJoueur.remove(jrASupp);
    }

    public static void main ( String []args){

        Partie partie = Partie.getInstance();

        partie.creerJoueurs();

        partie.distribuerMain();
        for (Joueur i : groupeJoueur)
        {
            System.out.println(i);

        }
        for (Joueur j : groupeJoueur)
            j.choisirIdentite();

      //  partie.eliminerJoueur(groupeJoueur, 0);

        partie.getPremierJoueur().jouer();
        //System.out.println(groupeJoueur.get(1).mainJoueur.get(1));
        //partie.effethunt(groupeJoueur.get(1).mainJoueur.get(1).effeth);
        partie.effethunt(Carte.Set.get(11).effeth);
    }


}
