package com.sample.simplydo;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimplyDoApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnATodoListItemWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate.getForEntity("/todolist/9999", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isNotNull();

		String title = documentContext.read("$.title");
		assertThat(title).isNotNull();


	}

	@Test
	void shouldNotReturnATodoListItemWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/todolist/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	void shouldCreateANewTodoItem() {
		TodoItem newTodoItem = new TodoItem(null, "Complete Backend", "", null, false);
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/todolist", newTodoItem, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewTodoItem = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewTodoItem, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void shouldReturnEntireTodoListWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity("/todolist", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
