package hello.login.web;

//import hello.login.domain.item.Item;
//import hello.login.domain.member.MemberVO;
        import hello.login.web.argumentresolver.Login;
import hello.login.web.member.MemberService;
        import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;


//    @GetMapping("/")
//    public String homeLoginV3ArgumentResolver(@Login MemberVO loginMemberVO, Model model) {
//
//        //세션에 회원 데이터가 없으면 home
//        if (loginMemberVO == null) {
//            return "home";
//        }
//
//        //세션이 유지되면 로그인으로 이동
//        model.addAttribute("member", loginMemberVO);
//        return "loginHome";
//    }
    @GetMapping("/loginHome")
    public String loginHome(HttpSession session, Model model) {
        Object ob1 = session.getAttribute("NAME");
        String mySessionName = (String)ob1;
    //세션이 유지되면 로그인으로 이동
    model.addAttribute("name", mySessionName);
    return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver() {

        //세션에 회원 데이터가 없으면 home

        return "home";

    }

    @PostMapping("/passwordCheck")
    @ResponseBody
    public int idCheck(@RequestParam("password") String password) {

        int cnt = memberService.passwordCheck(password);
        return cnt;

    }
}