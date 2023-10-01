package ovh.major.songify.song.domain.repository;

import org.springframework.stereotype.Repository;
import ovh.major.songify.song.domain.model.SongInMemo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SongRepositoryInMemo {
    Map<Integer, SongInMemo> simpleSongsDatabase = ExampleData.get();

    public Boolean containsKey(Integer songId) {
        return simpleSongsDatabase.containsKey(songId);
    }

    public SongInMemo getSong(Integer id) {
        return simpleSongsDatabase.get(id);
    }

    public Map<Integer, SongInMemo> getSongsLimited(Integer limit) {
        return simpleSongsDatabase.entrySet().stream().limit(limit).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, SongInMemo> getAllSongs() {
        return new HashMap<>(simpleSongsDatabase);
    }

    public Integer saveToDatabase(SongInMemo song) {
        Integer newKey = newKey(simpleSongsDatabase);
        simpleSongsDatabase.put(newKey, song);
        return newKey;
    }

    public SongInMemo saveToDatabase(Integer id, SongInMemo song) {
        SongInMemo oldSong = simpleSongsDatabase.get(id);
        simpleSongsDatabase.put(id, song);
        return oldSong;
    }

    private Integer newKey(Map<Integer, SongInMemo> map) {
        Integer lastKey = lastKey(map);
        int newKey = 1;
        if (lastKey != null) {
            newKey = lastKey + 1;
        }
        return newKey;
    }

    private Integer lastKey(Map<Integer, SongInMemo> map) {
        Integer lastKey = null;
        for (Map.Entry<Integer, SongInMemo> entry : map.entrySet()) {
            lastKey = entry.getKey();
        }
        return lastKey;
    }

    public void remove(Integer songId) {
        simpleSongsDatabase.remove(songId);
    }

    public void deleteAll() {
        simpleSongsDatabase.clear();
    }
}
