package ovh.major.songify.song.domain.repository;

import org.springframework.stereotype.Repository;
import ovh.major.songify.song.domain.model.SongEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SimpleSongDatabase {
    Map<Integer, SongEntity> simpleSongsDatabase = ExampleData.get();

    public Boolean containsKey(Integer songId) {
        return simpleSongsDatabase.containsKey(songId);
    }

    public SongEntity getSong(Integer id) {
        return simpleSongsDatabase.get(id);
    }

    public Map<Integer, SongEntity> getSongsLimited(Integer limit) {
        return simpleSongsDatabase.entrySet().stream().limit(limit).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, SongEntity> getAllSongs() {
        return new HashMap<>(simpleSongsDatabase);
    }

    public Integer saveToDatabase(SongEntity song) {
        Integer newKey = newKey(simpleSongsDatabase);
        simpleSongsDatabase.put(newKey, song);
        return newKey;
    }

    public SongEntity saveToDatabase(Integer id, SongEntity song) {
        SongEntity oldSong = simpleSongsDatabase.get(id);
        simpleSongsDatabase.put(id, song);
        return oldSong;
    }

    private Integer newKey(Map<Integer, SongEntity> map) {
        Integer lastKey = lastKey(map);
        int newKey = 1;
        if (lastKey != null) {
            newKey = lastKey + 1;
        }
        return newKey;
    }

    private Integer lastKey(Map<Integer, SongEntity> map) {
        Integer lastKey = null;
        for (Map.Entry<Integer, SongEntity> entry : map.entrySet()) {
            lastKey = entry.getKey();
        }
        return lastKey;
    }

    public void remove(Integer songId) {
        simpleSongsDatabase.remove(songId);
    }
}
