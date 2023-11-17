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

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.UsuarioDto;
import pi.quarto.semestre.codigo.util.Cripto;

@Controller
@RequestMapping("/login")
public class ControllerIndex {

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public String init(final Model model) {
        model.addAttribute("usuarioDto", new UsuarioDto());
        return "paginaUsuarioBackoffice";
    }

    public ModelAndView redirect(final Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:paginaEscolherLista");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView result(@ModelAttribute UsuarioDto usuarioDto) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        usuarioDto.getEmail();
        usuarioDto.getSenha();
        Cripto cripto = new Cripto();
        String senhaCriptografada = cripto.crip(usuarioDto.getSenha());

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean valido = usuarioDAO.validarAdmin(usuarioDto.getEmail(), senhaCriptografada, "Administrador", "Ativo");
        boolean valido2 = usuarioDAO.validarAdmin(usuarioDto.getEmail(), senhaCriptografada, "Estoquista", "Ativo");
        if (valido == true) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", usuarioDto.getEmail());
            session.setAttribute("userType", "Administrador");
            ModelAndView modelAndView = new ModelAndView("redirect:paginaEscolherLista");
            return modelAndView;
        }
        else if (valido2 == true) {
            System.out.print("entrou");
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", usuarioDto.getEmail());
            session.setAttribute("userType", "Estoquista");
            ModelAndView modelAndView = new ModelAndView("redirect:paginaEscolherListaEstoquista");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:login");
        return modelAndView;
    }
}

/*@PostMapping
    public ModelAndView result(@ModelAttribute UsuarioDto usuarioDto) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	System.out.println(usuarioDto.getEmail());
    	System.out.println(usuarioDto.getSenha());
    	Cripto cripto = new Cripto();
    	String senhaCriptografada = cripto.crip(usuarioDto.getSenha());
    	System.out.println(senhaCriptografada);
    	System.out.println(usuarioDto.getRequest());
    	
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	try {
			boolean valido = usuarioDAO.validarAdmin(usuarioDto.getEmail(), senhaCriptografada, "Administrador");
			if (valido == true) {
				ModelAndView modelAndView = new ModelAndView("redirect:paginaEscolherLista");
				IdSession.idMain = usuarioDAO.getId(usuarioDto.getEmail(), senhaCriptografada);
				IdSession.idType = "Administrador";
		    	return modelAndView;		
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}ModelAndView modelAndView = new ModelAndView("redirect:login");
    	System.out.println("init");
    	return modelAndView;	
    } 
*/