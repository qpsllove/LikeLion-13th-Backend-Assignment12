package com.likelion.search_likelion.animal.api.dto.response;

import com.likelion.search_likelion.animal.domain.Animal;
import com.likelion.search_likelion.animal.domain.Category;
import lombok.Builder;

@Builder
public record AnimalInfoResponseDto(
        Category category,

        String name,

        String gender
) {
    public static AnimalInfoResponseDto from(Animal animal) {
        return AnimalInfoResponseDto.builder()
                .name(animal.getName())
                .gender(animal.getGender())
                .category(animal.getCategory())
                .build();

    }
}
