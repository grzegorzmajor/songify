package ovh.major.songify.song.infrastructure.controller.mappers;

import ovh.major.songify.song.domain.model.SongInMemo;
import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;

public class SongEntityMapper {
    //ToDo require change Song from InMemo to Entity

    public static SongInMemo fromSingleSongRequestDto(SingleSongRequestDto song) {
        return SongInMemo.builder()
                .artist(song.artist())
                .name(song.songName())
                .build();
    }
}
