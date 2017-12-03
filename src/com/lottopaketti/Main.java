package com.lottopaketti;

import java.util.*;

public class Main {
    // Tehdään todennäköisyyksien rivien generointi vain kerran,
    // jotta uudelleen pelatessa ei tarvitse niitä tehdä uusiksi
    private static Todennakoisyydet todennakoisyydet = new Todennakoisyydet();

    public static void main(String[] args) {
        // ******** ALKUTOIMENPITEET ******** //
        // Tehdään uusi lottokone
        Lottokone lotto = new Lottokone();

        Scanner lukija = new Scanner(System.in);
        Pelaaja pelaajanRivit = null;

        boolean onnistui = false;
        int lottoRiviLkm = 1;
        while (!onnistui) {
            try {
                System.out.println("Tervetuloa pelaamaan lottoa! Kuinka monta lottoriviä haluat pelata?");
                lottoRiviLkm = lukija.nextInt();
                onnistui = true;
            } catch (InputMismatchException ex) {
                System.out.println("Antamasi arvo ei ollut luku.");
                lukija.next();
            }
        }

        System.out.println("Selvä, pelataan" + " " + lottoRiviLkm + " " + "riviä lottoa. Lähdetään täyttämään rivit. ");
        pelaajanRivit = new Pelaaja(lottoRiviLkm);

        //Joinataan todennäköisyysrivien generointi threadiin, jotta ohjelma ei mene liian pitkälle kun generointi ei olekkaan vielä valmis.
        System.out.println("Lasketaan todennäköisyyksiä...");

        // Valmistellaan todennäköisyydet
        todennakoisyydet.GeneroiTodennakoisyysRivit();

        //Laske todennäköisyydet
        lotto.TarkastaTodennakoisyydet(pelaajanRivit, todennakoisyydet);

        // ******** LOTTOKONEEN KÄYNNISTYS ******** //
        char syotto;
        do {
            System.out.println("Laita lottokone käyntiin painamalla 'K'");
            syotto = lukija.next().charAt(0);
            if (syotto != 'k')
                System.out.println("Väärä kirjain annettu");
        } while (syotto != 'k');

        System.out.println("Kiitos. Lottokone arpoo nyt illan lottonumerot.");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        System.out.print(".");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        System.out.print(".");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        System.out.print(".");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        System.out.print(".");

        System.out.println("\nTämän illan lottorivi on: ");
        lotto.TulostaLottoRivi();
        lotto.ArvoLisaNumero();
        lotto.TarkistaVoitot(pelaajanRivit);

        char pelaaUudelleen;
        do {
            System.out.println("Haluatko pelata uudelleen K/E?");
            pelaaUudelleen = lukija.next().charAt(0);
            if (pelaaUudelleen == 'k') {
                System.out.flush();
                String[] strArr = new String[0];
                main(strArr);
            } else if (pelaaUudelleen == 'e') {
                System.exit(0);
            }

        } while (pelaaUudelleen != 'k' || pelaaUudelleen != 'K' || pelaaUudelleen != 'e' || pelaaUudelleen != 'E');
    }
}
