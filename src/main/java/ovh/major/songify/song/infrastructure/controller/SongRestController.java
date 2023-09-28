package ovh.major.songify.song.infrastructure.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ovh.major.songify.song.domain.service.SongAdder;
import ovh.major.songify.song.domain.service.SongPatcher;
import ovh.major.songify.song.domain.service.SongRemover;
import ovh.major.songify.song.domain.service.SongRetriever;
import ovh.major.songify.song.infrastructure.controller.dto.request.PartialySingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.response.*;
import ovh.major.songify.song.infrastructure.controller.mappers.SingleSongResponseMapper;
import ovh.major.songify.song.infrastructure.controller.mappers.SongEntityMapper;
import ovh.major.songify.song.infrastructure.controller.mappers.UpdateSongResponseMapper;
import ovh.major.songify.song.domain.model.SongEntity;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {



    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongRemover songRemover;

    private final SongPatcher songPatcher;

    SongRestController(SongAdder songAdder,
                       SongRetriever songRetriever,
                       SongRemover songRemover,
                       SongPatcher songPatcher) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songRemover = songRemover;
        this.songPatcher = songPatcher;

    }


    @GetMapping
    public ResponseEntity<SongResponseDto> getSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            return ResponseEntity.ok(new SongResponseDto(songRetriever.getSongs(limit)));
        }
        return ResponseEntity.ok(new SongResponseDto(songRetriever.getSongs()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable(name = "id") Integer songId, @RequestHeader(required = false) String requestId) {
        log.info("RequestId: " + requestId);
        SingleSongResponseDto song = songRetriever.getSongById(songId);
        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<SingleSongResponseDto> addSongs(@RequestBody @Valid @NotBlank @NotEmpty SingleSongRequestDto songRequest) {
        songAdder.addSong(songRequest);
        return ResponseEntity.ok(SingleSongResponseMapper.formSingleSongRequestDto(songRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteRemoveSongDto> deleteSongsById(@PathVariable(name = "id") Integer songId, @RequestHeader(required = false) String requestId) {
        log.info("DELETING RequestId: " + requestId);
        return ResponseEntity.ok(songRemover.remove(songId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> putSongById(@PathVariable Integer id, @RequestBody @Valid SingleSongRequestDto songRequest) {
        SingleSongResponseDto oldSong = songPatcher.updateSong(id, SongEntityMapper.fromSingleSongRequestDto(songRequest));
        return ResponseEntity.ok(UpdateSongResponseMapper.fromSingleSongRequestDto(songRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> patchSongById(@PathVariable Integer id, @RequestBody PartialySingleSongRequestDto songRequest) {
        SingleSongResponseDto song =  songRetriever.getSongById(id);
        SongEntity newSong = SongEntity.builder()
                .artist((songRequest.artist() == null) ? song.artist() : songRequest.artist())
                .name((songRequest.songName() == null) ? song.songName() : songRequest.songName())
                .build();
        songPatcher.updateSong(id,newSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto("Song Updated", HttpStatus.OK));
    }

    @PostMapping("/list")
    public ResponseEntity<List<SingleSongResponseDto>> addSongs(@RequestBody List<SingleSongRequestDto> songsRequest) {
        if (songsRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<SingleSongResponseDto> response = new ArrayList<>();

        songsRequest.forEach(song -> {
            if (song.songName().length() > 0 && song.artist().length() > 0) {
                songAdder.addSong(song);
                response.add(SingleSongResponseMapper.formSingleSongRequestDto(song));
            } else {
                log.info("Song skipped - empty song name or artist");
            }
        });
        return ResponseEntity.ok(response);


    }



}

