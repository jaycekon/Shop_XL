package com.Shop.Controller;

import com.Shop.Model.User;
import com.Shop.Service.UserService;
import com.Shop.Util.DataConvertorUtil;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Created by Administrator on 2016/3/22 0022.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value ="/register",method = RequestMethod.GET)
    public String registe(){
        return "addUser";
    }

    @RequestMapping(value ="/register",method = RequestMethod.POST)
    public String registe(User user){
        userService.addUser(user);
        return "addUser";
    }

    @RequestMapping(value ="user",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String get(int id){
        User user = userService.findById(id);
        System.out.println(user.getUsername());
        String json = DataConvertorUtil.object2json(user);
        JsonObject object = new JsonObject();
        return json;
    }
}
