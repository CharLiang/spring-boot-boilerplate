/**
 * @(#)CommonApi.java, 2019-03-13.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api;

import com.google.common.collect.Lists;
import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.dao.mysql.model.RegionEntity;
import com.youdao.sortinghat.data.dto.GradeDTO;
import com.youdao.sortinghat.data.dto.RegionDTO;
import com.youdao.sortinghat.data.dto.SubjectDTO;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.CacheService;
import com.youdao.sortinghat.service.SchoolService;
import com.youdao.sortinghat.service.TermService;
import com.youdao.sortinghat.type.organization.GradeType;
import com.youdao.sortinghat.type.organization.SubjectType;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CommonApi
 *
 * @author 
 *
 */
@RestController
@RequestMapping("/api/common")
public class CommonApi extends BaseApi {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private TermService termService;

    @Autowired
    private CacheService cacheService;

    /**
     * 学校列表
     * @return
     */
    @GetMapping("/school")
    public Object school() {
        return Result.success(schoolService.getSchoolList());
    }

    /**
     * 学科列表
     * @return
     */
    @GetMapping("/subject")
    public Object subject() {
        return Result.success(Arrays.stream(SubjectType.values())
                .map(SubjectDTO::new)
                .collect(Collectors.toList()));
    }

    /**
     * 年级列表
     * @return
     */
    @GetMapping("/grade")
    public Object grade() {
        return Result.success(Arrays.stream(GradeType.values())
                .map(GradeDTO::new)
                .collect(Collectors.toList()));
    }

    /**
     * 学期列表
     * @return
     */
    @GetMapping("/term")
    public Object term() {
        return Result.success(termService.getTermList());
    }

    /**
     * 省份列表
     * @return
     */
    @GetMapping("/region")
    public Object region(@RequestParam(defaultValue = "0") int id) {
        Optional<Pair<RegionEntity, List<RegionEntity>>> regionEntityListPair = cacheService.getRegionCache().getUnchecked(id);
        if (regionEntityListPair.isPresent()) {
            return Result.success(regionEntityListPair.get().getValue().stream()
                    .map(RegionDTO::new)
                    .collect(Collectors.toList()));
        } else {
            return Result.success(Lists.newArrayList());
        }
    }
}