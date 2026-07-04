package AI_Timetable.repository;

import AI_Timetable.entity.TimetableSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TimetableSlotRepository extends JpaRepository<TimetableSlot, Long> {

    // Find a specific schedule by its name
    List<TimetableSlot> findByScheduleName(String scheduleName);

    // Delete ONLY a specific schedule by its name
    void deleteByScheduleName(String scheduleName);

    // Get a list of all the unique schedule names we have saved
    @Query("SELECT DISTINCT t.scheduleName FROM TimetableSlot t WHERE t.scheduleName IS NOT NULL")
    List<String> findAllScheduleNames();
}