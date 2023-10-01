package ovh.major.songify.song.domain.inmemo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ovh.major.songify.song.domain.dto.response.*;
import ovh.major.songify.song.domain.inmemo.SongInMemo;
import ovh.major.songify.song.domain.inmemo.services.SongInMemoAdder;
import ovh.major.songify.song.domain.inmemo.services.SongInMemoPatcher;
import ovh.major.songify.song.domain.inmemo.services.SongInMemoRemover;
import ovh.major.songify.song.domain.inmemo.services.SongInMemoRetriever;
import ovh.major.songify.song.domain.dto.request.PartiallySingleSongRequestDto;
import ovh.major.songify.song.domain.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.domain.mappers.SongResponseMapper;
import ovh.major.songify.song.domain.inmemo.SongInMemoMapper;
import ovh.major.songify.song.domain.mappers.UpdateSongResponseMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/inmemo/songs")
public class SongInMemoRestController {

    private final SongInMemoAdder songAdder;
    private final SongInMemoRetriever songRetriever;
    private final SongInMemoRemover songRemover;

    private final SongInMemoPatcher songPatcher;

    SongInMemoRestController(SongInMemoAdder songAdder,
                             SongInMemoRetriever songRetriever,
                             SongInMemoRemover songRemover,
                             SongInMemoPatcher songPatcher) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songRemover = songRemover;
        this.songPatcher = songPatcher;
    }

    @GetMapping
    public ResponseEntity<SongsInMemoResponseDto> getSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            return ResponseEntity.ok(new SongsInMemoResponseDto(songRetriever.getSongs(limit)));
        }
        return ResponseEntity.ok(new SongsInMemoResponseDto(songRetriever.getSongs()));
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
        SongResponseDto oldSong = songPatcher.updateSong(id, SongInMemoMapper.fromSingleSongRequestDto(songRequest));
        return ResponseEntity.ok(UpdateSongResponseMapper.fromSingleSongRequestDto(songRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateStatusSongDto> patchSongById(@PathVariable Integer id, @RequestBody PartiallySingleSongRequestDto songRequest) {
        SongResponseDto song = songRetriever.getSongById(id);
        SongInMemo newSong = SongInMemo.builder()
                .artist((songRequest.artist() == null) ? song.artist() : songRequest.artist())
                .name((songRequest.songName() == null) ? song.songName() : songRequest.songName())
                .build();
        songPatcher.updateSong(id, newSong);
        return ResponseEntity.ok(new PartiallyUpdateStatusSongDto("Song Updated", HttpStatus.OK));
    }

    @PostMapping("/list")
    public ResponseEntity<List<SongResponseDto>> addSongs(@RequestBody List<SingleSongRequestDto> songsRequest) {
        if (songsRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<SongResponseDto> response = new ArrayList<>();

        songsRequest.forEach(song -> {
            if (song.songName().length() > 0 && song.artist().length() > 0) {
                songAdder.addSong(song);
                response.add(SongResponseMapper.formSingleSongRequestDto(song));
            } else {
                log.info("Song skipped - empty song name or artist");
            }
        });
        return ResponseEntity.ok(response);


    }


}

