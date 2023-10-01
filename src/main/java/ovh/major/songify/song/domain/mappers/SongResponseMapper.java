package ovh.major.songify.song.domain.mappers;

import ovh.major.songify.song.domain.postgres.SongEntity;
import ovh.major.songify.song.domain.inmemo.SongInMemo;
import ovh.major.songify.song.domain.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.domain.dto.response.SongResponseDto;

public class SongResponseMapper {

    public static SongResponseDto formSongInMemo(SongInMemo song) {
        return SongResponseDto.builder()
                .songName(song.name())
                .artist(song.artist())
                .build();
    }

    public static SongResponseDto formSongEntity(SongEntity song) {
        return SongResponseDto.builder()
                .songName(song.getName())
                .artist(song.getArtist())
                .build();
    }

    public static SongResponseDto formSingleSongRequestDto(SingleSongRequestDto song) {
        return SongResponseDto.builder()
                .songName(song.songName())
                .artist(song.artist())
                .build();
    }

}
