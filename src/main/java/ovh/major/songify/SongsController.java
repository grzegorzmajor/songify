package ovh.major.songify;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
class SongsController {

    Map<Integer, String> simpleSongsDatabase = new HashMap<>(Map.of(
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
            Map<Integer, String> limitedMap = simpleSongsDatabase.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return ResponseEntity.ok(new SongResponseDto(limitedMap));
        }

        SongResponseDto response = new SongResponseDto(simpleSongsDatabase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(
            @PathVariable(name = "id") Integer songId,
            @RequestHeader(required = false) String requestId) {
        log.info("RequestId: " + requestId);
        String song = simpleSongsDatabase.get(songId);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<List<SingleSongResponseDto>> addSongs(@RequestBody @NotBlank List<SingleSongRequestDto> songs) {
        if (songs.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<SingleSongResponseDto> response = new ArrayList<>();

        songs.forEach(
                song -> {
                    int newKey = newKey(simpleSongsDatabase);
                    if (song.songName().length() > 0) {
                        simpleSongsDatabase.put(newKey, song.songName());
                        log.info("Added song named: " + song.songName() + " with id: " + newKey);
                        response.add(new SingleSongResponseDto(song.songName()));
                    } else {
                        log.info("Song skipped - empty name");
                    }
                });
        return ResponseEntity.ok(response);
    }

    private Integer newKey(Map<Integer, String> map) {
        Integer lastKey = lastKey(map);
        int newKey = 1;
        if (lastKey != null) {
            newKey = lastKey + 1;
        }
        return newKey;
    }
    private Integer lastKey(Map<Integer, String> map) {
        Integer lastKey = null;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            lastKey = entry.getKey();
        }
        return lastKey;    }

}

