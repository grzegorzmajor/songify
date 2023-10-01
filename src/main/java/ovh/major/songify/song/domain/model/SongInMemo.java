package ovh.major.songify.song.domain.model;

import lombok.Builder;

@Builder
public record SongInMemo(
        String name,
        String artist
) {
}