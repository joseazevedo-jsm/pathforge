package dev.pathforge;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SkillServiceIntegrationTest {

    @Autowired
    private SkillService skillService;

    @Test
    public void injectsSkillService(){
        assertNotNull(skillService);
    }
}
