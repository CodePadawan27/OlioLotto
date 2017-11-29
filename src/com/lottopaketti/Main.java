package com.lottopaketti;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        //ALUSTUS
        Scanner lukija = new Scanner(System.in);
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
        Pelaaja pelaajanRivit = new Pelaaja(lottoRiviLkm);

        //LOTTOKONEEN KÄYNNISTYS
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
        Lottokone illanRivi = new Lottokone();
        illanRivi.TulostaLottoRivi();
        illanRivi.ArvoLisaNumero();

        // ******** VOITON TARKISTUS ******** //

        Map<ArrayList<Integer>, VoittoLuokka> tulokset = new HashMap<>();
        for (ArrayList<Integer> lottorivi : pelaajanRivit.PalautaLottorivi()) {
            tulokset.put(lottorivi, tarkistaLottoRivi(illanRivi.PalautaLottoRivi(), lottorivi, illanRivi.PalautaLisaNumero()));
        }

        for (Map.Entry<ArrayList<Integer>, VoittoLuokka> tulos : tulokset.entrySet()) {
            ArrayList<Integer> lottoRivi = tulos.getKey();
            VoittoLuokka voittoLuokka = tulos.getValue();
            String palkinto = "";
            switch (voittoLuokka) {
                case PAAVOITTO:
                    palkinto = "Tuli päävoitto!!!";
                    break;
                case KUUSI_YKSI:
                    palkinto = "Tuli 6+1 oikein";
                    break;
                case KUUSI:
                    palkinto = "Tuli 6 oikein";
                    break;
                case VIISI:
                    palkinto = "Tuli 5 oikein";
                    break;
                case NELJA:
                    palkinto = "Tuli 4 oikein";
                    break;
                case KOLME_YKSI:
                    palkinto = "Tuli 3+1 oikein";
                    break;
                default:
                    palkinto = "Ei ikävä kyllä tullut voittoa tällä kertaa";
            }

            System.out.print("\nLottoriville: ");
            illanRivi.TulostaLottoRivi(lottoRivi);
            System.out.println("\n" + palkinto);
        }

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

        } while (pelaaUudelleen != 'k' || pelaaUudelleen != 'K' ||  pelaaUudelleen != 'e' || pelaaUudelleen != 'E');
    }

    //Metodi tarkistaa, osuiko joku lottoriveistä voittoluokkaan
    public static VoittoLuokka tarkistaLottoRivi(ArrayList<Integer> arvottuLottorivi, ArrayList<Integer> lottoRivi, Integer lisaNumero) {
        int samaNumero = 0;
        Boolean lisaNumeroLoytyi = false;
        // Looppaillaan kaikki arvotut lottonumerot läpi
        for (int i = 0; i < arvottuLottorivi.size(); i++) {
            // Looppaillaan jokainen lottorivin numero jokaiselle arvotulle lottonumerolle
            // ja tarkistetaan montako on samoja
            for (int x = 0; x < lottoRivi.size(); x++) {
                if (arvottuLottorivi.get(i) == lottoRivi.get(x)) {
                    samaNumero++;
                }
            }
        }
        // Tarkistetaan löytyykö lisänumero lottorivistä
        for (int i = 0; i < lottoRivi.size(); i++) {
            if (lottoRivi.get(i) == lisaNumero) {
                lisaNumeroLoytyi = true;
            }
        }

        // Palautetaan oikea voittoluokka riville oikeiden määrän perusteella (+ lisänumero)
        switch (samaNumero) {
            case 7:
                return VoittoLuokka.PAAVOITTO;
            case 6:
                if (lisaNumeroLoytyi) {
                    return (VoittoLuokka.KUUSI_YKSI);
                }
                return VoittoLuokka.KUUSI;
            case 5:
                return VoittoLuokka.VIISI;
            case 4:
                return VoittoLuokka.NELJA;
            case 3:
                if (lisaNumeroLoytyi) {
                    return (VoittoLuokka.KOLME_YKSI);
                }
                return VoittoLuokka.EI_VOITTOA;
            case 2: // 0 - 2 oikein -> game over -> oli lisänumero mitä tahansa
            case 1:
            case 0:
            default:
                return VoittoLuokka.EI_VOITTOA;
        }
    }
}
