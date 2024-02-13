package com.ll.tourdemonde.post.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPlaceDTO {
    @NotEmpty(message="장소 이름은 필수항목입니다.")
    private String placeName;
    @NotEmpty(message="주소는 필수항목입니다.")
    private String coordinate;
    @NotEmpty(message="리뷰는 필수항목입니다.")
    private String review;
    @NotNull(message="평점은 필수항목입니다.")
    private Integer rating;
}
