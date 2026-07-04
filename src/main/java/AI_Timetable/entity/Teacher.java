package AI_Timetable.entity;

import jakarta.persistence.*;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;

    // Stores unavailable days (e.g., "Friday" or "Monday")
    private String daysOff;

    public Teacher() {}

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getDaysOff() { return daysOff; }
    public void setDaysOff(String daysOff) { this.daysOff = daysOff; }
}