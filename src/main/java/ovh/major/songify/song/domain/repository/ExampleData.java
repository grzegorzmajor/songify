package ovh.major.songify.song.domain.repository;

import ovh.major.songify.song.domain.model.SongEntity;

import java.util.HashMap;
import java.util.Map;

public class ExampleData {

    public static Map<Integer, SongEntity> get() {
        return new HashMap<>(Map.of(
                1, new SongEntity("Wlazł kotek na płotek", "Unknown"),
                2, new SongEntity("Z popielnika na Wojtusia iskiereczka mruga", "Unknown"),
                3, new SongEntity("Ach śpij Kochanie", "Unknown"),
                4, new SongEntity("Gdzie strumyk płynie z wolna", "Unknown"),
                5, new SongEntity("Jedzie pociąg z daleka", "Unknown"),
                6, new SongEntity("Miała baba koguta", "Unknown"),
                7, new SongEntity("Mydło wszystko umyje", "Fasolki")
        ));
    }
}
