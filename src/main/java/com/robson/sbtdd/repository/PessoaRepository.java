package com.robson.sbtdd.repository;

import com.robson.sbtdd.model.Pessoa;

import java.util.Optional;

public interface PessoaRepository {

    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> findById(String cpf);
}
