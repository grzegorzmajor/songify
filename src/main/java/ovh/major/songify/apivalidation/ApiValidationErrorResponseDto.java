package ovh.major.songify.apivalidation;

import java.util.List;

public record ApiValidationErrorResponseDto(
        List<String> message
) {
}
