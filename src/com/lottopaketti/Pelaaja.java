package com.lottopaketti;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Pelaaja {

    //Kaikki pelaajan lottorivit sisältävä lista
    public ArrayList<ArrayList<Integer>> lottoRivit = new ArrayList<ArrayList<Integer>>();
    //Pelaajan yksi lottorivi
    public ArrayList<Integer> annetutLottonumerot = new ArrayList<>();

    public Pelaaja(int rivienLkm) {
        TaytaPelaajanRivi(rivienLkm);
    }

    public void TaytaPelaajanRivi(int lottoRiviLkm) {

    Scanner lukija = new Scanner(System.in);
    //Seuraava valittu numero
    int seuraavaNro = 1;
    //Lottorivin numero
    int lottoriviNro = 1;
    //Tyhjennä lottoRivit aina uudessa pelissä
        lottoRivit.clear();

    //Niin kauan kuin kaikkien lottorivien sisältämän listan koko on pienempi kuin lottorivien lkm, lisätään uusia lottorivejä.
        while (lottoRivit.size() != lottoRiviLkm)
    {
        //Tyhjennetään annetut lottonumerot array jokaisen rivin syötön jälkeen
        annetutLottonumerot.clear();

        System.out.println("Lottorivi nro " + lottoriviNro);
        do {
            System.out.println("Anna numero" + " " + "(" + (seuraavaNro) + "):");
            try {
                int luku = lukija.nextInt();
                //Jos annettu luku on suurempi kuin 40 tai pienempi kuin 1, niin annetaan virheilmoitus
                if (luku > 40 || luku < 1) {
                    System.out.println("Annettu luku ei ole arvoalueella");

                }
                //Jos annettu luku on jo listalla
                else if (annetutLottonumerot.contains(luku)) {
                    System.out.println("Olet jo valinnut tämän numeron lottorivillesi");
                }
                //Jos kaikki ok, niin lisätään annetutLottonumerot listaan annettu luku ja lisätään 1 seuraavaNro muuttujaan.
                else {
                    annetutLottonumerot.add(luku);
                    seuraavaNro++;
                }
            }
            catch (InputMismatchException ex) {
                System.out.println("Antamasi arvo ei ollut luku.");
                lukija.next();
            }
        } while (seuraavaNro < 8);
        System.out.println("Lottorivin luvut ovat: " + annetutLottonumerot.toString() + "\n");

        //Lisää annetutLottonumerot lottoRivit listaan
        lottoRivit.add(new ArrayList<>(annetutLottonumerot));

        seuraavaNro = 1;
        lottoriviNro++;
    }
}

    public ArrayList<ArrayList<Integer>> PalautaLottorivi() {

        return lottoRivit;
    }

    public void TulostaPelaajanLottoRivit()
    {
        for(ArrayList<Integer> e : lottoRivit)
            System.out.println(e.toString());
    }


}
