package ovh.major.songify.song.domain.service.inmemo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.model.SongInMemo;
import ovh.major.songify.song.domain.model.SongNotFoundException;
import ovh.major.songify.song.domain.repository.SongRepositoryInMemo;
import ovh.major.songify.song.infrastructure.controller.dto.response.SongResponseDto;
import ovh.major.songify.song.infrastructure.controller.mappers.SongResponseMapper;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class SongInMemoRetriever {

    private final SongRepositoryInMemo simpleSongsDatabase;

    public Map<Integer, SongResponseDto> getSongs(Integer limit) {
        Map<Integer, SongInMemo> request = simpleSongsDatabase.getSongsLimited(limit);
        Map<Integer, SongResponseDto> response = new HashMap<>();
        request.forEach((id,song) -> {
            response.put(id, SongResponseMapper.formSongInMemo(song));
        });

        return response;
    }

    public Map<Integer, SongResponseDto> getSongs() {
        Map<Integer, SongInMemo> request = simpleSongsDatabase.getAllSongs();
        Map<Integer, SongResponseDto> response = new HashMap<>();
        request.forEach((id,song) -> {
            response.put(id, SongResponseMapper.formSongInMemo(song));
        });
        return response;
    }

    public SongResponseDto getSongById(Integer songId) {
        if (!simpleSongsDatabase.containsKey(songId)) {
            throw new SongNotFoundException("Song with id " + songId + " not found.");
        }
        return SongResponseMapper.formSongInMemo(simpleSongsDatabase.getSong(songId));
    }

}
