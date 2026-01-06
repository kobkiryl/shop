# EPIC-001: Create Shop Card Application

**Epic ID**: EPIC-001
**Title**: Create Shop Card Application
**Status**: Completed
**Priority**: High
**Story Points**: 13
**Created**: 2025-10-30

## Description

Build a complete RESTful API for managing family shop cards using Spring Boot, Java 17, and Maven. The application provides CRUD operations with user authentication, role-based access control, and comprehensive test coverage.

## Business Value

Enable families to manage their shop cards digitally with secure, user-specific access. Each user can only view and manage their own shop cards, ensuring data privacy and security.

## Technical Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.5.7
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **Security**: Spring Security with BCrypt
- **Testing**: JUnit 5, Spring Boot Test

## Acceptance Criteria

- [x] RESTful API with 5 endpoints (GET, POST, PUT, DELETE, LIST)
- [x] User authentication with Spring Security
- [x] Role-based access control (CARD-OWNER role)
- [x] User data isolation (users can only access their own cards)
- [x] Pagination and sorting support
- [x] 18+ tests passing
- [x] Modern Java features (Records, Java 17)

## Tasks Breakdown

This epic contains 5 tasks that build the application from scratch:

| Task | Title | Points | Status |
|------|-------|--------|--------|
| [TASK-001](TASK-001-setup-and-database.md) | Project Setup & Database Schema | 3 | ✓ Done |
| [TASK-002](TASK-002-domain-and-repository.md) | Domain Model & Repository Layer | 3 | ✓ Done |
| [TASK-003](TASK-003-rest-api.md) | REST API Controller | 3 | ✓ Done |
| [TASK-004](TASK-004-security.md) | Security Configuration | 2 | ✓ Done |
| [TASK-005](TASK-005-testing.md) | Testing & Verification | 2 | ✓ Done |

## API Endpoints

- `GET /shopcards/{id}` - Get shop card by ID
- `POST /shopcards` - Create new shop card
- `GET /shopcards` - List all cards (with pagination/sorting)
- `PUT /shopcards/{id}` - Update shop card
- `DELETE /shopcards/{id}` - Delete shop card

## Test Users

| Username | Password | Role | Cards |
|----------|----------|------|-------|
| kiryl1 | 12345qwe | CARD-OWNER | 3 cards |
| max1 | qwerty1 | CARD-OWNER | 1 card |
| hank-owns-no-cards | qrs456 | NON-OWNER | 0 cards |

## Notes

- Uses Java Records for immutable domain models
- Spring Data JDBC for lightweight ORM
- BCrypt password encoding for security
- Test data automatically loaded for integration tests
