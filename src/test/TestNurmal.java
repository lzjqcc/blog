
import com.lzj.VO.ArticleMongo;
import com.lzj.domain.Article;
import com.lzj.domain.User;

import com.lzj.utils.ComentUtils;
import org.apache.commons.logging.Log;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;


public class TestNurmal {


    @Test
    public void testEnum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IntrospectionException {
        User user = new User();
        Object o = user;
        Class clazz = o.getClass();
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
        PropertyDescriptor descriptor = new PropertyDescriptor("updateTime", clazz);
        descriptor.getWriteMethod().invoke(user, new Date());
        System.out.println(user.getUpdateTime());
    }

    @Test
    public void testRemove() {
        List<String> list = new ArrayList<>();
        list.add("dd");
        list.add("aa");
        Iterator<String> iterato = list.iterator();
        while (iterato.hasNext()) {
            if (iterato.next().equals("aa")) {
                iterato.remove();
                ;
            }
        }
        System.out.println(list);
    }

    @Test
    public void testSplit() {
        String name = "a.jpg";
        String[] array = name.split("\\.");
        for (String a : array) {
            System.out.println(a);
        }
    }

    @Test
    public void testFile() throws IOException {
        String articlePic = ComentUtils.ARTICLE_PIC + "/3";
        System.out.println(articlePic);
        File parent = new File(articlePic);
        if (!parent.exists()) {
            parent.mkdirs();
        }

    }

    @Test
    public void testEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSentDate(new Date());
        message.setFrom("lzjqcc@163.com");
        message.setTo("1161889163@qq.com");
        message.setText("真实故事");
        message.setSubject("不用回复");
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setPassword("LZJ941005");
        sender.setHost("smtp.163.com");
        sender.setUsername("lzjqcc@163.com");
        sender.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.setProperty("javaMailProperties", "mail.smtp.auth=true");
        sender.setJavaMailProperties(properties);
        //sender.send(message);
        sender.send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("lzjqcc@163.com");
                message.setTo("1161889163@qq.com");
                message.setSubject("my subject");
                message.setText("<a href=\"http://www.baidu.com\">www.baidu.com</a> ;", true);

                //message.addInline("myLogo", new ClassPathResource("img/mylogo.gif"));
               // message.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));

            }
        });


    }

    @Test
    public void testRename() {
        File file = new File("/home/li/dd");
        file.renameTo(new File("/home/li/d"));
    }

    @Test
    public void testBeanUtil() {
        Article article = new Article();
        article.setId(1);
        article.setCreateTime(new Date());
        article.setContent("ddd");
        ArticleMongo articleMongo = new ArticleMongo();
        BeanUtils.copyProperties(article, articleMongo);
        System.out.println(articleMongo);
    }

    @Test
    public void testIcon() {
        String icon = ComentUtils.ICON_DIR.substring(ComentUtils.ICON_DIR.indexOf("static"));
        System.out.println(icon);
    }

    @Test
    public void testDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        System.out.print(format.format(date));
    }

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    static {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png"); //PNG (png)
    }

    /**
     * 得到上传文件的文件头
     *
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据制定文件的文件头判断其文件类型
     *
     * @param filePaht
     * @return
     */
    public static String getFileType(String filePaht) {
        String res = null;
        try {
            FileInputStream is = new FileInputStream(filePaht);
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);

            System.out.println(fileCode);


            //这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                if (key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


}
