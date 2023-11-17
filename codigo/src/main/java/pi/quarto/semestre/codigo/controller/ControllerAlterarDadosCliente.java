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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.ClienteDAO;
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.util.Cripto;

@Controller
@RequestMapping("/paginaAlterarDadosCliente")
public class ControllerAlterarDadosCliente {

    private final ClienteDAO clienteDAO;
    private HttpServletRequest request;

    public ControllerAlterarDadosCliente(ClienteDAO clienteDAO, HttpServletRequest request) {
        this.clienteDAO = clienteDAO;
        this.request = request;
    }

    @GetMapping
    public String init(Model model) throws SQLException {
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");

        Cliente cliente = clienteDAO.findByEmailDados(loggedInUserEmail);

        model.addAttribute("cliente", cliente);
        return "paginaAlterarDadosCliente";
    }

    @PostMapping
    public String result(@ModelAttribute Cliente cliente) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        Cripto cripto = new Cripto();
        String senhaCriptografada = cripto.crip(cliente.getSenha());

        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");

        cliente.setSenha(senhaCriptografada);
        clienteDAO.editar(loggedInUserEmail, cliente);
        return "redirect:/paginaOpcoesCliente";
    }
}
