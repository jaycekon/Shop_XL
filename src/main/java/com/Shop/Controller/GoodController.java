package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.AddressService;
import com.Shop.Service.GoodService;
import com.Shop.Service.UserService;
import com.Shop.Util.Page;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
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
import java.util.UUID;


/**
 * Created by Administrator on 2016/3/22 0022.
 */
@Controller
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 跳转到添加商品页面
     *
     * @return
     */
    @RequestMapping(value = "addGood", method = RequestMethod.GET)
    public String addGood() {
        return "backStage/Product/productPublish";
    }


    /**
     * 添加商品进入数据库，默认显示已下架
     *
     * @param good
     * @return
     */
    @RequestMapping(value = "addGoodDown", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addGoodDown(Good good, HttpServletRequest request) {
        good.setStatus(0);

        float pv = good.getDumpingPrices() - good.getFirmPrices();
        good.setPv(pv);
        good.setCreateTime(new Date());
        goodService.addGood(good);
        if (request.getParameterValues("files") != null) {
            String[] files = request.getParameterValues("files");
            System.out.println("封面图片数量" + files.length);
            if (files.length > 0) {
                good.setImg(files[0]);
            }
            for (int i = 0; i < files.length; i++) {
                System.out.println(i);
                Image image = new Image();
                image.setStatus(0);
                image.setAddress(files[i]);
                image.setGood(good);
                goodService.addImage(image);

            }
        }

        if (request.getParameterValues("detailfiles") != null) {
            String[] detailfiles = request.getParameterValues("detailfiles");
            System.out.println("商品详情图片数量" + detailfiles.length);
            for (int i = 0; i < detailfiles.length; i++) {
                System.out.println(i);
                Image image = new Image();
                image.setStatus(1);
                image.setAddress(detailfiles[i]);
                image.setGood(good);
                goodService.addImage(image);
            }
        }

        return "redirect:/listGood";
    }


    /**
     * 通过姓名查找商品
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "findGoodByName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Good> listGoodByName(String name) {
        List<Good> goods = goodService.listGoodByName(name);
        return goods;
    }


    /**
     * 更改商品状态
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "changeStatus/{id}", method = RequestMethod.GET)
    public String changeStatus(@PathVariable(value = "id") int id, HttpSession session) {
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }
        Good good = goodService.findGoodById(id);
        if (good.getStatus() == 1) {
            good.setStatus(0);
            goodService.updateGood(good);
        } else {
            good.setStatus(1);
            goodService.updateGood(good);
        }
        return "redirect:/listGood";
    }

    /**
     * 查看商品信息详情(后台）
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "goodDetail/{id}", method = RequestMethod.GET)
    public String goodDetail(@PathVariable(value = "id") int id, Model model) {
        Good good = goodService.findGoodById(id);
        model.addAttribute("good", good);
        List<Image> images = goodService.findImageByGoodIdAndStatus(good.getId(), 0);
        List<Image> images0 = goodService.findImageByGoodIdAndStatus(good.getId(), 1);
        List<Comment> comments = addressService.findCommentByGoodId(good.getId());
        model.addAttribute("images", images);
        model.addAttribute("images0", images0);
        model.addAttribute("comments", comments);
        return "backStage/Product/previewProduct";
    }

    /**
     * 用户查看商品详情，需要判断是否已认证，是否已查看
     *
     * @param id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "Detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        WatchProduct watchProduct = userService.findWatchProductByUIdAndGId(user.getId(), id);
        Good good = goodService.findGoodById(id);
        model.addAttribute("good", good);
        List<Image> images = goodService.findImageByGoodIdAndStatus(good.getId(), 0);
        List<Image> detailImages = goodService.findImageByGoodIdAndStatus(good.getId(), 1);
        List<Comment> comments = addressService.findCommentByGoodId(good.getId());


        model.addAttribute("images", images);
        model.addAttribute("detailImages", detailImages);
        model.addAttribute("comments", comments);
        model.addAttribute("watchProduct", watchProduct);
        return "frontStage/Good/detail";
    }

    /**
     * 编辑商品
     *
     * @param id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "editGood/{id}", method = RequestMethod.GET)
    public String editGood(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        Good good = goodService.findGoodById(id);
        List<Image> images0 = goodService.findImageByGoodIdAndStatus(good.getId(), 0);
        List<Image> images1 = goodService.findImageByGoodIdAndStatus(good.getId(), 1);
        model.addAttribute("good", good);
        model.addAttribute("images0", images0);
        model.addAttribute("images1", images1);
        return "backStage/Product/editProduct";
    }

    /**
     * 编辑商品后将商品信息存入数据库
     *
     * @param id
     * @param good
     * @return
     */
    @RequestMapping(value = "editGood/{id}", method = RequestMethod.POST)
    public String editGood(@PathVariable(value = "id") int id, Good good, HttpServletRequest request) {
        Good g = goodService.findGoodById(id);
        float pv = g.getDumpingPrices() - g.getFirmPrices();
        g.setName(good.getName());
        g.setPv(pv);
        g.setFirmPrices(good.getFirmPrices());
        g.setWholesaleCount(good.getWholesaleCount());
        g.setNum(good.getNum());
        System.out.println(good.getDescribes());
        g.setDescribes(good.getDescribes());
        g.setDumpingPrices(good.getDumpingPrices());
        g.setProductPrices(good.getProductPrices());
        g.setSaleCount(good.getSaleCount());
        g.setwPrices(good.getwPrices());
        goodService.deleteImage(g.getId(), 0);
        goodService.deleteImage(g.getId(), 1);
        if (request.getParameterValues("files") != null) {
            String[] files = request.getParameterValues("files");
            g.setImg(files[0]);
            log.info("封面图片数量：" + files.length);
            for (int i = 0; i < files.length; i++) {
                System.out.println(i);
                Image image = new Image();
                image.setStatus(0);
                image.setAddress(files[i]);
                image.setGood(good);
                goodService.addImage(image);
            }
        }
        if (request.getParameterValues("detailfiles") != null) {
            String[] detailfiles = request.getParameterValues("detailfiles");
            log.info("商品详情图片数量：" + detailfiles.length);
            for (int i = 0; i < detailfiles.length; i++) {
                System.out.println(i);
                Image image = new Image();
                image.setStatus(1);
                image.setAddress(detailfiles[i]);
                image.setGood(good);
                goodService.addImage(image);
            }
        }
        goodService.updateGood(g);
        return "redirect:/listGood";
    }

    /**
     * 通过ID查找商品
     *
     * @return
     */
    @RequestMapping(value = "checkGood", method = RequestMethod.GET)
    public String findGoodById() {
        return "backStage/Product/productQuery";
    }

    /**
     * 通过ID查找商品
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "checkGood", method = RequestMethod.POST)
    public String findGoodById(int id, Model model) {
        Good good = goodService.findGoodById(id);
        System.out.println(good.getName());
        model.addAttribute("good", good);
        return "backStage/Product/productQuery";
    }

    /**
     * 查看所有已上架的商品
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "listGoodUp/{pages}", method = RequestMethod.GET)
    public String listGoodUp(Model model, @PathVariable("pages") int pages) {
        Page<Good> page = new Page<>();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<Good> goods = goodService.listGoodUp(1);
        page.setTotalCount(goods.size());
        page.setList(goodService.listGoodUp(1, page));
        model.addAttribute("page", page);
        return "backStage/Product/productList";
    }

    /**
     * 查看所有已下架的商品
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "listGoodDown/{pages}", method = RequestMethod.GET)
    public String listGoodDown(Model model, @PathVariable("pages") int pages) {
        Page<Good> page = new Page<>();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<Good> goods = goodService.listGoodUp(0);
        page.setTotalCount(goods.size());
        page.setList(goodService.listGoodUp(0, page));
        model.addAttribute("page", page);
        return "backStage/Product/productList";
    }


    @RequestMapping(value = "/testGood", method = RequestMethod.GET)
    public String testGood() {
        return "frontStage/Good/detail";
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("files") MultipartFile files) {
        log.info("异步上传文件");
        String fileName = UUID.randomUUID().toString() + ".jpg";
        String file = "http://115.29.141.108/Shop_XL_war/" + fileName;
        String path = File.separator + "var" + File.separator + "www" + File.separator + "html" + File.separator + "Shop_XL_war" + File.separator;
        try {
            FileUtils.copyInputStreamToFile(files.getInputStream(), new File(path, fileName));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传出错");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", 1);
        jsonObject.addProperty("image", file);
        return jsonObject.toString();
    }

}
