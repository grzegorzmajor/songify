package ovh.major.songify.song.infrastructure.controller.dto.response;

import org.springframework.http.HttpStatus;

public record DeletingSongStatusDto(String message, HttpStatus httpStatus) {
}
