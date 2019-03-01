package com.robson.sbtdd.service;

import com.robson.sbtdd.model.Pessoa;
import com.robson.sbtdd.service.exception.UnicidadeCpfException;

public interface PessoaService {
    Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException;
}
