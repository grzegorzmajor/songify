package ovh.major.songify.song.domain.copyingcontroller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;
import ovh.major.songify.song.domain.postgres.SongEntity;
import ovh.major.songify.song.domain.inmemo.repository.SongRepositoryInMemo;
import ovh.major.songify.song.domain.postgres.repository.SongRepositoryPostgres;
import ovh.major.songify.song.domain.inmemo.services.SongInMemoRetriever;
import ovh.major.songify.song.domain.dto.response.SongResponseDto;

import java.util.Map;

@RestController
@Log4j2
public class SongCopyFromInMemoToPostgresRestController {
    public SongCopyFromInMemoToPostgresRestController(SongRepositoryPostgres songRepositoryPostgres, SongRepositoryInMemo songRepositoryInMemo, SongInMemoRetriever songInMemoRetriever) {
        this.songRepositoryPostgres = songRepositoryPostgres;
        this.songRepositoryInMemo = songRepositoryInMemo;
        this.songInMemoRetriever = songInMemoRetriever;
    }

    SongRepositoryPostgres songRepositoryPostgres;
    SongRepositoryInMemo songRepositoryInMemo;
    SongInMemoRetriever songInMemoRetriever;

    @PostMapping("/copy/{copyConfirm}/deleteallafter/{deleteConfirm}")
    public ResponseEntity<String> copySongs(@PathVariable Boolean copyConfirm, @PathVariable Boolean deleteConfirm) {
        if (copyConfirm) {
            String responseMessage;
            Map<Integer, SongResponseDto> songInMemoMap = songInMemoRetriever.getSongs();
            songInMemoMap.forEach((key, value) -> {
                SongEntity songEntity = SongEntity.builder()
                        .artist(value.artist())
                        .name(value.songName())
                        .build();
                songRepositoryPostgres.save(songEntity);
            });
            responseMessage = "Copied";
            if (deleteConfirm) {
                songRepositoryInMemo.deleteAll();
                responseMessage += " and deleted.";
            } else {
                responseMessage += " and not deleted. Deleting need confirmation in path.";
            }
            return ResponseEntity.ok(responseMessage);
        } else {
            String responseMessage = "Not copied and not deleted - need confirmation in path!";
            if (deleteConfirm) {
                responseMessage += " Deleting requires prior copying.";
            }
            return ResponseEntity.ok(responseMessage);
        }


    }
}
