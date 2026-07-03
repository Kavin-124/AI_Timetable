package AI_Timetable.service;

import AI_Timetable.entity.Student;
import AI_Timetable.entity.Teacher;
import AI_Timetable.entity.TimetableSlot;
import AI_Timetable.repository.StudentRepository;
import AI_Timetable.repository.TeacherRepository;
import AI_Timetable.repository.TimetableSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimetableService {

    @Autowired private TeacherRepository teacherRepo;
    @Autowired private StudentRepository studentRepo;
    @Autowired private TimetableSlotRepository slotRepo;

    public List<TimetableSlot> generateTimetable() {
        // 1. Clear the old timetable from the database
        slotRepo.deleteAll();

        List<Teacher> teachers = teacherRepo.findAll();
        List<Student> students = studentRepo.findAll();
        List<TimetableSlot> generatedSlots = new ArrayList<>();

        // Setup the week
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] periods = {"Period 1", "Period 2", "Period 3", "Period 4", "Period 5"};
        String[] subjects = {"Mathematics", "Science", "History", "English", "Computer Science"};

        // Safety check
        if (teachers.isEmpty() || students.isEmpty()) {
            throw new RuntimeException("You must add at least one Teacher and one Student group first!");
        }

        // 2. The Generation Algorithm
        for (Student student : students) {
            for (String day : days) {
                for (int i = 0; i < periods.length; i++) {

                    // Simple logic to cycle through available teachers and subjects
                    Teacher assignedTeacher = teachers.get((i + student.getId().intValue()) % teachers.size());
                    String assignedSubject = subjects[i % subjects.length];

                    // Create the slot
                    TimetableSlot slot = new TimetableSlot(day, periods[i], assignedSubject, assignedTeacher, student);
                    generatedSlots.add(slot);
                }
            }
        }

        // 3. Save all the newly generated slots to Clever Cloud
        return slotRepo.saveAll(generatedSlots);
    }

    public List<TimetableSlot> getTimetable() {
        return slotRepo.findAll();
    }
}