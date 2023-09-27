package ovh.major.songify.song.error;

import org.springframework.http.HttpStatus;

public record ErrorSongResponseDto (String message, HttpStatus httpStatus) {
}
