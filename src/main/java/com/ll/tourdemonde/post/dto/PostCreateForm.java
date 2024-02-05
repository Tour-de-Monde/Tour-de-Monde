package com.ll.tourdemonde.post.dto;


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
//    private List<PostPlace> postPlaces;

    private List<PostPlaceDTO> postPlaces;

//    private List<String> placeNames;
//    private List<String> coordinates;
//
//    private List<Integer> ratings;
//    private List<String> reviews;
}
