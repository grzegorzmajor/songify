package ovh.major.songify.song.dto.response;

import lombok.Builder;

@Builder
public record SingleSongResponseDto(
        String songName,String artist) {
}
