package com.lottopaketti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class Lottokone {
    //Arvottu lottorivi
    public ArrayList<Integer> arvottuLottorivi;
    //Random olio satunnaisia lukuja varten
    private static Random r = new Random();
    //Arvottu lisänumero
    private int lisanumero;

    public Lottokone() {

        ArvoNumerot();
    }

    //Luo uuden 7-numeroisen lottorivin ja asettaa sen.
    public void ArvoNumerot() {
        //Asetetaan arvottuLottorivi Arraylistiin HashSet listan luvut
        arvottuLottorivi = new ArrayList<>(GeneroiLottorivi());
    }

    //Generoi lottorivi
    public ArrayList<Integer> GeneroiLottorivi() {
        //Väliaikaiseen listan tallentamiseen tarkoitettu HashSet. Listassa ei voi olla samoja lukuja.
        HashSet<Integer> lodo = new HashSet<>();
        for (int i = 0; i < 7; ) {
            int seuraavaLottonumero = r.nextInt(40) + 1;
            lodo.add(seuraavaLottonumero);
            i = lodo.size();
        }

        return (new ArrayList<>(lodo));
    }

    //Arpoo lisänumeron ja tulostaa sen
    public void ArvoLisaNumero() {
        lisanumero = GeneroiLisaNumero(arvottuLottorivi);
        System.out.println(" ja lisänumero" + " " + lisanumero);
    }

    //Generoi lisänumeron
    public Integer GeneroiLisaNumero(ArrayList<Integer> arvottuRivi) {
        boolean loytyyko = false;
        Integer lisaNumero = -1;
        do {
            lisaNumero = r.nextInt(40) + 1;
            if (!arvottuRivi.contains(lisaNumero)) {
                loytyyko = true;
            }
        } while (loytyyko == false);
        return (lisaNumero);
    }

    //Palauttaa arvotun lisänumeron
    public int PalautaLisaNumero() {

        return lisanumero;
    }

    //Tulostaa lottorivin
    public void TulostaLottoRivi() {

        TulostaLottoRivi(arvottuLottorivi);
    }

    public void TulostaLottoRivi(ArrayList<Integer> lottoRivi) {
        Collections.sort(lottoRivi);
        for (Integer lottonumero : lottoRivi)
            System.out.print(lottonumero + " ");
    }

    //Palauttaa arvotun lottorivin
    public ArrayList<Integer> PalautaLottoRivi() {
        Collections.sort(arvottuLottorivi);
        return arvottuLottorivi;
    }
}
