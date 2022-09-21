package com.example.Coffee.entities.user;

import com.example.Coffee.entities.Location;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String phone;
    private String name;
    private String password;
    private Location location;
    private String birthday;
    private boolean active;

    public User build(){
        User user = new User();
        user.setId(this.id);
        user.setPhone(this.phone);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setLocation(this.location);
        user.setBirthday(this.birthday);
        user.setActive(this.active);
        return user;
    }
}
