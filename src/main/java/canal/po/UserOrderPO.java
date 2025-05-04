package canal.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderPO {

    private Integer id;
    private Integer uuid;
    private String name;
    private Integer age;

}
