package ovh.major.songify.song.domain.inmemo;

import ovh.major.songify.song.domain.dto.request.SingleSongRequestDto;

public class SongInMemoMapper {

    public static SongInMemo fromSingleSongRequestDto(SingleSongRequestDto song) {
        return SongInMemo.builder()
                .artist(song.artist())
                .name(song.songName())
                .build();
    }
}
