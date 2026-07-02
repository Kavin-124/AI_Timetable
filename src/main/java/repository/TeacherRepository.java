package AI_Timetable.repository;

import AI_Timetable.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // You don't need to write any code inside these brackets!
    // JpaRepository automatically gives you methods like save(), findAll(), deleteById(), etc.
}