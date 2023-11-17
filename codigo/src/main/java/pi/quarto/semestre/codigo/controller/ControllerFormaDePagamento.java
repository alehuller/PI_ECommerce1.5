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

import jakarta.servlet.http.HttpServletRequest;
import pi.quarto.semestre.codigo.dao.CarrinhoDAO;
import pi.quarto.semestre.codigo.model.Carrinho;

@Controller
@RequestMapping("/paginaFormaDePagamento")
public class ControllerFormaDePagamento {

    @GetMapping
    public String init(final Model model) {
        model.addAttribute("carrinho", new Carrinho());
        return "paginaFormaDePagamento";
    }

    @PostMapping
    public ModelAndView result(@ModelAttribute Carrinho carrinho) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        carrinhoDAO.inserirPagamento(carrinho);
        ModelAndView successModelAndView = new ModelAndView("redirect:/paginaResumoCompra");
        return successModelAndView;
    }
}
