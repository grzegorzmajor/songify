package ovh.major.songify.song.domain.postgres.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.postgres.SongEntity;
import ovh.major.songify.song.domain.postgres.repository.SongRepositoryPostgres;
import ovh.major.songify.song.domain.dto.response.SongResponseDto;
import ovh.major.songify.song.domain.mappers.SongResponseMapper;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SongPostgresRetriever {

    private final SongRepositoryPostgres songRepositoryPostgres;

    public List<SongResponseDto> getSongs(Pageable pageable) {
        List<SongEntity> songsEntity = songRepositoryPostgres.findAll(pageable);
        List<SongResponseDto> response = new ArrayList<>();
        songsEntity.forEach(song -> response.add(SongResponseMapper.formSongEntity(song)));

        return response;
    }

    public SongResponseDto getSongById(Integer songId) {
        SongEntity songEntity = songRepositoryPostgres.findById(songId)
                .orElseThrow();
        return SongResponseMapper.formSongEntity(songEntity);
    }

}
