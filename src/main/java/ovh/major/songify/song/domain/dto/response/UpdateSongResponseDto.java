package ovh.major.songify.song.domain.dto.response;

import lombok.Builder;

@Builder
public record UpdateSongResponseDto (
        String songName,
        String artist) {
}
