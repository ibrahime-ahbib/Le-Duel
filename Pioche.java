package appli;

import java.util.Collections;
import java.util.Stack;

public class Pioche {
    private Stack<Byte> pileDeCartes = new Stack<>();

    public int NombreCartesPioche(){
        return pileDeCartes.size();
    }

    public byte piocher (){
        return pileDeCartes.pop();
    }

    public Pioche(){
        for(int i = 2; i <= 59; ++i)
        {
            pileDeCartes.push((byte)i);
        }
        Collections.shuffle(pileDeCartes);
    }

    public Stack<Byte> getPileDeCartes() {
        return pileDeCartes;
    }
}
