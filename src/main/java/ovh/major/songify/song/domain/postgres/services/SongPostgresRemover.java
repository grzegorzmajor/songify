package ovh.major.songify.song.domain.postgres.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.postgres.repository.SongRepositoryPostgres;
import ovh.major.songify.song.domain.dto.response.DeletingSongStatusDto;

@AllArgsConstructor
@Service
@Log4j2
@Transactional
public class SongPostgresRemover {

    private final SongRepositoryPostgres songRepositoryPostgres;

    public DeletingSongStatusDto remove(Integer songId) {
        songRepositoryPostgres.deleteById(songId);
        log.info("Song Removed");
        return new DeletingSongStatusDto("Song with id " + songId + " has been deleted", HttpStatus.OK);
    }
}
