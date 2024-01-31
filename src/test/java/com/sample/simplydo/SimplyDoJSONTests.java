package com.sample.simplydo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class SimplyDoJSONTests {

    @Autowired
    private JacksonTester<TodoItem> json;

    @Test
    void TodoItemSerializationTest() throws IOException {

//        apparently they have only ID and amount, need to change it to
//        title, description, due_date, completed.
//        /test/resources/example/cashcard/expected.json
        TodoItem item = new TodoItem(9999L, "Implement CRUD APIs", "Implement CRUD APIs for the todo list app, simplydo using test driven development", Timestamp.valueOf("2024-02-01 23:59:59"), true);
        assertThat(json.write(item)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(item)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(item)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(9999);

    }
}