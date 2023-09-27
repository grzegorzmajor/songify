package ovh.major.songify.song.infrastructure.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ovh.major.songify.song.infrastructure.controller.dto.request.PartialySingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.response.*;
import ovh.major.songify.song.infrastructure.controller.mappers.SingleSongResponseMapper;
import ovh.major.songify.song.infrastructure.controller.mappers.SongEntityMapper;
import ovh.major.songify.song.infrastructure.controller.mappers.UpdateSongResponseMapper;
import ovh.major.songify.song.domain.model.SongNotFoundException;
import ovh.major.songify.song.domain.model.SongEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    Map<Integer, SongEntity> simpleSongsDatabase = exampleData();

    @GetMapping
    public ResponseEntity<SongResponseDto> getSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, SongEntity> limitedMap = simpleSongsDatabase.entrySet().stream().limit(limit).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return ResponseEntity.ok(new SongResponseDto(limitedMap));
        }
        SongResponseDto response = new SongResponseDto(simpleSongsDatabase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable(name = "id") Integer songId, @RequestHeader(required = false) String requestId) {
        log.info("RequestId: " + requestId);
        if (!simpleSongsDatabase.containsKey(songId)) {
            throw new SongNotFoundException("Song with id " + songId + " not found.");
        }
        SongEntity song = simpleSongsDatabase.get(songId);
        SingleSongResponseDto response = SingleSongResponseMapper.formSongEntity(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SingleSongResponseDto> addSongs(@RequestBody @Valid @NotBlank @NotEmpty SingleSongRequestDto songRequest) {
        int newKey = newKey(simpleSongsDatabase);
        simpleSongsDatabase.put(newKey, SongEntityMapper.fromSingleSongRequestDto(songRequest));
        log.info("Added song named: " + songRequest.songName() + " with id: " + newKey);
        return ResponseEntity.ok(SingleSongResponseMapper.formSingleSongRequestDto(songRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteRemoveSongDto> deleteSongsById(@PathVariable(name = "id") Integer songId, @RequestHeader(required = false) String requestId) {
        log.info("DELETING RequestId: " + requestId);
        if (simpleSongsDatabase.containsKey(songId)) {
            simpleSongsDatabase.remove(songId);
            log.info("Song Removed");
            return ResponseEntity.ok().body(new DeleteRemoveSongDto("Song with id " + songId + " has been deleted", HttpStatus.OK));
        } else {
            throw new SongNotFoundException("Song with id " + songId + " not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> putSongById(@PathVariable Integer id, @RequestBody @Valid SingleSongRequestDto songRequest) {
        if (!simpleSongsDatabase.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found.");
        }
        SongEntity oldSong = simpleSongsDatabase.put(id, SongEntityMapper.fromSingleSongRequestDto(songRequest));
        log.info("Song with id " + id + " was changed");
        return ResponseEntity.ok(UpdateSongResponseMapper.fromSingleSongRequestDto(songRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> patchSongById(@PathVariable Integer id, @RequestBody PartialySingleSongRequestDto songRequest) {
        if (!simpleSongsDatabase.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found.");
        }
        SongEntity song = simpleSongsDatabase.get(id);
        SongEntity newSong = SongEntity.builder()
                .artist((songRequest.artist() == null) ? song.artist() : songRequest.artist())
                .name((songRequest.songName() == null) ? song.name() : songRequest.songName())
                .build();

        simpleSongsDatabase.put(id, newSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto("Song Updated", HttpStatus.OK));
    }

    @PostMapping("/list")
    public ResponseEntity<List<SingleSongResponseDto>> addSongs(@RequestBody List<SingleSongRequestDto> songsRequest) {
        if (songsRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<SingleSongResponseDto> response = new ArrayList<>();

        songsRequest.forEach(song -> {
            int newKey = newKey(simpleSongsDatabase);
            if (song.songName().length() > 0) {
                simpleSongsDatabase.put(newKey, SongEntityMapper.fromSingleSongRequestDto(song));
                log.info("Added song named: " + song.songName() + " with id: " + newKey);
                response.add(SingleSongResponseMapper.formSingleSongRequestDto(song));
            } else {
                log.info("Song skipped - empty name");
            }
        });
        return ResponseEntity.ok(response);
    }

    private Integer newKey(Map<Integer, SongEntity> map) {
        Integer lastKey = lastKey(map);
        int newKey = 1;
        if (lastKey != null) {
            newKey = lastKey + 1;
        }
        return newKey;
    }

    private Integer lastKey(Map<Integer, SongEntity> map) {
        Integer lastKey = null;
        for (Map.Entry<Integer, SongEntity> entry : map.entrySet()) {
            lastKey = entry.getKey();
        }
        return lastKey;
    }

    private Map<Integer, SongEntity> exampleData() {
        return new HashMap<>(Map.of(
                1, new SongEntity("Wlazł kotek na płotek", "Unknown"),
                2, new SongEntity("Z popielnika na Wojtusia iskiereczka mruga", "Unknown"),
                3, new SongEntity("Ach śpij Kochanie", "Unknown"),
                4, new SongEntity("Gdzie strumyk płynie z wolna", "Unknown"),
                5, new SongEntity("Jedzie pociąg z daleka", "Unknown"),
                6, new SongEntity("Miała baba koguta", "Unknown"),
                7, new SongEntity("Mydło wszystko umyje", "Fasolki")
        ));
    }

}

