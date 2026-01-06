# TASK-003: REST API Controller

**Task ID**: TASK-003
**Epic**: [EPIC-001: Create Shop Card Application](EPIC-001-create-shop-card-app.md)
**Status**: Done ✓
**Priority**: High
**Story Points**: 3

## User Story

As a developer, I need to create REST API endpoints so that users can perform CRUD operations on their shop cards.

## Description

Implement REST controller with 5 endpoints for managing shop cards. Ensure users can only access their own cards using authentication context.

## Acceptance Criteria

- [ ] GET /shopcards/{id} - Retrieve single shop card
- [ ] POST /shopcards - Create new shop card
- [ ] GET /shopcards - List all user's cards (with pagination/sorting)
- [ ] PUT /shopcards/{id} - Update existing shop card
- [ ] DELETE /shopcards/{id} - Delete shop card
- [ ] User ownership enforced on all endpoints
- [ ] Proper HTTP status codes returned (200, 201, 204, 404)
- [ ] Location header included in POST response

## Dependencies

- TASK-001: Project Setup & Database Schema
- TASK-002: Domain Model & Repository Layer

## Files to Create

- `ShopCardController.java` in `com.example.shop` package

## Notes

- Use Principal to get authenticated username
- Always filter operations by owner field for security
- Support pagination parameters: page, size, sort
