package appli.jeu.tests;

import appli.jeu.Constantes;
import appli.jeu.Pioche;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TestPioche {

    @Test
    public void testCreationPioche() {
        Pioche pioche = new Pioche(Constantes.CARTE_INITIALE_PILE_ASCENDANTE +1, Constantes.CARTE_INITIALE_PILE_DESCENDANTE -1);
        assertEquals(58, pioche.getNombreCartes());
    }

    @Test
    public void testModificationPioche(){
        Pioche pioche = new Pioche(Constantes.CARTE_INITIALE_PILE_ASCENDANTE +1, Constantes.CARTE_INITIALE_PILE_DESCENDANTE -1);
        Random random = new Random();
        int nombreCartesAPiocher = random.nextInt(58); //On a initalement 58 cartes dans la pioche, donc on en pioche aléatoirement de 0 à 57 inclus
        for(int i = 0; i < nombreCartesAPiocher; ++i)
            pioche.piocher();
        assertEquals(pioche.getNombreCartes(), 58 - nombreCartesAPiocher);
    }
}
