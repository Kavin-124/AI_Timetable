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
        // 1. Clear the old timetable
        slotRepo.deleteAll();

        List<Teacher> teachers = teacherRepo.findAll();
        List<Student> students = studentRepo.findAll();
        List<TimetableSlot> generatedSlots = new ArrayList<>();

        if (teachers.isEmpty() || students.isEmpty()) {
            throw new RuntimeException("You must add at least one Professor and one Department first!");
        }

        // 2. The "Study Hall" Auto-Creator
        Teacher studyHall = null;
        for (Teacher t : teachers) {
            if (t.getName().equals("Study Hall")) {
                studyHall = t;
                break;
            }
        }

        // If a Study Hall doesn't exist, build it!
        if (studyHall == null) {
            studyHall = new Teacher();
            studyHall.setName("Study Hall");
            studyHall.setDepartment("Library");
            studyHall = teacherRepo.save(studyHall);
            teachers.add(studyHall); // Add to our working memory
        }

        // 3. The Booking System
        Set<String> busyProfessors = new HashSet<>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] periods = {"Period 1", "Period 2", "Period 3", "Period 4", "Period 5", "Period 6", "Period 7", "Period 8"};

        int shiftCounter = 0;

        // 4. The Smart Algorithm
        for (String day : days) {
            for (int i = 0; i < periods.length; i++) {
                String currentPeriod = periods[i];

                for (int s = 0; s < students.size(); s++) {
                    int rotatingStudentIndex = (s + shiftCounter) % students.size();
                    Student student = students.get(rotatingStudentIndex);

                    Teacher assignedTeacher = null;

                    // Search for a free professor
                    for (int t = 0; t < teachers.size(); t++) {
                        Teacher potentialTeacher = teachers.get((t + shiftCounter) % teachers.size());

                        // Ignore Study Hall in the normal loop, we ONLY use it as a backup
                        if (potentialTeacher.getName().equals("Study Hall")) continue;

                        String busyKey = potentialTeacher.getId() + "-" + day + "-" + currentPeriod;

                        // If free, lock them in!
                        if (!busyProfessors.contains(busyKey)) {
                            assignedTeacher = potentialTeacher;
                            busyProfessors.add(busyKey);
                            break;
                        }
                    }

                    // 5. The "No Free Period" Fallback
                    if (assignedTeacher == null) {
                        assignedTeacher = studyHall;
                        // Notice we DO NOT add Study Hall to the busyProfessors memory,
                        // because a library can hold unlimited students at once!
                    }

                    // Dynamically name the course
                    String courseName = assignedTeacher.getName().equals("Study Hall")
                            ? "Self Study / Library"
                            : assignedTeacher.getDepartment() + " 101";

                    TimetableSlot slot = new TimetableSlot(day, currentPeriod, courseName, assignedTeacher, student);
                    generatedSlots.add(slot);
                }
                shiftCounter++;
            }
        }

        return slotRepo.saveAll(generatedSlots);
    }

    public List<TimetableSlot> getTimetable() {
        return slotRepo.findAll();
    }
}