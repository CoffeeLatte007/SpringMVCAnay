package com.excelib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * Created by lizhaoz on 2016/3/27.
 */
@Controller
@SessionAttributes("articleId")
public class FollowMeController {
    private final String[] sensitiveWords=new String[]{"k1","s2"};
    @ModelAttribute("comment")
    public String replaceSensitiveWords(String comment) throws IOException{
        if (comment!=null){
            System.out.println("原始comment: "+comment);
            for (String sw :sensitiveWords) {
                comment=comment.replaceAll(sw,"");
            }
            System.out.println("去除敏感词comment:"+comment);
        }
        return comment;
    }
    @RequestMapping(value = {"/articles/{articleId}/comment"})
    public String doComment(@PathVariable String articleId,RedirectAttributes attributes,Model model) throws Exception{
        System.out.println(model.asMap().get("comment"));
        attributes.addFlashAttribute("comment",model.asMap().get("comment"));
        System.out.println(articleId);
        model.addAttribute("articleId", articleId);
        //保存评论内容
        return "redirect:/showArticle";
    }
    @RequestMapping(value = {"/showArticle"},method = {RequestMethod.GET})
    public String showArticle(Model model,SessionStatus sessionStatus){
        String articleId= (String) model.asMap().get("articleId");
        model.addAttribute("atricleTitle",articleId+"号文章标题");
        model.addAttribute("article",articleId+"号文章内容");
        sessionStatus.setComplete();
        return "article";
    }
}
