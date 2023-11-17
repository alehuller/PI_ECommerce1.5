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
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.ClienteDAO;
import pi.quarto.semestre.codigo.dao.EnderecoEntregaDAO;
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.EnderecoEntrega;

@Controller
@RequestMapping("/paginaCadastroEnderecoCliente")
public class ControllerCadastroEnderecoCliente {

    private HttpServletRequest request;

    public ControllerCadastroEnderecoCliente(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping
    public String init(final Model model) {
        model.addAttribute("enderecoEntrega", new EnderecoEntrega());
        return "paginaCadastroEnderecoCliente";
    }
    
    @PostMapping
    public ModelAndView result(@ModelAttribute EnderecoEntrega enderecoEntrega) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.findByEmailDados(loggedInUserEmail);

        EnderecoEntregaDAO enderecoEntregaDAO = new EnderecoEntregaDAO();
        enderecoEntregaDAO.inserirCliente(cliente.getId(), enderecoEntrega);

        ModelAndView successModelAndView = new ModelAndView("redirect:/paginaOpcoesCliente");
        return successModelAndView;
    }
}
