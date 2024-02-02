package com.sample.simplydo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Repository;


import java.util.Optional;

//@Repository
interface SimplyDoRepository extends CrudRepository<TodoItem, Long>, PagingAndSortingRepository<TodoItem, Long> {



}