package com.likelion.search_likelion.animal.domain.repository;


import com.likelion.search_likelion.animal.domain.Animal;
import com.likelion.search_likelion.animal.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.gender) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:category IS NULL OR a.category = :category)")
    Page<Animal> findKeywordAndCategory(@org.springframework.data.repository.query.Param("keyword") String keyword,
                                        @org.springframework.data.repository.query.Param("category") Category category, Pageable pageable);

}
