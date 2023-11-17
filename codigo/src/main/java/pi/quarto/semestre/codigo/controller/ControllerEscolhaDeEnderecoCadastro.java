package pi.quarto.semestre.codigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerEscolhaDeEnderecoCadastro {
    
    @GetMapping("paginaEscolhaDeEnderecoCadastro")
    public String paginaEscolhaDeEnderecoCadastro(Model model) {
        return "/paginaEscolhaDeEnderecoCadastro";
    }
}
