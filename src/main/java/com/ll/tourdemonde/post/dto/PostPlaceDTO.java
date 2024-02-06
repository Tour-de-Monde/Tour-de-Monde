package com.ll.tourdemonde.post.dto;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPlaceDTO {
    private String placeName;
    private String coordinate;
    private String review;
    private Integer rating;
}
