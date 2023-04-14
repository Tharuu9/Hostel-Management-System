package lk.ijse.hostel.entity;


import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "room")
public class Room implements SuperEntity {
    @Id
    private String roomId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String keyMoney;
    @Column(nullable = false)
    private int qty;

    @OneToMany(mappedBy = "room")
    @Cascade(CascadeType.ALL)
    List<Reservation> roomDetails=new ArrayList<>();

    public Room(String roomId, String type, String keyMoney, int qty) {
        this.roomId = roomId;
        this.type = type;
        this.keyMoney = keyMoney;
        this.qty = qty;
    }
}
