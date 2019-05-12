package hu.example.castlemanager;

public class Lovag {

    private int lovagero;

    Lovag() {
        this.lovagero = 100;
    }

    Lovag(int lovagero) {
        this.lovagero = lovagero;
    }

    private int lovagLevelUp() {
        this.lovagero = this.lovagero+10;
        return lovagero;
    }

    private int getLovagEro() {
        return lovagero;
    }

    @Override
    public String toString() {
        return "Lovag Ereje: "+lovagero;
    }

}
