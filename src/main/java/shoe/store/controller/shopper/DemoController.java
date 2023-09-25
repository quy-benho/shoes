package shoe.store.controller.shopper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/shopper")
public class DemoController {

    @GetMapping("/*.html")
    public String getDemoHtml() {

        String path = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();

        if (path.contains(".html")) {
            return path.substring(1, path.length() - 5);
        } else {
            return "redirect:/index";
        }
    }

}
