package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pi.quarto.semestre.codigo.dao.EnderecoEntregaDAO;
import pi.quarto.semestre.codigo.dao.EnderecoFaturamentoDAO;
import pi.quarto.semestre.codigo.model.EnderecoEntrega;

@Controller
@RequestMapping("/paginaCadastroEndereco")
public class ControllerCadastroEndereco {
    
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("enderecoEntrega", new EnderecoEntrega());
        return "paginaCadastroEndereco";
    }

    @PostMapping
    public ModelAndView result(@ModelAttribute EnderecoEntrega enderecoEntrega) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        EnderecoEntregaDAO enderecoEntregaDAO = new EnderecoEntregaDAO();
        enderecoEntregaDAO.inserir(enderecoEntrega);

        EnderecoFaturamentoDAO enderecoFaturamentoDAO = new EnderecoFaturamentoDAO();
        enderecoFaturamentoDAO.inserir(enderecoEntrega);

        ModelAndView successModelAndView = new ModelAndView("redirect:/paginaLoginCliente");
        return successModelAndView;
    }
}
