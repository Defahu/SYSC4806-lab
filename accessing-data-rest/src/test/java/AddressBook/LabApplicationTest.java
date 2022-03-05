package AddressBook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LabApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    private AddressBookRepository addressBook;
    private BuddyInfo buddyInfo = new BuddyInfo("lab6", "123", "carleton");

    @BeforeAll
    public void testAddBuddyInfo() throws Exception {
        //Test Create AddressBook
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/createAB/")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful());

        //Test Adding BuddyInfo
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/addressbook/1")
                        .content(mapper.writeValueAsString(buddyInfo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("lab6")));
    }

    @Test
    public void testGetBuddyInfo() throws Exception {
        this.mockMvc.perform(get("/addressbook/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("lab6")));
    }

    @Test
    public void testRemoveBuddyInfo() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .delete("/addressbook/1")
                .content(mapper.writeValueAsString(buddyInfo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("lab6"))));
    }

}
