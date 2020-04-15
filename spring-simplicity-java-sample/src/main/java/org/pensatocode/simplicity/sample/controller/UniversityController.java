package org.pensatocode.simplicity.sample.controller;

import org.pensatocode.simplicity.sample.domain.College;
import org.pensatocode.simplicity.sample.domain.Student;
import org.pensatocode.simplicity.sample.repository.CollegeRepository;
import org.pensatocode.simplicity.sample.repository.StudentRepository;
import org.pensatocode.simplicity.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController extends AbstractController<College, Long> {

    private StudentRepository studentRepository;

    public UniversityController(@Autowired CollegeRepository collegeRepository, @Autowired StudentRepository studentRepository) {
        super(collegeRepository);
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{collegeId}/students")
    @ResponseBody
    public List<Student> findAllStudentsByCollege(@PathVariable Long collegeId) {
        Assert.notNull(collegeId, "You must provide an ID to locate nested itens in an auxiliary repository.");
        return studentRepository.findAllByCollege(collegeId);
    }

}