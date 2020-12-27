package com.ncedu.cheetahtest.service.user;

import com.ncedu.cheetahtest.dao.genericdao.AbstractActiveDao;
import com.ncedu.cheetahtest.dao.resettoken.ResetTokenDao;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.user.ResetToken;
import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.UserDto;
import com.ncedu.cheetahtest.entity.user.UserPaginatedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ResetTokenDao resetTokenDao;
    private final AbstractActiveDao<User> userGenDao;

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    @Transactional
    public void createPasswordResetTokenForUser(User user, String token) {
        ResetToken myToken = new ResetToken(token, user.getId(), new Date());

        ResetToken resetToken = resetTokenDao.findResetTokenByUserId(user.getId());

        if (resetToken == null) {
            resetTokenDao.createToken(myToken);

            log.info("Token has been successfully created: " + myToken);
        } else {
            resetTokenDao.saveToken(myToken);

            log.info("Token has been successfully updated: " + myToken);
        }
    }

    @Override
    @Transactional
    public ResetToken findByToken(String token) {
        return resetTokenDao.findResetTokenByToken(token);
    }

    @Override
    @Transactional
    public void makeTokenExpired(ResetToken resetToken) {
        resetTokenDao.makeTokenExpired(resetToken);
    }

    @Override
    @Transactional
    public User editUser(UserDto user) {
        return userDao.editUser(user);
    }

    @Override
    @Transactional
    public User changeUserStatus(long id, String status) {
        return userDao.changeUserStatus(id, status);
    }

    @Override
    @Transactional
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    @Override
    @Transactional
    public List<UserDto> findUsersByName(int page, int size, String title) {
        List<User> users = userGenDao.findActiveByTitlePaginated(page, size, title);
        return mapUserToUserDto(users);
    }

    @Override
    @Transactional
    public void setUserLastRequest(String email, Date date) {
        userDao.setUserLastRequest(email, date);
    }

    @Override
    @Transactional
    public void setUserPhotoUrl(long id, String url) {
        userDao.setPhotoUrl(id, url);
    }

    @Override
    @Transactional
    public UserPaginatedDto getAllActiveUser(int size, int page) {
        List<User> users = userGenDao.getActivePaginated(page, size);
        int total = userGenDao.getAmountActiveElements();

        List<UserDto> usersDto =  mapUserToUserDto(users);
        return new UserPaginatedDto(usersDto, total);
    }

    @Override
    public UserPaginatedDto getSearchUserByNameEmailRole(String name, String email, String role,
                                                         int size, int page) {
        List<User> users = userDao.getSearchUserByNameEmailRole(name, email, role, size, page);
        int total = userDao.getCountSearchUserByNameEmailRole(name, email, role);

        List<UserDto> usersDto =  mapUserToUserDto(users);
        return new UserPaginatedDto(usersDto, total);
    }

    @Override
    public List<UserDto> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<UserDto> getWatchersByProjectId(int projectId) {
        List<User> users = userDao.getWatchersByProjectId(projectId);
        return mapUserToUserDto(users);
    }

    @Override
    @Transactional
    public void saveWatchers(int projectId, int[] ids) {
        userDao.deleteAllWatchersForProject(projectId);
        userDao.addWatchersForProject(projectId, ids);
    }


    private List<UserDto> mapUserToUserDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();

        for (User user : users) {
            usersDto.add(
                    new UserDto(
                            user.getId(),
                            user.getEmail(),
                            user.getName(),
                            user.getRole(),
                            user.getStatus()
                    ));
        }
        return usersDto;
    }
}
