package com.fintech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.controller.TransactionController;
import com.fintech.domain.Currency;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
public class E2ETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionServiceImpl transactionService;

    @Test
    public void testProcessTransaction_Successful() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAmount(BigDecimal.valueOf(5));
        requestDto.setCurrency(Currency.EUR);
        requestDto.setDebtorIBAN("EE89370400440532013000");
        requestDto.setCreditorIBAN("EE1420041010050500013M02606");
        requestDto.setDescription("my test transactions");
        requestDto.setReference("ref00912");
        when(transactionService.isTransactionProcessed(any(TransactionRequestDto.class), anyString())).thenReturn(true);
        mockMvc.perform(post("/api/v1/transaction")
                        .content(new ObjectMapper().writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        verify(transactionService, times(1)).isTransactionProcessed(eq(requestDto), anyString());
    }

}

