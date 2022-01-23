package com.odontoyou;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import javax.swing.text.AbstractDocument.Content;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odontoyou.model.AuthenticationResponse;
import com.odontoyou.model.Users;
import com.odontoyou.repo.UsersRepo;

@SpringBootTest
@AutoConfigureMockMvc
class PontoApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UsersRepo repo;
	
	@Test
	String autenticar() throws Exception {
		Users user = new Users("41411642830", "nome teste", "123456", false, "");

		MvcResult result = mockMvc.perform(post("/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk()).andReturn();

		AuthenticationResponse response = objectMapper.readValue(result.getResponse().getContentAsByteArray(), AuthenticationResponse.class);
		
		Assertions.assertNotNull(response, "Autenticação nula");
		Assertions.assertNull(response.getMensagem(), "Autenticação retornou mensgem de erro");
		return response.getJwt();
	}

	
	/*@Test
	void salvandoNovoUsuario() throws Exception {
		String jwt = autenticar();
		String cpf = "123456";
		Users user = new Users(cpf, "teste", "teste", false);

		mockMvc.perform(post("/users")
				.contentType("application/json")
				.header("Authorization", "Bearer " + jwt)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk());

		Optional<Users> userReturn = repo.findById(cpf);

		Assertions.assertEquals(userReturn.get().getCpf(), cpf);
	}*/
	
	
	@Test
	void retornoDaListaUsuarios() throws Exception {
		String jwt = autenticar();
		
		MvcResult result = mockMvc.perform(get("/users")
				.contentType("application/json")
				.header("Authorization", "Bearer " + jwt))
				.andDo(print())
				.andExpect(status().isOk()).andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		//List<Users> ltRetorno = objectMapper.readValue(contentAsString, List<Users>.class);
		Assertions.assertFalse(contentAsString.isEmpty());
	}
}
