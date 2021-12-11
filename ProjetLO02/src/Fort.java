import java.util.*;

public class Fort implements StrategyJouer  {




    public void jouer(Joueur joueurAccusateur) {

        Random random = new Random();
        Boolean reponse1;
        int reponse2;
        Joueur joueurAccuse;

        ArrayList<Joueur> groupeJoueur;
        groupeJoueur = Partie.getleGroupeJoueur();
        Partie.finRound(groupeJoueur);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("C'est a " + joueurAccusateur.getNomJoueur() + " de jouer");
        System.out.println(joueurAccusateur.getNomJoueur() + " joue");
        reponse1 = random.nextBoolean();

        //reponse1 = true; // ATTENTION A RETIRER C EST POUR LES TESTS
        if (reponse1 || joueurAccusateur.mainJoueur.isEmpty() || (joueurAccusateur.mainJoueur.size()==1 && joueurAccusateur.mainJoueur.get(0).effeth.matches("^reveal") && !joueurAccusateur.isIdRevele()) )
        {
            System.out.println("Le bot a choisit d'accuser un autre joeur d'être une sorcière");
            // reponse2 = joueurAccusateur.choisirjoueur();
            reponse2 = choisirjoueur(joueurAccusateur);
            joueurAccuse = groupeJoueur.get(reponse2);

            while (true) {
                if (joueurAccusateur.joueurInterdit != joueurAccuse && !joueurAccuse.idRevele/* && Partie.getleGroupeJoueur().size() <= 2*/) {
                    //
                    if(joueurAccuse instanceof Bot){ // le joueur accuse est un bot
                        joueurAccuse.etreaccuse(joueurAccusateur);
                    }
                    else{ // le joueur accuse est un Joueur
                        joueurAccuse.etreaccuse(joueurAccusateur);
                     //   super.accuser(joueurAccuse); // c'est ce qu'il faut faire mais c'est pas ficile
                    }

                    break;
                }
                System.out.println("Vous ne pouvez pas accuser ce joueur !");
                //reponse2 = joueurAccusateur.choisirjoueur();
                reponse2 = choisirjoueur(joueurAccusateur);
                joueurAccuse = groupeJoueur.get(reponse2);
            }

        }else if (!reponse1) {

            System.out.println(joueurAccusateur.nomJoueur + " a décidé d'utiliser une carte Rumeur ");
             Effet.effethunt(joueurAccusateur.choisircarte().effeth, joueurAccusateur);


        }
    }


    public void etreaccuse(Joueur joueurAccusateur,Joueur joueurAccuse) {
        Random random = new Random();
        System.out.println(joueurAccusateur.nomJoueur + " Accuse " + joueurAccuse.nomJoueur + " d'être une sorciere");

        joueurAccuse.iAccusateur = Partie.getleGroupeJoueur().indexOf(joueurAccusateur);

        System.out.println(joueurAccuse.nomJoueur + " Voulez vous reveler votre identité ? (oui ou non) ");
        boolean reponse = random.nextBoolean();
        if (!joueurAccuse.isIdRevele() && joueurAccuse.getId() == Joueur.Identite.Sorciere )
        {
             reponse = false;
        }
        //0
        //
        // System.out.println(reponse);
        if (reponse|| joueurAccuse.mainJoueur.isEmpty()) {
            if(joueurAccuse.mainJoueur.size() == 0){
                System.out.println("Vous n'avez plus de carte rumeur dans votre main vous devez revelez votre identité !");
            }
            joueurAccuse.idRevele = true;

            if (joueurAccuse.id == Joueur.Identite.Sorciere) {
                System.out.println(joueurAccuse.nomJoueur + " est une " + joueurAccuse.id);
                Partie.addPoint(joueurAccusateur, 1);
                System.out.println(joueurAccusateur.nomJoueur + " Vous avez découvert une sorciere");
                Partie.eliminerJoueur(joueurAccuse);
                joueurAccusateur.jouer();
            } else if (joueurAccuse.id == Joueur.Identite.Villageois) {
                System.out.println(joueurAccuse.nomJoueur + " est un " + joueurAccuse.id);
                joueurAccuse.jouer();
            }
        } else if (!reponse) {
            System.out.println("Vous avez choisi de ne pas réveler votre identité ");
            System.out.println("vous devez a présent résoudre l'effet witch d'une de vos carte");

            // Effet.effetwitch(joueurAccuse.choisircarte().effetw,joueurAccuse);

            Effet.effetwitch(joueurAccuse.choisircarte().effetw,joueurAccuse);

        }
    }

    public Carte choisircarte(Joueur joueurAccuse) {
        Random random = new Random();
        int numeroCarte;
        boolean reponseValide = false ;

        System.out.println(joueurAccuse.getNomJoueur() + " choisis une carte de sa main : ");

        for (int j =0; j <joueurAccuse.mainJoueur.size();j++){
            System.out.printf("%n" + j + " : " + joueurAccuse.mainJoueur.get(j));
        }
        System.out.println();
        do {
            numeroCarte = random.nextInt(joueurAccuse.mainJoueur.size());
            System.out.println(numeroCarte);
            if (numeroCarte <= joueurAccuse.mainJoueur.size() && numeroCarte >= 0) {
                reponseValide = true;
            }
        }while(!reponseValide);

        joueurAccuse.carteRevele.add(joueurAccuse.mainJoueur.get(numeroCarte));
        joueurAccuse.mainJoueur.remove(numeroCarte);
        return joueurAccuse.carteRevele.getLast();
    }

    public int choisirjoueur(Joueur joueurAccusateur) {
        int identJoueur;
        Random random = new Random();
        List<Integer> noj = new LinkedList<>();

        for (Joueur j : Partie.getleGroupeJoueur()) {
            if (j != joueurAccusateur) {
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

}
