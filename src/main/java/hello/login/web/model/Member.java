package hello.login.web.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;


@ToString
@Getter
@Setter
public class Member {

    private Integer memberKey;
    private String login_id; //로그인 ID
    private String name; //사용자 이름
    private String password;
    private String passwordCheck;
    private String email1;
    private String email2;
    private String zipcode;
    private String address_one;
    private String address_two;
    @Size(min=3)
    private String tel1;
    @Size(min=4)
    private String tel2;
    @Size(min=4)
    private String tel3;

}
