package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.GoodService;
import com.Shop.Service.UserService;
import com.google.gson.JsonObject;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    /**
     * 跳转到添加商品页面
     * @return
     */
    @RequestMapping(value ="addGood" ,method = RequestMethod.GET)
    public String addGood(){
        return "backStage/Product/productPublish";
    }


    /**
     * 添加商品进入数据库，默认显示已下架
     * @param good
     * @param session
     * @param files
     * @return
     */
    @RequestMapping(value = "addGoodDown",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String addGoodDown(Good good,HttpSession session,@RequestParam("files")MultipartFile[] files){
//        if(session.getAttribute("loginTerrace") == null){
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("status",false);
//            return jsonObject.toString();
//        }
        good.setStatus(0);
        goodService.addGood(good);

        String path="D:/XAMPP/xamp/htdocs/Shop_XL/";
        for(int i = 0;i<files.length;i++){
            System.out.println(i);
            if(!files[i].isEmpty()){
                String fileName = path + new Date().getTime() + good.getId()+".jpg";
                try {
                    //拿到输出流，同时重命名上传的文件
                    FileOutputStream os = new FileOutputStream(fileName);
                    //拿到上传文件的输入流
                    FileInputStream in = (FileInputStream) files[i].getInputStream();
                    //以写字节的方式写文件
                    int b = 0;
                    while((b=in.read()) != -1){
                        os.write(b);
                    }
                    os.flush();
                    os.close();
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("上传出错");
                }
                Image image = new Image();
                image.setAddress(fileName);
                image.setGood(good);
                goodService.addImage(image);
            }
        }
        return "redirect:/listGood";
    }



    /**
     * 显示所有商品，从数据库中获取所有商品
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "listGood",method = RequestMethod.GET)
    public String listGood(Model model,HttpSession session){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }
        List<Good> goods = goodService.listGood();
        model.addAttribute("goods",goods);
        return "backStage/Product/productList";
    }

    /**
     * 通过姓名查找商品
     * @param name
     * @return
     */
    @RequestMapping(value = "findGoodByName",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Good> listGoodByName(String name){
        List<Good> goods = goodService.listGoodByName(name);
        return goods;
    }


    /**
     * 更改商品状态
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "changeStatus/{id}",method = RequestMethod.GET)
    public String changeStatus(@PathVariable(value = "id") int id,HttpSession session){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }
        Good good = goodService.findGoodById(id);
        if(good.getStatus() == 1){
            good.setStatus(0);
            goodService.updateGood(good);
        }else{
            good.setStatus(1);
            goodService.updateGood(good);
        }
        return "redirect:/listGood";
    }

    /**
     * 查看商品信息详情(后台）
     * @param id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "goodDetail/{id}",method = RequestMethod.GET)
    public String goodDetail(@PathVariable(value ="id")int id,HttpSession session,Model model){
        Good good= goodService.findGoodById(id);
        model.addAttribute("good",good);
        List<String> address = goodService.findImageByGoodId(good.getId());
        model.addAttribute("address",address);
        return "backStage/Product/previewProduct";
    }

    /**
     * 用户查看商品详情，需要判断是否已认证，是否已查看
     * @param id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "Detail/{id}",method = RequestMethod.GET)
    public String detail(@PathVariable(value ="id")int id,HttpSession session,Model model){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/";
        }
        User user = (User)session.getAttribute("loginUser");
        WatchProduct watchProduct = userService.findWatchProductByUIdAndGId(user.getId(),id);
        Good good= goodService.findGoodById(id);
        model.addAttribute("good",good);
        List<String> address = goodService.findImageByGoodId(good.getId());
        model.addAttribute("address",address);
        model.addAttribute("watchProduct",watchProduct);
        return "frontStage/Good/productDetail";
    }

    /**
     * 编辑商品
     * @param id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "editGood/{id}",method = RequestMethod.GET)
    public String editGood(@PathVariable(value = "id") int id,HttpSession session,Model model){
        Good good = goodService.findGoodById(id);
        model.addAttribute("good",good);
        return "backStage/Product/editProduct";
    }

    /**
     * 编辑商品后将商品信息存入数据库
     * @param id
     * @param session
     * @param good
     * @param files
     * @return
     */
    @RequestMapping(value = "editGood/{id}",method = RequestMethod.POST)
    public String editGood(@PathVariable(value = "id") int id,HttpSession session,Good good,@RequestParam("files")MultipartFile[] files){
        Good g = goodService.findGoodById(id);
        g.setName(good.getName());
        g.setWholesaleCount(good.getWholesaleCount());
        g.setNum(good.getNum());
        System.out.println(good.getDescribes());
        g.setDescribes(good.getDescribes());
        g.setDumpingPrices(good.getDumpingPrices());
        g.setProductPrices(good.getProductPrices());
        g.setSaleCount(good.getSaleCount());
        g.setPv(good.getPv());
        g.setwPrices(good.getwPrices());
        goodService.updateGood(g);
        if(files.length>0){
            goodService.clearImage(good.getId());
            String path="D:/XAMPP/xamp/htdocs/Shop_XL/";
            for(int i = 0;i<files.length;i++){
                if(!files[i].isEmpty()){
                    String fileName = path + new Date().getTime() + good.getId()+".jpg";
                    try {
                        //拿到输出流，同时重命名上传的文件
                        FileOutputStream os = new FileOutputStream(fileName);
                        //拿到上传文件的输入流
                        FileInputStream in = (FileInputStream) files[i].getInputStream();
                        //以写字节的方式写文件
                        int b = 0;
                        while((b=in.read()) != -1){
                            os.write(b);
                        }
                        os.flush();
                        os.close();
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("上传出错");
                    }
                    Image image = new Image();
                    image.setAddress(fileName);
                    image.setGood(good);
                    goodService.addImage(image);
                }
            }
        }
        return "redirect:/listGood";
    }

    /**
     * 通过ID查找商品
     * @return
     */
    @RequestMapping(value = "checkGood",method = RequestMethod.GET)
    public String findGoodById(){
        return "backStage/Product/productQuery";
    }

    /**
     * 通过ID查找商品
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "checkGood",method = RequestMethod.POST)
    public String findGoodById(int id,Model model){
        Good good = goodService.findGoodById(id);
        System.out.println(good.getName());
        model.addAttribute("good",good);
        return "backStage/Product/productQuery";
    }

    /**
     * 查看所有已上架的商品
     * @param model
     * @return
     */
    @RequestMapping(value = "listGoodUp",method = RequestMethod.GET)
    public String listGoodUp(Model model){
        List<Good> goods = goodService.listGoodUp(1);
        model.addAttribute("goods",goods);
        return "backStage/Product/productList";
    }

    /**
     * 查看所有已下架的商品
     * @param model
     * @return
     */
    @RequestMapping(value = "listGoodDown",method = RequestMethod.GET)
    public String listGoodDown(Model model){
        List<Good> goods = goodService.listGoodUp(0);
        model.addAttribute("goods",goods);
        return "backStage/Product/productList";
    }
}
