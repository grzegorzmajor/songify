package ovh.major.songify.song.infrastructure.controller.dto.response;

import ovh.major.songify.song.domain.model.SongEntity;

import java.util.Map;

public record SongResponseDto(Map<Integer, SongEntity> songs) {
}
