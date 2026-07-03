package AI_Timetable.controller;

import AI_Timetable.entity.Student;
import AI_Timetable.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    @GetMapping
    public List<Student> getStudents() {
        return service.getAllStudents();
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return "Student successfully deleted!";
    }
}