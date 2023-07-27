package com.fintech.controller;

import com.fintech.dto.TransactionRequestDto;
import com.fintech.service.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.fintech.util.Constants.SESSION_ID_HEADER;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction")
@Slf4j
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @PostMapping
    public ResponseEntity processTransaction(@RequestBody @Valid TransactionRequestDto transactionDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            final StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        final String sessionId = request.getAttribute(SESSION_ID_HEADER).toString();
        log.info("incoming request requestId - {} dto - {}", request.getAttribute(SESSION_ID_HEADER), transactionDto);
        return new ResponseEntity<>(transactionService.isTransactionProcessed(transactionDto, sessionId), HttpStatus.OK);
    }
}