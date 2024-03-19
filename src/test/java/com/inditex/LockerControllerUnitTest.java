package com.inditex;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.inditex.controllers.LockerController;
import com.inditex.repositories.*;
import com.inditex.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LockerController.class)
class LockerControllerUnitTest{

    @MockBean
    private LockerRepository lockerRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateLocker() throws Exception {
	Locker lock = new Locker(13, 12);
	mockMvc.perform(post("/api/locker").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(lock)))
			.andExpect(status().isCreated());
    }
    
    @Test
    void shouldGetNoLockers() throws Exception{
        List<Locker> lockers = new ArrayList<Locker>();
        when(lockerRepository.findAll()).thenReturn(lockers);
        mockMvc.perform(get("/api/lockers"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoLockers() throws Exception{
	Locker l1 = new Locker(13, 12);
	Locker l2 = new Locker(3, 23);

        List<Locker> lockers = new ArrayList<Locker>();
        lockers.add(l1);
        lockers.add(l2);

        when(lockerRepository.findAll()).thenReturn(lockers);
        mockMvc.perform(get("/api/lockers"))
                .andExpect(status().isOk());
                
    }
    @Test
    void shouldGetLockerById() throws Exception {
	Locker l1 = new Locker(10, 3);
	l1.setId(1);

	Optional<Locker> opt = Optional.of(l1);

	assertThat(opt).isPresent();
	assertThat(opt.get().getId()).isEqualTo(l1.getId());
	assertThat(l1.getId()).isEqualTo(1);

        when(lockerRepository.findById(l1.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/lockers/" + l1.getId()))
                .andExpect(status().isOk());

    }
    
    @Test
    void shouldNotGetAnyLockersById() throws Exception{
        long id = 31;
        mockMvc.perform(get("/api/lockers/" + id))
                .andExpect(status().isNotFound());
    }
}

