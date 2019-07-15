package com.zoomway.patrol.controller;

import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.entity.Tower;
import com.zoomway.patrol.entity.TowerStyle;
import com.zoomway.patrol.service.TowerService;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("api/tower")
@Validated
public class TowerController {
    private static final Logger logger = LoggerFactory.getLogger(com.zoomway.patrol.controller.LineController.class);
    @Autowired
    TowerService towerService;

    /**
     * get line towers by wblu @ 2019-07-15
     *
     * @param debug
     */
    @GetMapping(value = "/")
    public HttpResult getLineTower(
            @Range(min = 1, message = "LineId should > 0") @RequestParam Integer line,
            @RequestParam(required = false,defaultValue = "0")Integer debug){
        try {
            List<Tower> towers = towerService.getByLine(line);
            if(towers == null){
                return HttpResult.ParamError("Cannot find any tower of line " + line);
            }
            return HttpResult.success(towers);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return HttpResult.ServerError(e);
        }
    }
    /**
     * query Tower Styles by wblu @ 2019-07-15
     *
     */
    @GetMapping(value = "/style")
    public HttpResult getTowerStyle(@Range(min = 0, message = "StyleId should > 0") @RequestParam(required = false,defaultValue = "0") Integer id){
        try {
            if(id == 0) {
                List<TowerStyle> styles = towerService.getStyle();
                if (styles == null) {
                    return HttpResult.ParamError("Cannot find any style of tower");
                }
                return HttpResult.success(styles);
            }else{
                TowerStyle style = towerService.getStyle(id);
                if (style == null) {
                    return HttpResult.ParamError("Cannot find any style of tower");
                }
                return HttpResult.success(style);
            }
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            e.printStackTrace();
            return HttpResult.ServerError(e);
        }
    }
}
