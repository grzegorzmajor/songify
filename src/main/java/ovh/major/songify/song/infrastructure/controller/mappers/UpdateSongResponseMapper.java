package ovh.major.songify.song.infrastructure.controller.mappers;

import ovh.major.songify.song.infrastructure.controller.dto.request.SingleSongRequestDto;
import ovh.major.songify.song.infrastructure.controller.dto.response.UpdateSongResponseDto;

public class UpdateSongResponseMapper {
    public static UpdateSongResponseDto fromSingleSongRequestDto(SingleSongRequestDto songRequest) {
        return UpdateSongResponseDto.builder()
                .artist(songRequest.artist())
                .songName(songRequest.songName())
                .build();
    }
}
