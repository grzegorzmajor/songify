package ovh.major.songify.song.domain.dto.response;

import java.util.Map;

public record SongsInMemoResponseDto(Map<Integer, SongResponseDto> songs) {
}
