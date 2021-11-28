import java.util.*;

public interface Input {
    public static int inputInt()
    {
        boolean bool = false;
        int result = -1;
        while(!bool)
        {
            Scanner sc = new Scanner(System.in);
            try
            {
                result = sc.nextInt();
                sc.nextLine();
                bool = true;
            }
            catch(InputMismatchException e)
            {
                System.out.println("Veuillez saisir un chiffre");
                bool = false;
            }
        }
        return result;
    }

    public static  String inputString()
    {
        boolean bool = false;
        boolean isNumeric = false;
        String result = "false";
        while(!bool || isNumeric==true )
        {
            Scanner sc = new Scanner(System.in);
            try
            {
                result = sc.nextLine();
                isNumeric = result.matches("[+-]?\\d*(\\.\\d+)?");
                bool = true;
            }
            catch(InputMismatchException e)
            {
                System.out.println("Veuillez saisir un String");
                bool = false;
            }
            if ( isNumeric){
                System.out.println("Veuillez saisir un String");
            }
        }
        return result;
    }
}
