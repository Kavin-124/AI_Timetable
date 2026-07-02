package AI_Timetable.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String grade;

    // This is where the magic happens! We link the Student to a Teacher.
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}