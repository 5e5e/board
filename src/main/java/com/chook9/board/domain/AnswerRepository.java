package com.chook9.board.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

	List<Answer> findByQuestionId(Long id);

}
