package com.example.demo;

import org.springframework.data.repository.CrudRepository;

/**
 * Table/routines for "Message" items.
 * @author amp
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

}
