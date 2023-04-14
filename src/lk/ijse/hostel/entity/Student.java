package lk.ijse.hostel.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "student")
public class Student implements SuperEntity {
    @Id
    private String stId;
    @Column(nullable = false)
    private String stName;
    @Column(columnDefinition = "TEXT" , nullable = false)
    private String stAddress;
    @Column(nullable = false)
    private String contactNo;
    @Column(columnDefinition = "DATE" , nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String gender;

    @OneToMany(mappedBy = "student")
    @Cascade(CascadeType.ALL)
    List<Reservation> reservations = new ArrayList<>();

    public Student(String stId, String stName, String stAddress, String contactNo, LocalDate dob, String gender) {
        this.stId = stId;
        this.stName = stName;
        this.stAddress = stAddress;
        this.contactNo = contactNo;
        this.dob = dob;
        this.gender = gender;
    }
}
