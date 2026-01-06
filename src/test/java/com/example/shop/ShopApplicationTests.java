package com.example.shop;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ShopApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnShopCardWhenDataIsSaved() {
		// Given: Shop card with ID 99 exists for user kiryl1 (from test data)

		// When: Request shop card by ID
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards/99", String.class);

		// Then: Return 200 OK with correct shop card data
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);

		Double amount = documentContext.read("$.amount");
		assertThat(amount).isEqualTo(987.56);
	}

	@Test
	void shouldReturnNotFoundWhenShopCardDoesNotExist() {
		// Given: No shop card with ID 1000 exists

		// When: Request non-existent shop card
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards/1000", String.class);

		// Then: Return 404 NOT FOUND with empty body
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	void shouldCreateNewShopCard() {
		// Given: New shop card data with amount 300.00
		ShopCard newShopCard = new ShopCard(null, 300.00, null);

		// When: Create new shop card
		ResponseEntity<Void> createResponse = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.postForEntity("/shopcards", newShopCard, Void.class);

		// Then: Return 201 CREATED with Location header
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewShopCard = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity(locationOfNewShopCard, String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		Double amount = documentContext.read("$.amount");

		assertThat(id).isNotNull();
		assertThat(amount).isEqualTo(300.00);
	}

	@Test
	void shouldReturnAllShopCardsWhenListIsRequested() {
		// Given: User kiryl1 has 3 shop cards (IDs: 99, 100, 101)

		// When: Request all shop cards
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards", String.class);

		// Then: Return 200 OK with all 3 shop cards
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int shopCardCount = documentContext.read("$.length()");
		assertThat(shopCardCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);

		JSONArray amounts = documentContext.read("$..amount");
		assertThat(amounts).containsExactlyInAnyOrder(987.56, 1.00, 150.00);
	}

	@Test
	void shouldReturnPagedShopCards() {
		// Given: User kiryl1 has 3 shop cards

		// When: Request first page with size 1
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards?page=0&size=1", String.class);

		// Then: Return 200 OK with exactly 1 shop card
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnSortedShopCards() {
		// Given: User kiryl1 has 3 shop cards with different amounts

		// When: Request first page sorted by amount descending
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards?page=0&size=1&sort=amount,desc", String.class);

		// Then: Return 200 OK with highest amount (987.56) first
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);

		double amount = documentContext.read("$[0].amount");
		assertThat(amount).isEqualTo(987.56);
	}

	@Test
	void shouldRejectUserWithoutCardOwnerRole() {
		// Given: User hank-owns-no-cards has role NON-OWNER

		// When: Attempt to access shop cards endpoint
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("hank-owns-no-cards", "qrs456")
				.getForEntity("/shopcards/99", String.class);

		// Then: Return 403 FORBIDDEN
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	void shouldNotAllowAccessToOtherUsersCards() {
		// Given: Shop card 102 belongs to user max1, not kiryl1

		// When: User kiryl1 attempts to access max1's card
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards/102", String.class);

		// Then: Return 404 NOT FOUND (data isolation)
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldUpdateExistingShopCard() {
		// Given: Shop card 99 exists with amount 987.56
		ShopCard shopCardUpdate = new ShopCard(null, 19.99, null);
		HttpEntity<ShopCard> request = new HttpEntity<>(shopCardUpdate);

		// When: Update shop card amount to 19.99
		ResponseEntity<Void> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.exchange("/shopcards/99", HttpMethod.PUT, request, Void.class);

		// Then: Return 204 NO CONTENT and amount is updated
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards/99", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		Double amount = documentContext.read("$.amount");
		assertThat(id).isEqualTo(99);
		assertThat(amount).isEqualTo(19.99);
	}

	@Test
	void shouldReturnNotFoundWhenUpdatingNonExistentCard() {
		// Given: No shop card with ID 99999 exists
		ShopCard unknownCard = new ShopCard(null, 19.99, null);
		HttpEntity<ShopCard> request = new HttpEntity<>(unknownCard);

		// When: Attempt to update non-existent card
		ResponseEntity<Void> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.exchange("/shopcards/99999", HttpMethod.PUT, request, Void.class);

		// Then: Return 404 NOT FOUND
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldNotUpdateCardOwnedByAnotherUser() {
		// Given: Shop card 102 belongs to user max1
		ShopCard maxsCard = new ShopCard(null, 333.33, null);
		HttpEntity<ShopCard> request = new HttpEntity<>(maxsCard);

		// When: User kiryl1 attempts to update max1's card
		ResponseEntity<Void> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.exchange("/shopcards/102", HttpMethod.PUT, request, Void.class);

		// Then: Return 404 NOT FOUND (data isolation)
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldDeleteExistingShopCard() {
		// Given: Shop card 99 exists for user kiryl1

		// When: Delete shop card 99
		ResponseEntity<Void> response = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.exchange("/shopcards/99", HttpMethod.DELETE, null, Void.class);

		// Then: Return 204 NO CONTENT and card is deleted
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.getForEntity("/shopcards/99", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldReturnNotFoundWhenDeletingNonExistentCard() {
		// Given: No shop card with ID 99999 exists

		// When: Attempt to delete non-existent card
		ResponseEntity<Void> deleteResponse = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.exchange("/shopcards/99999", HttpMethod.DELETE, null, Void.class);

		// Then: Return 404 NOT FOUND
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldNotDeleteCardOwnedByAnotherUser() {
		// Given: Shop card 102 belongs to user max1

		// When: User kiryl1 attempts to delete max1's card
		ResponseEntity<Void> deleteResponse = restTemplate
				.withBasicAuth("kiryl1", "12345qwe")
				.exchange("/shopcards/102", HttpMethod.DELETE, null, Void.class);

		// Then: Return 404 NOT FOUND and card still exists for max1
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

		ResponseEntity<String> getResponse = restTemplate
				.withBasicAuth("max1", "qwerty1")
				.getForEntity("/shopcards/102", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
