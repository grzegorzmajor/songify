package ovh.major.songify.song.infrastructure.controller.dto.response;

import java.util.Map;

public record SongResponseDto(Map<Integer, SingleSongResponseDto> songs) {
}
