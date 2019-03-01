package com.robson.sbtdd.service.impl;

import com.robson.sbtdd.model.Pessoa;
import com.robson.sbtdd.model.Telefone;
import com.robson.sbtdd.repository.PessoaRepository;
import com.robson.sbtdd.service.PessoaService;
import com.robson.sbtdd.service.exception.TelefoneNaoEncontradoException;
import com.robson.sbtdd.service.exception.UnicidadeCpfException;
import com.robson.sbtdd.service.exception.UnicidadeTelefoneException;

import java.util.Optional;

public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws Exception {
        Optional<Pessoa> optional = pessoaRepository.findById(pessoa.getCpf());

        if (optional.isPresent()) {
            throw new UnicidadeCpfException();
        }

        final String ddd = pessoa.getTelefones().get(0).getDdd();
        final String numero = pessoa.getTelefones().get(0).getNumero();
        optional = pessoaRepository.findTelefoneDddAndTelefoneNumero(ddd, numero);

        if (optional.isPresent()) {
            throw new UnicidadeTelefoneException();
        }

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
        Optional<Pessoa> optional = pessoaRepository.findTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
        return optional.orElseThrow(() -> new TelefoneNaoEncontradoException());
    }
}
