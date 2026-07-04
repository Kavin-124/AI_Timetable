package AI_Timetable.controller;

import AI_Timetable.entity.Teacher;
import AI_Timetable.repository.TeacherRepository;
import AI_Timetable.repository.TimetableSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; // The magic import!
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired private TeacherRepository teacherRepository;
    @Autowired private TimetableSlotRepository slotRepository;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // THE FIX: @Transactional forces the database to do this in the perfect order
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteTeacher(@PathVariable Long id) {
        slotRepository.deleteAll();
        teacherRepository.deleteById(id);
    }
}