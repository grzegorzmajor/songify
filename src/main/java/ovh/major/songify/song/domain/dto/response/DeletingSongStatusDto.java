package ovh.major.songify.song.domain.dto.response;

import org.springframework.http.HttpStatus;

public record DeletingSongStatusDto(String message, HttpStatus httpStatus) {
}
