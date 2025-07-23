package dao;


import eccezioni.SegnalazioneGiaAvvenutaException;

import entita.Binario;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;

import java.util.*;

public class ArchivioSegnalazioniMemory {

    private static final Set<LevelCrossing> LevelCrossingSegnalati = new HashSet<>();
    private static final List<Binario> BinariSegnalati = new ArrayList<>();
    private static final Set<String> indirizzibinariSegnalate = new HashSet<>();

   //per memorizzare codice utente alle segnalazioni in memory
    private static final Map<String, List<EntitaFerroviaria>> segnalazioniPerUtente = new HashMap<>();

    private ArchivioSegnalazioniMemory() {}

    public static void salvaLevelCrossing(LevelCrossing nuovoPL, String codiceUtente) throws SegnalazioneGiaAvvenutaException {
        for (LevelCrossing pl : LevelCrossingSegnalati) {
            if (pl.getInfo().equals(nuovoPL.getInfo())) {
                throw new SegnalazioneGiaAvvenutaException("Level Crossing già segnalato con stesso codice ");
            }
        }
        LevelCrossingSegnalati.add(nuovoPL);
        registraSegnalazioneUtente(codiceUtente, nuovoPL);
    }


    public static void salvabinario(Binario nuovoBinario, String codiceUtente) throws SegnalazioneGiaAvvenutaException {
        for (Binario b : BinariSegnalati) {
            if (b.getInfo().equals(nuovoBinario.getInfo())
                    && b.getlocalizzazione().equals(nuovoBinario.getlocalizzazione())) {
                throw new SegnalazioneGiaAvvenutaException("Binario già segnalato con stesso numero e localizzazione.");
            }
        }
        BinariSegnalati.add(nuovoBinario);
        registraSegnalazioneUtente(codiceUtente, nuovoBinario);
    }


    private static void registraSegnalazioneUtente(String codiceUtente, EntitaFerroviaria entita) {
        segnalazioniPerUtente.computeIfAbsent(codiceUtente, k -> new ArrayList<>()).add(entita);
    }

    public static Set<LevelCrossing> getLevelCrossingSegnalati() {
        return Collections.unmodifiableSet(LevelCrossingSegnalati);
    }

    public static List<Binario> getBinariSegnalati() {
        return Collections.unmodifiableList(BinariSegnalati);
    }

    public static List<EntitaFerroviaria> getSegnalazioniPerUtente(String codiceUtente) {
        return segnalazioniPerUtente.getOrDefault(codiceUtente, Collections.emptyList());
    }
}


