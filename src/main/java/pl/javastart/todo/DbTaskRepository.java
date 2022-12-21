package pl.javastart.todo;

import org.springframework.data.repository.CrudRepository;

interface DbTaskRepository extends CrudRepository<Task, Long> {}