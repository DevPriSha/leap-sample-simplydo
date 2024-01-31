package com.sample.simplydo;

import org.springframework.data.annotation.Id;
import java.sql.Timestamp;

record TodoItem(@Id Long id, String title, String description, Timestamp duedate, Boolean completed) {


}