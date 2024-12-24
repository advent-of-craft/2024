package domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import domain.core.Error

data class StockUnit(val value: Int) {

    companion object {
        fun from(stock: Int): Either<Error, StockUnit> =
            if (stock >= 0) StockUnit(stock).right()
            else Error("A stock unit cannot be negative").left()
    }

    fun isSupplied(): Boolean = value > 0
    fun decrease(): StockUnit = StockUnit(value - 1)
}
