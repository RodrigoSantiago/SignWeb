package com.flat.brasilprev.sign.controller;

import com.flat.brasilprev.sign.model.User;
import com.flat.brasilprev.sign.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class IndexController {

    private UserRepository userRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("modelUser", new User());
        return "index";
    }

    @PostMapping("/")
    public String indexPost(Model model, @Valid User user, BindingResult result) {

        if (user.getNome() == null || user.getNome().length() < 3) {
            model.addAttribute("nomeError", "Nome muito curto");
            result.rejectValue("nome", "Nome muito curto");
        } else if (userRepository.findByNome(user.getNome()).size() > 0) {
            model.addAttribute("nomeError", "Nome já cadastrado");
            result.rejectValue("nome", "Nome já cadastrado");
        } else {
            model.addAttribute("nomeError", false);
        }

        if (user.getCpf() == null || !user.getCpf().matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}")) {
            model.addAttribute("cpfError",  "CPF inválido");
            result.rejectValue("cpf", "CPF inválido");
        } else if (userRepository.findByCpf(user.getCpf()).size() > 0) {
            model.addAttribute("cpfError",  "CPF já cadastrado");
            result.rejectValue("cpf", "CPF já cadastrado");
        } else {
            model.addAttribute("cpfError", false);
        }

        if (user.getSenha() == null || user.getSenha().length() < 3) {
            model.addAttribute("senhaError", "Senha muito curta");
            result.rejectValue("senha", "Senha muito curta");
        } else {
            model.addAttribute("senhaError", false);
        }

        if (result.hasErrors()) {
            model.addAttribute("modelUser", user);
            return "index";
        }

        userRepository.save(user);
        model.addAttribute("title", "Usuario Cadastrado");
        model.addAttribute("content", "Bem vindo " + user.getNome() + "!");
        model.addAttribute("buttonURL", "/login");
        model.addAttribute("buttonText", "Login");
        return "message";
    }
}
