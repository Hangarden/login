package hello.login.web.member;

//import hello.login.domain.member.MemberRepository;
import hello.login.web.Rsa;
import hello.login.web.common.MessageDto;
import hello.login.web.mapper.MemberMapper;
import hello.login.web.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    private final MemberMapper memberMapper;

//    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {

        return "members/addMemberForm";
    }


    /**
     * 	//addMember method 방식이 다르기 때문에 주소를 중복해서 사용가능(get과 post)
     * @param member(회원가입 폼 전송시 요소의 name과 dto의 멤버변수의 이름과 같으면 자동으로 바인딩하는 객체)
     * 			ㄴ 커멘드 객체
     * @return redirect: -> request.sendRedirect("") 와 같은 의미
     */
    @PostMapping("/add")
    public String addMember(Member member) {
        memberService.addMember(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "result", required = false) String result) {

        model.addAttribute("title", "회원로그인");

        if(result != null) model.addAttribute("result", result);

        return "members/login";
    }



    @PostMapping("/login")
    public String login(Member member, HttpSession session, Model model,HttpServletRequest request) {

        String loginId = member.getLogin_id();
        String password = member.getPassword();

        Member checkMember = memberMapper.selectMember(loginId);

        if(checkMember != null && checkMember.getPassword() != null && password.equals(checkMember.getPassword())) {
            //String sessionId = checkMember.getMemberId();
            String sessionIP = request.getRemoteAddr();
            session.setAttribute("IP", sessionIP);
            String sessionName = checkMember.getName();
            Integer sessionId = checkMember.getMemberKey();
            session.setAttribute("MEMBER_ID", sessionId);
            session.setAttribute("SID", loginId);
            System.out.printf("{}", loginId);
            session.setAttribute("NAME", sessionName);
            System.out.printf("{}", sessionName);

            return "redirect:/loginHome";
        }

        MessageDto message = new MessageDto("등록된 회원이 없습니다.", "/members/login", RequestMethod.GET, null);

        return showMessageAndRedirect(message, model);
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //아이디 중복체크
    @PostMapping("add/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("loginId") String loginId) {

        int cnt = memberService.idCheck(loginId);
        return cnt;

    }


    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }
}
