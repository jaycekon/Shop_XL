package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.AddressService;
import com.Shop.Service.GoodService;
import com.Shop.Service.TerraceService;
import com.Shop.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Controller
public class InsertMessageController {
    @Autowired
    TerraceService terraceService;
    @Autowired
    UserService userService;
    @Autowired
    GoodService goodService;
    @Autowired
    AddressService addressService;

    /**
     * 测试使用接口，添加角色
     * @param roles
     * @return
     */
    @RequestMapping(value = "addRoles", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addRoles(Roles roles) {
        JsonObject object = new JsonObject();
        userService.addRoles(roles);
        object.addProperty("status", true);
        return object.toString();
    }

    /**
     * 测试用接口，添加大区
     * @param areas
     * @return
     */
    @RequestMapping(value = "addAreas", method = RequestMethod.POST)
    @ResponseBody
    public String addAreas(Areas areas) {
        JsonObject object = new JsonObject();
        userService.addArea(areas);
        object.addProperty("status", true);
        return object.toString();
    }



    /**
     * 模拟用户登录
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "loginUser", method = RequestMethod.POST)
    public String login(User user, HttpSession session) {
        User u = userService.loginUser(user);
        session.setAttribute("loginUser", u);
        return "redirect:/";
    }

    /**
     * 测试用
     * 跳转到更新商品页面，从数据库中获取到商品信息，保存到request请求中
     * @param id
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "updateGood/{id}",method = RequestMethod.GET)
    public String updateGood(@PathVariable(value ="id") int id, HttpSession session, HttpServletRequest request){
        if(session.getAttribute("loginTerrace") == null){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status",false);
            return jsonObject.toString();
        }
        Good good = goodService.findGoodById(id);
        request.setAttribute("good",good);
        return "backStage/Product/previewProduct";
    }

    /**
     * 测试用
     * 保存更新好的商品信息
     * @param good
     * @return
     */
    @RequestMapping(value = "updateGood",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateGood(Good good){
        boolean status = goodService.updateGood(good);
        JsonObject object = new JsonObject();
        object.addProperty("status",status);
        return object.toString();
    }


    @RequestMapping(value ="listTopArea",method = RequestMethod.GET)
    @ResponseBody
    public String listTopArea(){
        List<Area> areas =addressService.findTopArea();
        Gson gson = new Gson();
        return gson.toJson(areas);
    }

}
