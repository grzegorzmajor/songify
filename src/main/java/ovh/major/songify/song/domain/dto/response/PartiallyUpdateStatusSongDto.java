package ovh.major.songify.song.domain.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public record PartiallyUpdateStatusSongDto(String message, HttpStatus status) {
}
