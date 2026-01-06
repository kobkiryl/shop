# TASK-002: Domain Model & Repository Layer

**Task ID**: TASK-002
**Epic**: [EPIC-001: Create Shop Card Application](EPIC-001-create-shop-card-app.md)
**Status**: Done ✓
**Priority**: High
**Story Points**: 3

## User Story

As a developer, I need to create the domain model and repository layer so that the application can interact with the database.

## Description

Create ShopCard domain model using Java Record and implement repository interface with Spring Data JDBC for database operations.

## Acceptance Criteria

- [ ] ShopCard record created with 3 fields: id, amount, owner
- [ ] @Id annotation on id field
- [ ] Repository interface extending CrudRepository and PagingAndSortingRepository
- [ ] Custom query method: findByIdAndOwner
- [ ] Custom query method: findByOwner (with Pageable)
- [ ] Custom query method: existsByIdAndOwner
- [ ] Code compiles successfully

## Dependencies

- TASK-001: Project Setup & Database Schema

## Files to Create

- `ShopCard.java` - Domain model (Java Record)
- `ShopCardRepository.java` - Repository interface

## Notes

- Use Java 17 Record for immutable data class
- Spring Data generates query implementations from method names
- Repository methods automatically handle owner-based filtering
