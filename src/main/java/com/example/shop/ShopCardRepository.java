package com.example.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShopCardRepository extends CrudRepository<ShopCard, Long>, PagingAndSortingRepository<ShopCard, Long> {

    Optional<ShopCard> findByIdAndOwner(Long id, String owner);

    Page<ShopCard> findByOwner(String owner, Pageable pageable);

    boolean existsByIdAndOwner(Long id, String owner);
}
