package ovh.major.songify.song.domain.dto.request;

import lombok.Builder;

@Builder
public record PartiallySingleSongRequestDto(
        String songName,
        String artist)
{

}
