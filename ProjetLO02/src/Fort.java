import java.util.ArrayList;
import java.util.Random;

public class Fort implements StrategyJouer  {




    @Override
    public void jouer(Bot cbot) {
        Random random = new Random();
        System.out.println("test12");
        Boolean reponse1;
        int reponse2;
        Joueur joueurAccuse;

        ArrayList<Joueur> groupeJoueur;
        groupeJoueur = Partie.getleGroupeJoueur();
        Partie.finRound(groupeJoueur);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("C'est a " + cbot.getNomJoueur() + " de jouer");
        System.out.println(cbot.getNomJoueur() + " voulez vous accuser un autre joueur d'être une sorcière ? (oui ou non)");

        reponse1 = random.nextBoolean();

        if (reponse1 || cbot.mainJoueur.isEmpty() || (cbot.mainJoueur.size()==1 && cbot.mainJoueur.get(0).effeth.matches("^reveal") && !cbot.isIdRevele()) )
        {
            reponse2 = cbot.choisirjoueur();
            joueurAccuse = groupeJoueur.get(reponse2);

            while (true) {
                if (cbot.joueurInterdit != joueurAccuse || !joueurAccuse.idRevele || Partie.getleGroupeJoueur().size() <= 2) { // Attention
                    cbot.accuser(joueurAccuse);
                    break;
                }
                System.out.println("Vous ne pouvez pas accuser ce joueur !");
                reponse2 = cbot.choisirjoueur();
                joueurAccuse = groupeJoueur.get(reponse2);
            }

        }else if (!reponse1) {

            System.out.println("Vous avez décidé d'utiliser une carte Rumeur ");
            Effet.effethunt(cbot.choisircarte().effeth, cbot);

        }
    }
}
