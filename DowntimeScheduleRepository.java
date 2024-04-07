package DownloadNotifier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DowntimeScheduleRepository extends JpaRepository<DowntimeSchedule, Long> {
    // You can define custom query methods here if needed
}
