package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.ResetToken;
import com.ncedu.cheetahtest.entity.user.UserDto;

import java.util.Date;
import java.util.List;

public interface UserDao {

    void createDeveloper(User user);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    void changeUserPassword(ResetToken resetToken, String password);

    User findUserByToken(String token);

    void setUserLastRequest(String email, Date lastRequest);

    User editUser(UserDto user);

    User changeUserStatus(long id, String status);

    User findUserById(long id);

    int getSearchedActiveTotalElements(String title);

    List<User> findActiveByTitlePaginated(int page, int size, String title);
}
