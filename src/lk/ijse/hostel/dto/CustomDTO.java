package lk.ijse.hostel.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomDTO {
    private String stId;
    private String stName;
    private String stAddress;
    private String contactNo;
    private String dob;
    private String gender;

    private String roomId;
    private String roomType;
    private String keyMoney;
    private String roomQty;

    private String resId;
    private LocalDate date;
    private double key_Money;
    private String status;
    private Integer qty;

    private String userID;
    private String userName;
    private String password;

}
