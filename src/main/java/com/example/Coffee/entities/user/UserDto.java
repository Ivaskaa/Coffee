package com.example.Coffee.entities.user;

import com.example.Coffee.entities.Location;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String phone;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @Size(max = 255, message = "Must be less than 255 characters")
    private String password;
    private Long locationId;
    private String birthday;
    private boolean active;

    public User build(){
        User user = new User();
        user.setId(this.id);
        user.setPhone(this.phone);
        user.setName(this.name);
        user.setPassword(this.password);
        if (locationId != null) {
            Location location = new Location();
            location.setId(locationId);
            user.setLocation(location);
        }
        user.setBirthday(this.birthday);
        user.setActive(this.active);
        return user;
    }
}
