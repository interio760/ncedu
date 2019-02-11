package com.edunetcracker.startreker;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles(profiles = "test")
public class BootServerApplicationTests {

    @Test
    public void contextLoads() {
    }
}
