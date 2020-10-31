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
    List<TransactionModel> incomingTransactions = new ArrayList<>();
    List<TransactionModel> outgoingTransactions = new ArrayList<>();

    public BankModel(List<String> motifs, String rootCountryMotif) {
        bank = motifs.stream()
                .collect(Collectors.toMap(Function.identity(), motif -> 0));
        bank.put(rootCountryMotif, INITIAL_AMOUNT);
    }

    public boolean isEveryMotivePresent() {
        return bank.values()
                .stream()
                .allMatch(value -> value > 0);
    }

    public TransactionModel getTransactionForNeighbour() {
        TransactionModel newOutgoingTransaction = new TransactionModel(bank.entrySet().stream()
                .filter(entry -> entry.getValue() >= REPRESENTATIVE_PORTION_DIVISOR)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> getRepresentativePortion(entry.getValue()))
                ));
        outgoingTransactions.add(newOutgoingTransaction);
        return newOutgoingTransaction;
    }

    public void addTransaction(TransactionModel transaction) {
        incomingTransactions.add(transaction);
    }

    public void executeTransactions() {
        outgoingTransactions.forEach(transaction -> transaction.getMotifValues()
                .forEach((motif, value) -> addTransactionMotif(motif, -value)));
        outgoingTransactions.clear();

        incomingTransactions.forEach(transaction -> transaction.getMotifValues()
                .forEach(this::addTransactionMotif));
        incomingTransactions.clear();
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
