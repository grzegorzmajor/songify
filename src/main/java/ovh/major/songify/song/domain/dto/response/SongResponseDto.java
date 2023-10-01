package ovh.major.songify.song.domain.dto.response;

import lombok.Builder;

@Builder
public record SongResponseDto(
        String songName,String artist) {
}
