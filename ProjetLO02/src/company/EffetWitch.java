package company;

public interface EffetWitch {

    public default void effetwitch(String effet)
    {



        String effets[] = effet.split("&");
        for (int i = 0; i < effets.length; i++)
        {



            switch(effets[i]) {

                case "reveal":
                    System.out.println("reveal");
                    break;

                case "choosenextp":
                    System.out.println("choosenextp");
                    break;

                case "getbackcard":
                    System.out.println("getbackcard");
                    break;

                case "lookid":
                    System.out.println("lookid");
                    break;

                case "takcardrfp":
                case "takcardfp":
                    System.out.println("takcardrfp or takcardfp");
                    break;

                case "takdcard":
                    System.out.println("takdcard");
                    break;

                case "discardc ":
                    System.out.println("discardc");
                    break;

                case "qwitch":
                    System.out.println("qwitch");
                    break;

                case "sreveal":
                    System.out.println("sreveal");
                    break;


            }



        }


    }
}
