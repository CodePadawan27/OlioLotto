package com.lottopaketti;

import java.util.ArrayList;

public class Lottorivi<T> {
    private ArrayList<Integer> lottoRivi;
    // Käytetään mm. Integer lisänumerona ja VoittoLuokka voittoluokkana
    // eri tilanteissa
    private T value;

    public Lottorivi(ArrayList<Integer> lottoRivi, T value) {
        this.lottoRivi = lottoRivi;
        this.value = value;
    }

    // Asettaa lottorivin
    public void setLottorivi(ArrayList<Integer> lottoRivi) { this.lottoRivi = lottoRivi; }
    // Palauttaa lottorivin
    public ArrayList<Integer> getLottorivi() { return lottoRivi; }

    // Asettaa value arvon
    public void setValue(T t) { this.value = t; }
    // Palauttaa value arvon
    public T getValue() { return value; }
}
