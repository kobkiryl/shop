package com.example.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shopcards")
public class ShopCardController {

    private final ShopCardRepository shopCardRepository;

    public ShopCardController(ShopCardRepository shopCardRepository) {
        this.shopCardRepository = shopCardRepository;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<ShopCard> findById(@PathVariable Long requestedId, Principal principal) {
        Optional<ShopCard> shopCard = shopCardRepository.findByIdAndOwner(requestedId, principal.getName());
        return shopCard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createShopCard(@RequestBody ShopCard newShopCard, UriComponentsBuilder ucb, Principal principal) {
        ShopCard shopCardWithOwner = new ShopCard(newShopCard.id(), newShopCard.amount(), principal.getName());
        ShopCard savedShopCard = shopCardRepository.save(shopCardWithOwner);
        URI locationOfNewShopCard = ucb
                .path("/shopcards/{id}")
                .buildAndExpand(savedShopCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewShopCard).build();
    }

    @GetMapping
    public ResponseEntity<List<ShopCard>> findAll(Pageable pageable, Principal principal) {
        Page<ShopCard> page = shopCardRepository.findByOwner(
                principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                )
        );
        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<Void> putShopCard(@PathVariable Long requestedId, @RequestBody ShopCard shopCardUpdate, Principal principal) {
        if (!shopCardRepository.existsByIdAndOwner(requestedId, principal.getName())) {
            return ResponseEntity.notFound().build();
        }
        ShopCard updatedShopCard = new ShopCard(requestedId, shopCardUpdate.amount(), principal.getName());
        shopCardRepository.save(updatedShopCard);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteShopCard(@PathVariable Long requestedId, Principal principal) {
        if (!shopCardRepository.existsByIdAndOwner(requestedId, principal.getName())) {
            return ResponseEntity.notFound().build();
        }
        shopCardRepository.deleteById(requestedId);
        return ResponseEntity.noContent().build();
    }
}
