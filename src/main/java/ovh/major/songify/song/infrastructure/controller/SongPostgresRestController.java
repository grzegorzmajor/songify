package ovh.major.songify.song.infrastructure.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ovh.major.songify.song.domain.service.postgre.SongPostgresAdder;
import ovh.major.songify.song.domain.service.postgre.SongPostgresPatcher;
import ovh.major.songify.song.domain.service.postgre.SongPostgresRemover;
import ovh.major.songify.song.domain.service.postgre.SongPostgresRetriever;
import ovh.major.songify.song.infrastructure.controller.dto.request.PartiallySingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.response.*;
import ovh.major.songify.song.infrastructure.controller.mappers.SongResponseMapper;
import ovh.major.songify.song.infrastructure.controller.mappers.UpdateSongResponseMapper;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/psql/songs")
public class SongPostgresRestController {

    private final SongPostgresAdder songAdder;
    private final SongPostgresRetriever songRetriever;
    private final SongPostgresRemover songRemover;

    private final SongPostgresPatcher songPatcher;

    SongPostgresRestController(SongPostgresAdder songAdder,
                               SongPostgresRetriever songRetriever,
                               SongPostgresRemover songRemover,
                               SongPostgresPatcher songPatcher) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songRemover = songRemover;
        this.songPatcher = songPatcher;
    }

    @GetMapping
    public ResponseEntity<List<SongResponseDto>> getSongs() {
        return ResponseEntity.ok(songRetriever.getSongs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDto> getSongsById(@PathVariable(name = "id") Integer songId, @RequestHeader(required = false) String requestId) {
        log.info("RequestId: " + requestId);
        SongResponseDto song = songRetriever.getSongById(songId);
        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<SongResponseDto> addSongs(@RequestBody @Valid @NotBlank @NotEmpty SingleSongRequestDto songRequest) {
        songAdder.addSong(songRequest);
        return ResponseEntity.ok(SongResponseMapper.formSingleSongRequestDto(songRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletingSongStatusDto> deleteSongsById(@PathVariable(name = "id") Integer songId, @RequestHeader(required = false) String requestId) {
        log.info("DELETING RequestId: " + requestId);
        return ResponseEntity.ok(songRemover.remove(songId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> putSongById(@PathVariable Integer id, @RequestBody @Valid SingleSongRequestDto songRequest) {
        SongResponseDto oldSong = songPatcher.updateSong(id, PartiallySingleSongRequestDto.builder()
                .artist(songRequest.artist())
                .songName(songRequest.songName())
                .build());
        return ResponseEntity.ok(UpdateSongResponseMapper.fromSingleSongRequestDto(songRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateStatusSongDto> patchSongById(@PathVariable Integer id, @RequestBody PartiallySingleSongRequestDto songRequest) {
        songPatcher.updateSong(id, songRequest);
        return ResponseEntity.ok(new PartiallyUpdateStatusSongDto("Song Updated", HttpStatus.OK));
    }

}

