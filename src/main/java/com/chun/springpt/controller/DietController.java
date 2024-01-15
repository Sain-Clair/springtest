package com.chun.springpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import com.chun.springpt.service.DietService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.DietVO;
import com.chun.springpt.vo.NutritionVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Slf4j
public class DietController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DietService Dservice;

    @GetMapping("/food_recommand")
    public Map<String, Object> foodRecommand() {
        // 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        log.info("로깅 - 요청아이디: {}", userName);

        // 외부 URL로부터 데이터 가져오기
        String url = "http://localhost:9000/dlmodel/getCal?id=" + userName;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // 외부 URL로부터 받은 데이터 반환
        return response;
    }

    @GetMapping("/diet_cal_analysis")
    public Map<String, Object> getDietList(@RequestParam("startPeriod") String startPeriod,
            @RequestParam("endPeriod") String endPeriod, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        log.info("로깅 - 요청아이디: {}", userName);

        List<DietVO> dietList = Dservice.selectDietList(userName, startPeriod, endPeriod);
        Map<String, Object> recommandCal = Dservice.getRecommandCal(userName);
        BigDecimal recommandCalBigDecimal = (BigDecimal) recommandCal.get("result");
        Double recommandCalDouble = recommandCalBigDecimal.doubleValue();
        log.info("로깅 - 권장 칼로리: {}", recommandCalDouble);

        List<DietVO> differ_last = Dservice.differ_last(userName);

        Map<String, Object> response = new HashMap<>();
        response.put("dietList", dietList);
        response.put("last_differ", differ_last);
        response.put("recommandCal", recommandCal.get("result"));

        return response; // JSON 형식으로 데이터를 반환합니다.
    }

    @GetMapping("diet_weight_analysis")
    public Map<String, Object>getWeightList(@RequestParam("startPeriod") String startPeriod,
        @RequestParam("endPeriod") String endPeriod) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        log.info("로깅 - 요청아이디: {}", userName);
        List<DietVO> weightList = Dservice.selectWeightList(userName, startPeriod, endPeriod);

        DietVO targetWeight = Dservice.getTargetWeight(userName);
        
        Map<String, Object> response = new HashMap<>();
        response.put("weightList", weightList);
        response.put("targetWeight",targetWeight);

        return response; // JSON 형식으로 데이터를 반환합니다.

    }

    @GetMapping("/getRecommandTandangi")
    public Map<String, Object> getRecommandTandangi(@RequestParam("startPeriod") String startPeriod,
    @RequestParam("endPeriod") String endPeriod) {

        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        Map<String, Object> recommandTandnagi = Dservice.GetRecommandTandangi(userName);
        NutritionVO avgTanDanGi = Dservice.getWeekAvgTandangi(userName, startPeriod, endPeriod);
        
        Map<String, Object> response = new HashMap<>();
        response.put("recommandTandnagi", recommandTandnagi);
        response.put("avgTanDanGi",avgTanDanGi);
        return response;
    }

}