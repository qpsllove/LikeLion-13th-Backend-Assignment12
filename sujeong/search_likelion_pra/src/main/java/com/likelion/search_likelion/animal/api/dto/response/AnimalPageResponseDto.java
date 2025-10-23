package com.likelion.search_likelion.animal.api.dto.response;

import com.likelion.search_likelion.global.api.dto.response.PaginationDto;

import java.util.List;

public record AnimalPageResponseDto(
        List<AnimalSummaryResponseDto> posts,
        PaginationDto pagination
) {
}
