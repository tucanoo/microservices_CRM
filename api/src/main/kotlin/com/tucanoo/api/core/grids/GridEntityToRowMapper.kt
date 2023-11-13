package com.tucanoo.api.core.grids

// interface for mapping a grid entity to a row
fun interface GridEntityToRowMapper<T> {
    fun toCellData(entity: T): Map<String, Any?>
}