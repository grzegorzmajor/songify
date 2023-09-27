package ovh.major.songify;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class SongsController {
    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getSongs(){
        SongResponseDto songResponseDto = new SongResponseDto(List.of("Wlazł kotek na płotek", "Z popielnika na Wojtusia iskiereczka mruga"));
        return ResponseEntity.ok(songResponseDto);
    }

}
