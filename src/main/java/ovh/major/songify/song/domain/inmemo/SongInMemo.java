package ovh.major.songify.song.domain.inmemo;

import lombok.Builder;

@Builder
public record SongInMemo(
        String name,
        String artist
) {
}