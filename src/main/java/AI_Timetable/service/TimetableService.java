package AI_Timetable.service;

import AI_Timetable.entity.Student;
import AI_Timetable.entity.Teacher;
import AI_Timetable.entity.TimetableSlot;
import AI_Timetable.repository.StudentRepository;
import AI_Timetable.repository.TeacherRepository;
import AI_Timetable.repository.TimetableSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // The magic fix for duplicates!

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TimetableService {

    @Autowired private TeacherRepository teacherRepo;
    @Autowired private StudentRepository studentRepo;
    @Autowired private TimetableSlotRepository slotRepo;

    @Transactional // This locks the database so it wipes old data cleanly before adding new data
    public List<TimetableSlot> generateTimetable() {
        // 1. Wipe the slate clean
        slotRepo.deleteAll();

        List<Teacher> teachers = teacherRepo.findAll();
        List<Student> students = studentRepo.findAll();
        List<TimetableSlot> generatedSlots = new ArrayList<>();

        if (teachers.isEmpty() || students.isEmpty()) {
            throw new RuntimeException("You must add at least one Professor and one Class first!");
        }

        // Auto-Create Study Hall if it doesn't exist
        Teacher studyHall = null;
        for (Teacher t : teachers) {
            if (t.getName().equals("Study Hall")) {
                studyHall = t;
                break;
            }
        }
        if (studyHall == null) {
            studyHall = new Teacher();
            studyHall.setName("Study Hall");
            studyHall.setDepartment("Library");
            studyHall = teacherRepo.save(studyHall);
            teachers.add(studyHall);
        }

        Set<String> busyProfessors = new HashSet<>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        // Real College Time Slots
        String[] periods = {
                "09:00 AM - 10:00 AM", // Period 1
                "10:00 AM - 11:00 AM", // Period 2
                "11:15 AM - 12:15 PM", // Period 3 (After 15 min break)
                "12:15 PM - 01:15 PM", // Period 4
                "02:00 PM - 03:00 PM", // Period 5 (After 45 min lunch)
                "03:00 PM - 04:00 PM", // Period 6
                "04:15 PM - 05:15 PM", // Period 7 (After 15 min break)
                "05:15 PM - 06:15 PM"  // Period 8
        };

        int shiftCounter = 0;

        for (String day : days) {
            for (int i = 0; i < periods.length; i++) {
                String currentPeriod = periods[i];

                for (int s = 0; s < students.size(); s++) {
                    int rotatingStudentIndex = (s + shiftCounter) % students.size();
                    Student student = students.get(rotatingStudentIndex);

                    Teacher assignedTeacher = null;

                    for (int t = 0; t < teachers.size(); t++) {
                        Teacher potentialTeacher = teachers.get((t + shiftCounter) % teachers.size());
                        if (potentialTeacher.getName().equals("Study Hall")) continue;

                        String busyKey = potentialTeacher.getId() + "-" + day + "-" + currentPeriod;
                        if (!busyProfessors.contains(busyKey)) {
                            assignedTeacher = potentialTeacher;
                            busyProfessors.add(busyKey);
                            break;
                        }
                    }

                    if (assignedTeacher == null) {
                        assignedTeacher = studyHall;
                    }

                    String courseName = assignedTeacher.getName().equals("Study Hall")
                            ? "Self Study"
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