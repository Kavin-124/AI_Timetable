package AI_Timetable.entity;

import jakarta.persistence.*; // Note: If you are on an older Spring Boot, this might be javax.persistence.*

@Entity
public class TimetableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // These EXACT names are what JavaScript is looking for!
    private String day;
    private String period;
    private String subject;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Student student;

    public TimetableSlot() {
    }

    public TimetableSlot(String day, String period, String subject, Teacher teacher, Student student) {
        this.day = day;
        this.period = period;
        this.subject = subject;
        this.teacher = teacher;
        this.student = student;
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
}