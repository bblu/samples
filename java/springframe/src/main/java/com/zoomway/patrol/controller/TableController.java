package com.zoomway.patrol.controller;
import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.entity.Line;
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
@RequestMapping("api/table")
@Validated
public class TableController {
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

}
