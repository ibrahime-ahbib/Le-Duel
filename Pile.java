package appli;

public class Pile {
    private byte nbCartes;
    private byte carteSommetAscendante;
    private byte carteSommetDescendante;

    public Pile(){
        nbCartes = 58;
        carteSommetDescendante = 60;
        carteSommetAscendante = 1;
    }

    public byte getCarteSommetAscendante(){
        return carteSommetAscendante;
    }

    public byte getCarteSommetDescendante(){
        return carteSommetDescendante;
    }


    public void setCarteSommetDescendante(byte CarteSommetDescendante) {
        this.carteSommetDescendante = CarteSommetDescendante;
    }

    public void setCarteSommetAscendante(byte CarteSommetAscendante) {
        this.carteSommetAscendante = CarteSommetAscendante;
    }
    //MiseAJour de la pile

}


