package appli;

import java.util.Scanner;
import java.util.ArrayList;

public class Jeu
{
    private Joueur joueur1, joueur2, joueurActif, joueurInactif;

    Jeu(Joueur joueur1, Joueur joueur2)
    {
        this.joueur1 = this.joueurActif = joueur1;
        this.joueur2 = this.joueurInactif = joueur2;
    }

    private boolean JouerCartes(String cartesAJouees)
    {
        byte      pile_ascendante_joueur = joueurActif.getPile().getCarteSommetAscendante()
                , pile_ascendante_joueur_adverse = joueurInactif.getPile().getCarteSommetAscendante()
                , pile_descendante_joueur = joueurActif.getPile().getCarteSommetDescendante()
                , pile_descendante_joueur_adverse = joueurInactif.getPile().getCarteSommetDescendante();

        //1) vérifier que le format est bon <=> charAt(0) && charAt(1) sont des chiffres; charAt(2) == 'v' || '^' && charAt(4) s'il existe == ' ' '
        //2) vérifier que la carte peut être jouée

        String[] cartesJoueesSpilter = cartesAJouees.split("\\s+");
        ArrayList<Byte> nombresJoues = new ArrayList<>();
        boolean a_joue_adversaire = false;
        int nb_cartes_jouees_pile = 0;
        int nb_cartes_jouees_pile_adverse = 0;

        for(String s : cartesJoueesSpilter)
        {
            if(((s.length() == 4 && s.charAt(3) != '\'')
                    || (s.length() != 3 && s.length() != 4)
                    || !Character.isDigit(s.charAt(0))
                    || !Character.isDigit(s.charAt(1))
                    || (s.charAt(2) != '^' && s.charAt(2) != 'v')))
                return false;

            byte numCarte = Byte.parseByte(s.substring(0, 2));
            if(numCarte < 2 || numCarte > 59 || !joueurActif.getCarteEnMain().contains(numCarte))
                return false;

            for(byte b : nombresJoues)
                if(b == numCarte)
                    return false;

            //Chaine valide, numéro aussi
            if(s.length() == 4)
            {
                if(s.charAt(2) == 'v')
                {
                    if(numCarte <= pile_descendante_joueur_adverse)
                        return false;
                    pile_descendante_joueur_adverse = numCarte;
                    //Pile descendante adverse
                }
                else
                {
                    if(numCarte >= pile_ascendante_joueur_adverse)
                        return false;
                    pile_ascendante_joueur_adverse = numCarte;
                    //Pile ascendante adverse
                }
                a_joue_adversaire = true;
                nb_cartes_jouees_pile_adverse++;
            }
            else
                {
                if (s.charAt(2) == 'v')
                {
                    if (numCarte >= pile_descendante_joueur && numCarte - 10 != pile_descendante_joueur)
                        return false;
                    pile_descendante_joueur = numCarte;
                    //Pile descendante du joueur
                }
                else
                {

                    if (numCarte <= pile_ascendante_joueur && numCarte != pile_ascendante_joueur - 10)
                        return false;
                    pile_ascendante_joueur = numCarte;
                    //Pile ascendante
                }
                nb_cartes_jouees_pile++;
            }
            nombresJoues.add(numCarte);
        }

        if(nb_cartes_jouees_pile < 2 || nb_cartes_jouees_pile_adverse > 1)
            return false;

        joueurActif.getPile().setCarteSommetDescendante(pile_descendante_joueur);
        joueurActif.getPile().setCarteSommetAscendante(pile_ascendante_joueur);

        joueurInactif.getPile().setCarteSommetAscendante(pile_ascendante_joueur_adverse);
        joueurInactif.getPile().setCarteSommetDescendante(pile_descendante_joueur_adverse);

        for(Byte b : nombresJoues)
            joueurActif.getCarteEnMain().remove(b);

        joueurActif.piocher(a_joue_adversaire ? 6 - nombresJoues.toArray().length : 2);

        Joueur temp = joueurInactif;
        joueurInactif = joueurActif;
        joueurActif = temp;

        return true;
    }


    public void lancer()
    {
        Scanner lireCoup = new Scanner(System.in);

        while(joueur1.PeutJouerPartie() && joueur2.PeutJouerPartie())
        {
            System.out.println(joueur1);
            System.out.println(joueur2);
            System.out.println(joueurActif.cartesMain());

            System.out.print("> ");

            String cartesJouees = lireCoup.nextLine();
            while (!JouerCartes(cartesJouees))
            {
                System.out.print("#> ");
                cartesJouees = lireCoup.nextLine();
            }
        }

        if(joueur1.partieGagnee(joueur2))
            System.out.println("> NORD a gagné la partie");

        else if(joueur2.partieGagnee(joueur1))
            System.out.println("> SUD a gagné la partie");

        System.out.println(joueur1.cartesMain());
        System.out.println(joueur1);
        System.out.println(joueur2.cartesMain());
        System.out.println(joueur2);
    }
}
