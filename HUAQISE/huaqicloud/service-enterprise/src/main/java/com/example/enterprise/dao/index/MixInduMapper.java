package com.example.enterprise.dao.index;

import com.example.enterprise.po.index.integrate.MassDiscrete;
import com.example.enterprise.po.index.integrate.MixIndustry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:
 * @Date: 2020/10/24 0:04
 */
@Mapper
@Repository
public interface MixInduMapper {


    /**
     * 插入混合行业企业两化指标
     * @param mixIndustry
     * @return
     */
    int insertMixIndu(MixIndustry mixIndustry);

    /**
     * 根据企业id获得混合行业化两化指标
     * @param enterprise_id
     * @return
     */
    MixIndustry getMixIndByEpId(@Param("enterprise_id")Integer enterprise_id);

    /**
     * 获取所有的混合行业化两化指标，用于计算企业信用评分
     * @return
     */
    List<MixIndustry> getAllMixInd();
}
