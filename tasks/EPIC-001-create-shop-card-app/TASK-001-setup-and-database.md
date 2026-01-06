# TASK-001: Project Setup & Database Schema

**Task ID**: TASK-001
**Epic**: [EPIC-001: Create Shop Card Application](EPIC-001-create-shop-card-app.md)
**Status**: Done ✓
**Priority**: High
**Story Points**: 3

## User Story

As a developer, I need to set up the project foundation and database schema so that I can start building the shop card application.

## Description

Configure Maven dependencies for Spring Boot, Web, Data JDBC, Security, and H2 database. Create database schema and test data for development.

## Acceptance Criteria

- [ ] pom.xml updated with 6 dependencies:
  - spring-boot-starter-web
  - spring-boot-starter-data-jdbc
  - spring-boot-starter-security
  - h2 (runtime)
  - spring-boot-starter-test (test scope)
  - spring-security-test (test scope)
- [ ] Database schema created with shop_card table
- [ ] Table has 3 columns: ID (auto-increment), AMOUNT (number), OWNER (varchar)
- [ ] Test data file created with 4 sample records
- [ ] Project compiles successfully

## Dependencies

None (first task)

## Files to Create/Modify

- `pom.xml` - Add dependencies
- `src/main/resources/schema.sql` - Database schema
- `src/test/resources/data.sql` - Test data (4 shop cards)

## Verification

```bash
./mvnw clean compile
```

Should output: `BUILD SUCCESS`

## Notes

- H2 is in-memory database (data resets on restart)
- Spring Boot auto-executes schema.sql on startup
- Test data only loaded during test execution
