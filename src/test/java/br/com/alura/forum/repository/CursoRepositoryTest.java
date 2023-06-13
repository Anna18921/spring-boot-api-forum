package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJdbcTest
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloNome(){
        String nomeCurso = "HTML 5";
        Curso curso = cursoRepository.findByNome(nomeCurso);
        Assert.assertNull(curso);
        Assert.assertEquals(nomeCurso, curso.getNome());
    }
}