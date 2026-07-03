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

        // 2. The Booking System (Tracks who is teaching when)
        Set<String> busyProfessors = new HashSet<>();

        // Setup the college week
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] periods = {"Period 1", "Period 2", "Period 3", "Period 4", "Period 5", "Period 6", "Period 7", "Period 8"};

        // Safety check
        if (teachers.isEmpty() || students.isEmpty()) {
            throw new RuntimeException("You must add at least one Professor and one Department first!");
        }

        // 3. The Smart Generation Algorithm
        for (Student student : students) {
            for (String day : days) {
                for (int i = 0; i < periods.length; i++) {
                    String currentPeriod = periods[i];
                    Teacher assignedTeacher = null;

                    // Loop through available professors to find one who is FREE
                    for (int t = 0; t < teachers.size(); t++) {
                        // Start searching at a shifted index so different classes get different teachers
                        Teacher potentialTeacher = teachers.get((i + student.getId().intValue() + t) % teachers.size());

                        // Create a unique booking key (e.g., "1-Monday-Period 1")
                        String busyKey = potentialTeacher.getId() + "-" + day + "-" + currentPeriod;

                        // If the professor is NOT busy, assign them and lock the slot!
                        if (!busyProfessors.contains(busyKey)) {
                            assignedTeacher = potentialTeacher;
                            busyProfessors.add(busyKey); // Mark them as busy for this specific time
                            break; // Stop looking for a professor
                        }
                    }

                    // If we successfully found a free professor, create the class!
                    if (assignedTeacher != null) {
                        // Dynamically name the course based on the Professor's department
                        String courseName = assignedTeacher.getDepartment() + " 101";

                        TimetableSlot slot = new TimetableSlot(day, currentPeriod, courseName, assignedTeacher, student);
                        generatedSlots.add(slot);
                    }
                    // NOTE: If NO professor is free, the algorithm safely skips this block,
                    // effectively giving the students a "Free Period" / "Self Study" block!
                }
            }
        }

        // 4. Save all the newly generated slots to Clever Cloud
        return slotRepo.saveAll(generatedSlots);
    }

    public List<TimetableSlot> getTimetable() {
        return slotRepo.findAll();
    }
}