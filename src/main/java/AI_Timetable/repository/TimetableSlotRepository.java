package AI_Timetable.repository;

import AI_Timetable.entity.TimetableSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableSlotRepository extends JpaRepository<TimetableSlot, Long> {
    // Helper method to clear the old timetable when generating a new one
    void deleteAll();

    // Helper to view timetable by a specific student group
    List<TimetableSlot> findByStudentId(Long studentId);
}