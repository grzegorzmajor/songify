package ovh.major.songify.song.infrastructure.controller.dto.response;

import lombok.Builder;

@Builder
public record UpdateSongResponseDto (
        String songName,
        String artist) {
}
