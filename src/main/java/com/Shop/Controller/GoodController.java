package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.GoodService;
import com.Shop.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2016/3/22 0022.
 */
@Controller
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "addGoodUp",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addGoodUp(Good good,HttpSession session,HttpServletRequest request){
        if(session.getAttribute("loginTerrace") == null){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status",false);
            return jsonObject.toString();
        }
        good.setStatus(1);
        goodService.addGood(good);
        String[] address = request.getParameterValues("address");
        Image image = null;
        for(String str:address){
            image = new Image();
            image.setGood(good);
            image.setAddress(str);
            goodService.addImage(image);
        }
        Gson gson = new Gson();
        String json = gson.toJson(good);
        return json;
    }

    @RequestMapping(value = "addGoodDown",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addGoodDown(Good good,HttpSession session,HttpServletRequest request){
        if(session.getAttribute("loginTerrace") == null){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status",false);
            return jsonObject.toString();
        }
        good.setStatus(0);
        goodService.addGood(good);
        String[] address = request.getParameterValues("address");
        Image image = null;
        for(String str:address){
            image = new Image();
            image.setGood(good);
            image.setAddress(str);
            goodService.addImage(image);
        }
        Gson gson = new Gson();
        String json = gson.toJson(good);
        return json;
    }

    @RequestMapping(value = "updateGood",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateGood(int id,HttpSession session){
        if(session.getAttribute("loginTerrace") == null){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status",false);
            return jsonObject.toString();
        }
        Good good = goodService.findGoodById(id);
        Gson gson = new Gson();
        return gson.toJson(good);
    }

    @RequestMapping(value = "updateGood",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateGood(Good good){
        boolean status = goodService.updateGood(good);
        JsonObject object = new JsonObject();
        object.addProperty("status",status);
        return object.toString();
    }

    @RequestMapping(value = "listGood",method = RequestMethod.GET)
    public String listGood(Model model,HttpSession session){
        if(session.getAttribute("loginTerrace") == null){
            return "redirect:/loginTerrace";
        }
        List<Good> goods = goodService.listGood();
        model.addAttribute("goods",goods);
        return "backStage/page/productManage/productList";
    }

    @RequestMapping(value = "findGoodByName",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Good> listGoodByName(String name){
        List<Good> goods = goodService.listGoodByName(name);
        return goods;
    }




    @RequestMapping(value = "changeStatus",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String changeStatus(int id,HttpSession session){
        JsonObject object = new JsonObject();
        if(session.getAttribute("loginTerrace") == null){
            object.addProperty("status",false);
            object.addProperty("message","用户未登录");
            return object.toString();
        }
        Good good = goodService.findGoodById(id);
        if(good.getStatus() == 1){
            good.setStatus(0);
            goodService.updateGood(good);
        }else{
            good.setStatus(1);
            goodService.updateGood(good);
        }
        object.addProperty("status",true);
        object.addProperty("message","货品状态更改成功");
        return object.toString();
    }
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String addGood(){
        return "addFile";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String addUser(@RequestParam("files") CommonsMultipartFile[] files, HttpServletRequest request){
        System.out.println(request.getSession().getServletContext().getRealPath("/app/img/"));
//        String path = request.getServletPath()+"/app/img/good/";
//        System.out.println(path);
//        for(int i = 0;i<files.length;i++){
//            System.out.println("fileName---------->" + files[i].getOriginalFilename());
//
//            if(!files[i].isEmpty()){
//                int pre = (int) System.currentTimeMillis();
//                try {
//                    //拿到输出流，同时重命名上传的文件
//                    FileOutputStream os = new FileOutputStream(path + new Date().getTime() + files[i].getOriginalFilename());
//                    //拿到上传文件的输入流
//                    FileInputStream in = (FileInputStream) files[i].getInputStream();
//
//                    //以写字节的方式写文件
//                    int b = 0;
//                    while((b=in.read()) != -1){
//                        os.write(b);
//                    }
//                    os.flush();
//                    os.close();
//                    in.close();
//                    int finaltime = (int) System.currentTimeMillis();
//                    System.out.println(finaltime - pre);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.out.println("上传出错");
//                }
//            }
//        }
        return "/success";
    }
}
