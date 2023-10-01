package ovh.major.songify.song.domain.repository;

import ovh.major.songify.song.domain.model.SongInMemo;

import java.util.HashMap;
import java.util.Map;

public class ExampleData {

    public static Map<Integer, SongInMemo> get() {
        return new HashMap<>(Map.of(
                1, new SongInMemo("Wlazł kotek na płotek", "Unknown"),
                2, new SongInMemo("Z popielnika na Wojtusia iskiereczka mruga", "Unknown"),
                3, new SongInMemo("Ach śpij Kochanie", "Unknown"),
                4, new SongInMemo("Gdzie strumyk płynie z wolna", "Unknown"),
                5, new SongInMemo("Jedzie pociąg z daleka", "Unknown"),
                6, new SongInMemo("Miała baba koguta", "Unknown"),
                7, new SongInMemo("Mydło wszystko umyje", "Fasolki")
        ));
    }
}