package ovh.major.songify.song.infrastructure.controller.mappers;

import ovh.major.songify.song.domain.model.SongEntity;
import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;

public class SongEntityMapper {

    public static SongEntity fromSingleSongRequestDto(SingleSongRequestDto song) {
        return SongEntity.builder()
                .artist(song.artist())
                .name(song.songName())
                .build();
    }
}
