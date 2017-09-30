
import com.lzj.domain.User;

import com.lzj.utils.ComentUtils;
import org.apache.commons.logging.Log;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;


public class TestNurmal {


    @Test
    public void testEnum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IntrospectionException {
        User user=new User();
        Object o=user;
        Class clazz=o.getClass();
     /*   Method method=clazz.getMethod("setUpdateTime", Date.class);
        try {
            method.invoke(user,new Date());
            System.out.println(user.getUpdateTime());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
        PropertyDescriptor descriptor=new PropertyDescriptor("updateTime",clazz);
        descriptor.getWriteMethod().invoke(user,new Date());
        System.out.println(user.getUpdateTime());
    }
    @Test
    public void testIcon(){
        String icon= ComentUtils.ICON_DIR.substring(ComentUtils.ICON_DIR.indexOf("static"));
        System.out.println(icon);
    }

}
