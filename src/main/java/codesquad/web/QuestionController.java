package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "questionService")
    private QuestionService questionService;

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "/qna/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Question question) {
        Question addedQuestion = questionService.add(question);
        return "redirect:" + addedQuestion.generateUrl();
    }

    @GetMapping("{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/show";
    }

    @GetMapping("")
    public String readList(Model model) {
        List<Question> questions = questionService.findAll();
        log.debug("question size : {}", questions.size());
        model.addAttribute("questions", questions);
        return "/home";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Question target) {
        Question updatedQuestion = questionService.update(id, target);
        return "redirect:" + updatedQuestion.generateUrl();
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        questionService.delete(id);
        return "redirect:/questions";
    }
}
