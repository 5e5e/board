package com.example.demo.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {

	List<Answer> findByQuestionId(String questionId);

}
