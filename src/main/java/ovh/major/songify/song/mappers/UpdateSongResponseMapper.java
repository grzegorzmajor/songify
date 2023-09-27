package ovh.major.songify.song.mappers;

import ovh.major.songify.song.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.dto.response.UpdateSongResponseDto;

public class UpdateSongResponseMapper {
    public static UpdateSongResponseDto fromSingleSongRequestDto(SingleSongRequestDto songRequest) {
        return UpdateSongResponseDto.builder()
                .artist(songRequest.artist())
                .songName(songRequest.songName())
                .build();
    }
}
