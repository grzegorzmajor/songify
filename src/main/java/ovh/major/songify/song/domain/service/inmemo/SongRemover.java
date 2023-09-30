package ovh.major.songify.song.domain.service.inmemo;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.model.SongNotFoundException;
import ovh.major.songify.song.domain.repository.SongRepositoryInMemo;
import ovh.major.songify.song.infrastructure.controller.dto.response.DeleteRemoveSongDto;

@AllArgsConstructor
@Service
@Log4j2
public class SongRemover {

    private final SongRepositoryInMemo simpleSongsDatabase;

    public DeleteRemoveSongDto remove(Integer songId) {
        if (simpleSongsDatabase.containsKey(songId)) {
            simpleSongsDatabase.remove(songId);
            log.info("Song Removed");
            return new DeleteRemoveSongDto("Song with id " + songId + " has been deleted", HttpStatus.OK);
        } else {
            throw new SongNotFoundException("Song with id " + songId + " not found.");
        }
    }
}
