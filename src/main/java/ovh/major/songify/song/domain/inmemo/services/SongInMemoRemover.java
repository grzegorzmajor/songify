package ovh.major.songify.song.domain.inmemo.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.inmemo.repository.SongRepositoryInMemo;
import ovh.major.songify.song.domain.errorsandexceptions.SongNotFoundException;
import ovh.major.songify.song.domain.dto.response.DeletingSongStatusDto;

@AllArgsConstructor
@Service
@Log4j2
public class SongInMemoRemover {

    private final SongRepositoryInMemo simpleSongsDatabase;

    public DeletingSongStatusDto remove(Integer songId) {
        if (simpleSongsDatabase.containsKey(songId)) {
            simpleSongsDatabase.remove(songId);
            log.info("Song Removed");
            return new DeletingSongStatusDto("Song with id " + songId + " has been deleted", HttpStatus.OK);
        } else {
            throw new SongNotFoundException("Song with id " + songId + " not found.");
        }
    }
}
