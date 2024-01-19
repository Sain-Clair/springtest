package com.chun.springpt.dao;


import com.chun.springpt.vo.ImgRequestVO;
import com.chun.springpt.vo.UpPhotoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UpPhotoDao {
    List<UpPhotoVo> todayPhotoList(String user_id);
    int deleteFoodData(int upphotoid);
    void updateQuantity(UpPhotoVo vo);

    Map<String,Object> selectFoodWeight(int upphotoid);

    void insertRequestFood(ImgRequestVO vo);
}
