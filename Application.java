package appli;

import appli.jeu.Jeu;

import java.util.Scanner;


public class Application {
    public static void main(String[] args) {
        Jeu jeu = new Jeu("NORD", "SUD");
        Scanner entreeUtilisateur = new Scanner(System.in);

        while(jeu.peutContinuer())
        {
            System.out.println(jeu.getPremierJoueur().informationsPlateau());
            System.out.println(jeu.getSecondJoueur().informationsPlateau());
            System.out.println(jeu.getJoueurActif().informationsCartesMain());

            System.out.print("> ");

            String cartesJouees = entreeUtilisateur.nextLine();

            while (!jeu.jouerCartes(cartesJouees))
            {
                System.out.print("#> ");
                cartesJouees = entreeUtilisateur.nextLine();
            }
            System.out.println(jeu.getInformationsDernierCoupJoue());
        }

        System.out.println(jeu.getPremierJoueur().informationsPlateau());
        System.out.println(jeu.getSecondJoueur().informationsPlateau());
        System.out.println(jeu.getJoueurActif().informationsCartesMain());
        System.out.println("partie finie, " + jeu.getGagnant() + " a gagné");
    }
}
