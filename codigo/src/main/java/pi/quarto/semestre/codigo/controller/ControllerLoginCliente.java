package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
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
import pi.quarto.semestre.codigo.model.ClienteDto;
import pi.quarto.semestre.codigo.model.UsuarioDto;
import pi.quarto.semestre.codigo.util.Cripto;

@Controller
@RequestMapping("/paginaLoginCliente")
public class ControllerLoginCliente {

    @Autowired
    private HttpServletRequest request;
    
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("clienteDto", new ClienteDto());
        return "paginaLoginCliente";
    }

    public ModelAndView redirect(final Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:landingPage");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView result(@ModelAttribute ClienteDto clienteDto) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        clienteDto.getEmail();
        clienteDto.getSenha();
        Cripto cripto = new Cripto();
        String senhaCriptografada = cripto.crip(clienteDto.getSenha());

        ClienteDAO clienteDAO = new ClienteDAO();
        boolean valido = clienteDAO.validarCliente(clienteDto.getEmail(), senhaCriptografada);
        if (valido == true) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", clienteDto.getEmail());
            ModelAndView modelAndView = new ModelAndView("redirect:landingPage");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:paginaLoginClienteF");
        return modelAndView;
    }
}
