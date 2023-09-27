package ovh.major.songify.song.dto.response;

import lombok.Builder;

@Builder
public record UpdateSongResponseDto (
        String songName,
        String artist) {
}
