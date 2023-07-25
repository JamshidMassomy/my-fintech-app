package com.fintech.service;

import com.fintech.contract.AccountValidationRule;
import com.fintech.contract.ITransaction;
import com.fintech.contract.ValidationRule;
import com.fintech.domain.Account;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.dto.TransactionResponseDto;
import com.fintech.repository.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.fintech.util.ValidateAccount.isAccountExists;
import static com.fintech.util.ValidateTransaction.isAmountGreaterThanZero;
import static com.fintech.util.ValidateTransaction.isCreditorAndDebtorDifferent;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransaction {

    private final AccountMapper accountMapper;

    /**
     * @description process transaction
     * @param  transactionRequestDto
     * @returns TransactionResponseDto
     */
    @Override
    @Transactional
    public TransactionResponseDto processTransaction(final TransactionRequestDto transactionRequestDto) {
        boolean isValidTransaction = isTransactionValid(transactionRequestDto);
        final String fromIban = transactionRequestDto.getDebtorIBAN();
        final String toIban = transactionRequestDto.getCreditorIBAN();
        final List<Account> fromAccount = accountMapper.areAccountsExist(List.of(fromIban, toIban));
        boolean isValidateAccount  = isAccountsValid(fromAccount);

        // sufficent balance
        //
        // validate fund is avalaiblee
        //


        if (isValidTransaction && isValidateAccount) {
            // withDraw(Account from, BigDecimal amount);
            // deposit(Account to, BigDecimal amount)
        } else {
            // validation occured / check if account or
        }


        return null;
    }

    private boolean isTransactionProcessed(TransactionRequestDto transactionRequestDto) {
        return false;
    }


    /**
     * @description Validate transaction
     * @param transactionRequestDto
     * @returns void
     */
    private static boolean isTransactionValid(final TransactionRequestDto transaction) {
        final List<ValidationRule> validationRules = Arrays.asList(
                isAmountGreaterThanZero(),
                isCreditorAndDebtorDifferent()
        );
        return validationRules.stream()
                .allMatch(rule -> rule.apply(transaction));
    }

    private static boolean isAccountsValid(final List<Account> account) {
        final List<AccountValidationRule> accountValidationRulesList = Arrays.asList(
                isAccountExists()
        );
        return accountValidationRulesList.stream()
                .allMatch(rule -> rule.apply(account));
    }

    private void deposit(final Account to, BigDecimal amount) {
        String IBAN = to.getIBAN();
        accountMapper.updateAccount(IBAN, amount);
    }

    private void withDraw(final Account from, BigDecimal amount) {
        // boolean is validate stream or functional programming
        validateBalance(from);
    }



    /**
     * @description Fetches existing account balance
     * @param  IBAN, Currency
     * @returns BigDecimal
     */
    private BigDecimal fetchAccountBalance(String IBAN) {
        accountMapper.fetchBalance(IBAN);
        return BigDecimal.ZERO;
    }


    /**
     * @description Validate Balance
     * @param  transactionDto
     * @returns void
     */
    private void validateBalance(Account account) {
        /**
         *
         *
         *   public void ensureBalanceSufficient(Payment payment) {
         *
         *         var currency = payment.getDebtorAccountCurrency();
         *         var balanceAmount = fetchBalanceFromCm(Iban.valueOf(payment.getDebtorIban()), currency);
         *         var paymentAmount = payment.getDebtorAccountAmount();
         *         if (balanceAmount.subtract(paymentAmount).signum() < 0) {
         *             log.info("Balance is not sufficient for the payment {} {}", LogMap.of("balance amount", balanceAmount), LogMap.of("payment amount", paymentAmount));
         *             throw new InsufficientBalanceException();
         *         }
         *     }
         *
         * */
    }




}
