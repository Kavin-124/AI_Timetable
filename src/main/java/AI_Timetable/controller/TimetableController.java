package AI_Timetable.controller;

import AI_Timetable.entity.TimetableSlot;
import AI_Timetable.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableService service;

    // This is the button that triggers the AI generation!
    @PostMapping("/generate")
    public List<TimetableSlot> generate() {
        return service.generateTimetable();
    }

    // This fetches the generated schedule to show on the screen
    @GetMapping
    public List<TimetableSlot> getTimetable() {
        return service.getTimetable();
    }
}