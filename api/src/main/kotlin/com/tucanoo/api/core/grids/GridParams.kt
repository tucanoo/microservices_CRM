package com.tucanoo.api.core.grids

/* Represents the sorting, pagination and filter parameters passed from ReactDataGrid */
class GridParams {
    var draw = 0
    var length = 0
    var start = 0
    var currentPage = 0
        get() = start / length     // Calculate the current page from the start and length
    var sort: String? = null
    var filter: String? = null

    // Apply some default pagination options
    fun DataGridParams() {
        draw = 1
        length = 30
        start = 30
        currentPage = 1
    }
}