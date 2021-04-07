package org.zhouhy.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * 知识点总结:
 * 1. 在Security中.loginPage("/login.html") 这里如果写成这样,并且在controller里面没有配置过/login.html这个路径, 
 * 那么它会以访问静态资源的方式来找到login.html, 那么也就是说在login.html里面一些关于thymeleaf的标签就不起作用了.
 * 
 * 2. 关于thymeleaf, 它的默认路径是template 可以通过在application.properties中设置 spring.thymeleaf.prefix=classpath:/static/
 * 把它的路径改成static
 * 
 * 3. 关于这个静态资源访问 Spring Boot 默认会挨个从META-INF/resources > resources > static > public  里面找是否存在相应的资源
 * 所谓静态资源的默认目录实际上就是http://localhost:8080/能直接访问到的目录. 例如如果在static里面新建一个文件夹css 里面放一个文件main.css
 * 也只需http://localhost:8080/css/main.css
 * 
 * 4. 由于这个静态资源的默认目录的存在, 所以在spring security 中设置忽略文件的时候不需要加入静态资源的默认目录
 * 例如在本例的静态资源都在static目录下, 那么就直接写成 web.ignoring().antMatchers("/js/**", "/css/**","/images/**"); 即可
 * 不需要再加static
 * 
 * 5. 由于这个静态资源的默认目录的存在, 在页面中写成<link rel="stylesheet" type="text/css" href="css/main.css">即可不需要写成
 * <link rel="stylesheet" type="text/css" href="/static/css/main.css">
 *     
 * 6. successForwardUrl 表示不管你是从哪里来的，登录后一律跳转到 successForwardUrl 指定的地址。例如 successForwardUrl 
 * 指定的地址为 /index ，你在浏览器地址栏输入 http://localhost:8080/hello，结果因为没有登录，重定向到登录页面，当你登录成功之后，
 * 就会服务端跳转到 /index 页面
 * 
 * 7. defaultSuccessUrl 跟上述情况相反, 在上述的情况下它会跳到 http://localhost:8080/hello 但如果将defaultSuccessUrl 第二个参数
 * 设置成true, 效果跟successForwardUrl 是一样的
 * 
 * 8. 由于本例 .loginProcessingUrl("/doLogin") 是post请求, 所以登录成功到.defaultSuccessUrl("/welcome") 也肯定是post请求
 * 如果此时把/welcome在controller里设置成get请求的, 那么就会失败
 * 
 * 9. 在本例中提供了三种方法得到用户的用户名. 参看本例的Controller
 * 
 * 10. 如果你的用户名和密码从数据库来并且靠JPA来取得那么要做到以下三点
 *  1) User 必须要实现 UserDetails
 *  2) 必须要有一个实现UserDetailsService的类,并实现这个类的loadUserByUsername方法, 这个类其实就是UserService类
 *  3) 在SecurityConfig中注入UserService类, 并且 auth.userDetailsService(userService);
 *  
 * 11. 如果要在thymeleaf中引入spring security的标签需要做到这样几步
 *  1) 在pom文件中引入 thymeleaf-extras-springsecurity5 参见本例pom文件
 *  2) 在前台页面中输入xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5" 
 *  或者 xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4 (4.x版)
 *  3) 具体参看本例的使用方式
 * 
 * 12. http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll(); 就等于所有认证都不需要了
 * 
 * 13. 开启csrf有这么几个步骤
 *  1) 注销.csrf().disable()
 *  2) 在前台加入<input type="hidden" th:value="${_csrf.token}" th:name="${_csrf.parameterName}">
 *  3) logout一定要是post请求
 *  
 *  14. 关于这个logout 加入这个.logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST")) 那么logout必须是post请求
 *  如果不加这个在没有csrf认证的情况下,既可以是 get请求也可以是post请求
 * 
 *  15. 关于这个successForwardUrl 这个跳转好像并不改变浏览器里的地址, 例如.defaultSuccessUrl("/welcome")登录成功之后地址栏里会是/welcom
 *  而这个.successForwardUrl("/welcome")登录成功之后 地址栏里变成了/doLogin
 *  
 *  16. 关于这个return "redirect:/welcome"; 它会直接跳转到/welcome 这个路径中去, 而且这个路径必须要有GET请求访问,如果只是POST请求会报错
 *  
 *  
 *  
 * */
@SpringBootApplication
public class FormLoginApp {
    public static void main(String[] args) {
        SpringApplication.run(FormLoginApp.class,args);
    }
}
