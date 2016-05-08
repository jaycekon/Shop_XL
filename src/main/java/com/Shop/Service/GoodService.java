package com.Shop.Service;

import com.Shop.Dao.CommentDao;
import com.Shop.Dao.GoodDao;
import com.Shop.Dao.ImageDao;
import com.Shop.Model.Good;
import com.Shop.Model.Image;
import com.Shop.Util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
@Service
public class GoodService {
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private CommentDao commentDao;
    public boolean addGood(Good good){
        goodDao.save(good);
        return true;
    }

    public List<Good> listGood(){
        return goodDao.findAll();
    }

    public List<Good> listGoodByPage(Page<Good> page){
        return goodDao.findAllByPage(page);
    }


    public List<Good> listGoodUp(int status){
        return goodDao.findAllByStatus(status);
    }

    public List<Good> listGoodUp(int status,Page page){
        return goodDao.findAllByStatus(status,page);
    }

    public List<Good> listGoodByName(String name){
        return goodDao.findAllByName(name);
    }

    public Good findGoodById(int id){
        return goodDao.findById(id);
    }

    public boolean updateGood(Good good){
        goodDao.update(good);
        return true;
    }

    public void addImage(Image image){
        imageDao.save(image);
    }

    public Image findImageById(int id){
        return imageDao.findById(id);
    }

    public void updateImage(Image image){
        imageDao.update(image);
    }

    public List<Image> findImageByGoodId(int id){
        List<Image> imgs = imageDao.findAllByGoodId(id);
        return imgs;
    }

    public List<Image> findImage(){
        return imageDao.findImage();
    }

    public void clearImage(int id){
        List<Image> imgs = imageDao.findAllByGoodId(id);
        for(Image img:imgs){
            imageDao.delete(img);
        }
    }
}
