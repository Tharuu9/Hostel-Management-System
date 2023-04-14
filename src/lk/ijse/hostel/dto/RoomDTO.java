package lk.ijse.hostel.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private String roomId;
    private String roomType;
    private String keyMoney;
    private int roomQty;
}
