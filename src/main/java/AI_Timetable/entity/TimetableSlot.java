package AI_Timetable.entity;

import jakarta.persistence.*;

@Entity
public class TimetableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String day;
    private String period;
    private String subject;

    // NEW: The label to group schedules together (e.g. "Fall 2026")
    private String scheduleName;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Student student;

    public TimetableSlot() {
    }

    public TimetableSlot(String day, String period, String subject, Teacher teacher, Student student, String scheduleName) {
        this.day = day;
        this.period = period;
        this.subject = subject;
        this.teacher = teacher;
        this.student = student;
        this.scheduleName = scheduleName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getScheduleName() { return scheduleName; }
    public void setScheduleName(String scheduleName) { this.scheduleName = scheduleName; }
}