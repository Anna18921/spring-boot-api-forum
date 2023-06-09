package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDetailsDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.form.TopicoUpdateForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "TopicosControllerListTopics")
	public Page<TopicoDto> listTopics(@RequestParam(required = false) String cursoNome, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable pagination) {


		Page<Topico> topicos;

		if (cursoNome == null) {
			topicos = topicoRepository.findAll(pagination);
		} else {
			topicos = topicoRepository.findByCurso_Nome(cursoNome, pagination);
		}

		return TopicoDto.converter(topicos);

	}

	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> createTopic(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicoDetailsDto> showTopic(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);

		if (topico.isPresent()) {

			return ResponseEntity.ok(new TopicoDetailsDto(topico.get()));
		}

		return ResponseEntity.notFound().build();

	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicoUpdateForm form) {

		Optional<Topico> optional = topicoRepository.findById(id);

		if (optional.isPresent()) {
			Topico topico = form.updateTopic(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDetailsDto(topico));
		}

		return ResponseEntity.notFound().build();


	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);

		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.status(204).build();
		}

		return ResponseEntity.notFound().build();
	}


}
 