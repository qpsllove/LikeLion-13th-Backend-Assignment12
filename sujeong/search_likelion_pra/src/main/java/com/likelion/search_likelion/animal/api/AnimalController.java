package com.likelion.search_likelion.animal.api;

import com.likelion.search_likelion.animal.api.dto.request.AnimalSaveRequestDto;
import com.likelion.search_likelion.animal.api.dto.response.AnimalInfoResponseDto;
import com.likelion.search_likelion.animal.api.dto.response.AnimalPageResponseDto;
import com.likelion.search_likelion.animal.api.dto.response.AnimalSummaryResponseDto;
import com.likelion.search_likelion.exception.Response;
import com.likelion.search_likelion.exception.status.SuccessStatus;
import com.likelion.search_likelion.global.api.dto.response.PaginationDto;

import com.likelion.search_likelion.animal.application.AnimalService;
import com.likelion.search_likelion.animal.application.SearchService;
import com.likelion.search_likelion.animal.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final SearchService searchService;

    // 반려동물 생성
    @PostMapping(value = "/save")
    public ResponseEntity<Response<Long>> animalSave(@RequestBody AnimalSaveRequestDto animalSaveRequestDto) {
        Response<Long> response = animalService.animalSave(animalSaveRequestDto);
        return ResponseEntity
                .status(SuccessStatus.ANIMAL_CREATED.getStatus())
                .body(response);
    }

    // animalId로 반려동물 상세 조회
    @GetMapping("/{animalId}")
    public Response<AnimalInfoResponseDto> AnimalFindById(@PathVariable("animalId") Long animalId) {
        AnimalInfoResponseDto animalInfoResponseDto = animalService.getAnimalId(animalId);
        return Response.success(SuccessStatus.ANIMAL_SUCCESS, animalInfoResponseDto);
    }

    // 반려동물 전체 조회(요약 정보)
    @GetMapping
    public Response<AnimalPageResponseDto> animalFindAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            // PageableDefault로 페이지네이션 기본값 적용, pageable 파라미터 추가
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Category categoryEnum = Category.from(category); // 문자열로 받은 뒤, Enum 형식으로 변환

        // Repository에서 DB조회 후 페이징 처리된 결과를 DTO로 변환해서 담음, 페이지로 나눈 데이터 + 페이징 정보
        Page<AnimalSummaryResponseDto> animalPageDto = searchService.searchAnimals(keyword, categoryEnum, pageable);

        // PaginationDto 생성(페이지화 된 정보(animalPageDto)를 가져온다)
        PaginationDto paginationDto = new PaginationDto(
                animalPageDto.getNumber() + 1, // 0부터 시작해서 +1
                animalPageDto.getTotalPages(), // 총 페이지수
                animalPageDto.getTotalElements(), // 총 데이터 항목 수
                animalPageDto.getSize() // 해당 페이지의 보여지는 항목 수
        );
        // 게시글 리스트와 페이지네이션 정보를 합친다
        AnimalPageResponseDto animalPageResponseDto = new AnimalPageResponseDto(animalPageDto.getContent(), paginationDto);
        return Response.success(SuccessStatus.ANIMAL_SUCCESS, animalPageResponseDto);
    }
}
