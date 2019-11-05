package com.zhang.yong.quit.programminglearning.modules.qz.controller;

import com.zhang.yong.quit.programminglearning.modules.qz.QzBaseController;
import com.zhang.yong.quit.programminglearning.utils.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangyong created on 2019/10/29
 **/
@Controller
@RequestMapping(QzBaseController.prefix + "/quiz")
public class QzQuizController implements QzBaseController {

    @GetMapping("test")
    public void test(Model model) {
        model.addAttribute("currentUser",SecurityUtil.getCurrentUser());
    }

    @GetMapping("myTest")
    public void myTest(Model model) {
        model.addAttribute("currentUser",SecurityUtil.getCurrentUser());
    }

    @GetMapping("testHistory")
    public void testHistory(Model model) {
        model.addAttribute("currentUser",SecurityUtil.getCurrentUser());
    }
}
