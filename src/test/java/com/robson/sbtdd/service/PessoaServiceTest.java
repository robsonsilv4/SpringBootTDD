package com.robson.sbtdd.service;

import com.robson.sbtdd.model.Pessoa;
import com.robson.sbtdd.model.Telefone;
import com.robson.sbtdd.repository.PessoaRepository;
import com.robson.sbtdd.service.exception.TelefoneNaoEncontradoException;
import com.robson.sbtdd.service.exception.UnicidadeCpfException;
import com.robson.sbtdd.service.exception.UnicidadeTelefoneException;
import com.robson.sbtdd.service.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {
    private static final String NOME = "Robson Silva";
    private static final String CPF = "12345678900";
    private static final String DDD = "88";
    private static final String NUMERO = "999999999";

    @MockBean
    private PessoaRepository pessoaRepository;

    private PessoaService sut;

    private Pessoa pessoa;
    private Telefone telefone;

    public PessoaServiceTest() {
    }

    @Before
    public void setUp() {
        sut = new PessoaServiceImpl(pessoaRepository);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        telefone = new Telefone();
        telefone.setDdd(DDD);
        telefone.setNumero(NUMERO);

        pessoa.setTelefones(Arrays.asList(telefone));

        when(pessoaRepository.findById(CPF)).thenReturn(Optional.empty());
        when(pessoaRepository.findTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
    }

    @Test
    public void deve_salvar_pessoa_no_repository() throws Exception {
        sut.salvar(pessoa);

        verify(pessoaRepository).save(pessoa);
    }

    @Test(expected = UnicidadeCpfException.class)
    public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
        when(pessoaRepository.findById(CPF)).thenReturn(Optional.of(pessoa));

        sut.salvar(pessoa);
    }

    @Test(expected = UnicidadeTelefoneException.class)
    public void nao_deve_salvar_duas_pessoas_com_o_mesmo_telefone() throws Exception {
        when(pessoaRepository.findTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        sut.salvar(pessoa);
    }

    @Test(expected = TelefoneNaoEncontradoException.class)
    public void nao_deve_retornar_excecao_de_nao_encontrado_quando_nao_existir_pessoa_com_o_ddd_e_numero_de_telefone() throws Exception {
        sut.buscarPorTelefone(telefone);
    }

    @Test
    public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
        when(pessoaRepository.findTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaTeste = sut.buscarPorTelefone(telefone);

        verify(pessoaRepository).findTelefoneDddAndTelefoneNumero(DDD, NUMERO);

        assertThat(pessoaTeste).isNotNull();
        assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
        assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
    }
}
