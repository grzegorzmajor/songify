package ovh.major.songify;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
class SongsController {

    Map<Integer, String> simpleDatabase = new HashMap<>(Map.of(
            1, "Wlazł kotek na płotek",
            2, "Z popielnika na Wojtusia iskiereczka mruga",
            3, "Ach śpij Kochanie",
            4, "Gdzie strumyk płynie z wolna",
            5, "Jedzie pociąg z daleka",
            6, "Miała baba koguta"
    ));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, String> limitedMap = simpleDatabase.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return ResponseEntity.ok(new SongResponseDto(limitedMap));
        }

        SongResponseDto response = new SongResponseDto(simpleDatabase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(
            @PathVariable(name = "id") Integer songId,
            @RequestHeader(required = false) String requestId) {
        log.info("RequestId: " + requestId);
        String song = simpleDatabase.get(songId);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

}

