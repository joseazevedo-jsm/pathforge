package dev.pathforge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class SkillControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void rejectsInvalidSkillRequest() throws Exception {
        mockMvc.perform(
            post("/api/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "   ",
                        "estimatedHours": 0
                    }
                    """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name").value("must not be blank"))
        .andExpect(jsonPath("$.estimatedHours")
        .value("must be greater than 0"));
    }

    @Test
    public void createsValidSkill() throws Exception {

        mockMvc.perform(
            post("/api/skills")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "Spring Testing",
                        "estimatedHours": 5
                    }
                    """)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.name").value("Spring Testing"))
            .andExpect(jsonPath("$.estimatedHours").value(5))
            .andExpect(jsonPath("$.completed").value(false));
    }
}
