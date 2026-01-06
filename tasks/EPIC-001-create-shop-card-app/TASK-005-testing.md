# TASK-005: Testing & Verification

**Task ID**: TASK-005
**Epic**: [EPIC-001: Create Shop Card Application](EPIC-001-create-shop-card-app.md)
**Status**: Done ✓
**Priority**: High
**Story Points**: 2

## User Story

As a developer, I need comprehensive tests to verify the application works correctly and prevent regressions.

## Description

Create JSON serialization tests and integration tests covering all API endpoints, authentication, and authorization scenarios.

## Acceptance Criteria

### JSON Tests
- [ ] JSON serialization/deserialization tests created
- [ ] Test single object to/from JSON
- [ ] Test array of objects to/from JSON
- [ ] Test JSON resources created (single.json, list.json)

### Integration Tests
- [ ] Test: Get existing shop card returns 200 OK
- [ ] Test: Get non-existent card returns 404 NOT FOUND
- [ ] Test: Create new shop card returns 201 CREATED with Location header
- [ ] Test: List all cards returns 200 OK with correct count
- [ ] Test: Pagination works (page/size parameters)
- [ ] Test: Sorting works (sort parameter)
- [ ] Test: User without CARD-OWNER role gets 403 FORBIDDEN
- [ ] Test: User cannot access cards owned by others (404 NOT FOUND)
- [ ] Test: Update existing card returns 204 NO CONTENT
- [ ] Test: Update non-existent card returns 404 NOT FOUND
- [ ] Test: Cannot update card owned by others (404 NOT FOUND)
- [ ] Test: Delete existing card returns 204 NO CONTENT
- [ ] Test: Delete non-existent card returns 404 NOT FOUND
- [ ] Test: Cannot delete card owned by others (404 NOT FOUND)
- [ ] All 18 tests pass successfully

## Dependencies

- TASK-001: Project Setup & Database Schema
- TASK-002: Domain Model & Repository Layer
- TASK-003: REST API Controller
- TASK-004: Security Configuration

## Files to Create

- `ShopCardJsonTest.java` - JSON serialization tests
- Update `ShopApplicationTests.java` - Integration tests
- `src/test/resources/com/example/shop/single.json` - Test resource
- `src/test/resources/com/example/shop/list.json` - Test resource

## Verification

Run all tests:
```bash
./mvnw test
```

Expected output:
```
Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Notes

- Use `@JsonTest` for JSON tests
- Use `@SpringBootTest` with `RANDOM_PORT` for integration tests
- Use `@DirtiesContext` to reset DB after each test
- Use `TestRestTemplate` with `.withBasicAuth()` for authenticated requests
- Test data automatically loaded from `src/test/resources/data.sql`
