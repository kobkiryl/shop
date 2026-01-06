# TASK-004: Security Configuration

**Task ID**: TASK-004
**Epic**: [EPIC-001: Create Shop Card Application](EPIC-001-create-shop-card-app.md)
**Status**: Done ✓
**Priority**: High
**Story Points**: 2

## User Story

As a system administrator, I need to secure the API with authentication so that only authorized users can access their shop cards.

## Description

Configure Spring Security with HTTP Basic Authentication, BCrypt password encoding, and role-based access control. Create in-memory test users for development.

## Acceptance Criteria

- [ ] Security configuration class created
- [ ] HTTP Basic Authentication enabled
- [ ] CSRF disabled (REST API)
- [ ] `/shopcards/**` endpoints require CARD-OWNER role
- [ ] BCrypt password encoder configured
- [ ] 3 test users created:
  - kiryl1 (password: 12345qwe, role: CARD-OWNER)
  - max1 (password: qwerty1, role: CARD-OWNER)
  - hank-owns-no-cards (password: qrs456, role: NON-OWNER)
- [ ] Unauthorized users receive 403 FORBIDDEN
- [ ] NON-OWNER role cannot access /shopcards endpoints

## Dependencies

- TASK-003: REST API Controller

## Files to Create

- `SecurityConfig.java` in `com.example.shop` package

## Notes

- Use `@Configuration` and `@EnableWebSecurity` annotations
- Create `SecurityFilterChain` bean
- Create `PasswordEncoder` bean (BCryptPasswordEncoder)
- Create `UserDetailsService` bean with InMemoryUserDetailsManager
