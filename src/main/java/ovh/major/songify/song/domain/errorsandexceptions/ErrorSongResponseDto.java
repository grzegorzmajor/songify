package ovh.major.songify.song.domain.errorsandexceptions;

import org.springframework.http.HttpStatus;

public record ErrorSongResponseDto (String message, HttpStatus httpStatus) {
}
