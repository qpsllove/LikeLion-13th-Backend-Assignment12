package com.likelion.search_likelion.animal.api.dto.response;

import com.likelion.search_likelion.animal.domain.Animal;
import lombok.Builder;

@Builder
public record AnimalSummaryResponseDto(

        Long animal_id,

        String name
){
    public static AnimalSummaryResponseDto from(Animal animal) {

        return AnimalSummaryResponseDto.builder()
                .animal_id(animal.getAnimalId())
                .name(animal.getName())
                .build();
    }

}
