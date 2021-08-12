package com.flat.brasilprev.sign.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNomeAndSenha(String nome, String senha);

    List<User> findByNome(String nome);

    List<User> findByCpf(String cpf);
}
