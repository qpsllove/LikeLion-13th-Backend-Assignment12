package com.likelion.search_likelion.animal.api.dto.request;

import com.likelion.search_likelion.animal.domain.Category;

public record AnimalSaveRequestDto(

        Category category,

        String name,

        String gender

) {
}
