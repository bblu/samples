package com.zoomway.patrol.controller;
import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.entity.Line;
import com.zoomway.patrol.entity.Tower;
import com.zoomway.patrol.service.LineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.Range;

import java.util.HashMap;
import java.util.List;

//@EnableTransactionManagement

@CrossOrigin
@RestController
@RequestMapping("api/line")
@Validated
public class LineController {
    private static final Logger logger = LoggerFactory.getLogger(LineController.class);
    @Autowired
    LineService lineService;

    /**
     * 批量查询 by wblu @ 2019-05-30
     *
     * @param style  返回数据格式默认list，否则是tree
     */
    @GetMapping(value = "/")
    public HttpResult getLine(@RequestParam(required = false,defaultValue = "list")String style){
        HttpResult res = new HttpResult();
        try {
            List<Line> lines = lineService.getLine();
            if(lines == null){
                res.setCode(500);
                return res.setMessage("internal error");
            }
            if(style.equals("tree")){

            }
            return res.setData(lines);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return res.setMessage(e.getMessage());
        }
    }
    /**
     * 批量查询 by wblu @ 2019-06-19
     *
     * @param debug
     */
    @GetMapping(value = "/list")
    public HttpResult getLine(@RequestParam(required = false,defaultValue = "0")Integer debug){
        HttpResult res = new HttpResult();
        try {
            List<Line> lines = lineService.getLine();
            if(lines == null){
                res.setCode(500);
                return res.setMessage("internal error");
            }
            return res.setData(lines);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return res.setMessage(e.getMessage());
        }
    }
    /**
     * 批量查询 by wblu @ 2019-06-19
     *
     * @param debug
     */
    @GetMapping(value = "/tree")
    public HttpResult getLineTree(@RequestParam(required = false,defaultValue = "0")Integer debug){
        HttpResult res = new HttpResult();
        try {
            List<Line> lines = lineService.getLine();
            if(lines == null){
                res.setCode(500);
                return res.setMessage("internal error");
            }
            for(Line l :lines){

            }
            return res.setData(lines);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return res.setMessage(e.getMessage());
        }
    }
    /**
     * 批量查询 by wblu @ 2019-06-19
     *
     * @param debug
     */
    @GetMapping(value = "/tower")
    public HttpResult getLineTower(
            @Range(min = 1, message = "LineId should > 0") @RequestParam Integer line,
            @RequestParam(required = false,defaultValue = "0")Integer debug){
        HttpResult res = new HttpResult();
        try {
            List<Tower> towers = lineService.getTower(line);
            if(towers == null){
                res.setCode(500);
                return res.setMessage("internal error");
            }
            return res.setData(towers);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return res.setMessage(e.getMessage());
        }
    }
}
