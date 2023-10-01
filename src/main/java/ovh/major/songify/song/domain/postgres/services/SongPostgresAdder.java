package ovh.major.songify.song.domain.postgres.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.postgres.SongEntity;
import ovh.major.songify.song.domain.postgres.repository.SongRepositoryPostgres;
import ovh.major.songify.song.domain.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.domain.postgres.SongEntityMapper;

@Log4j2
@Service
@AllArgsConstructor
public class SongPostgresAdder {

    private final SongRepositoryPostgres songRepositoryPostgres;

    public void addSong(SingleSongRequestDto songRequest) {
        SongEntity savedSong = songRepositoryPostgres.save(SongEntityMapper.fromSingleSongRequestDto(songRequest));
        log.info("Added song: " + savedSong.getArtist() + " " + savedSong.getName() + " with id: " + savedSong.getId());
    }
}
