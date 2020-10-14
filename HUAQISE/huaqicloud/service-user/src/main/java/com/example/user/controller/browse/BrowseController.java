package com.example.user.controller.browse;

import com.alibaba.fastjson.JSON;
import com.example.common.vo.ResponseVO;
import com.example.enterprise.api.EpServiceClient;
import com.example.enterprise.vo.EnterpriseVO;
import com.example.user.bl.browse.BrowseService;
import com.example.user.dto.browse.BrowseDto;
import com.example.user.po.Browse;
import com.example.user.vo.BrowseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:
 * @Date: 2020/10/13 19:58
 */
@RestController
@RequestMapping("/api/browse")
@Api(tags = "浏览记录接口")
public class BrowseController {

    @Autowired
    private BrowseService browseService;

    @Resource
    private EpServiceClient epServiceClient;

    @PostMapping("/insert")
    @ApiOperation(value = "插入浏览记录")
    @ApiImplicitParam(name = "browseDto", value = "浏览记录", required = true ,dataType = "BrowseDto")
    public ResponseVO insertBrowse(@RequestBody BrowseDto browseDto){
        int effect = browseService.insertBrowse(browseDto);
        if(effect>0){
            return ResponseVO.buildSuccess();
        }else{
            return ResponseVO.buildFailure("浏览记录插入失败");
        }
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除浏览记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true ,dataType = "Integer"),
            @ApiImplicitParam(name = "epId", value = "企业Id", required = true ,dataType = "Integer")
    })
    public ResponseVO deleteBrowse(@RequestParam Integer userId,@RequestParam Integer epId){
        int effect = browseService.deleteBrowse(userId,epId);
        if(effect>0){
            return ResponseVO.buildSuccess();
        }else{
            return ResponseVO.buildFailure("浏览记录删除失败");
        }
    }


    @PostMapping("/getAllBrowse")
    @ApiOperation(value = "获取用户浏览记录")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true ,dataType = "Integer")
    public ResponseVO getBrowsesByUid(@RequestParam Integer userId){
        List<BrowseDto> browseDtos = browseService.getBrowseByUid(userId);
        List<BrowseVO> browseVOS = browseDtos.stream().filter(browseDto -> browseDto!=null).map(browseDto -> {
            BrowseVO browseVO=new BrowseVO();
            BeanUtils.copyProperties(browseDto,browseVO);
            ResponseVO responseVO=epServiceClient.getEnterpriseById(browseDto.getEpId());
            if (responseVO.getContent()!=null){
                browseVO.setEnterpriseVO(JSON.parseObject(JSON.toJSONString(responseVO.getContent()),EnterpriseVO.class));
            }
            return browseVO;
        }).collect(Collectors.toList());
        return ResponseVO.buildSuccess(browseVOS);
    }
}