package de.netzkronehd.springtest;

import de.netzkronehd.oas.api.server.springboot.UserApiDelegate;
import de.netzkronehd.oas.api.server.springboot.models.UserDto;
import de.netzkronehd.oas.api.server.springboot.models.UserUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserDelegate implements UserApiDelegate {


    @Override
    public ResponseEntity<UserDto> createUser(UserUpdateDto userUpdateDto, String uuid) {
        return UserApiDelegate.super.createUser(userUpdateDto, uuid);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String username) {
        return UserApiDelegate.super.deleteUser(username);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUserAll() {
        return UserApiDelegate.super.getUserAll();
    }

    @Override
    public ResponseEntity<UserDto> getUserByMail(String mail) {
        return UserApiDelegate.super.getUserByMail(mail);
    }

    @Override
    public ResponseEntity<UserDto> getUserByUuid(String uuid) {
        return UserApiDelegate.super.getUserByUuid(uuid);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsersByRole(String roleName) {
        return UserApiDelegate.super.getUsersByRole(roleName);
    }

    @Override
    public ResponseEntity<UserDto> updateUserData(String uuid, UserUpdateDto userUpdateDto) {
        return UserApiDelegate.super.updateUserData(uuid, userUpdateDto);
    }
}
