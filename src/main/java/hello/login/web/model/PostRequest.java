package hello.login.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    private Long id;             // PK
    private String title;        // 제목
    private String content;      // 내용

    private String writer;       // 작성자

    private Integer memberKey;

    public void removeTag() {
        this.title = this.title.replaceAll("<", "&lt;");
        this.title = this.title.replaceAll(">", "&gt;");
        this.content = this.content.replaceAll("<", "&lt;");
        this.content = this.content.replaceAll(">", "&gt;");
        this.content = this.content.replaceAll("&", "&amp;");
        this.content = this.content.replaceAll("\\(", "&#40;");
        this.content = this.content.replaceAll("\\)", "&#41;");
        this.content = this.content.replaceAll("\"", "&quot;");
        this.content = this.content.replaceAll("[$]", "&#36;");
        this.content = this.content.replaceAll("[*]", "&#42;");
        this.content = this.content.replaceAll("[+]", "&#43;");
        this.content = this.content.replaceAll("[|]", "&#124;");
        this.content = this.content.replaceAll("\\." , "&#46;");
        this.content = this.content.replaceAll("\\?" , "&#63;");
        this.content = this.content.replaceAll("\\[" , "&#91;");
        this.content = this.content.replaceAll("\\^" , "&#94;");
        this.content = this.content.replaceAll("\\^" , "&#94;");
        this.content = this.content.replaceAll("!", "&#33;");
        this.content = this.content.replaceAll("%", "&#37;");
        this.content = this.content.replaceAll(",", "&#44;");
        this.content = this.content.replaceAll("-", "&#45;");
        this.content = this.content.replaceAll("/", "&#47;");
        this.content = this.content.replaceAll(":", "&#58;");
        this.content = this.content.replaceAll("=", "&#61;");
        this.content = this.content.replaceAll("@", "&#64;");
        this.content = this.content.replaceAll("_", "&#95;");
        this.content = this.content.replaceAll("\\{" , "&#123;");
    }
}
