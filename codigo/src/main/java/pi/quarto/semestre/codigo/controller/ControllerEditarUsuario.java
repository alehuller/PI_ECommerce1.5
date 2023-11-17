package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.util.Cripto;

/*@Controller
@RequestMapping("/paginaEditarCliente/{cpf}")
public class ControllerEditarUsuario {
    
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("usuario", new Usuario());
        return "paginaEditarCliente";
    }

    @PostMapping
    public ModelAndView result(@ModelAttribute Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {    	
    		
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	usuarioDAO.editar(usuario);
			
       	ModelAndView modelAndView = new ModelAndView("redirect:/paginaOrganizacao");
        	return modelAndView;
    }
}*/

@Controller
@RequestMapping("/paginaEditarCliente/{cpf}")
public class ControllerEditarUsuario {
    
    private final UsuarioDAO usuarioDAO;
    private HttpServletRequest request;

    public ControllerEditarUsuario(UsuarioDAO usuarioDAO, HttpServletRequest request) {
        this.usuarioDAO = usuarioDAO;
        this.request = request;
    }

    @GetMapping
    public String init(@PathVariable String cpf, Model model) throws SQLException {
        Usuario usuario = usuarioDAO.findByCpf(cpf); // Implemente o método findByCpf no seu DAO
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");
        
        if (loggedInUserEmail != null && loggedInUserEmail.equals(usuario.getEmail())) {
            model.addAttribute("usuario", usuario);
            return "paginaEditarCliente"; 
        } else {
            model.addAttribute("usuario", usuario);
            return "paginaEditarClienteNaoLogado"; // Redireciona para outra página em caso de usuário inválido
        }
    }

    @PostMapping
    public String result(@ModelAttribute Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        Cripto cripto = new Cripto();
        String senhaCriptografada = cripto.crip(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        usuarioDAO.editar(usuario); // Implemente o método editar no seu DAO
        return "redirect:/paginaOrganizacao";
    }
}
