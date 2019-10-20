/**
 * @(#)TermService.java, 2019-03-14.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.youdao.sortinghat.dao.mysql.model.TermEntity;
import com.youdao.sortinghat.data.dto.TermDTO;
import com.youdao.sortinghat.service.base.BaseService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TermService
 *
 * @author 
 *
 */
@Service
public class TermService extends BaseService<TermEntity> {

    /**
     * 获取学校列表
     * @return
     */
    @Cacheable(value = "getTermList")
    public List<TermDTO> getTermList() {
        List<TermEntity> termEntityList = selectAll();
        return termEntityList.stream()
                .filter(TermEntity::isActivate)
                .map(TermDTO::new)
                .collect(Collectors.toList());
    }
}