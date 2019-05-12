package hu.example.castlemanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class KastelySzarny implements Serializable {

    final static int MAX_SZOBAK = 5;
    final static int LOVAGOK_PER_SZOBA = 3;
    ArrayList <Lovag> lovagok = new ArrayList<>();
    int szobak;
    String name;

    KastelySzarny(String name) {
        this.szobak = 0;
        this.name = name;
    }

    public boolean addLovag() {
        if (szobak*LOVAGOK_PER_SZOBA > lovagok.size()) {
            lovagok.add(new Lovag());
            return true;
        }
        return false;
    }

    public boolean addSzoba() {
        if (szobak < MAX_SZOBAK) {
            szobak++;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfLovag() {
        return lovagok.size();
    }

    @Override
    public String toString() {
        return "Név: "+name+"\nSzobak szama: "+szobak+", Lovagok szama: "+lovagok.size();
    }

}
