package ovh.major.songify.song.domain.model;

import lombok.Builder;

@Builder
public record SongEntity(String name, String artist) {
}
