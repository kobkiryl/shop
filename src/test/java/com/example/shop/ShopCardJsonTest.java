package com.example.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShopCardJsonTest {

    @Autowired
    private JacksonTester<ShopCard> json;

    @Autowired
    private JacksonTester<ShopCard[]> jsonList;

    private ShopCard[] shopCards;

    @BeforeEach
    void setUp() {
        shopCards = new ShopCard[]{
                new ShopCard(99L, 987.56, "kiryl1"),
                new ShopCard(100L, 1.00, "kiryl1"),
                new ShopCard(101L, 150.00, "kiryl1")
        };
    }

    @Test
    void shouldSerializeShopCardToJson() throws IOException {
        // Given: A shop card object with id=99, amount=987.56
        ShopCard shopCard = shopCards[0];

        // When: Serialize to JSON
        // Then: JSON matches expected format and contains correct values
        assertThat(json.write(shopCard)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(shopCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(shopCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(shopCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(shopCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(987.56);
    }

    @Test
    void shouldSerializeShopCardListToJson() throws IOException {
        // Given: Array of 3 shop cards

        // When: Serialize array to JSON
        // Then: JSON matches expected list format
        assertThat(jsonList.write(shopCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void shouldDeserializeJsonToShopCardList() throws IOException {
        // Given: JSON array string with 3 shop cards
        String expected = """
                [
                  {"id": 99, "amount": 987.56 , "owner": "kiryl1"},
                  {"id": 100, "amount": 1.00 , "owner": "kiryl1"},
                  {"id": 101, "amount": 150.00, "owner": "kiryl1" }
                ]
                """;

        // When: Deserialize JSON to array
        // Then: Array matches expected shop cards
        assertThat(jsonList.parse(expected)).isEqualTo(shopCards);
    }

    @Test
    void shouldDeserializeJsonToShopCard() throws IOException {
        // Given: JSON string with single shop card
        String expected = """
                {
                    "id": 99,
                    "amount": 987.56,
                    "owner": "kiryl1"
                }
                """;

        // When: Deserialize JSON to object
        // Then: Object matches expected values
        assertThat(json.parse(expected)).isEqualTo(new ShopCard(99L, 987.56, "kiryl1"));
        assertThat(json.parseObject(expected).id()).isEqualTo(99);
        assertThat(json.parseObject(expected).amount()).isEqualTo(987.56);
    }
}
