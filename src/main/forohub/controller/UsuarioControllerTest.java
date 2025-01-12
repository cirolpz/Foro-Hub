package alura.forohub.controller;

import alura.forohub.SecurityTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest

@Import(SecurityTestConfig.class)
class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("Debería devolver http 201 y el usuario creado cuando los datos son válidos")
    void registrar_escenario2() throws Exception {
            String usuarioJson = """
        {
            "nombre": "Juan Perez",
            "email": "juan.perez@example.com",
            "password": "password123"
        }
        """;

            var response = mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(usuarioJson))
                    .andReturn().getResponse();
            assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.getContentAsString()).contains("Juan Perez");
        }

    @Test
    @DisplayName("Deberia devolver http 400 cuando no tiene datos la request")
    @WithMockUser(username = "testuser", roles = "USER") // Simula un usuario
//no hace falta loguearse es con Spring security test
    void registrar_escenario1() throws Exception {
        var response = mockMvc.perform(post("/usuarios"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 200 y una lista de usuarios")
    @WithMockUser(username = "testuser", roles = "USER") // Simula un usuario
    void listarUsuarios() throws Exception {
        var response = mockMvc.perform(get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("["); // La respuesta es una lista JSON
    }

    @Test
    @DisplayName("Debería devolver http 200 y el usuario cuando el ID es válido")
    @WithMockUser(username = "testuser", roles = "USER") // Simula un usuario
    void traerUsuario() throws Exception {
        var response = mockMvc.perform(get("/usuarios/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Juan Perez"); // Cambiar según los datos de prueba
    }

    @Test
    @DisplayName("Debería devolver http 200 y el usuario actualizado")
    @WithMockUser(username = "testuser", roles = "USER") // Simula un usuario
    void actualizarUsuario() throws Exception {
        String usuarioActualizadoJson = """
        {
            "nombre": "Juan Perez Actualizado",
            "email": "juan.actualizado@example.com",
            "password": "newpassword123"
        }
    """;

        var response = mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioActualizadoJson))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Juan Perez Actualizado", "juan.actualizado@example.com");
    }

    @Test
    @DisplayName("Debería devolver http 204 cuando se elimina un usuario")
    @WithMockUser(username = "testuser", roles = "USER") // Simula un usuario
    void eliminarUsuario() throws Exception {
        var response = mockMvc.perform(delete("/usuarios/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}