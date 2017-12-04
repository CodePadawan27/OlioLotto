package com.lottopaketti;

import java.text.DecimalFormat;
import java.util.*;

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
        //Väliaikaiseen listan tallentamiseen tarkoitettu HashSet. Listassa ei voi olla samoja lukuja.
        HashSet<Integer> lodo = new HashSet<>();
        for (int i = 0; i < 7; ) {
            int seuraavaLottonumero = r.nextInt(40) + 1;
            lodo.add(seuraavaLottonumero);
            i = lodo.size();
        }

        //Asetetaan arvottuLottorivi Arraylistiin HashSet listan luvut
        arvottuLottorivi = new ArrayList<>(lodo);
    }

    //Arpoo lisänumeron ja tulostaa sen
    public void ArvoLisaNumero() {
        boolean loytyyko = false;
        lisanumero = -1;
        do {
            lisanumero = r.nextInt(40) + 1;
            if (!arvottuLottorivi.contains(lisanumero)) {
                loytyyko = true;
            }
        } while (loytyyko == false);
        System.out.println(" ja lisänumero" + " " + lisanumero);
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

    // Tarkistetaan tuliko voittoja
    public void TarkistaVoitot(Pelaaja pelaajanRivit){
        List<Lottorivi<VoittoLuokka>> tulokset = new ArrayList<>();

        for (ArrayList<Integer> lottorivi : pelaajanRivit.PalautaLottorivi()) {
            tulokset.add(new Lottorivi<>(lottorivi, TarkistaLottoRivi(PalautaLottoRivi(), lottorivi, PalautaLisaNumero())));
        }

        for (Lottorivi<VoittoLuokka> tulos : tulokset) {
            String palkinto = "";
            switch (tulos.getValue()) {
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
            TulostaLottoRivi(tulos.getLottorivi());
            System.out.println("\n" + palkinto);
        }
    }

    public void TarkastaTodennakoisyydet(Pelaaja pelaajanRivit, Todennakoisyydet todennakoisyydet) {
        //Pelaajan lottorivit
        ArrayList<ArrayList<Integer>> lottoRivit = pelaajanRivit.PalautaLottorivi();

        //Todennäköisyys rivit
        List<Lottorivi<Integer>> todRivit = todennakoisyydet.PalautaTodennakoisyysRivit();

        List<Lottorivi<VoittoLuokka>> tulokset = new ArrayList<>();

        //Käydään läpi kaikilla käyttäjän antamilla riveillä
        for (ArrayList<Integer> lottorivi : lottoRivit) {
            //Tarkistetaan käyttäjän antama rivi kaikkia todennäköisyyslaskentaan generoitua riviä vastaan
            for (Lottorivi<Integer> tulos : todRivit) {
                tulokset.add(new Lottorivi<>(tulos.getLottorivi(), TarkistaLottoRivi(tulos.getLottorivi(), lottorivi, tulos.getValue())));
            }
        }

        Integer seitsemanOikein = 0;
        Integer kuusiYksiOikein = 0;
        Integer kuusiOikein = 0;
        Integer viisiOikein = 0;
        Integer neljaOikein = 0;
        Integer kolmeYksiOikein = 0;
        Integer eiVoittoa = 0;

        // Ynnätään oikein tulokset yhteen (kaikista käyttäjän antamista riveistä)
        for (Lottorivi<VoittoLuokka> tulos : tulokset) {
            switch (tulos.getValue()) {
                case PAAVOITTO:
                    seitsemanOikein++;
                    break;
                case KUUSI_YKSI:
                    kuusiYksiOikein++;
                    break;
                case KUUSI:
                    kuusiOikein++;
                    break;
                case VIISI:
                    viisiOikein++;
                    break;
                case NELJA:
                    neljaOikein++;
                    break;
                case KOLME_YKSI:
                    kolmeYksiOikein++;
                    break;
                default:
                    eiVoittoa++;
                    break;
            }
        }

        // Tulostetaan todennäköisyydet
        DecimalFormat df = new DecimalFormat("#0.0000000000");
        System.out.println("Seitsemän oikein (" + seitsemanOikein + ") = " + df.format((double) seitsemanOikein / tulokset.size()) + " %");
        System.out.println("Kuusi + yksi oikein (" + kuusiYksiOikein + ") = " + df.format((double) kuusiYksiOikein / tulokset.size()) + " %");
        System.out.println("Kuusi oikein (" + kuusiOikein + ") = " + df.format((double) kuusiOikein / tulokset.size()) + " %");
        System.out.println("Viisi oikein (" + viisiOikein + ") = " + df.format((double) viisiOikein / tulokset.size()) + " %");
        System.out.println("Neljä oikein (" + neljaOikein + ") = " + df.format((double) neljaOikein / tulokset.size()) + " %");
        System.out.println("Kolme + yksi oikein (" + kolmeYksiOikein + ") = " + df.format((double) kolmeYksiOikein / tulokset.size()) + " %");
        System.out.println();
    }

    //Metodi tarkistaa, osuiko joku lottoriveistä voittoluokkaan
    public VoittoLuokka TarkistaLottoRivi(ArrayList<Integer> arvottuLottorivi, ArrayList<Integer> lottoRivi, Integer lisaNumero) {
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
