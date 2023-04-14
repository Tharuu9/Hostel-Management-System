package lk.ijse.hostel.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTM {
    private String roomId;
    private String roomType;
    private String keyMoney;
    private int roomQty;
}
