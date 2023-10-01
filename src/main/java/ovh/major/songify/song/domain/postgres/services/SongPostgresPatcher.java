package ovh.major.songify.song.domain.postgres.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ovh.major.songify.song.domain.postgres.SongEntity;
import ovh.major.songify.song.domain.postgres.repository.SongRepositoryPostgres;
import ovh.major.songify.song.domain.dto.request.PartiallySingleSongRequestDto;
import ovh.major.songify.song.domain.dto.response.SongResponseDto;
import ovh.major.songify.song.domain.mappers.SongResponseMapper;

import java.util.Optional;


@Log4j2
@Service
@AllArgsConstructor
public class SongPostgresPatcher {

    private final SongRepositoryPostgres songRepositoryPostgres;

    public SongResponseDto updateSong(Integer id, PartiallySingleSongRequestDto requestDto) {
        SongEntity songEntity = songRepositoryPostgres.findById(id)
                .orElseThrow();
        if (requestDto.songName()!=null) {
            songEntity.setName(requestDto.songName());
        }
        if (requestDto.artist()!=null) {
            songEntity.setArtist(requestDto.artist());
        }
        Integer updatedId = songRepositoryPostgres.updateById(id,songEntity);
        Optional<SongEntity> updatedSong = songRepositoryPostgres.findById(updatedId);
        SongEntity song = updatedSong.orElseThrow();
        return SongResponseMapper.formSongEntity(song);
    }

}