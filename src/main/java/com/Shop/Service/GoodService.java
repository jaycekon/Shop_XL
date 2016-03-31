package com.Shop.Service;

import com.Shop.Dao.GoodDao;
import com.Shop.Dao.ImageDao;
import com.Shop.Model.Good;
import com.Shop.Model.Image;
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
    public boolean addGood(Good good){
        goodDao.save(good);
        return true;
    }

    public List<Good> listGood(){
        return goodDao.findAll();
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

    public List<String> findImageByGoodId(int id){
        List<Image> imgs = imageDao.findAllByGoodId(id);
        List<String> address = new ArrayList<>();
        for(Image img :imgs){
            address.add(img.getAddress());
        }
        return address;
    }

    public void clearImage(int id){
        List<Image> imgs = imageDao.findAllByGoodId(id);
        for(Image img:imgs){
            imageDao.delete(img);
        }
    }
}
