package ovh.major.songify.song.infrastructure.controller.dto.response;

import lombok.Builder;

@Builder
public record SongResponseDto(
        String songName,String artist) {
}
