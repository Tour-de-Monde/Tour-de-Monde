package com.ll.tourdemonde.post.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class PostCreateForm {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Size(max = 50)
    private String title;
    @NotEmpty(message = "카테고리를 입력해주세요.")
    private String category;
    private Long authorId;
    @NotEmpty(message = "장소 정보를 입력해주세요.")
    private List<@Valid PostPlaceDTO> postPlaces;
}
