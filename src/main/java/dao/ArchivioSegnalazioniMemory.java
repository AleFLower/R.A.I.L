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

    public static void salvaLevelCrossing(LevelCrossing levelCrossing, String codiceUtente) throws SegnalazioneGiaAvvenutaException {
        if (!LevelCrossingSegnalati.add(levelCrossing)) {
            throw new SegnalazioneGiaAvvenutaException("Level Crossing già segnalato in memoria.");
        }
        registraSegnalazioneUtente(codiceUtente, levelCrossing);
    }

    public static void salvabinario(Binario binario, String codiceUtente) throws SegnalazioneGiaAvvenutaException {
        if (!indirizzibinariSegnalate.add(binario.getlocalizzazione())) {
            throw new SegnalazioneGiaAvvenutaException("Binario già segnalato in memoria.");
        }
        BinariSegnalati.add(binario);
        registraSegnalazioneUtente(codiceUtente, binario);
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


