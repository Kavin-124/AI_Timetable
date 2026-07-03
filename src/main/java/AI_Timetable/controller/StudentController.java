package AI_Timetable.controller;

import AI_Timetable.entity.Student;
import AI_Timetable.repository.StudentRepository;
import AI_Timetable.repository.TimetableSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired private StudentRepository studentRepository;

    // 1. Bring in the Timetable memory
    @Autowired private TimetableSlotRepository slotRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        // 2. THE FIX: Clear the schedule before deleting the student!
        slotRepository.deleteAll();
        studentRepository.deleteById(id);
    }
}