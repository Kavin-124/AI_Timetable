package AI_Timetable.service;

import AI_Timetable.entity.Student;
import AI_Timetable.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student saveStudent(Student student) {
        return student;
    }

    public void deleteStudent(Long ignoredId) {
    }
}