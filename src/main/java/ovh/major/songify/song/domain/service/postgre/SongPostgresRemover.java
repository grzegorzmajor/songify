package ovh.major.songify.song.domain.service.postgre;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.repository.SongRepositoryPostgres;
import ovh.major.songify.song.infrastructure.controller.dto.response.DeletingSongStatusDto;

@AllArgsConstructor
@Service
@Log4j2
public class SongPostgresRemover {

    private final SongRepositoryPostgres songRepositoryPostgres;

    public DeletingSongStatusDto remove(Integer songId) {
        songRepositoryPostgres.deleteById(songId);
        log.info("Song Removed");
        return new DeletingSongStatusDto("Song with id " + songId + " has been deleted", HttpStatus.OK);
    }
}
