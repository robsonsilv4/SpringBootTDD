package com.robson.sbtdd.service.impl;

import com.robson.sbtdd.model.Pessoa;
import com.robson.sbtdd.repository.PessoaRepository;
import com.robson.sbtdd.service.PessoaService;
import com.robson.sbtdd.service.exception.UnicidadeCpfException;

import java.util.Optional;

public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException {
        Optional<Pessoa> optional = pessoaRepository.findById(pessoa.getCpf());

        if (optional.isPresent()) {
            throw new UnicidadeCpfException();
        }

        return pessoaRepository.save(pessoa);
    }
}
