package com.backend.pessoa.controller;

import com.backend.pessoa.dto.PessoaDto;
import com.backend.pessoa.model.Pessoa;
import com.backend.pessoa.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PessoaController {

    @Autowired
    public PessoaRepository pessoaRepository;

    @PostMapping("/pessoas")
    public ResponseEntity<Pessoa> createPessoa(@RequestBody PessoaDto pessoaDto){
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDto, pessoa);

        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPessoa);
    }

    @GetMapping("/pessoas/{id}")
}   public ResponseEntity<Object> getPessoaById(@PathVariable UUID id){
    Optional<Pessoa> foundPessoa = pessoaRepository.findById(id);
    if(foundPessoa.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found!");
    }
    return ResponseEntity.status(HttpStatus.OK).body(foundPessoa.get());
}

@DeleteMapping("/pessoas/{id}")
public ResponseEntity<String> deletePessoaById(@PathVariable UUID id){
    Optional<Pessoa> foundPessoa = pessoaRepository.findById(id);
    if(foundPessoa.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found!");
    }
    pessoaRepository.delete(foundPessoa.get());
    return ResponseEntity.status(HttpStatus.OK).body("Pessoa deleted successfully!");
}

@PutMapping("/pessoas/{id}")
public ResponseEntity<Object> updatePessoaById(@RequestBody PessoaDto pessoaDto,
                                                @PathVariable UUID id){
    Optional<Pessoa> foundPessoa = pessoaRepository.findById(id);
    if(foundPessoa.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found!");
    }

    Pessoa pessoa = foundPessoa.get();
    BeanUtils.copyProperties(pessoaDto, pessoa);
    return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.save(pessoa));
    }
