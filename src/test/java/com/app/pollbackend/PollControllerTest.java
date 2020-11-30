package com.app.pollbackend;

import com.app.pollbackend.domain.Poll;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.log4testng.Logger;
import static org.assertj.core.api.Assertions.assertThat;


@FixMethodOrder(MethodSorters.JVM)
public class PollControllerTest extends AbstractTest{
    private static final Logger log = Logger.getLogger(PollControllerTest.class);
    public static Integer id;

    @Test
    public void add() throws Exception {
        Poll poll = new Poll();
        poll.setPollTitle("Test Poll Title");

        String inputJson = super.mapToJson(poll);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/poll/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();
        Poll poll1 = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Poll.class);
        id = poll1.getId();
        assertThat(poll1).isNotNull();
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poll/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void get() throws Exception {
        log.debug("POLL GET TEST RUN!!");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/poll/get/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Poll poll = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Poll.class);
        assertThat(poll.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poll/delete/"+id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
