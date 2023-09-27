package ovh.major.songify.song.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PartialySingleSongRequestDto(
        String songName,
        String artist)
{

}
