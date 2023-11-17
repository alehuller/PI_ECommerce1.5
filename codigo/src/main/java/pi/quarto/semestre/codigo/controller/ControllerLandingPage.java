package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.model.ProdutoAllDto;

@Controller
public class ControllerLandingPage {
    
    @Autowired
    private ProdutoDAO produtoDAO;

    @GetMapping("landingPage")
    public String paginaLandingPage(Model model) throws SQLException {
        ProdutoDAO produtoRepository = new ProdutoDAO();
        List<ProdutoAllDto> produtos = produtoRepository.findActiveProductsForLandingPage();
        model.addAttribute("produtos", produtos);
        return "/landingPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        // Realize as ações de logout, como invalidar a sessão
        HttpSession session = request.getSession();
        session.invalidate();

        // Redirecione para a página de login ou outra página desejada
        return "redirect:/landingPage"; // Substitua pelo URL da página de login
    }
}
