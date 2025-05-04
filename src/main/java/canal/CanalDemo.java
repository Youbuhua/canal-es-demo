package canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lyh
 * @date 2025/4/30 16:05
 * @description TODO
 */
public class CanalDemo {


    public static void main(String[] args) throws InvalidProtocolBufferException {
        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress("localhost", 11111), "example", "", "");



        while(true) {
            canalConnector.connect();

//            canalConnector.subscribe("canal-demo-01.*");
            canalConnector.subscribe(".*\\..*");

            Message message = canalConnector.get(100);

            List<CanalEntry.Entry> entries = message.getEntries();

            if(entries.size() <= 0) {
                System.out.println("无数据");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                for(CanalEntry.Entry entry : entries) {
                    String tableName = entry.getHeader().getTableName();

                    CanalEntry.EntryType entryType = entry.getEntryType();

                    if(CanalEntry.EntryType.ROWDATA.equals(entryType)) {
                        ByteString storeValue = entry.getStoreValue();

                        CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(storeValue);

                        CanalEntry.EventType eventType = rowChange.getEventType();

                        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();

                        for(CanalEntry.RowData rowData : rowDatasList) {
                            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
                            Map<String, Object> bMap = new HashMap<>();

                            for(CanalEntry.Column column : beforeColumnsList) {
                                bMap.put(column.getName(), column.getValue());
                            }

                            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                            Map<String, Object> aMap = new HashMap<>();

                            for(CanalEntry.Column column : afterColumnsList) {
                                aMap.put(column.getName(), column.getValue());
                            }

                            System.out.println("表名:" + tableName + " 操作类型:" + eventType);
                            System.out.println("改前:" + bMap);
                            System.out.println("改后:" + aMap);
                        }
                    }
                }
            }
        }
    }
}
