package com.zhang.yong.quit.programminglearning.modules.admin.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.zhang.yong.quit.programminglearning.modules.admin.AdminBaseController;
import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzMenu;
import com.zhang.yong.quit.programminglearning.modules.admin.service.QzMenuService;
import com.zhang.yong.quit.programminglearning.modules.admin.service.QzUserService;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuiz;
import com.zhang.yong.quit.programminglearning.modules.qz.service.QzQuizService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author zhangyong created on 2019/10/23
 **/
@Controller
@RequestMapping(AdminBaseController.prefix)
public class AdminController implements AdminBaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${my.app.is.single.session}")
    private String isSingleSession;

    @Value("${my.app.session.timeout}")
    private Long sessionTimeout;

    @Autowired
    private Producer producer;

    @Autowired
    private QzUserService qzUserService;

    @Autowired
    private QzMenuService qzMenuService;

    @Autowired
    private QzQuizService qzQuizService;

    @GetMapping("index")
    public void index() {

    }

    @GetMapping("hello")
    public void hello(Model model) {
        model.addAttribute("message","欢迎来到我的世界");
    }

    @RequestMapping("login")
    public void login(Model model) {
        log.info(JSON.toJSONString(qzMenuService.getById(1L)));
//        log.info(JSON.toJSONString(qzQuizService.getAll()));
        QzQuiz qzQuiz = qzQuizService.getById(1L);
        log.info(JSON.toJSONString(qzQuiz));
    }

    @PostMapping(value="login")
    public String doLogin(String username, String password, String validCode, String serviceUrl,
                          HttpSession session, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        String errorMsg = "";
        if (StringUtils.isEmpty(username)){
            errorMsg = "用户名不能为空";
        }
        if (StringUtils.isEmpty(password)){
            errorMsg = "用户名不能为空";
        }
        if(StringUtils.isEmpty(validCode)) {
            errorMsg = "验证码不能为空";
        }
        try {
            if(null == session) {
                errorMsg = "当前会话已失效";
            }else{
                if(null == session.getAttribute(Constants.KAPTCHA_SESSION_KEY)
                        || !validCode.equalsIgnoreCase(session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString())) {
                    errorMsg = "验证码输入错误";
                }
                if(null == session.getAttribute(Constants.KAPTCHA_SESSION_DATE)
                        ||  new Date().getTime() - (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE) > 60000) {
                    errorMsg = "验证码已过期";
                }
                //登录
                if(org.springframework.util.StringUtils.isEmpty(errorMsg)){
                    Subject currentUser = SecurityUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(username,password);
                    currentUser.login(token);
                    if("true".equalsIgnoreCase(isSingleSession)) {
                        ServletContext servletContext  = session.getServletContext();
                        if(servletContext.getAttribute(username) != null) {
                            try {
                                Subject preUser = (Subject)servletContext.getAttribute(username);
                                preUser.logout();
                            }catch (Exception e){
                                // ignore the exception
                            }
                        }
                        servletContext.setAttribute(username,currentUser);
                    }
                    return "redirect:/admin/index";
                }
            }
        } catch ( UnknownAccountException uae ) {
            log.warn("用户帐号不正确");
            errorMsg = "用户帐号或密码不正确";

        } catch ( IncorrectCredentialsException ice ) {
            log.warn("用户密码不正确");
            errorMsg = "用户帐号或密码不正确";

        } catch ( LockedAccountException lae ) {
            log.warn("用户帐号被锁定");
            errorMsg = lae.getMessage();

        } catch ( AuthenticationException ae ) {
            log.warn("登录出错",ae);
            errorMsg = "登录失败：用户认证不通过,请再试一次";
        }  catch (Exception e) {
            log.warn("登陆失败",e.getLocalizedMessage());
            errorMsg = "登录失败,请再试一次";
        }
        redirectAttributes.addFlashAttribute("username",username);
        redirectAttributes.addFlashAttribute("password",password);
        redirectAttributes.addFlashAttribute("errorMsg",errorMsg);
        redirectAttributes.addFlashAttribute("serviceUrl",serviceUrl);
        return "redirect:/admin/login";
    }

    @GetMapping("kaptcha")
    public void kaptcha(HttpSession session, HttpServletResponse resp) throws IOException {
        // Set to expire far in the past.
        resp.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        resp.setHeader("Pragma", "no-cache");

        // return a jpeg
        resp.setContentType("image/jpeg");

        // create the text for the image
        String capText = this.producer.createText();

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // store the date in the session so that it can be compared
        // against to make sure someone hasn't taken too long to enter
        // their kaptcha
        session.setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date().getTime());

        // create the image with the text
        BufferedImage bi = this.producer.createImage(capText);

        ServletOutputStream out = resp.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        out.close();
    }


    @GetMapping("logout")
    public String logout(RedirectAttributes redirectAttributes) {
        try {
            SecurityUtils.getSubject().logout();
            return "redirect:/admin/login";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg",e.getLocalizedMessage());
            return "redirect:/admin/login";
        }
    }

    @GetMapping("unauthc")
    public void unauthc() {

    }


    /**
     * 用户监控所有运行中的线程信息的接口
     * @author zhangyong created on 2019/10/8 2:19 PM
     * @param model
     * @return
     */
    @RequiresPermissions("admin:stackTrace:view")
    @GetMapping("stackTrace")
    public void stackTrace(Model model) {
        Map<Thread, StackTraceElement[]> threadStacks = Thread.getAllStackTraces();
        model.addAttribute("threadNum",threadStacks.size());
        model.addAttribute("threadStacks",threadStacks);
        model.addAttribute("currentThread",Thread.currentThread());
    }
}
