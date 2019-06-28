package com.zoomway.patrol.controller;
import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.entity.Line;
import com.zoomway.patrol.entity.Tower;
import com.zoomway.patrol.service.LineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.Range;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getLine(@RequestParam(required = false,defaultValue = "list")String style){
        try {
            List<Line> lines = lineService.getLine();
            if(lines == null){
                return  new ResponseEntity("NotFindeError",HttpStatus.BAD_REQUEST);
            }
            if(style.equals("tree")){

            }
            Map<String,Object> map = new HashMap<>();
            map.put("data",lines);
            return new ResponseEntity(map,HttpStatus.OK);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 批量查询 by wblu @ 2019-06-19
     *
     * @param debug
     */
    @GetMapping(value = "/list")
    public HttpResult getLine(@RequestParam(required = false,defaultValue = "0")Integer debug){
        try {
            List<Line> lines = lineService.getLine();
            if(lines == null){
                return HttpResult.ServerError("Cannot find any power line");
            }
            return HttpResult.success(lines);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return HttpResult.ServerError(e);
        }
    }
    /**
     * 批量查询 by wblu @ 2019-06-19
     *
     * @param debug
     */
    @GetMapping(value = "/tree")
    public HttpResult getLineTree(@RequestParam(required = false,defaultValue = "0")Integer debug){
        try {
            List<Line> lines = lineService.getLine();
            if(lines == null){
                return HttpResult.ServerError("Cannot find any power line");
            }
            for(Line l :lines){
            }
            return HttpResult.success(lines);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return HttpResult.ServerError(e);
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
        try {
            List<Tower> towers = lineService.getTower(line);
            if(towers == null){
                return HttpResult.ServerError("Cannot find any tower of line " + line);
            }
            return HttpResult.success(towers);
        }catch (Exception e){
            logger.error("请求线路异常" + e.getMessage());
            return HttpResult.ServerError(e);
        }
    }
}
