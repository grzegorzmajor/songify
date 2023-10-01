package ovh.major.songify.song.domain.postgres;

import ovh.major.songify.song.domain.dto.request.SingleSongRequestDto;

public class SongEntityMapper {

    public static SongEntity fromSingleSongRequestDto(SingleSongRequestDto song) {
        return SongEntity.builder()
                .artist(song.artist())
                .name(song.songName())
                .build();
    }
}
