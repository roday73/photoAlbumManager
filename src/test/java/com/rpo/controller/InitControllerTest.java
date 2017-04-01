package com.rpo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rpo.service.AlbumService;
import com.rpo.service.PhotoService;

@RunWith(SpringJUnit4ClassRunner.class)
public class InitControllerTest extends BaseControllerTest{
	
	@Mock
	AlbumService albumService;

	@Mock
	PhotoService photoService;

	@InjectMocks
	protected InitController initController;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(initController)
				.build();
	}

    @Test
    public void initTest() throws Exception {    	
    	
    	mockMvc.perform(get("/init"))
	        .andExpect(status().isOk());
    }   

}
