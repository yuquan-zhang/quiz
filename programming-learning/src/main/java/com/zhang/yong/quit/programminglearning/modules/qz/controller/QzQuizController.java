package com.zhang.yong.quit.programminglearning.modules.qz.controller;

import com.zhang.yong.quit.programminglearning.modules.qz.QzBaseController;
import com.zhang.yong.quit.programminglearning.modules.qz.form.AnswerForm;
import com.zhang.yong.quit.programminglearning.modules.qz.service.QzQuizService;
import com.zhang.yong.quit.programminglearning.utils.SecurityUtil;
import com.zhang.yong.quit.programminglearning.views.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhangyong created on 2019/10/29
 **/
@Controller
@RequestMapping(QzBaseController.prefix + "/quiz")
public class QzQuizController implements QzBaseController {

    @Autowired
    private QzQuizService qzQuizService;

    @GetMapping("test")
    public void test(Model model) {
        model.addAttribute("currentUser",SecurityUtil.getCurrentUser());
    }

    @ResponseBody
    @PostMapping("tests")
    public JsonObject tests(@RequestBody Map<String, Object> params) {
        return JsonObject.success(qzQuizService.getAllByUserId(SecurityUtil.getCurrentUser().getId()));
    }

    @ResponseBody
    @PostMapping("saveAnswer")
    public JsonObject saveAnswer(@RequestBody AnswerForm form) {
        qzQuizService.saveAnswer(form);
        return JsonObject.success();
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
