package ovh.major.songify.song.domain.model;

import lombok.*;

@Builder
public record SongInMemo (
        String name,
        String artist
) {}