package com.chun.springpt.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.DietDao;
import com.chun.springpt.vo.DietVO;
import com.chun.springpt.vo.NutritionVO;

import java.util.Map;
import java.util.HashMap;
@Service
public class DietService {
    @Autowired
    private DietDao dietDao;

    public List<DietVO> selectDietList(String userName, String startPeriod, String endPeriod) {
        return dietDao.Dailyinfo(userName, startPeriod, endPeriod);
    }

    public Map<String, Object> getRecommandCal(String userName){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("userName", userName);
        resultMap.put("result",0);
        dietDao.getRecommandCal(resultMap);
        return resultMap;
    }

    public List<DietVO> differ_last(String userName){
        return dietDao.get_differ_lastday(userName);
    }


    public List<DietVO> selectWeightList(String userName, String startPeriod, String endPeriod) {
        return dietDao.getWeekWeight(userName, startPeriod, endPeriod);
    }

    public DietVO getTargetWeight(String userName) {
        return dietDao.getTargetWeight(userName);
    }
    
    public Map<String, Object> GetRecommandTandangi(String userName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        dietDao.getRecommandTandangi(params); // 저장 프로시저 호출
        return params; // OUT 매개변수가 포함된 결과 맵 반환
        
    }

    public NutritionVO getWeekAvgTandangi(String userName,String startPeriod, String endPeriod){
        return dietDao.getWeekAvgTandangi(userName, startPeriod, endPeriod);
    }
    
}
