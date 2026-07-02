package AI_Timetable.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Rule 1: Name cannot be blank or just spaces
    @NotBlank(message = "Teacher name is required")
    private String name;

    // Rule 2: Department cannot be blank AND must be at least 2 characters long
    @NotBlank(message = "Department is required")
    @Size(min = 2, message = "Department must be at least 2 letters long")
    private String department;
}