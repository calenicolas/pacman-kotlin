package com.pacmankata.infrastructure

import com.pacmankata.core.Points

class LocalPoints : Points {

    private var amount = 0

    override fun increase(amount: Int) {
        this.amount += amount
    }

    override fun toString(): String {
        return amount.toString()
    }
}
