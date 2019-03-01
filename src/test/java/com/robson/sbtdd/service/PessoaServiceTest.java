package com.robson.sbtdd.service;

import com.robson.sbtdd.model.Pessoa;
import com.robson.sbtdd.repository.PessoaRepository;
import com.robson.sbtdd.service.exception.UnicidadeCpfException;
import com.robson.sbtdd.service.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {
    private static final String NOME = "Robson Silva";
    private static final String CPF = "12345678900";

    @MockBean
    private PessoaRepository pessoaRepository;

    private PessoaService sut;
    private Pessoa pessoa;

    public PessoaServiceTest() {
    }

    @Before
    public void setUp() {
        sut = new PessoaServiceImpl(pessoaRepository);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        when(pessoaRepository.findById(CPF)).thenReturn(Optional.empty());
    }

    @Test
    public void deve_salvar_pessoa_no_repository() throws UnicidadeCpfException {
        sut.salvar(pessoa);

        verify(pessoaRepository).save(pessoa);
    }

    @Test(expected = UnicidadeCpfException.class)
    public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws UnicidadeCpfException {
        when(pessoaRepository.findById(CPF)).thenReturn(Optional.of(pessoa));

        sut.salvar(pessoa);
    }
}
