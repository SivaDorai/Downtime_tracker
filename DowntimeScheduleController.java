package DownloadNotifier;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DowntimeScheduleController {
    @Autowired
    private DowntimeScheduleRepository downtimeScheduleRepository;

    @GetMapping("/h2")
    public String home() {
        return "index"; // Assuming "index.html" is your home page
    }
    
    @PostMapping("/downtime-schedule")
    public DowntimeSchedule createDowntimeSchedule(@RequestBody DowntimeSchedule downtimeSchedule) {
        System.out.println(downtimeSchedule.getReason());
        return downtimeScheduleRepository.save(downtimeSchedule);
    }

    @GetMapping("/downtime-schedule")
    public List<DowntimeSchedule> getDowntimeSchedule() {
        return downtimeScheduleRepository.findAll();
    }
}
