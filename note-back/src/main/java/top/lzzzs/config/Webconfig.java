package top.lzzzs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.lzzzs.interceptor.JwtInterceptor;

@Configuration
public class Webconfig implements WebMvcConfigurer {
    @Value("${resources-url.image-out-url}")
    private String imgOutUrl;

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截路径可自行配置多个 可用 ，分隔开
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 因为tomcat出于保护机制，上传一个文件要重启服务才能上传成功，为了不泄露服务器绝对路径
         * 所以配置映射就可以解决此问题
         */
        String os = System.getProperty("os.name");
        String path = "";
        System.out.println(os);
        if (os.toLowerCase().startsWith("win")) {
            // win
            path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\note-img\\";
        } else if (os.toLowerCase().startsWith("mac")) {
            // mac
            path = System.getProperty("user.dir") + "/src/main/resources/static/note-img/";
        } else if (os.toLowerCase().startsWith("linux")) {
            // linux 生产环境
            path = System.getProperty("user.dir") + "/" + imgOutUrl;
        }
        registry.addResourceHandler("/static/note-img/**").
                addResourceLocations("file:" + path);
    }
}
