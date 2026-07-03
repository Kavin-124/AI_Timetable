package AI_Timetable.service;

import AI_Timetable.entity.Teacher;
import AI_Timetable.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    // This tells Spring Boot to give the Manager access to the Chef
    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // Business Rule 1: Get a list of all teachers
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Business Rule 2: Hire (save) a new teacher
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // Business Rule 3: Fire (delete) a teacher by their ID
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    // Business Rule 4: Update a teacher's department
    public Teacher updateTeacherDepartment(Long id, String newDepartment) {
        // Find the teacher first, or throw an error if they don't exist
        Teacher existingTeacher = teacherRepository.findById(id).orElseThrow();

        // Change the department
        existingTeacher.setDepartment(newDepartment);

        // Save the updated teacher back to the Pantry
        return teacherRepository.save(existingTeacher);
    }
}