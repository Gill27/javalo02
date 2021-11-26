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
    private LinkedList<Carte> carteRevelé = new LinkedList<>();
    private Joueur joueurInterdit;
    private static boolean  mustAccuse = false;
    public int iAccusateur;

    public String toString() {
        return "Joueur : " + nomJoueur + " mainJoueur = " + mainJoueur;
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

    public void rejouer(){

    }
    public void reveal()
    {
        if (this.idRevele == true && this.id == Identite.Villageois) {
            Scanner sc = new Scanner(System.in);
            System.out.println(this.getNomJoueur() + " a activé l'effet reveal de la carte");
            System.out.println(this.getNomJoueur() + ", choisissez le joueur que vous vouler réveler");
            System.out.println("Tapez le numéro correspondant au joueur : ");

            int idjr = choisirjoueur();
            int flag = 0;
            do {
                if (!Partie.getleGroupeJoueur().get(idjr).carteRevelé.contains(Carte.Set.get(5))) break;
                System.out.println("Vous ne pouvez pas choisir ce joueur puisqu'il a révélé Ducking Stool");

                idjr = choisirjoueur();



            } while (true);
            if (Partie.getleGroupeJoueur().get(idjr).id == Identite.Sorciere) {
                System.out.println("Bravo " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " est une sorcière");
                System.out.println("Vous gagnez 2 points");
                Partie.eliminerJoueur(Partie.getleGroupeJoueur().get(idjr));

                Partie.addPoint(this, 2);
                this.jouer();
            } else {
                System.out.println("Et non... " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " était un villageois");
                System.out.println("Vous perdez 2 points");
                Partie.subPoint(this, 2);
                Partie.getleGroupeJoueur().get(idjr).idRevele = true;
                Partie.getleGroupeJoueur().get(idjr).jouer();

            }
        }

        System.out.println("Vous ne pouvez pas jouer cette carte");
        this.mainJoueur.add(this.carteRevelé.getLast());
        this.effethunt(choisircarte().effeth, this);

    }

    private int choisirjoueur() {
        Scanner sc = new Scanner(System.in);
        int idjr;
        List noj = new LinkedList();

        for (Joueur j : Partie.getleGroupeJoueur()) {
            if (j != this) {
                System.out.print(j.getNomJoueur() + " : " + Partie.getleGroupeJoueur().indexOf(j) + "  |  ");
                noj.add(Partie.getleGroupeJoueur().indexOf(j));
            }
        }
        System.out.println();

        do {
            idjr = sc.nextInt();
            sc.nextLine();
            if (noj.contains(idjr)) break;
            System.out.println("Numéro incorrect, réessayez : ");
        } while ( 1==1);

        return idjr;
    }

    public void sreveal() {
        if(this.id == Identite.Sorciere)
        {
            System.out.println(this.getNomJoueur()+" était une sorcière !");
            int indexjp, indexj = Partie.getleGroupeJoueur().indexOf(this);

            if (indexj ==0 )  indexjp = Partie.getleGroupeJoueur().size()-1;
            else indexjp = indexj-1;

            Partie.partie.eliminerJoueur(this);
            System.out.println(" Le joueur suivant est : " + Partie.getleGroupeJoueur().get(indexjp).getNomJoueur());
            Partie.getleGroupeJoueur().get(indexjp).jouer();
        }
        else if (this.id == Identite.Villageois)
        {
            System.out.println(this.getNomJoueur()+" est un villageois, c'est à son tours !");
            this.idRevele = true;
            this.jouer();
        }
    }
    public void qwitch() {

        Scanner sc = new Scanner(System.in);
        System.out.println(this.getNomJoueur() + " a activé l'effet de la carte");
        System.out.println(this.getNomJoueur() + ", choisissez le joueur que vous vouler réveler ou faire défausser une carte");
        System.out.println("Tapez le numéro correspondant au joueur : ");
        int idjr = choisirjoueur();
        int flag = 0;
        do {
            if (!Partie.getleGroupeJoueur().get(idjr).carteRevelé.contains(Carte.Set.get(6))) break;
            System.out.println("Vous ne pouvez pas choisir ce joueur puisqu'il a révélé Ducking Stool");

            idjr = choisirjoueur();
        } while (true);

        if (Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty() == false) {
            System.out.println(Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " Voulez vous révéler votre identité ?");
            String stdr = sc.nextLine();
            if (stdr.equalsIgnoreCase("non") || stdr.equalsIgnoreCase("n"))
            {

                System.out.println(Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + ", vous devez maintenant défausser une carte : ");
                Carte carterem = Partie.getleGroupeJoueur().get(idjr).choisircarte();
                Carte.Pioche.add(carterem);
                Partie.getleGroupeJoueur().get(idjr).mainJoueur.remove(carterem);
                Partie.getleGroupeJoueur().get(idjr).jouer();


            }

        }
        System.out.println("Vous avez décider de révélé votre identité : ");
        if (Partie.getleGroupeJoueur().get(idjr).id == Identite.Sorciere) {
            System.out.println("Bravo " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " est une sorcière");
            System.out.println(this.getNomJoueur() + ", vous gagnez 1 points");
            Partie.eliminerJoueur(Partie.getleGroupeJoueur().get(idjr));
            Partie.addPoint(this, 1);
            this.jouer();
        } else {
            System.out.println("Et non... " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " était un villageois");
            System.out.println(this.getNomJoueur() + ", vous perdez 1 points");
            Partie.getleGroupeJoueur().get(idjr).idRevele = true;

            Partie.subPoint(this, 1);
            Partie.getleGroupeJoueur().get(idjr).jouer();

        }
    }
    public void takecardrfp(){
        Scanner sc = new Scanner(System.in);
        System.out.println(this.getNomJoueur() + " a activé l'effet de la carte");
        System.out.println(this.getNomJoueur() + ", choisissez le joueur où vous allez lui prendre une carte");
        System.out.println("Tapez le numéro correspondant au joueur : ");

        int idjr =choisirjoueur();
        if ( Partie.getleGroupeJoueur().get(idjr).carteRevelé.isEmpty() == false) {


            Carte carterem = Partie.getleGroupeJoueur().get(idjr).carteRevelé.get((int)Math.floor(Math.random() * (Partie.getleGroupeJoueur().get(idjr).carteRevelé.size()-1 - 0 +1)));

            this.mainJoueur.add(carterem);
            Partie.getleGroupeJoueur().get(idjr).carteRevelé.remove(carterem);


        }
        else System.out.println("Le joueur n'a pas de carte ! ");
    }

    public void takecardfp ()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(this.getNomJoueur() + " a activé l'effet de la carte");
        System.out.println(this.getNomJoueur() + ", choisissez le joueur où vous allez lui prendre une carte");
        System.out.println("Tapez le numéro correspondant au joueur : ");

        int idjr = choisirjoueur();
        System.out.println(Partie.getleGroupeJoueur().get(idjr).mainJoueur);
        System.out.println(Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty());
        if ( Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty() != true) {
            System.out.println("Passé");

            Carte carterem = Partie.getleGroupeJoueur().get(idjr).mainJoueur.get((int)Math.floor(Math.random() * (Partie.getleGroupeJoueur().get(idjr).mainJoueur.size()-1 - 0 +1)));

            this.mainJoueur.add(carterem);
            Partie.getleGroupeJoueur().get(idjr).mainJoueur.remove(carterem);
            System.out.println("Vous avez récupéré la carte" + carterem.nom);
            this.choosenextp();

        }
        System.out.println("Pas passé");
    }


    public void takedcard(){

        if (Carte.Pioche.isEmpty())
        {
            System.out.println("Il n'y a pas de carte dans la pioche.");
            System.out.println("Vous ne pouvez donc pas jouer cette carte");
            this.mainJoueur.add(this.carteRevelé.getLast());
            this.effethunt(choisircarte().effeth, this);
        }
        this.mainJoueur.add(Carte.Pioche.get(Carte.Pioche.size()-1));
        Carte.Pioche.remove(Carte.Pioche.size()-1);

        System.out.println("Vous devez maintenant défausser une carte : ");
        Carte carterem = choisircarte();
        Carte.Pioche.add(carterem);
        this.mainJoueur.remove(carterem);

    }
    public void lookid(){
        if (this.idRevele = true && this.id == Identite.Villageois) {
            Scanner sc = new Scanner(System.in);
            System.out.println(this.getNomJoueur() + " a activé l'effet reveal de la carte");
            System.out.println(this.getNomJoueur() + ", choisissez le joueur que vous vouler inspecter");
            System.out.println("Tapez le numéro correspondant au joueur : ");

            int idjr = choisirjoueur();

            System.out.println(Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " est un " + Partie.getleGroupeJoueur().get(idjr).id);
        }
        else {
            System.out.println("Vous ne pouvez pas jouer cette carte");

            this.mainJoueur.add(this.carteRevelé.getLast());
            this.effethunt(choisircarte().effeth, this);
        }
    }
    public void getbackcard ( String situation){

        System.out.println(this.carteRevelé.size());
        if (this.carteRevelé.size() < 2)
        {

            System.out.println("Vous n'avez pas de carte révélé");
            this.mainJoueur.add(this.carteRevelé.getLast());
            if (situation=="hunt") this.effethunt(choisircarte().effeth, this);
            if (situation=="witch") this.effetwitch(choisircarte().effetw, this);
        }
        else {
            this.mainJoueur.add(this.carteRevelé.get(this.carteRevelé.size()-2));

        }

    }

    public void takeOneCard(){
        Joueur joueurAccusateur =Partie.getleGroupeJoueur().get(this.iAccusateur);
        int ncarte;
        boolean reponseValide = false ;
        Scanner sc = new Scanner(System.in);
        System.out.println("Vous pouvez prendre l'une des cartes de " + joueurAccusateur.nomJoueur + " car il vous a accusé");
        System.out.println("Ecrivez le numéro correspondant à la carte");
        for (int j =0; j <joueurAccusateur.mainJoueur.size(); j++){
            System.out.printf("%n" + j + " : " + joueurAccusateur.mainJoueur.get(j));
        }
        System.out.println();
        do {
            ncarte = sc.nextInt();
            sc.nextLine();
            if (ncarte < joueurAccusateur.mainJoueur.size() && ncarte >= 0) {
                reponseValide = true;
            }
            else {
                System.out.printf(joueurAccusateur.nomJoueur + " ne dispose pas de cette carte dans sa main %n Veuillez recommencer %n");
            }
        }while(reponseValide == false);
        this.mainJoueur.add(joueurAccusateur.mainJoueur.get(ncarte));
        joueurAccusateur.mainJoueur.remove(ncarte);
        System.out.println(joueurAccusateur.nomJoueur + " Voici votre nouvelle main de carte : " + joueurAccusateur.mainJoueur);
        System.out.println(this.nomJoueur + " Voici votre nouvelle main de carte : " + this.mainJoueur);
    }

    public void choosenextp(){
        Scanner sc = new Scanner(System.in);
        System.out.println(this.getNomJoueur() + " a activé l'effet choosenextp de la carte");
        System.out.println(this.getNomJoueur() + ", choisissez le joueur qui joueras au tour suivant");
        System.out.println("Tapez le numéro correspondant au joueur : ");

        int idjr = choisirjoueur();

        Partie.getleGroupeJoueur().get(idjr).jouer();


    }
    public void diacardRandomCard(){
        boolean reponseValide = false;
        System.out.println(this.getNomJoueur() + " Une carte aléatoire de ta main va être défaussé !");
        System.out.println(this.nomJoueur + " Voici ta main actuelle " + this.mainJoueur);
        if (this.mainJoueur.size() >= 0) {
            Collections.shuffle(this.mainJoueur); // mélange les cartes
            this.mainJoueur.remove(this.mainJoueur.get(0));  // supprime la premiere carte
            System.out.println(this.nomJoueur + " Voici ta nouvelle main apres défausse " + this.mainJoueur);
        }else{
            System.out.println("Vous n'avez plus de carte rumeur dans votre main'");
        }
    }
    public void cantaccy (){
        Partie.getleGroupeJoueur().get(this.iAccusateur).joueurInterdit = this;
        mustAccuse = true;
    }
    private void MustAccuse (ArrayList<Joueur> groupeJoueur)
    {// idée surcharger la methode jouer ?
        int reponse;
        Scanner sc = new Scanner(System.in);
        mustAccuse = false;
        System.out.println("C'est " + this.getNomJoueur() + " qui joue");
        do {
            do {
                System.out.println("Choisir le joueur que vous voulez accuser mais ca ne doit pas être " + joueurInterdit.nomJoueur);
                for (Joueur j : groupeJoueur) {
                    System.out.print(j.getNomJoueur() + " : " + groupeJoueur.indexOf(j) + "  |  ");
                }
                System.out.println();
                reponse = sc.nextInt();
                sc.nextLine();
            }while((reponse <0 || reponse>=groupeJoueur.size()) || (groupeJoueur.get(reponse).nomJoueur).equalsIgnoreCase(joueurInterdit.nomJoueur)  || (groupeJoueur.get(reponse).nomJoueur).equalsIgnoreCase(this.nomJoueur));
            if ( groupeJoueur.get(reponse).nomJoueur.equalsIgnoreCase(this.nomJoueur)) {
                System.out.println("Vous ne pouvez pas vous accuser vous même ! Veuillez choisir un autre joueur");
            }else{
                this.accuser(groupeJoueur.get(reponse));
            }
        }while(groupeJoueur.get(reponse).nomJoueur.equalsIgnoreCase(this.nomJoueur));

    }
    public void jouer() { /*      this = joueur Accusateur      */
        String reponse1;
        int reponse2;
        Joueur joueurAccuse;
        ArrayList<Joueur> groupeJoueur;
        boolean reponseValide = false;
        int ncarte;

        groupeJoueur = Partie.getleGroupeJoueur();
        System.out.println("C'est " + this.getNomJoueur() + " qui joue");
        System.out.println(this.getNomJoueur() + " voulez vous accuser un autre joueur d'être une sorcière ? (oui ou non)");
        Scanner sc = new Scanner(System.in);
        reponse1 = sc.nextLine();

        if (reponse1.equalsIgnoreCase("oui" ) || this.mainJoueur.isEmpty()) {
            reponse2 = choisirjoueur();
            joueurAccuse = groupeJoueur.get(reponse2);
            do{


                if ( this.joueurInterdit != joueurAccuse) this.accuser(joueurAccuse);
                System.out.println("Vous ne pouvez pas accuser ce joueur !");
                reponse2 = choisirjoueur();
                joueurAccuse = groupeJoueur.get(reponse2);
            }
            while (true);

        } else if (reponse1.equalsIgnoreCase("non")) {

            System.out.println("Vous avez décidé d'utiliser une carte rumeur : ");

            this.effethunt(choisircarte().effeth, this);

        }
    }
    public void discardCard (){
        Scanner sc = new Scanner(System.in);
        int carteDefausse;
        boolean reponseValide = false;
        if (this.mainJoueur.size()>0) {
            System.out.println(this.getNomJoueur() + " tu dois te défausser d'une carte");
            System.out.println("Choisir une carte dans votre main " + this.mainJoueur);
            for (int j = 0; j < this.mainJoueur.size(); j++) {
                System.out.printf("%n" + j + " pour " + this.mainJoueur.get(j));
            }
            System.out.println();
            do {
                carteDefausse = sc.nextInt();
                sc.nextLine();
                if (carteDefausse <= this.mainJoueur.size() && carteDefausse >= 0) {
                    reponseValide = true;
                    this.mainJoueur.remove(carteDefausse);
                    System.out.println(this.nomJoueur + " Voici ta main " + this.mainJoueur);
                }
                if (reponseValide == false) {
                    System.out.println("Vous ne disposez pas de cette carte dans votre main ");
                    System.out.println("Veuillez recommencer");
                }
            } while (reponseValide == false);
        }else {
            System.out.println("tu n'as aucunne carte a défausse");
        }
    }
    public void reprendreCarte () {
        boolean reponseValide = false;
        int ncarte;
        Scanner sc = new Scanner(System.in);
        if (this.carteRevelé.size() > 0 ){
            System.out.println("Vous devez récuperer un de vos cartes déjà révélées. Voici vos cartes révéles :");
            for (int j =0; j <this.carteRevelé.size(); j++){
                System.out.printf("%n" + j + " : " + this.carteRevelé.get(j));
            }
            System.out.println();
            System.out.println("Entrer le numéro correspondant a la carte que vous souhaité récupéré");
            do {
                System.out.println();
                ncarte = sc.nextInt();
                sc.nextLine();
                if (ncarte < this.carteRevelé.size() && ncarte >= 0) {
                    reponseValide = true;
                }else {
                    System.out.printf("Vous ne disposez pas de cette carte dans vos cartes déja révéles. Veuillez recommencer %n");
                }
            }while(reponseValide == false);
            this.mainJoueur.add(carteRevelé.get(ncarte));
        }else{
            System.out.println ("Vous n'avez pas encore de carte révélées");
        }

    }
    public Carte choisircarte()
    {

        Scanner sc = new Scanner(System.in);
        System.out.println("Choisir une carte dans votre main : " + this.mainJoueur);
        boolean reponseValide = false ;
        int ncarte;

        for (int j =0; j <this.mainJoueur.size()
                ; j++){

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

        this.carteRevelé.add(this.mainJoueur.get(ncarte));

        this.mainJoueur.remove(ncarte);


        Carte cartetest = this.carteRevelé.getLast();
        System.out.print(cartetest);

        return cartetest;

    }


    public void accuser ( Joueur joueurAccuse){
        System.out.println(this.nomJoueur + " Accuse " + joueurAccuse.nomJoueur + " d'être une sorciere");

        Scanner sc = new Scanner(System.in);
        String reponseNomCarte;
        String [] tab;
        tab = new String[joueurAccuse.mainJoueur.size()];
        boolean reponseValide = false;
        int ncarte;
        Carte tempon;
        boolean premierAppel = true;
        boolean PremierAppelEtreAccuse = true;

        joueurAccuse.iAccusateur = Partie.getleGroupeJoueur().indexOf(this);

        if (joueurAccuse.idRevele == false) {
            System.out.println(joueurAccuse.nomJoueur + " Voulez vous reveler votre identité ? (oui ou non) ");
            String reponse = read();
            if (reponse.equalsIgnoreCase("oui") || joueurAccuse.mainJoueur.size() == 0) {
                System.out.println("passe oui");
                if(joueurAccuse.mainJoueur.size() == 0){
                    System.out.println("Vous n'avez plus de carte rumeur dans votre main vous devez revelez votre identité !");
                }
                System.out.println("passe accuse");
                joueurAccuse.idRevele = true;

                if (joueurAccuse.id == Identite.Sorciere) {
                    System.out.println("passe accuseaddpt");
                    Partie.addPoint(this, 1);
                    System.out.println(joueurAccuse.nomJoueur + " est une " + id);
                    System.out.println(this.nomJoueur + " Vous avez découvert une sorciere, vous gagnez un point");
                    Partie.eliminerJoueur(joueurAccuse);
                    this.jouer();
                } else if (joueurAccuse.id == Identite.Villageois) {
                    System.out.println(joueurAccuse.nomJoueur + " est un " + id);
                    joueurAccuse.jouer();
                }
            } else if (reponse.equalsIgnoreCase("non")) {
                System.out.println("Vous avez choisi de ne pas réveler votre identité ");
                System.out.println("vous devez a présent résoudre l'effet witch d'une de vos carte");
                System.out.println(joueurAccuse.nomJoueur + " voici votre main " + joueurAccuse.mainJoueur + " veuillez choisir une carte");



                joueurAccuse.effetwitch( joueurAccuse.choisircarte().effetw,joueurAccuse);
            }
        }else{
            System.out.println("Vous ne pouvez pas accuser quelqu'un dont l'identité est deja connue !");
        }
    }

}

