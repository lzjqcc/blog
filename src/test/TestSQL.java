import com.google.common.collect.Lists;
import com.lzj.annotation.EnableRelationTable;
import com.lzj.domain.BaseEntity;
import com.lzj.domain.Friend;
import com.lzj.exception.SystemException;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class TestSQL {
    private static final String TABLE_NAME = "tableName";
    private static final String FIELD_VALUE = "fieldName";
    private static final String KEY_ROW = "keyRow";
    private static Map<String, Object> parseFieldAnnotation(BaseEntity entity) throws IllegalAccessException, IntrospectionException {
        Map<String, Object> tableInfo = new HashMap<>();
        Map<String, Object> fieldMap = new LinkedHashMap<>();
        Map<String, List> keyRowMap = new HashMap<>();
        tableInfo.put(KEY_ROW, keyRowMap);
        fieldMap.put("create_time", new Date());
        fieldMap.put("update_time", new Date());
        Class entityClass = entity.getClass();
        Method idMethod = PropertyUtils.getReadMethod(new PropertyDescriptor("id", entityClass));
        EnableRelationTable idRelation = idMethod.getAnnotation(EnableRelationTable.class);
        if (idRelation != null) {
            fieldMap.put(idRelation.value()[0],entity.getId());
        }
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Method method = PropertyUtils.getReadMethod(new PropertyDescriptor(field.getName(),entityClass));
            EnableRelationTable relationTable = method.getAnnotation(EnableRelationTable.class);
            if (relationTable == null) {
                continue;
            }
            if (!relationTable.keyRow()) {
                fieldMap.put(relationTable.value()[0], field.get(entity));
            }else {
                keyRowMap.put(relationTable.value()[0], (List) field.get(entity));
            }
            if (tableInfo.get(TABLE_NAME) == null) {
                tableInfo.put(TABLE_NAME, relationTable.relationTableName());
            }
        }
        tableInfo.put(FIELD_VALUE, fieldMap);
        return tableInfo;
    }
    private static String buildSQL(BaseEntity entity) throws IllegalAccessException, IntrospectionException {
        Map<String, Object> map = parseFieldAnnotation(entity);
        Map fieldMap = (Map) map.get(FIELD_VALUE);
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ")
                .append(map.get(TABLE_NAME))
                .append(" (create_time,update_time");
        Set<Map.Entry<String, Object>> entries = fieldMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            builder.append("," + entry.getKey());
        }
        builder.append(") values ");
        Map keyRowMap = (Map) map.get(KEY_ROW);

        builder.append(buildValues(keyRowMap, singleValue(fieldMap)));

        return builder.toString();
    }

    /**
     * (1,2,3,
     * @return
     */
    private static String singleValue(Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        builder.append(" (");
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (!entry.getValue().getClass().getSimpleName().endsWith("List")) {
                builder.append(entry.getValue()+",");
            }
        }
        return builder.toString();
    }
    //
    private static String buildValues(Map map, String singleValue) {
        Set<Map.Entry<String,Object>> keyRowEntry = map.entrySet();
        List list = null;
        String fieldName = null;
        for (Map.Entry<String,Object> entry : keyRowEntry) {
            list = (List) entry.getValue();
            fieldName = entry.getKey();
        }
        if (list == null) {
            throw new SystemException(120,"关联"+fieldName+"为null;插入keyRow信息"+map);
        }
        StringBuilder builder = new StringBuilder();

        for (Object o : list) {
            builder.append(" "+singleValue);
            builder.append(o +"),");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
    @Test
    public void test() throws IntrospectionException, IllegalAccessException {
        Friend friend = new Friend();
        friend.setId(1);
        friend.setFriendId(2);
        friend.setCurrentAccountId(4);
        List<Integer> list =Lists.newArrayList(4, 9, 10);
        friend.setFunctionList(list);
        String sql = buildSQL(friend);
        System.out.println(sql);
    }
}
