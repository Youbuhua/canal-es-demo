package canal.dao.elasticsearch;

import org.apache.ibatis.annotations.Mapper;
import canal.po.UserOrderPO;

import java.util.List;

@Mapper
public interface IElasticsearchUserOrderDao {

    List<UserOrderPO> selectByUserId();

}
