package ovh.major.songify.song.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SingleSongRequestDto(
        @NotNull(message = "songName must not be null")
        @NotEmpty(message = "songName must not be empty")
        String songName,
        @NotNull(message = "songName must not be null")
        @NotEmpty(message = "songName must not be empty")
        String artist
) {
}
