package com.flat.brasilprev.sign.controller;

import com.flat.brasilprev.sign.model.User;
import com.flat.brasilprev.sign.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model) {
        return "signup";
    }

    @PostMapping("/login")
    public String loginPost(String nome, String senha, Model model) {
        List<User> users = userRepository.findByNomeAndSenha(nome, senha);
        if (users.size() == 1) {
            User user = users.get(0);
            model.addAttribute("title", "Bem vindo " + nome);
            model.addAttribute("content", "Você fez um login com sucesso.\n CPF : " + user.getCpf() + "\nCEP : " + user.getCep());
            model.addAttribute("buttonURL", "/");
            model.addAttribute("buttonText", "Sair");
            return "message";
        } else {
            model.addAttribute("nome", nome);
            model.addAttribute("nomeError", "Nome e senha não correspondem ao login");
            return "signup";
        }
    }
}
