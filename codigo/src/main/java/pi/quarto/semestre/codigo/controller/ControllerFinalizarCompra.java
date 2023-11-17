package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.ClienteDAO;
import pi.quarto.semestre.codigo.dao.EnderecoEntregaDAO;
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.EnderecoEntrega;
import pi.quarto.semestre.codigo.model.EnderecoEntregaAllDto;

@Controller
@RequestMapping("/paginaEndereco")
public class ControllerFinalizarCompra {
    
    private final EnderecoEntregaDAO enderecoEntregaDAO;
    private HttpServletRequest request;

    public ControllerFinalizarCompra(EnderecoEntregaDAO enderecoEntregaDAO, HttpServletRequest request) {
        this.enderecoEntregaDAO = enderecoEntregaDAO;
        this.request = request;
    }

    @GetMapping
    public String init(Model model) throws SQLException {
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.findByEmailDados(loggedInUserEmail);
        System.out.print(loggedInUserEmail);
        System.out.print(cliente.getId());


        List<EnderecoEntregaAllDto> enderecos = enderecoEntregaDAO.findEnderecosByClienteId(cliente.getId());
        model.addAttribute("enderecos", enderecos);
        return "paginaEscolherEnderecoCheckout";
    }

    @PostMapping
    public String result(@RequestParam Long id) throws SQLException{
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.enderecoPrincipal(loggedInUserEmail, id);

        return "irParaPagamento";
    }
}
