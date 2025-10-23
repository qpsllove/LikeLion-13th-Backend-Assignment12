package com.likelion.search_likelion.animal.application;

import com.likelion.search_likelion.animal.api.dto.response.AnimalSummaryResponseDto;
import com.likelion.search_likelion.animal.domain.Category;
import com.likelion.search_likelion.animal.domain.Animal;
import com.likelion.search_likelion.animal.domain.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final AnimalRepository animalRepository;

    public Page<AnimalSummaryResponseDto> searchAnimals(String keyword, Category category, Pageable pageable) {

        // animalRepository에서 검색해서 전달
        Page<Animal> allAnimals = animalRepository.findKeywordAndCategory(keyword,category, pageable);

        // DB에서 페이징된 Animal 객체들을 Dto로 매핑해서 반환
        return allAnimals.map(AnimalSummaryResponseDto::from);
    }
}
