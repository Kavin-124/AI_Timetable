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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        // 2. The Booking System
        Set<String> busyProfessors = new HashSet<>();

        // Setup the college week (Matched to your 8-period schedule!)
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] periods = {"Period 1", "Period 2", "Period 3", "Period 4", "Period 5", "Period 6", "Period 7", "Period 8"};

        if (teachers.isEmpty() || students.isEmpty()) {
            throw new RuntimeException("You must add at least one Professor and one Department first!");
        }

        int shiftCounter = 0; // The secret sauce that makes them take turns!

        // 3. The Fair-Share Algorithm (Time-First Loop)
        for (String day : days) {
            for (int i = 0; i < periods.length; i++) {
                String currentPeriod = periods[i];

                // Loop through students, but ROTATE who gets to pick first
                for (int s = 0; s < students.size(); s++) {

                    int rotatingStudentIndex = (s + shiftCounter) % students.size();
                    Student student = students.get(rotatingStudentIndex);

                    Teacher assignedTeacher = null;

                    // Find a free professor
                    for (int t = 0; t < teachers.size(); t++) {
                        Teacher potentialTeacher = teachers.get((t + shiftCounter) % teachers.size());
                        String busyKey = potentialTeacher.getId() + "-" + day + "-" + currentPeriod;

                        if (!busyProfessors.contains(busyKey)) {
                            assignedTeacher = potentialTeacher;
                            busyProfessors.add(busyKey);
                            break;
                        }
                    }

                    // Assign the class if a professor was found
                    if (assignedTeacher != null) {
                        String courseName = assignedTeacher.getDepartment() + " 101";
                        TimetableSlot slot = new TimetableSlot(day, currentPeriod, courseName, assignedTeacher, student);
                        generatedSlots.add(slot);
                    }
                }
                // Increase the counter so the NEXT student group gets priority in the next period!
                shiftCounter++;
            }
        }

        // 4. Save to Cloud
        return slotRepo.saveAll(generatedSlots);
    }

    public List<TimetableSlot> getTimetable() {
        return slotRepo.findAll();
    }
}