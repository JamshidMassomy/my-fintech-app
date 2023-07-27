package com.fintech.service;

import com.fintech.contract.Transaction;
import com.fintech.contract.ValidationRule;
import com.fintech.domain.Account;
import com.fintech.domain.Currency;
import com.fintech.domain.TransactionStatus;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.exception.*;
import com.fintech.repository.mapper.AccountMapper;
import com.fintech.repository.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.fintech.util.Constants.*;
import static com.fintech.util.ValidateTransaction.isAmountGreaterThanZero;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements Transaction {

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    /**
     * @description process transaction
     * @param  transactionRequestDto
     * @returns TransactionResponseDto
     */
    @Override
    @Transactional
    public boolean processTransaction(final TransactionRequestDto transactionRequestDto, String sessionId) {
        final String debtorIban = transactionRequestDto.getDebtorIBAN();
        final String creditorIBAN = transactionRequestDto.getCreditorIBAN();
        final Account debitorAccount = accountMapper.findByIBAN(debtorIban);
        final Account creditorAccount = accountMapper.findByIBAN(creditorIBAN);

        if (transactionRequestDto.getCurrency() != Currency.EUR) {
            /*
             * for time being to support only EUR to EUR transactions for showcase only.
             * */
            throw new UnSupportedCurrencyException(INVALID_CURRENCY);
        }

        applyBaseValidation(debitorAccount, creditorAccount, transactionRequestDto);
        boolean isBalanceSufficient = isBalanceSufficient(debitorAccount, transactionRequestDto.getAmount());

        if (isBalanceSufficient) {
            withDraw(debitorAccount, transactionRequestDto.getAmount());
            deposit(creditorAccount, transactionRequestDto.getAmount());
            final com.fintech.domain.Transaction transaction = com.fintech.domain.Transaction.builder()
                    .reference(transactionRequestDto.getReference())
                    .dateTime(LocalDateTime.now())
                    .description(transactionRequestDto.getDescription())
                    .creditorIBAN(transactionRequestDto.getCreditorIBAN())
                    .debtorIBAN(transactionRequestDto.getDebtorIBAN())
                    .status(TransactionStatus.COMPLETED)
                    .currency(transactionRequestDto.getCurrency())
                    .amount(transactionRequestDto.getAmount())
                    .createdSessionId(sessionId).
                    build();
            transactionMapper.saveTransaction(transaction);
        } else {
           throw new FailedTransactionException(FAILED_TRANSACTION);
        }
        return true;
    }

    private void applyBaseValidation(final Account debitorAccount, final Account creditorAccount, final TransactionRequestDto transactionRequestDto) {
        if (debitorAccount == null || creditorAccount == null) {
            throw new AccountNotFoundException(NO_MATCHING_ACCOUNT);
        }
        final BigDecimal amount = transactionRequestDto.getAmount();
        final List<ValidationRule> validationRules = List.of(
                isAmountGreaterThanZero()
        );
        boolean isAmountValid = validationRules.stream()
                .allMatch(rule -> rule.apply(transactionRequestDto));
        if (!isAmountValid) {
            throw new InvalidNumberException(INVALID_NUMBER);
        }
        BigDecimal currentBalance = debitorAccount.getBalance();
        if (currentBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(INSUFFICIENT_ACCOUNT_BALANCE);
        }
        if (debitorAccount.getIBAN().equals(creditorAccount.getIBAN())) {
            throw new FailedTransactionException(FAILED_TRANSACTION);
        }
    }

    private static boolean isBalanceSufficient(final Account debitorAccount, final BigDecimal transactionAmount) {
        BigDecimal currentBalance = debitorAccount.getBalance();
        if (currentBalance.compareTo(BigDecimal.ZERO) <= 0 || transactionAmount.compareTo(currentBalance) > 0) {
            throw new InsufficientBalanceException(INSUFFICIENT_ACCOUNT_BALANCE);
        }
        return true;
    }

    public boolean isTransactionProcessed(final TransactionRequestDto transactionRequestDto, String uniqueSessionId) {
        return processTransaction(transactionRequestDto, uniqueSessionId);
    }



    @Transactional
    public void deposit(final Account accountToUpdate, final BigDecimal amount) {
        BigDecimal newBalance = accountToUpdate.getBalance().add(amount);
        log.info("depositing amount {} - {}", newBalance, accountToUpdate.getIBAN());
        accountMapper.updateAccount(accountToUpdate.getIBAN(), newBalance);
    }

    @Transactional
    public void withDraw(final Account from, final BigDecimal amount) {
        BigDecimal newBalance = from.getBalance().subtract(amount);
        log.info("withdrawing amount {} - {}", newBalance, from.getIBAN());
        accountMapper.updateAccount(from.getIBAN(), newBalance);
    }


}
