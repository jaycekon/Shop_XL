package com.Shop.Service;

import com.Shop.Dao.AddressDao;
import com.Shop.Dao.AreaDao;
import com.Shop.Dao.CommentDao;
import com.Shop.Model.Area;
import com.Shop.Model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
@Service
public class AddressService {
    @Autowired
    AreaDao areaDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    AddressDao addressDao;

    public void addArea(Area area){
        areaDao.save(area);
    }

    public void updateArea(Area area){
        areaDao.update(area);
    }

    public Area findAreaByAreaName(String name){
        return areaDao.findAreaByAreaName(name);
    }

    public Area findAreaById(int id){
        return areaDao.findById(id);
    }


    public List<Area> findTopArea(){
        return areaDao.findTopArea();
    }



    public List<Area> findAllArea(){
        return areaDao.findAll();
    }

    public List<Area> findAllAreaByAreaId(int area_id){
        return areaDao.findAllByAreaId(area_id);
    }

    public void addComment(Comment comment){
        commentDao.save(comment);
    }


    public List<Comment> findCommentByGoodId(int good_id){
        return commentDao.findCommentByGoodId(good_id);
    }

    public void deleteAddress(int id){
        addressDao.deleteById(id);
    }
}
