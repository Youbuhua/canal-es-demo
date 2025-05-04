package canal;

import canal.dao.elasticsearch.IElasticsearchUserOrderDao;
import canal.po.UserOrderPO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserOrderDaoTest {

    @Resource
    private IElasticsearchUserOrderDao userOrderDao;

    @Test
    public void test() {
        List<UserOrderPO> userOrderPOS = userOrderDao.selectByUserId();
        log.info("测试结果：{}", JSON.toJSONString(userOrderPOS));
    }

}