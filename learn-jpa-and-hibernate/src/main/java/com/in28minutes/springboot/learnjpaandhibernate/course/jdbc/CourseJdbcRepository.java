package com.in28minutes.springboot.learnjpaandhibernate.course.jdbc;

import com.in28minutes.springboot.learnjpaandhibernate.course.Course;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseJdbcRepository {
  private final JdbcTemplate jdbcTemplate;

  public CourseJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final String INSERT_QUERY =
      """
      INSERT INTO course (id, name, author)
      VALUES (?, ?, ?)
      """;

  private static final String DELETE_QUERY =
      """
      DELETE FROM course
      WHERE id = ?
      """;

  private static final String SELECT_QUERY =
      """
      SELECT * FROM course
      WHERE id = ?
      """;

  public void insert(Course course) {
    jdbcTemplate.update(INSERT_QUERY, course.getId(), course.getName(), course.getAuthor());
  }

  public void deleteById(long id) {
    jdbcTemplate.update(DELETE_QUERY, id);
  }

  public Course findById(long id) {
    return jdbcTemplate.queryForObject(SELECT_QUERY, new BeanPropertyRowMapper<>(Course.class), id);
  }
}
