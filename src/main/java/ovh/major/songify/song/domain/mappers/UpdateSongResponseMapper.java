package ovh.major.songify.song.domain.mappers;

import ovh.major.songify.song.domain.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.domain.dto.response.UpdateSongResponseDto;

public class UpdateSongResponseMapper {
    public static UpdateSongResponseDto fromSingleSongRequestDto(SingleSongRequestDto songRequest) {
        return UpdateSongResponseDto.builder()
                .artist(songRequest.artist())
                .songName(songRequest.songName())
                .build();
    }
}
