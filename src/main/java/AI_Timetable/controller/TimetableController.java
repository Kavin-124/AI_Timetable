package AI_Timetable.controller;

import AI_Timetable.entity.TimetableSlot;
import AI_Timetable.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired private TimetableService service;

    // Generates a schedule and attaches a name to it
    @PostMapping("/generate")
    public List<TimetableSlot> generate(@RequestParam String name) {
        return service.generateTimetable(name);
    }

    // Fetches a specific schedule by its name
    @GetMapping
    public List<TimetableSlot> getTimetable(@RequestParam String name) {
        return service.getTimetable(name);
    }

    // Gets a list of all saved schedule names for our dropdown menu
    @GetMapping("/configs")
    public List<String> getConfigs() {
        return service.getAllConfigNames();
    }
}