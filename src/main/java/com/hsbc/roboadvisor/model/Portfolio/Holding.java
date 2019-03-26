package com.hsbc.roboadvisor.model.Portfolio;

import com.hsbc.roboadvisor.model.Common.CurrencyAmount;

public class Holding
{
    private Long fundId;

    private Integer units;

    private CurrencyAmount balance;

    public Holding() {
        //empty constructor
    }

    public Holding(Long fundId, Integer units, CurrencyAmount balance) {
        this.fundId = fundId;
        this.units = units;
        this.balance = balance;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public CurrencyAmount getBalance() {
        return balance;
    }

    public void setBalance(CurrencyAmount balance) {
        this.balance = balance;
    }
}
