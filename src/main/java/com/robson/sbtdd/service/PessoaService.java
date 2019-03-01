package com.robson.sbtdd.service;

import com.robson.sbtdd.model.Pessoa;
import com.robson.sbtdd.model.Telefone;
import com.robson.sbtdd.service.exception.TelefoneNaoEncontradoException;

public interface PessoaService {
    Pessoa salvar(Pessoa pessoa) throws Exception;

    Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException;
}
