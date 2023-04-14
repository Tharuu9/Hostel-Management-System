package lk.ijse.hostel.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation implements SuperEntity {
    @Id
    private String resId;
    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Room room;
    private double keyMoney;
    private String status;
    private Integer qty;

    public Reservation(String resId, LocalDate date, double keyMoney, int qty, String status, Room roomId, Student studentId) {
        this.resId=resId;
        this.date=date;
        this.keyMoney=keyMoney;
        this.qty=qty;
        this.status=status;
        this.room=roomId;
        this.student=studentId;

    }
}
