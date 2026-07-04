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

    @PostMapping("/generate")
    public List<TimetableSlot> generate(@RequestParam String name) {
        return service.generateTimetable(name);
    }

    @GetMapping
    public List<TimetableSlot> getTimetable(@RequestParam String name) {
        return service.getTimetable(name);
    }

    @GetMapping("/configs")
    public List<String> getConfigs() {
        return service.getAllConfigNames();
    }

    @DeleteMapping("/config")
    public void deleteConfig(@RequestParam String name) {
        service.deleteTimetableConfig(name);
    }
}