package ovh.major.songify.song.infrastructure.controller.dto.request;

import lombok.Builder;

@Builder
public record PartiallySingleSongRequestDto(
        String songName,
        String artist)
{

}
