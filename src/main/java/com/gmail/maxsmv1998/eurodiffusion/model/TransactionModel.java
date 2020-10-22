package com.gmail.maxsmv1998.eurodiffusion.model;

import lombok.Value;

import java.util.Map;

@Value
class TransactionModel {
    Map<String, Integer> motifValues;

    public TransactionModel(Map<String, Integer> motifValues) {
        this.motifValues = Map.copyOf(motifValues);
    }
}
