package pi.quarto.semestre.codigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerClienteEscolherLista {
    
    @GetMapping("paginaOpcoesCliente")
    public String paginaOpcoesCliente(Model model) {
        return "/paginaOpcoesCliente";
    }
}
