package AI_Timetable.controller;

import AI_Timetable.entity.Teacher;
import AI_Timetable.repository.TeacherRepository;
import AI_Timetable.repository.TimetableSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired private TeacherRepository teacherRepository;

    // 1. Bring in the Timetable memory
    @Autowired private TimetableSlotRepository slotRepository;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        // 2. THE FIX: Clear the schedule before deleting the professor!
        slotRepository.deleteAll();
        teacherRepository.deleteById(id);
    }
}