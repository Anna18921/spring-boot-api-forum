package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDetailsDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.form.TopicoUpdateForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDto> listTopics(String cursoNome) {
		List<Topico> topicos;
		
		if (cursoNome == null) {
			topicos = topicoRepository.findAll();			
		} else {
			topicos = topicoRepository.findByCurso_Nome(cursoNome);
		}
		
		return TopicoDto.converter(topicos); 
		
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> createTopic(@RequestBody @Valid TopicoForm  form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public TopicoDetailsDto showTopic(@PathVariable Long id) {
		Topico topico = topicoRepository.getOne(id);
		
		return new TopicoDetailsDto(topico);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicoUpdateForm  form) {
		
		Topico topico = form.updateTopic(id, topicoRepository);
		
		return ResponseEntity.ok(new TopicoDto(topico));
	}
	
	
}
 