package AI_Timetable.controller;

import AI_Timetable.entity.Student;
import AI_Timetable.repository.StudentRepository;
import AI_Timetable.repository.TimetableSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; // The magic import!
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired private StudentRepository studentRepository;
    @Autowired private TimetableSlotRepository slotRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // THE FIX: @Transactional forces the database to do this in the perfect order
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteStudent(@PathVariable Long id) {
        slotRepository.deleteAll();
        studentRepository.deleteById(id);
    }
}