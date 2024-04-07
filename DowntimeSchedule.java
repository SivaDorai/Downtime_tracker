package DownloadNotifier;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "downtime_schedule") 
public class DowntimeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @Column(name = "date")
    private String date;
    @Getter
    @Setter
    @Column(name = "start_time")
    private String startTime;
    @Getter
    @Setter
    @Column(name = "end_time")
    private String endTime;
    @Getter
    @Setter
    @Column(name = "reason")
    private String reason;
    @Getter
    @Setter
    @Column(name = "application")
    private String application;



    // Getters and setters
    // You can generate these using your IDE or write them manually
}
