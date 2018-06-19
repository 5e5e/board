package com.example.demo.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

	Object findByUserId(String userId);

	User findByName(String writer);
}
