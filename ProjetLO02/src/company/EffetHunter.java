package company;

public interface EffetHunter {


        public default void effethunt(String effet, Joueur cjoueur)
    {



        String effets[] = effet.split("&");
        for (int i = 0; i < effets.length; i++)
        {



        switch(effets[i]) {

            case "reveal":
                System.out.println("reveal");
                cjoueur.reveal();
                break;

            case "choosenextp":
                System.out.println("choosenextp");
                cjoueur.choosenextp();
                break;

            case "getbackcard":
                System.out.println("getbackcard");
                cjoueur.getbackcard("hunt");
                break;

            case "lookid":
                System.out.println("lookid");
                cjoueur.lookid();
                break;

            case "takcardrfp":
                System.out.println("takcardrfp");
                cjoueur.takecardrfp();
                break;
            case "takcardfp":
                System.out.println("takcardfp");
                cjoueur.takecardfp();
                break;

            case "takdcard":
                System.out.println("takdcard");
                cjoueur.takedcard();
                break;

            case "discardc":
                System.out.println("discardc");
                cjoueur.takedcard();
                break;

            case "qwitch":
                System.out.println("qwitch");
                cjoueur.qwitch();
                break;

            case "sreveal":
                System.out.println("sreveal");
                cjoueur.sreveal();
                break;


        }



        }


    }
}
