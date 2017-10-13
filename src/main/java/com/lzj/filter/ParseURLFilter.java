package com.lzj.filter;

import org.apache.catalina.connector.RequestFacade;
import org.apache.coyote.Request;
import org.apache.tomcat.util.buf.MessageBytes;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.Field;

import static javax.swing.UIManager.get;
import static javax.swing.UIManager.removeAuxiliaryLookAndFeel;

public class ParseURLFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(servletRequest);
        if (servletRequest instanceof RequestFacade){
            RequestFacade facade= (RequestFacade) servletRequest;
            Class clzz=RequestFacade.class;
            try {
                Field field=clzz.getDeclaredField("request");
                field.setAccessible(true);
                org.apache.catalina.connector.Request request=(org.apache.catalina.connector.Request) field.get(facade);
                Class requestClass=request.getClass();
                Field coyoteField=requestClass.getDeclaredField("coyoteRequest");
                coyoteField.setAccessible(true);
                Request coyoteRequest=(Request)coyoteField.get(request);
                Class requestClazz=Request.class;
                Field uriMBField= requestClazz.getDeclaredField("uriMB");
                uriMBField.setAccessible(true);
                MessageBytes uriMB=(MessageBytes)uriMBField.get(coyoteRequest);
                String path=uriMB.getString();
                uriMB.setString(path);
                filterChain.doFilter(facade,servletResponse);
                System.out.println(((RequestFacade) servletRequest).getRequestURL());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            facade.getRequestURL();
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
