package com.Shop.Service;

import com.Shop.Dao.GoodDao;
import com.Shop.Dao.ImageDao;
import com.Shop.Model.Good;
import com.Shop.Model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Good good1 =goodDao.findById(good.getId());
        good1.setName(good.getName());
        good1.setNum(good.getNum());
        good1.setWholesaleCount(good.getWholesaleCount());
        goodDao.update(good1);
        return true;
    }

    public void addImage(Image image){
        imageDao.save(image);
    }

    public List<Image> findImageByGoodId(int id){
        return imageDao.findAllByGoodId(id);
    }
}
