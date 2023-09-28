package ovh.major.songify.song.domain.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.model.SongEntity;
import ovh.major.songify.song.domain.model.SongNotFoundException;
import ovh.major.songify.song.domain.repository.SimpleSongDatabase;
import ovh.major.songify.song.infrastructure.controller.dto.response.SingleSongResponseDto;
import ovh.major.songify.song.infrastructure.controller.mappers.SingleSongResponseMapper;

@Log4j2
@Service
@AllArgsConstructor
public class SongPatcher {

    private final SimpleSongDatabase simpleSongsDatabase;

    public SingleSongResponseDto updateSong(Integer id, SongEntity song) {
        if (!simpleSongsDatabase.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found.");
        }
        log.info("Changing song with id " + id );
        return SingleSongResponseMapper.formSongEntity(simpleSongsDatabase.saveToDatabase(id, song));
    }


}
