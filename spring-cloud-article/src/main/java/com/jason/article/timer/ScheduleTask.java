package com.jason.article.timer;

import com.jason.article.mapper.ArticleMapper;
import com.jason.article.service.IArticleService;
import com.jason.utils.FastjsonUtil;
import com.jason.utils.RedisKeyUtil;
import com.jason.utils.UUidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName AsyncArticleService
 * @Description TODO
 * @Author GCJ
 * @Date 2020/10/15 12:34
 */
@Component
@EnableScheduling
public class ScheduleTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private IArticleService articleService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 定时任务异步刷新
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void asyncArticleRelation(){
        LOGGER.info("文章收藏定时任务开始执行刷新redis内容到mysql");
        long start = System.currentTimeMillis();
        Map<Object, Object> redisMap = stringRedisTemplate.opsForHash().entries(RedisKeyUtil.MAP_KEY_ART_COLLECT);
        if(!CollectionUtils.isEmpty(redisMap)){
            for (Map.Entry<Object, Object> entry : redisMap.entrySet()) {
                Long account = null;
                try {
                    account = Long.valueOf(entry.getKey().toString());
                    Set<String> list = FastjsonUtil.deserializeToSet(entry.getValue().toString(), String.class);
                    Map<String, Object> map = new HashMap<>();
                    map.put("account",account);
                    map.put("articleList",entry.getValue().toString());
                    map.put("articleCount",list.size());
                    map.put("id", UUidUtils.getUUid());
                    map.put("createTime",new Date());
                    articleService.asyncArticleRelation(account,map);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("异常回滚:->account{}",account);
                    continue;
                }
            }
        }
        long end = System.currentTimeMillis();
        LOGGER.info("文章收藏定时任务结束,用时->{}毫秒",(end - start));

    }
}
