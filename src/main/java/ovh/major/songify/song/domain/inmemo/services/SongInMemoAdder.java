package ovh.major.songify.song.domain.inmemo.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.inmemo.repository.SongRepositoryInMemo;
import ovh.major.songify.song.domain.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.domain.inmemo.SongInMemoMapper;


@Log4j2
@Service
@AllArgsConstructor
public class SongInMemoAdder {

    private final SongRepositoryInMemo simpleSongsDatabase;


    public void addSong(SingleSongRequestDto songRequest) {
        Integer newKey = simpleSongsDatabase.saveToDatabase(SongInMemoMapper.fromSingleSongRequestDto(songRequest));
        log.info("Added song named: " + songRequest.songName() + " with id: " + newKey);
    }

}
