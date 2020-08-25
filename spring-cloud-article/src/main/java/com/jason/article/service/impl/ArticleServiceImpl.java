package com.jason.article.service.impl;

import com.jason.article.domain.ArticleDto;
import com.jason.article.domain.ArticleVo;
import com.jason.article.mapper.ArticleMapper;
import com.jason.article.service.ArticleService;
import com.jason.domain.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleServiceImpl
 * @Description TODO
 * @Author GCJ
 * @Date 2019/12/2 11:07
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResultVo queryArticle() {
        ResultVo result = new ResultVo();
        List<ArticleVo> list = articleMapper.queryArticle();
        result.setData(list);
        return result;

    }

    @Override
    public ResultVo queryArticleById(String id) {
        ResultVo result = new ResultVo();
        ArticleVo articleVo = articleMapper.queryArticleById(id);
        result.setData(articleVo);
        return result;
    }

    @Override
    public ResultVo addSomeOneArticle(ArticleDto dto) {
        ResultVo result = new ResultVo();
        articleMapper.addSomeOneArticle(dto);
        return null;
    }



}
