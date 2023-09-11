package com.in28minutes.springboot.learnjpaandhibernate.course;

import com.in28minutes.springboot.learnjpaandhibernate.course.springdatajpa.CourseSpringDataJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseCommandLineRunner implements CommandLineRunner {

//  private final CourseJdbcRepository repository;
  
//  private final CourseJpaRepository repository;

  private final CourseSpringDataJpaRepository repository;

  public CourseCommandLineRunner(CourseSpringDataJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... args) throws Exception {
    repository.save(new Course(1, "Learn AWS", "in28minutes"));
    repository.save(new Course(2, "Learn Azure", "in28minutes"));
    repository.save(new Course(3, "Learn DevOps", "in28minutes"));

    repository.deleteById(2L);

    System.out.println(repository.findById(1L));
    System.out.println(repository.findById(3L));

    System.out.println(repository.findAll());
    System.out.println(repository.count());
    System.out.println(repository.findByAuthor("in28minutes"));
    System.out.println(repository.findByAuthor(""));
    System.out.println(repository.findByName("Learn AWS"));
    System.out.println(repository.findByName(""));
  }
}
