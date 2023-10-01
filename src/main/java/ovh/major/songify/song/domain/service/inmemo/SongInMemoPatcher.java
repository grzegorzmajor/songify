package ovh.major.songify.song.domain.service.inmemo;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.model.SongInMemo;
import ovh.major.songify.song.domain.model.SongNotFoundException;
import ovh.major.songify.song.domain.repository.SongRepositoryInMemo;
import ovh.major.songify.song.infrastructure.controller.dto.response.SongResponseDto;
import ovh.major.songify.song.infrastructure.controller.mappers.SongResponseMapper;

@Log4j2
@Service
@AllArgsConstructor
public class SongInMemoPatcher {

    private final SongRepositoryInMemo simpleSongsDatabase;

    public SongResponseDto updateSong(Integer id, SongInMemo song) {
        if (!simpleSongsDatabase.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found.");
        }
        log.info("Changing song with id " + id );
        return SongResponseMapper.formSongInMemo(simpleSongsDatabase.saveToDatabase(id, song));
    }


}
