package pi.quarto.semestre.codigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerIrParaPagamanto {

    @GetMapping("irParaPagamento")
    public String irParaPagamento(Model model) {
        return "/irParaPagamento";
    }
}
