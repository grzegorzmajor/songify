package ovh.major.songify.song.mappers;

import ovh.major.songify.song.controller.SongEntity;
import ovh.major.songify.song.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.dto.response.SingleSongResponseDto;

public class SingleSongResponseMapper {

    public static SingleSongResponseDto formSongEntity(SongEntity song) {
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
