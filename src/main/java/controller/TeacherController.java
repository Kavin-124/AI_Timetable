package AI_Timetable.controller;

import AI_Timetable.entity.Teacher;
import AI_Timetable.service.TeacherService;
import jakarta.validation.Valid; // This is the new import for Validation!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    // This gives the Waiter (Controller) access to the Manager (Service)
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // Endpoint 1: ADD a new teacher (Now with @Valid to check the rules!)
    @PostMapping
    public Teacher addTeacher(@Valid @RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }

    // Endpoint 2: GET all teachers (Fetches the menu/list of teachers)
    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    // Endpoint 3: DELETE a teacher by ID
    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "Teacher deleted successfully!";
    }

    // Endpoint 4: UPDATE a teacher's department
    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Long id, @RequestParam String department) {
        return teacherService.updateTeacherDepartment(id, department);
    }
}