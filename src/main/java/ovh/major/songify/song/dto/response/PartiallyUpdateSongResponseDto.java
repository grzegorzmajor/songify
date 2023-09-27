package ovh.major.songify.song.dto.response;

import org.springframework.http.HttpStatus;

public record PartiallyUpdateSongResponseDto(String message, HttpStatus status) {
}
