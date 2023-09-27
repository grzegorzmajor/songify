package ovh.major.songify.song.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PartialySingleSongRequestDto(
        String songName,
        String artist)
{

}
