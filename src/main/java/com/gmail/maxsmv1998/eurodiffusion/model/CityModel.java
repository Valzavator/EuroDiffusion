package com.gmail.maxsmv1998.eurodiffusion.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

@Builder
@Value
class CityModel {
    @ToString.Exclude
    CountryModel country;
    @Getter(AccessLevel.NONE)
    BankModel bankModel;
    @NonFinal
    boolean isComplete;
    int x;
    int y;

    public TransactionModel getTransactionForNeighbour() {
        return bankModel.getTransactionForNeighbour();
    }

    public void addTransaction(TransactionModel transaction) {
        bankModel.addTransaction(transaction);
    }

    public void executeTransactions() {
        bankModel.executeTransactions();
    }

    public boolean checkComplete() {
        isComplete = isComplete || bankModel.isEveryMotivePresent();
        return isComplete;
    }
}
