package com.fintech.controller;

import com.fintech.dto.TransactionRequestDto;
import com.fintech.dto.TransactionResponseDto;
import com.fintech.service.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction")
@Slf4j
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDto> processTransaction(@Valid @RequestBody TransactionRequestDto transactionDto, HttpServletRequest request) {
        log.info("incoming request request - {} dto - {} ", request, transactionDto);
        return new ResponseEntity<>(transactionService.processTransaction(transactionDto), HttpStatus.OK);
    }
}
