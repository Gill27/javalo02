import java.util.Collections;
import java.util.Objects;

public class Effet  {
    


    //////////////Méthode de Hunter //////////////

    public static void reveal(Joueur cjoueur) {
        if (cjoueur.idRevele && cjoueur.id == Joueur.Identite.Villageois) {

            System.out.println(cjoueur.getNomJoueur() + " a activé l'effet reveal de la carte");
            System.out.print(cjoueur.getNomJoueur() + ", choisissez le joueur que vous vouler réveler.");
            System.out.println(" Tapez le numéro correspondant au joueur : ");

            int idjr = cjoueur.choisirjoueur();
            while (Partie.getleGroupeJoueur().get(idjr).carteRevele.contains(Carte.Set.get(5))) {
                System.out.println("Vous ne pouvez pas choisir ce joueur puisqu'il a révélé Ducking Stool");

                idjr = cjoueur.choisirjoueur();

            }
            if (Partie.getleGroupeJoueur().get(idjr).id == Joueur.Identite.Sorciere) {
                System.out.println("Bravo " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " est une sorcière");
                System.out.println("Vous gagnez 2 points");
                Partie.eliminerJoueur(Partie.getleGroupeJoueur().get(idjr));

                Partie.addPoint(cjoueur, 2);
                cjoueur.jouer();
            } else {
                System.out.println("Et non... " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " était un villageois");
                System.out.println("Vous perdez 2 points");
                Partie.subPoint(cjoueur, 2);
                Partie.getleGroupeJoueur().get(idjr).idRevele = true;
                Partie.getleGroupeJoueur().get(idjr).jouer();

            }
        }

        System.out.println("Vous ne pouvez pas jouer cette carte");
        cjoueur.mainJoueur.add(cjoueur.carteRevele.getLast());
        effethunt(cjoueur.choisircarte().effeth, cjoueur);

    }

    public static void sreveal(Joueur cjoueur) {
        if(cjoueur.id == Joueur.Identite.Sorciere)
        {
            System.out.println(cjoueur.getNomJoueur()+" était une sorcière !");
            int indexjp, indexj = Partie.getleGroupeJoueur().indexOf(cjoueur);

            if (indexj ==0 )  indexjp = Partie.getleGroupeJoueur().size()-1;
            else indexjp = indexj-1;

            Partie.eliminerJoueur(cjoueur);
            System.out.println(" Le joueur suivant est : " + Partie.getleGroupeJoueur().get(indexjp).getNomJoueur());
            Partie.getleGroupeJoueur().get(indexjp).jouer();
        }
        else if (cjoueur.id == Joueur.Identite.Villageois)
        {
            System.out.println(cjoueur.getNomJoueur()+" est un villageois, c'est à son tours !");
            cjoueur.idRevele = true;
            cjoueur.jouer();
        }
    }

    public static void qwitch(Joueur cjoueur) {


        System.out.println(cjoueur.getNomJoueur() + " a activé l'effet de la carte");
        System.out.print(cjoueur.getNomJoueur() + ", choisissez le joueur que vous vouler réveler ou faire défausser une carte.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");
        int idjr = cjoueur.choisirjoueur();
        while (Partie.getleGroupeJoueur().get(idjr).carteRevele.contains(Carte.Set.get(6))) {
            System.out.println("Vous ne pouvez pas choisir ce joueur puisqu'il a révélé Ducking Stool");

            idjr = cjoueur.choisirjoueur();
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
        if (Partie.getleGroupeJoueur().get(idjr).id == Joueur.Identite.Sorciere) {
            System.out.println("Bravo " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " est une sorcière");
            System.out.println(cjoueur.getNomJoueur() + ", vous gagnez 1 points");
            Partie.eliminerJoueur(Partie.getleGroupeJoueur().get(idjr));
            Partie.addPoint(cjoueur, 1);
            cjoueur.jouer();
        } else {
            System.out.println("Et non... " + Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " était un villageois");
            System.out.println(cjoueur.getNomJoueur() + ", vous perdez 1 points");
            Partie.getleGroupeJoueur().get(idjr).idRevele = true;

            Partie.subPoint(cjoueur, 1);
            Partie.getleGroupeJoueur().get(idjr).jouer();

        }
    }

    public static void takecardrfp(Joueur cjoueur){

        System.out.println(cjoueur.getNomJoueur() + " a activé l'effet de la carte");
        System.out.print(cjoueur.getNomJoueur() + ", choisissez le joueur où vous allez lui prendre une carte.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");

        int idjr =cjoueur.choisirjoueur();
        if (!Partie.getleGroupeJoueur().get(idjr).carteRevele.isEmpty()) {


            Carte carterem = Partie.getleGroupeJoueur().get(idjr).carteRevele.get((int)Math.floor(Math.random() * (Partie.getleGroupeJoueur().get(idjr).carteRevele.size() - 1 +1)));

            cjoueur.mainJoueur.add(carterem);
            Partie.getleGroupeJoueur().get(idjr).carteRevele.remove(carterem);


        }
        else System.out.println("Le joueur n'a pas de carte ! ");
    }

    public static void takecardfp (Joueur cjoueur) {

        System.out.println(cjoueur.getNomJoueur() + " a activé l'effet de la carte");
        System.out.print(cjoueur.getNomJoueur() + ", choisissez le joueur où vous allez lui prendre une carte.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");

        int idjr = cjoueur.choisirjoueur();
        System.out.println(Partie.getleGroupeJoueur().get(idjr).mainJoueur);
        System.out.println(Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty());
        if (!Partie.getleGroupeJoueur().get(idjr).mainJoueur.isEmpty()) {
            System.out.println("Passé");

            Carte carterem = Partie.getleGroupeJoueur().get(idjr).mainJoueur.get((int)Math.floor(Math.random() * (Partie.getleGroupeJoueur().get(idjr).mainJoueur.size() - 1 +1)));

            cjoueur.mainJoueur.add(carterem);
            Partie.getleGroupeJoueur().get(idjr).mainJoueur.remove(carterem);
            System.out.println("Vous avez récupéré la carte" + carterem.nom);
            choosenextp(cjoueur);

        }
        System.out.println("Pas passé");
    }

    public static void takedcard(Joueur cjoueur){

        if (Carte.Pioche.isEmpty())
        {
            System.out.println("Il n'y a pas de carte dans la pioche.");
            System.out.println("Vous ne pouvez donc pas jouer cette carte");
            cjoueur.mainJoueur.add(cjoueur.carteRevele.getLast());
            effethunt(cjoueur.choisircarte().effeth, cjoueur);
        }
        cjoueur.mainJoueur.add(Carte.Pioche.get(Carte.Pioche.size()-1));
        Carte.Pioche.remove(Carte.Pioche.size()-1);

        System.out.println("Vous devez maintenant défausser une carte : ");
        Carte carterem = cjoueur.choisircarte();
        Carte.Pioche.add(carterem);
        cjoueur.mainJoueur.remove(carterem);

        cjoueur.jouer();

    }

    public static void lookid(Joueur cjoueur){
        if (cjoueur.idRevele && cjoueur.id == Joueur.Identite.Villageois) {

            System.out.println(cjoueur.getNomJoueur() + " a activé l'effet reveal de la carte");
            System.out.print(cjoueur.getNomJoueur() + ", choisissez le joueur que vous vouler inspecter.");
            System.out.println(" Tapez le numéro correspondant au joueur : ");

            int idjr = cjoueur.choisirjoueur();

            System.out.println(Partie.getleGroupeJoueur().get(idjr).getNomJoueur() + " est un " + Partie.getleGroupeJoueur().get(idjr).id);
        }
        else {
            System.out.println("Vous ne pouvez pas jouer cette carte");

            cjoueur.mainJoueur.add(cjoueur.carteRevele.getLast());
            effethunt(cjoueur.choisircarte().effeth, cjoueur);
        }
    }

    public static void getbackcard (Joueur cjoueur, String situation){

        System.out.println(cjoueur.carteRevele.size());
        if (cjoueur.carteRevele.size() < 2)
        {

            System.out.println("Vous n'avez pas de carte révélé");
            cjoueur.mainJoueur.add(cjoueur.carteRevele.getLast());
            if (Objects.equals(situation, "hunt")) effethunt(cjoueur.choisircarte().effeth, cjoueur);
            if (Objects.equals(situation, "witch")) effetwitch(cjoueur.choisircarte().effetw, cjoueur);
        }
        else {
            cjoueur.mainJoueur.add(cjoueur.carteRevele.get(cjoueur.carteRevele.size()-2));

        }

    }

    //////////////Méthode de Witch //////////////

    public static void takeOneCard(Joueur cjoueur){
        Joueur joueurAccusateur =Partie.getleGroupeJoueur().get(cjoueur.iAccusateur);
        int ncarte;
        boolean reponseValide = false ;
        System.out.println(cjoueur.getNomJoueur() + " Vous pouvez prendre l'une des cartes de " + joueurAccusateur.nomJoueur + " car il vous a accusé");
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
        cjoueur.mainJoueur.add(joueurAccusateur.mainJoueur.get(ncarte));
        joueurAccusateur.mainJoueur.remove(ncarte);
        System.out.println(joueurAccusateur.nomJoueur + " Voici votre nouvelle main de carte : " + joueurAccusateur.mainJoueur);
        System.out.println(cjoueur.nomJoueur + " Voici votre nouvelle main de carte : " + cjoueur.mainJoueur);
    }

    public static void choosenextp(Joueur cjoueur){
        System.out.println(cjoueur.getNomJoueur() + " a activé l'effet choosenextp de la carte");
        System.out.print(cjoueur.getNomJoueur() + ", choisissez le joueur qui joueras au tour suivant.");
        System.out.println(" Tapez le numéro correspondant au joueur : ");


        int idjr =  cjoueur.choisirjoueur();

        Partie.getleGroupeJoueur().get(idjr).jouer();
    }

    public static void diacardRandomCard(Joueur cjoueur){
        System.out.println(cjoueur.getNomJoueur() + " Une carte aléatoire de ta main va être défaussé !");
        System.out.println(cjoueur.nomJoueur + " Voici ta main actuelle " + cjoueur.mainJoueur);
        Collections.shuffle(cjoueur.mainJoueur); // mélange les cartes
        cjoueur.mainJoueur.remove(cjoueur.mainJoueur.get(0));  // supprime la premiere carte
        System.out.println(cjoueur.nomJoueur + " Voici ta nouvelle main apres défausse " + cjoueur.mainJoueur);
    }

    public static void cantaccy (Joueur cjoueur){
        Partie.getleGroupeJoueur().get(cjoueur.iAccusateur).joueurInterdit = cjoueur;
    }

    public static void discardCard (Joueur cjoueur){

        int carteDefausse;
        boolean reponseValide = false;
        if (cjoueur.mainJoueur.size()>0) {
            System.out.println(cjoueur.getNomJoueur() + " tu dois te défausser d'une carte");
            System.out.println("Choisir une carte dans votre main " + cjoueur.mainJoueur);
            for (int j = 0; j < cjoueur.mainJoueur.size(); j++) {
                System.out.printf("%n" + j + " : " + cjoueur.mainJoueur.get(j));
            }
            System.out.println();
            do {
                carteDefausse = Input.inputInt();
                if (carteDefausse <= cjoueur.mainJoueur.size() && carteDefausse >= 0) {
                    reponseValide = true;
                    cjoueur.mainJoueur.remove(carteDefausse);
                    System.out.println(cjoueur.nomJoueur + " Voici ta main " + cjoueur.mainJoueur);
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
    public static void effethunt(String effet, Joueur cjoueur)
    {




        String effets[] = effet.split("&");
        for (int i = 0; i < effets.length; i++)
        {
            System.out.println(cjoueur.getNomJoueur());


            switch(effets[i]) {

                case "reveal":
                    System.out.println("reveal");
                    reveal(cjoueur);
                    break;

                case "choosenextp":
                    System.out.println("choosenextp");
                    choosenextp(cjoueur);
                    break;

                case "getbackcard":
                    System.out.println("getbackcard");
                    getbackcard(cjoueur,"hunt");
                    break;

                case "lookid":
                    System.out.println("lookid");
                    lookid(cjoueur);
                    break;

                case "takcardrfp":
                    System.out.println("takcardrfp");
                    takecardrfp(cjoueur);
                    break;
                case "takcardfp":
                    System.out.println("takcardfp");
                    takecardfp(cjoueur);
                    break;

                case "takdcard":
                    System.out.println("takdcard");
                    takedcard(cjoueur);
                    break;

                case "discardc":
                    System.out.println("discardc");
                    takedcard(cjoueur);
                    break;

                case "qwitch":
                    System.out.println("qwitch");
                    qwitch(cjoueur);
                    break;

                case "sreveal":
                    System.out.println("sreveal");
                    sreveal(cjoueur);
                    break;


            }



        }


    }

    public static void effetwitch(String effet, Joueur joueurAccuse)
    {

        String effets[] = effet.split("&");
        for (int i = 0; i < effets.length; i++)
        {
            switch(effets[i]) {

                case "takenext": // take next turn
                    joueurAccuse.jouer();
                    break;

                case "disc": // discard a crad from your hand
                    discardCard(joueurAccuse);
                    break;

                case "getbackcard": // take one of your own revealed cards into your hand
                    getbackcard(joueurAccuse,"witch");
                    break;

                case "choosenextp": // choose next player
                    choosenextp(joueurAccuse);
                    break;

                case "takecarday":  // take one card from the hand of the player who accused you
                    takeOneCard(joueurAccuse);
                    break;

                case "discardcay":  // the player who accused you discards a random card from their hand
                    diacardRandomCard(Partie.getleGroupeJoueur().get(joueurAccuse.iAccusateur));
                    break;

                case "cantaccy": // choose next player & on their trun must accuse a player other than you, if possible
                    cantaccy(joueurAccuse);
                    break;

                default :
                    System.out.println("Une erreur est survenue !");
                    joueurAccuse.jouer();
                    break;


            }
        }
    }
}
