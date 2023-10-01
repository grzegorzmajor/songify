package ovh.major.songify.song.infrastructure.controller.dto.response;

import java.util.Map;

public record SongsInMemoResponseDto(Map<Integer, SongResponseDto> songs) {
}
