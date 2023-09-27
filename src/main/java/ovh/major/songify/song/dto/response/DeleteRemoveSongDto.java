package ovh.major.songify.song.dto.response;

import org.springframework.http.HttpStatus;

public record DeleteRemoveSongDto(String message, HttpStatus httpStatus) {
}
