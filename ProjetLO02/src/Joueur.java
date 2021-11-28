import java.util.*;


public class Joueur implements EffetHunter,EffetWitch,Input {

    private final String nomJoueur;




    public enum Identite {Sorciere,Villageois,Default} // enumeration du type Identite
    private Identite id ; // id de type Identite
    private boolean idRevele;
    private boolean premierAppel = true;
    public ArrayList<Carte> mainJoueur = new ArrayList<>();
    private final LinkedList<Carte> carteRevele = new LinkedList<>();
    private Joueur joueurInterdit;
    public int iAccusateur;

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
            this.effethunt(choisircarte().effeth, this);
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

                joueurAccuse.effetwitch( joueurAccuse.choisircarte().effetw,joueurAccuse);
            }

    }

    private int choisirjoueur() {
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

    //////////////Méthode de Hunter //////////////

    public void reveal() {
        if (this.idRevele && this.id == Identite.Villageois) {

            System.out.println(this.getNomJoueur() + " a activé l'effet reveal de la carte");
            System.out.print(this.getNomJoueur() + ", choisissez le joueur que vous vouler réveler.");
            System.out.println(" Tapez le numéro correspondant au joueur : ");

            int idjr = choisirjoueur();
            while (Partie.getleGroupeJoueur().get(idjr).carteRevele.contains(Carte.Set.get(5))) {
                System.out.println("Vous ne pouvez pas choisir ce joueur puisqu'il a révélé Ducking Stool");

                idjr = choisirjoueur();

            }
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
        this.mainJoueur.add(this.carteRevele.getLast());
        this.effethunt(choisircarte().effeth, this);

    }

    public void sreveal() {
        if(this.id == Identite.Sorciere)
        {
            System.out.println(this.getNomJoueur()+" était une sorcière !");
            int indexjp, indexj = Partie.getleGroupeJoueur().indexOf(this);

            if (indexj ==0 )  indexjp = Partie.getleGroupeJoueur().size()-1;
            else indexjp = indexj-1;

            Partie.eliminerJoueur(this);
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


        System.out.println(this.getNomJoueur() + " a activé l'effet de la carte");
        System.out.print(this.getNomJoueur() + ", choisissez le joueur que vous vouler réveler ou faire défausser une carte.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");
        int idjr = choisirjoueur();
        while (Partie.getleGroupeJoueur().get(idjr).carteRevele.contains(Carte.Set.get(6))) {
            System.out.println("Vous ne pouvez pas choisir ce joueur puisqu'il a révélé Ducking Stool");

            idjr = choisirjoueur();
        }

        if (!Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty()) {
            System.out.println(Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " Voulez vous révéler votre identité ?");
            String stdr = Input.inputString();
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

        System.out.println(this.getNomJoueur() + " a activé l'effet de la carte");
        System.out.print(this.getNomJoueur() + ", choisissez le joueur où vous allez lui prendre une carte.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");

        int idjr =choisirjoueur();
        if (!Partie.getleGroupeJoueur().get(idjr).carteRevele.isEmpty()) {


            Carte carterem = Partie.getleGroupeJoueur().get(idjr).carteRevele.get((int)Math.floor(Math.random() * (Partie.getleGroupeJoueur().get(idjr).carteRevele.size() - 1 +1)));

            this.mainJoueur.add(carterem);
            Partie.getleGroupeJoueur().get(idjr).carteRevele.remove(carterem);


        }
        else System.out.println("Le joueur n'a pas de carte ! ");
    }

    public void takecardfp () {

        System.out.println(this.getNomJoueur() + " a activé l'effet de la carte");
        System.out.print(this.getNomJoueur() + ", choisissez le joueur où vous allez lui prendre une carte.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");

        int idjr = choisirjoueur();
        System.out.println(Partie.getleGroupeJoueur().get(idjr).mainJoueur);
        System.out.println(Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty());
        if (!Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty()) {
            System.out.println("Passé");

                Carte carterem = Partie.getleGroupeJoueur().get(idjr).mainJoueur.get((int)Math.floor(Math.random() * (Partie.getleGroupeJoueur().get(idjr).mainJoueur.size() - 1 +1)));

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
            this.mainJoueur.add(this.carteRevele.getLast());
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
        if (this.idRevele && this.id == Identite.Villageois) {

            System.out.println(this.getNomJoueur() + " a activé l'effet reveal de la carte");
            System.out.print(this.getNomJoueur() + ", choisissez le joueur que vous vouler inspecter.");
            System.out.println(" Tapez le numéro correspondant au joueur : ");

            int idjr = choisirjoueur();

            System.out.println(Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " est un " + Partie.getleGroupeJoueur().get(idjr).id);
        }
        else {
            System.out.println("Vous ne pouvez pas jouer cette carte");

            this.mainJoueur.add(this.carteRevele.getLast());
            this.effethunt(choisircarte().effeth, this);
        }
    }

    public void getbackcard ( String situation){

        System.out.println(this.carteRevele.size());
        if (this.carteRevele.size() < 2)
        {

            System.out.println("Vous n'avez pas de carte révélé");
            this.mainJoueur.add(this.carteRevele.getLast());
            if (Objects.equals(situation, "hunt")) this.effethunt(choisircarte().effeth, this);
            if (Objects.equals(situation, "witch")) this.effetwitch(choisircarte().effetw, this);
        }
        else {
             this.mainJoueur.add(this.carteRevele.get(this.carteRevele.size()-2));

        }

    }

    //////////////Méthode de Witch //////////////

    public void takeOneCard(){
        Joueur joueurAccusateur =Partie.getleGroupeJoueur().get(this.iAccusateur);
        int ncarte;
        boolean reponseValide = false ;
        System.out.println(this.getNomJoueur() + " Vous pouvez prendre l'une des cartes de " + joueurAccusateur.nomJoueur + " car il vous a accusé");
        System.out.println("Ecrivez le numéro correspondant à la carte");
        for (int j =0; j <joueurAccusateur.mainJoueur.size(); j++){
            System.out.printf("%n" + j + " : " + joueurAccusateur.mainJoueur.get(j));
        }
        System.out.println();
        do {
            ncarte = Input.inputInt();
            if (ncarte < joueurAccusateur.mainJoueur.size() && ncarte >= 0) {
                reponseValide = true;
            }
            else {
                System.out.printf(joueurAccusateur.nomJoueur + " ne dispose pas de cette carte dans sa main %n Veuillez recommencer %n");
            }
        }while(!reponseValide);
        this.mainJoueur.add(joueurAccusateur.mainJoueur.get(ncarte));
        joueurAccusateur.mainJoueur.remove(ncarte);
        System.out.println(joueurAccusateur.nomJoueur + " Voici votre nouvelle main de carte : " + joueurAccusateur.mainJoueur);
        System.out.println(this.nomJoueur + " Voici votre nouvelle main de carte : " + this.mainJoueur);
    }

    public void choosenextp(){
        System.out.println(this.getNomJoueur() + " a activé l'effet choosenextp de la carte");
        System.out.print(this.getNomJoueur() + ", choisissez le joueur qui joueras au tour suivant.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");

        int idjr = choisirjoueur();
        Partie.getleGroupeJoueur().get(idjr).jouer();
    }

    public void diacardRandomCard(){
        System.out.println(this.getNomJoueur() + " Une carte aléatoire de ta main va être défaussé !");
        System.out.println(this.nomJoueur + " Voici ta main actuelle " + this.mainJoueur);
        Collections.shuffle(this.mainJoueur); // mélange les cartes
        this.mainJoueur.remove(this.mainJoueur.get(0));  // supprime la premiere carte
        System.out.println(this.nomJoueur + " Voici ta nouvelle main apres défausse " + this.mainJoueur);
    }

    public void cantaccy (){
        Partie.getleGroupeJoueur().get(this.iAccusateur).joueurInterdit = this;
    }

    public void discardCard (){

        int carteDefausse;
        boolean reponseValide = false;
        if (this.mainJoueur.size()>0) {
            System.out.println(this.getNomJoueur() + " tu dois te défausser d'une carte");
            System.out.println("Choisir une carte dans votre main " + this.mainJoueur);
            for (int j = 0; j < this.mainJoueur.size(); j++) {
                System.out.printf("%n" + j + " : " + this.mainJoueur.get(j));
            }
            System.out.println();
            do {
                carteDefausse = Input.inputInt();
                if (carteDefausse <= this.mainJoueur.size() && carteDefausse >= 0) {
                    reponseValide = true;
                    this.mainJoueur.remove(carteDefausse);
                    System.out.println(this.nomJoueur + " Voici ta main " + this.mainJoueur);
                }
                if (!reponseValide) {
                    System.out.println("Vous ne disposez pas de cette carte dans votre main ");
                    System.out.println("Veuillez recommencer");
                }
            } while (!reponseValide);
        }else {
            System.out.println("tu n'as aucunne carte a défausse");
        }
    }


}

