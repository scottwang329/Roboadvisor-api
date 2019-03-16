package com.hsbc.roboadvisor.controller;
import static org.hamcrest.CoreMatchers.is;

import com.hsbc.roboadvisor.model.PortfolioPreference.Allocation;
import com.hsbc.roboadvisor.model.PortfolioPreference.PortfolioPreference;
import com.hsbc.roboadvisor.model.PortfolioPreference.PortfolioType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class GetPortfolioPreferenceTest extends PortfolioPreferenceControllerTest {

    private static final Integer PID1 = 1;
    private static final Integer PID2 = 2;
    private static final String CID1 = "cid1";
    private static final Integer deviation = 1;
    private static final PortfolioType type = PortfolioType.fund;
    private static final List<Allocation> allocations = new ArrayList<Allocation>();

    private PortfolioPreference pp1 = new PortfolioPreference();
    private PortfolioPreference pp2 = new PortfolioPreference();

    @BeforeClass
    public void setup() {
        super.setup();

        // We assume that the getPortfolioPreference method relies on the portfolioRepositoryService
        when(portfolioRepositoryService.findPreferenceByPortfolioId(PID1)).thenReturn(pp1);
        when(portfolioRepositoryService.findPreferenceByPortfolioId(PID2)).thenReturn(null);
        pp1.setId(PID1);
        pp1.setDeviation(deviation);
        pp1.setType(type);
        pp1.setAllocations(allocations);

        pp2.setId(PID2);
    }

    @Test
    public void MissingHeader() {
        try{
            mockMvc.perform(MockMvcRequestBuilders.post("/roboadvisor/portfolio/" + PID1))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (Exception e) {
            if(e.getClass() != MissingRequestHeaderException.class)
                fail("Received unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void throwsIfPortfolioIdDoesNotExist(){
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/roboadvisor/portfolio/" + PID2)
                    .header("x-custid", CID1))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e){
            fail("Recieved unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void findsExistingPortfolioPreference() {
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/roboadvisor/portfolio/" + PID1)
                    .header("x-custid", CID1))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(PID1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.deviation", is(deviation)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.type", is(type.toString())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.allocations", is(allocations)));
        } catch (Exception e){
            fail(e.getMessage());
        }
    }
}
