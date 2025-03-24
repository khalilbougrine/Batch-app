package spring_batch_json.example.batch_app.Test_Service_Controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring_batch_json.example.batch_app.controller.VoitureController;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.service.VoitureService;

import java.util.Arrays;
import java.util.List;

class VoitureControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private VoitureController voitureController;

    @Mock
    private VoitureService voitureService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voitureController).build();
    }

    @Test
    void testGetAllVoitures() throws Exception {
        Voiture voiture1 = new Voiture("123ABC", "Toyota", "Corolla", 2020, 50000);
        Voiture voiture2 = new Voiture("456DEF", "Honda", "Civic", 2022, 30000);
        List<Voiture> voitures = Arrays.asList(voiture1, voiture2);
        Page<Voiture> voiturePage = new PageImpl<>(voitures, PageRequest.of(0, 5), voitures.size());

        when(voitureService.getAllVoitures(0, 5)).thenReturn(voiturePage);

        mockMvc.perform(get("/api/voitures?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].marque").value("Toyota"))
                .andExpect(jsonPath("$.content[1].marque").value("Honda"));
    }

    @Test
    void testGetVoitureById() throws Exception {
        Voiture voiture = new Voiture("123ABC", "Toyota", "Corolla", 2020, 50000);

        when(voitureService.getVoitureById(1L)).thenReturn(voiture);

        mockMvc.perform(get("/api/voitures/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marque").value("Toyota"))
                .andExpect(jsonPath("$.modele").value("Corolla"));
    }

    @Test
    void testAddVoiture() throws Exception {
        Voiture voiture = new Voiture("789GHI", "Ford", "Focus", 2021, 25000);

        // Correction : S'assurer que le service renvoie la voiture ajoutée
        when(voitureService.addVoiture(any(Voiture.class))).thenReturn(voiture);

        mockMvc.perform(post("/api/voitures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voiture)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marque").value("Ford"))
                .andExpect(jsonPath("$.modele").value("Focus"));
    }

    @Test
    void testUpdateVoiture() throws Exception {
        Voiture updatedVoiture = new Voiture("789GHI", "Ford", "Fiesta", 2022, 20000);

        when(voitureService.updateVoiture(eq(1L), any(Voiture.class))).thenReturn(updatedVoiture);

        mockMvc.perform(put("/api/voitures/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVoiture)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marque").value("Ford"))
                .andExpect(jsonPath("$.modele").value("Fiesta"));
    }

    @Test
    void testDeleteVoiture() throws Exception {
        doNothing().when(voitureService).deleteVoiture(1L);

        mockMvc.perform(delete("/api/voitures/1"))
                .andExpect(status().isNoContent());

        verify(voitureService, times(1)).deleteVoiture(1L);
    }
}


