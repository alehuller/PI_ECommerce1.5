package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.util.CpfValidacao;
import pi.quarto.semestre.codigo.util.Cripto;

@Controller
@RequestMapping("/paginaCadastroCliente")
public class ControllerCadastroUsuario {
    
    @GetMapping
    public String init(final Model model) {
    	 model.addAttribute("usuario", new Usuario()); 
    	return "paginaCadastroCliente";
    }

    /*@PostMapping
    public ModelAndView result(@ModelAttribute Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException { 
        CpfValidacao cpfValidacao = new CpfValidacao();
        boolean cpfValidado = cpfValidacao.isCPF(usuario.getCpf());
        System.out.println(usuario.getCpf());
        System.out.println(cpfValidado);
        
        if (!cpfValidado) {
            ModelAndView errorModelAndView = new ModelAndView("paginaCadastroCliente");
            errorModelAndView.addObject("cpfError", "CPF inválido");
            return errorModelAndView;
        }
        Cripto cripto = new Cripto();
        String senhaCriptografada = cripto.crip(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(usuario);
        
        ModelAndView successModelAndView = new ModelAndView("redirect:/paginaOrganizacao");
        return successModelAndView;
    }*/

    @PostMapping
    public ModelAndView result(@ModelAttribute Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
    CpfValidacao cpfValidacao = new CpfValidacao();
    boolean cpfValidado = cpfValidacao.isCPF(usuario.getCpf());
    System.out.println(usuario.getCpf());
    System.out.println(cpfValidado);
    
    if (!cpfValidado) {
        ModelAndView errorModelAndView = new ModelAndView("paginaCadastroCliente");
        errorModelAndView.addObject("cpfError", "CPF inválido");
        return errorModelAndView;
    }
    
    // Verificar se já existe um usuário com o email fornecido
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    if (usuarioDAO.findByEmail(usuario.getEmail())) {
        ModelAndView errorModelAndView = new ModelAndView("paginaCadastroCliente");
        errorModelAndView.addObject("emailError", "Email já está em uso");
        return errorModelAndView;
    }
    
    Cripto cripto = new Cripto();
    String senhaCriptografada = cripto.crip(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);
    usuario.setStatuss("Ativo");
    
    usuarioDAO.inserir(usuario);
    
    ModelAndView successModelAndView = new ModelAndView("redirect:/paginaOrganizacao");
    return successModelAndView;
}

}
