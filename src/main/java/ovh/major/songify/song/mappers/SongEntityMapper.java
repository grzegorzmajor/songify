package ovh.major.songify.song.mappers;

import ovh.major.songify.song.controller.SongEntity;
import ovh.major.songify.song.dto.request.SingleSongRequestDto;

public class SongEntityMapper {

    public static SongEntity fromSingleSongRequestDto(SingleSongRequestDto song) {
        return SongEntity.builder()
                .artist(song.artist())
                .name(song.songName())
                .build();
    }
}
