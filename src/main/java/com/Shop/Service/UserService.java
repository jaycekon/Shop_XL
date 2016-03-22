package com.Shop.Service;

import com.Shop.Dao.UserDao;
import com.Shop.Model.User;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/3/22 0022.
 */
@Service
public class UserService {
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void addUser(User user){
        userDao.save(user);
    }

    public User findById(int id){
        return userDao.findById(id);
    }
}
