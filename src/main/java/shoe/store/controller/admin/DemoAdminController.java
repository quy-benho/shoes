package shoe.store.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/admin")
public class DemoAdminController {

    @GetMapping("/*.html")
    public String getDemoHtml() {

        String path = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        System.out.println(path);
        if (path.contains(".html")) {
            return path.substring(1, path.length() - 5);
        } else {
            return "redirect:/index";
        }
    }

}
