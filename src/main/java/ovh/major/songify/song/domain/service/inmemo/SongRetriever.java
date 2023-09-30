package ovh.major.songify.song.domain.service.inmemo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.model.SongInMemo;
import ovh.major.songify.song.domain.model.SongNotFoundException;
import ovh.major.songify.song.domain.repository.SongRepositoryInMemo;
import ovh.major.songify.song.infrastructure.controller.dto.response.SingleSongResponseDto;
import ovh.major.songify.song.infrastructure.controller.mappers.SingleSongResponseMapper;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class SongRetriever {

    private final SongRepositoryInMemo simpleSongsDatabase;

    public Map<Integer, SingleSongResponseDto> getSongs(Integer limit) {
        Map<Integer, SongInMemo> request = simpleSongsDatabase.getSongsLimited(limit);
        Map<Integer, SingleSongResponseDto> response = new HashMap<>();
        request.forEach((id,song) -> {
            response.put(id,SingleSongResponseMapper.formSongInMemo(song));
        });

        return response;
    }

    public Map<Integer, SingleSongResponseDto> getSongs() {
        Map<Integer, SongInMemo> request = simpleSongsDatabase.getAllSongs();
        Map<Integer, SingleSongResponseDto> response = new HashMap<>();
        request.forEach((id,song) -> {
            response.put(id,SingleSongResponseMapper.formSongInMemo(song));
        });
        return response;
    }

    public SingleSongResponseDto getSongById(Integer songId) {
        if (!simpleSongsDatabase.containsKey(songId)) {
            throw new SongNotFoundException("Song with id " + songId + " not found.");
        }
        return SingleSongResponseMapper.formSongInMemo(simpleSongsDatabase.getSong(songId));
    }

}
