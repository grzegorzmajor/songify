package ovh.major.songify.song.infrastructure.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorSongResponseDto (String message, HttpStatus httpStatus) {
}