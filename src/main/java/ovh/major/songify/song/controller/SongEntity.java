package ovh.major.songify.song.controller;

import lombok.Builder;

@Builder
public record SongEntity(String name, String artist) {
}
