package ovh.major.songify.song.domain.postgres.services;

import lombok.AllArgsConstructor;
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
    //ToDo require adding pagination

    private final SongRepositoryPostgres songRepositoryPostgres;

    public List<SongResponseDto> getSongs() {
        List<SongEntity> songsEntity = songRepositoryPostgres.findAll();
        List<SongResponseDto> response = new ArrayList<>();
        songsEntity.forEach(song -> {
            response.add(SongResponseMapper.formSongEntity(song));
        });

        return response;
    }

//    public Map<Integer, SongResponseDto> getSongs() {
//        Map<Integer, SongInMemo> request = simpleSongsDatabase.getAllSongs();
//        Map<Integer, SongResponseDto> response = new HashMap<>();
//        request.forEach((id,song) -> {
//            response.put(id,SingleSongResponseMapper.formSongInMemo(song));
//        });
//        return response;
//    }

    public SongResponseDto getSongById(Integer songId) {
        SongEntity songEntity = songRepositoryPostgres.findById(songId)
                .orElseThrow();
        return SongResponseMapper.formSongEntity(songEntity);
    }

}