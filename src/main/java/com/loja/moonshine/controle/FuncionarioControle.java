package com.loja.moonshine.controle;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import com.loja.moonshine.modelos.Funcionarios;
//import com.loja.moonshine.repositorios.CidadeRepositorio;
import com.loja.moonshine.repositorios.FuncionarioRepositorio;

@Controller
public class FuncionarioControle {
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	// @Autowired
	// private CidadeRepositorio cidadeRepositorio;
	
	
	@GetMapping("/administrativo/funcionarios/cadastrar")
	public ModelAndView cadastrar(Funcionarios funcionarios) {
		ModelAndView mv =  new ModelAndView("administrativo/funcionarios/cadastro");
		mv.addObject("funcionario",funcionarios);
		//mv.addObject("listaCidades",cidadeRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/funcionarios/lista");
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Funcionarios> funcionario = funcionarioRepositorio.findById(id);
		return cadastrar(funcionario.get());
	}
	
	@GetMapping("/administrativo/funcionarios/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Funcionarios> funcionarios = funcionarioRepositorio.findById(id);
		funcionarioRepositorio.delete(funcionarios.get());
		return listar();
	}
	
	@PostMapping("/administrativo/funcionarios/salvar")
	public ModelAndView salvar(@Valid Funcionarios funcionarios, BindingResult result) {

		if(result.hasErrors()) {
			return cadastrar(funcionarios);
		}
		funcionarios.setSenha(new BCryptPasswordEncoder().encode(funcionarios.getSenha()));
		funcionarioRepositorio.saveAndFlush(funcionarios);
		
		return cadastrar(new Funcionarios());
	}

}