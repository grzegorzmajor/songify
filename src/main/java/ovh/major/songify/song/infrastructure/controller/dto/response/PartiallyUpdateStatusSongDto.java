package ovh.major.songify.song.infrastructure.controller.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public record PartiallyUpdateStatusSongDto(String message, HttpStatus status) {
}
