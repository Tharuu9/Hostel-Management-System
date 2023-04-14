package lk.ijse.hostel.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    private String userId;
    private String userName;
    private String password;
}
