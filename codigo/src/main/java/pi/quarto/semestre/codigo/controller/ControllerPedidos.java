package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.PedidoDAO;
import pi.quarto.semestre.codigo.model.PedidoAllDto;

@Controller
@RequestMapping("/paginaPedidos")
public class ControllerPedidos {

    private HttpServletRequest request;

    public ControllerPedidos(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping
    public String init(final Model model) throws SQLException {
        PedidoDAO pedidoRepository = new PedidoDAO();
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");
        List<PedidoAllDto> pedidos = pedidoRepository.findPedidos(loggedInUserEmail);
        model.addAttribute("pedidos", pedidos);

        return "paginaPedidos";
    }
    
}
