package ovh.major.songify;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
class SongsController {

    Map<Integer,String> simpleDatabase = new HashMap<>();

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getSongs(){
        simpleDatabase.put(1,"Wlazł kotek na płotek");
        simpleDatabase.put(2,"Z popielnika na Wojtusia iskiereczka mruga");

        SongResponseDto response = new SongResponseDto(simpleDatabase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable(name = "id" ) Integer songId) {
        String song = simpleDatabase.get(songId);
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

}

