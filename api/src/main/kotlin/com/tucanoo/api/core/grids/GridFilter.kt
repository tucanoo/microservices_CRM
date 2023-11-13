package com.tucanoo.api.core.grids

/* Represents the filter parameters passed from ReactDataGrid */
class GridFilter {
    var name: String? = null
    var operator: String? = null
    var type: String? = null
    var value: String? = null
    var active = true
}