package com.lottopaketti;

import java.util.*;

public class Todennakoisyydet {
    //Random olio satunnaisia lukuja varten
    private static Random r = new Random();
    //Todennäköisyyslaskentaan
    private int maara;
    private List<Lottorivi<Integer>> rivit;

    public Todennakoisyydet() {
        maara = 186435;//60;
        rivit = new ArrayList<>();
    }

    //Generoidaan todennäköisyyslaskentaan lottorivit
    public void GeneroiTodennakoisyysRivit() {
        // Jos rivit on jo generoitu, ei tehdä sitä enää turhaan uusiksi
        // (Tämä voi tapahtua, jos käyttäjä pelaa uusiksi)
        if (rivit.size() > 0) {
            return;
        }
        for (int i = 0; i < maara; i++) {
            ArrayList<Integer> rivi = GeneroiLottoriviJaLisanumero();
            // Otetaan viimeinen indeksi rivistä (joka on lisänumero) talteen
            Integer lisaNumero = rivi.indexOf(7);
            // Poistetaan lisänumero rivistä
            rivi.remove(7);
            // Lisätään rivi ja lisänumero muistiin
            rivit.add(new Lottorivi<>(rivi, lisaNumero));
        }
    }

    //Generoi lottorivi
    private ArrayList<Integer> GeneroiLottoriviJaLisanumero() {
        //Väliaikaiseen listan tallentamiseen tarkoitettu HashSet. Listassa ei voi olla samoja lukuja.
        HashSet<Integer> lodo = new HashSet<>();
        for (int i = 0; i < 8; ) {
            int seuraavaLottonumero = r.nextInt(40) + 1;
            lodo.add(seuraavaLottonumero);
            i = lodo.size();
        }

        return (new ArrayList<>(lodo));
    }

    //Palauttaa todennäköisyys rivit
    public List<Lottorivi<Integer>> PalautaTodennakoisyysRivit() {
        return rivit;
    }
}
