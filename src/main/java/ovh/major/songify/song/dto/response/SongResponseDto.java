package ovh.major.songify.song.dto.response;

import ovh.major.songify.song.controller.SongEntity;

import java.util.Map;

public record SongResponseDto(Map<Integer, SongEntity> songs) {
}
