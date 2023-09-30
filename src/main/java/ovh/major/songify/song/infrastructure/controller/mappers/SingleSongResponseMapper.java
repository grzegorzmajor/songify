package ovh.major.songify.song.infrastructure.controller.mappers;

import ovh.major.songify.song.domain.model.SongInMemo;
import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.response.SingleSongResponseDto;

public class SingleSongResponseMapper {
    //ToDo require adding mapper from Entity

    public static SingleSongResponseDto formSongInMemo(SongInMemo song) {
        return SingleSongResponseDto.builder()
                .songName(song.name())
                .artist(song.artist())
                .build();
    }

    public static SingleSongResponseDto formSingleSongRequestDto(SingleSongRequestDto song) {
        return SingleSongResponseDto.builder()
                .songName(song.songName())
                .artist(song.artist())
                .build();
    }

}
