package com.gmail.maxsmv1998.eurodiffusion.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.gmail.maxsmv1998.eurodiffusion.constant.Motif.INITIAL_AMOUNT;
import static com.gmail.maxsmv1998.eurodiffusion.constant.Motif.REPRESENTATIVE_PORTION_DIVISOR;

@Value
class BankModel {
    @Getter(AccessLevel.NONE)
    Map<String, Integer> bank;
    @NonFinal
    List<TransactionModel> transactions = new ArrayList<>();

    public BankModel(List<String> motifs, String rootCountryMotif) {
        bank = motifs.stream()
                .collect(Collectors.toMap(Function.identity(), motif -> 0));
        bank.compute(rootCountryMotif, (k, v) -> INITIAL_AMOUNT);
    }

    public boolean isEveryMotivePresent() {
        return bank.values()
                .stream()
                .allMatch(value -> value > 0);
    }

    public TransactionModel getTransactionForNeighbour() {
        return new TransactionModel(bank.entrySet().stream()
                .filter(entry -> entry.getValue() >= REPRESENTATIVE_PORTION_DIVISOR)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> getRepresentativePortion(entry.getValue()))
                ));
    }

    public void addTransaction(TransactionModel transaction) {
        transactions.add(transaction);
    }

    public void executeTransactions() {
        transactions.forEach(transaction -> transaction.getMotifValues()
                .forEach(this::addTransactionMotif));
    }

    private void addTransactionMotif(String transactionMotif, Integer transactionValue) {
        bank.compute(transactionMotif, (bankMotif, bankValue) ->
                Objects.nonNull(bankValue)
                        ? bankValue + transactionValue
                        : transactionValue
        );
    }

    private Integer getRepresentativePortion(Integer value) {
        return value / REPRESENTATIVE_PORTION_DIVISOR;
    }
}
