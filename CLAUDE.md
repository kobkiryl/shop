# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.5.7 application using Maven as the build tool. The project is currently a minimal setup with Spring Boot starter dependencies and uses Java 17.

## Build and Development Commands

### Building the Project
```bash
# Clean and build the project
./mvnw clean install

# Build without running tests
./mvnw clean install -DskipTests

# On Windows
mvnw.cmd clean install
```

### Running the Application
```bash
# Run the Spring Boot application
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

### Running Tests
```bash
# Run all tests
./mvnw test

# Run a specific test class
./mvnw test -Dtest=ShopApplicationTests

# Run a specific test method
./mvnw test -Dtest=ShopApplicationTests#contextLoads

# On Windows, use mvnw.cmd instead
```

### Other Useful Commands
```bash
# Package the application as JAR
./mvnw package

# Clean build artifacts
./mvnw clean
```

## Project Structure

- **Package**: `com.example.shop`
- **Main Application**: [ShopApplication.java](src/main/java/com/example/shop/ShopApplication.java) - Standard Spring Boot main class with `@SpringBootApplication` annotation
- **Configuration**: [application.properties](src/main/resources/application.properties) - Spring configuration file
- **Tests**: Located in `src/test/java/com/example/shop/`

## Technology Stack

- **Java Version**: 17
- **Spring Boot Version**: 3.5.7
- **Build Tool**: Maven (with Maven Wrapper included)
- **Test Framework**: JUnit 5 (Jupiter)
- **Assertion Library**: AssertJ

## Testing Strategy

### Test Structure
All tests follow the **Given-When-Then** pattern for clarity and consistency:
- **Given**: Set up test data and preconditions
- **When**: Execute the action being tested
- **Then**: Verify the outcome using AssertJ assertions

### Assertion Style
Use AssertJ's fluent `assertThat()` API for all assertions:
- ✅ `assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);`
- ❌ `assertEquals(HttpStatus.OK, response.getStatusCode());`

### Test Naming
Test method names should clearly describe the scenario:
- Pattern: `should[ExpectedBehavior]When[Condition]`
- Example: `shouldReturnShopCardWhenDataIsSaved()`

### Test Types
- **Integration Tests**: Test full API endpoints with authentication
- **JSON Tests**: Test serialization/deserialization of domain models
