package company;

public interface EffetWitch {

    public default void effetwitch(String effet, Joueur joueurAccuse)
    {

        String effets[] = effet.split("&");
        for (int i = 0; i < effets.length; i++)
        {
            switch(effets[i]) {

                case "takenext": // take next turn
                    joueurAccuse.jouer();
                    break;

                case "disc": // discard a crad from your hand
                    joueurAccuse.discardCard();
                    break;

                case "getbackcard": // take one of your own revealed cards into your hand
                    joueurAccuse.getbackcard("witch");
                    break;

                case "choosenextp": // choose next player
                    joueurAccuse.choosenextp();
                    break;

                case "takecarday":  // take one card from the hand of the player who accused you
                    joueurAccuse.takeOneCard();
                    break;

                case "discardcay":  // the player who accused you discards a random card from their hand
                    Partie.getleGroupeJoueur().get(joueurAccuse.iAccusateur).diacardRandomCard();
                    break;

                case "cantaccy": // choose next player & on their trun must accuse a player other than you, if possible
                    joueurAccuse.cantaccy();
                    break;

                default :
                    System.out.println("Une erreur est survenue !");
                    joueurAccuse.jouer();
                    break;


            }
        }
    }
}