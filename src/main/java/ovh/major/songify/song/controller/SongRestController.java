package ovh.major.songify.song.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ovh.major.songify.song.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.dto.response.DeleteRemoveSongDto;
import ovh.major.songify.song.dto.response.SingleSongResponseDto;
import ovh.major.songify.song.dto.response.SongResponseDto;
import ovh.major.songify.song.dto.response.UpdateSongResponseDto;
import ovh.major.songify.song.error.SongNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SongRestController {

    Map<Integer, SongEntity> simpleSongsDatabase = new HashMap<>(Map.of(
            1, new SongEntity("Wlazł kotek na płotek", "Unknown"),
            2, new SongEntity("Z popielnika na Wojtusia iskiereczka mruga", "Unknown"),
            3, new SongEntity("Ach śpij Kochanie", "Unknown"),
            4, new SongEntity("Gdzie strumyk płynie z wolna", "Unknown"),
            5, new SongEntity("Jedzie pociąg z daleka", "Unknown"),
            6, new SongEntity("Miała baba koguta", "Unknown")));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, SongEntity> limitedMap = simpleSongsDatabase.entrySet().stream().limit(limit).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return ResponseEntity.ok(new SongResponseDto(limitedMap));
        }

        SongResponseDto response = new SongResponseDto(simpleSongsDatabase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable(name = "id") Integer songId, @RequestHeader(required = false) String requestId) {
        log.info("RequestId: " + requestId);
        if (!simpleSongsDatabase.containsKey(songId)) {
            throw new SongNotFoundException("Song with id " + songId + " not found.");
        }
        SongEntity song = simpleSongsDatabase.get(songId);
        SingleSongResponseDto response = SingleSongResponseDto.builder()
                .artist(song.artist())
                .songName(song.name())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/song")
    public ResponseEntity<SingleSongResponseDto> addSongs(@RequestBody @Valid @NotBlank @NotEmpty SingleSongRequestDto song) {
        int newKey = newKey(simpleSongsDatabase);
        simpleSongsDatabase.put(newKey, SongEntity.builder()
                .artist(song.artist())
                .name(song.songName())
                .build());
        log.info("Added song named: " + song.songName() + " with id: " + newKey);
        return ResponseEntity.ok(SingleSongResponseDto.builder()
                .songName(song.songName())
                .artist(song.artist())
                .build());
    }

    @DeleteMapping("/songs/{id}")
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

    @PutMapping("/songs/{id}")
    public ResponseEntity<UpdateSongResponseDto> putSongById(@PathVariable Integer id, @RequestBody @Valid SingleSongRequestDto song) {
        if (!simpleSongsDatabase.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found.");
        }
        SongEntity oldSong = simpleSongsDatabase.replace(id, SongEntity.builder()
                .name(song.songName())
                .artist(song.artist())
                .build());
        log.info("Song with id " + id + " with old name " + oldSong + " was changed. New name id " + song.songName());
        return ResponseEntity.ok(UpdateSongResponseDto.builder()
                .artist(song.artist())
                .songName(song.songName())
                .build());
    }

    @PatchMapping("/songs/{id}/{newId}")
    public ResponseEntity<UpdateSongResponseDto> patchSongById(@PathVariable Integer id, @PathVariable Integer newId, @RequestBody SingleSongRequestDto song) {
        if (!simpleSongsDatabase.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found.");
        }
        simpleSongsDatabase.remove(id);
        simpleSongsDatabase.put(newId, SongEntity.builder()
                .artist(song.artist())
                .name(song.songName())
                .build());
        return ResponseEntity.ok(UpdateSongResponseDto.builder()
                .songName(song.songName())
                .artist(song.artist())
                .build());
    }

    @PostMapping("/songs")
    public ResponseEntity<List<SingleSongResponseDto>> addSongs(@RequestBody List<SingleSongRequestDto> songs) {
        if (songs.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<SingleSongResponseDto> response = new ArrayList<>();

        songs.forEach(song -> {
            int newKey = newKey(simpleSongsDatabase);
            if (song.songName().length() > 0) {
                simpleSongsDatabase.put(newKey, SongEntity.builder()
                        .name(song.songName())
                        .artist(song.artist())
                        .build());
                log.info("Added song named: " + song.songName() + " with id: " + newKey);
                response.add(SingleSongResponseDto.builder()
                        .artist(song.artist())
                        .songName(song.songName())
                        .build());
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

}

