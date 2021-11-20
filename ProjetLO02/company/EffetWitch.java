package com.company;

public class EffetWitch {

    public void effetWitch (String nomCarte, Joueur joueur){

        switch (nomCarte){
            case "angry_mob":
                Partie.joueurSuivant(joueur);
                break;
            case "the_inquisition":
                Partie.joueurSuivant(joueur);
                break;

        }
    }
}
