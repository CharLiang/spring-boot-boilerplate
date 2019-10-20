/**
 * @(#)CacheService.java, 2019-01-10.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.youdao.sortinghat.dao.mysql.mapper.RegionMapper;
import com.youdao.sortinghat.dao.mysql.model.RegionEntity;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * CacheService
 *
 * @author 
 *
 */
@Service
public class CacheService {

    private static Logger logger = LoggerFactory.getLogger(CacheService.class);

    public static final long ALWAYS = 1;

    public static final long OFTEN = 5;

    public static final long SOMETIMES = 12;

    public static final long SELDOM = 24;

    public static final long HARDLY = 60;

    public static final long RARELY = 120;

    @Autowired
    private RegionMapper regionMapper;

    /**
     * 省份选项
     */
    private LoadingCache<Integer, Optional<Pair<RegionEntity, List<RegionEntity>>>> regionCache = CacheBuilder.newBuilder()
            .expireAfterAccess(RARELY, TimeUnit.MINUTES)
            .concurrencyLevel(8)
            .maximumSize(10000)
            .recordStats()
            .build(new CacheLoader<Integer, Optional<Pair<RegionEntity, List<RegionEntity>>>>() {
                @Override
                public Optional<Pair<RegionEntity, List<RegionEntity>>> load(Integer id) {
                    RegionEntity regionEntity = null;
                    if (id == 0) {
                        regionEntity = new RegionEntity("未选择", (byte) 0, 0);
                    } else {
                        regionEntity = regionMapper.selectByPrimaryKey(id);
                    }
                    if (regionEntity == null) {
                        return Optional.empty();
                    } else {
                        List<RegionEntity> regionEntityList = regionMapper.getByParentId(id);
                        return Optional.of(new Pair<>(regionEntity, regionEntityList));
                    }
                }
            });

    public LoadingCache<Integer, Optional<Pair<RegionEntity, List<RegionEntity>>>> getRegionCache() {
        return regionCache;
    }
}