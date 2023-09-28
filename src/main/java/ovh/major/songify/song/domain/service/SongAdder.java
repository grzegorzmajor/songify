package ovh.major.songify.song.domain.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.repository.SimpleSongDatabase;
import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.mappers.SongEntityMapper;


@Log4j2
@Service
@AllArgsConstructor
public class SongAdder {

    private final SimpleSongDatabase simpleSongsDatabase;


    public void addSong(SingleSongRequestDto songRequest) {
        Integer newKey = simpleSongsDatabase.saveToDatabase(SongEntityMapper.fromSingleSongRequestDto(songRequest));
        log.info("Added song named: " + songRequest.songName() + " with id: " + newKey);
    }

}
