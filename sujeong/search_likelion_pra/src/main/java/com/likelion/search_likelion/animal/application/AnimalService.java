package com.likelion.search_likelion.animal.application;


import com.likelion.search_likelion.animal.api.dto.request.AnimalSaveRequestDto;
import com.likelion.search_likelion.animal.api.dto.response.AnimalInfoResponseDto;
import com.likelion.search_likelion.animal.domain.Animal;
import com.likelion.search_likelion.animal.domain.repository.AnimalRepository;
import com.likelion.search_likelion.exception.CustomException;
import com.likelion.search_likelion.exception.Response;
import com.likelion.search_likelion.exception.status.ErrorStatus;
import com.likelion.search_likelion.exception.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimalService {
    private final AnimalRepository animalRepository;

    // 반려동물 저장
    @Transactional
    public Response<Long> animalSave(AnimalSaveRequestDto animalSaveRequestDto) {
        if(animalSaveRequestDto.category() == null){
            throw new CustomException(ErrorStatus.ANIMAL_CREATE_FAILED);
        }


        Animal animal = Animal.builder()
                .name(animalSaveRequestDto.name())
                .gender(animalSaveRequestDto.gender())
                .category(animalSaveRequestDto.category())
                .build();
        animalRepository.save(animal);
        return Response.success(SuccessStatus.ANIMAL_CREATED, animal.getAnimalId());
    }

    //AnimalId로 반려동물 상세 조회
    public AnimalInfoResponseDto getAnimalId(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ANIMAL_NOT_FOUND));

        return AnimalInfoResponseDto.from(animal);
    }

    // 반려동물 요약 전체 조회(페이지네이션)
    public Page<Animal> getAnimalPage(Pageable pageable) {
        return animalRepository.findAll(pageable);

    }
}