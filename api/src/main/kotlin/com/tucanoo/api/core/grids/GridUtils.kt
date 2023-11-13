package com.tucanoo.api.core.grids

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.function.Consumer


@Component
class GridUtils(private val objectMapper: ObjectMapper) {

    private val log = LoggerFactory.getLogger(GridUtils::class.java)

    fun <T> getDataForDataGrid(
        params: GridParams,
        repository: JpaSpecificationExecutor<T>,
        mapper: GridEntityToRowMapper<T>
    ): String? {
        val filters: List<GridFilter> = getFiltersFromParams(params)

        val sort: Sort? = getSortFromParams(params)
        if (sort == null)
            return null

        val specification = CommonSpecification<T>(filters)

        val pageRequest: org.springframework.data.domain.Pageable =
            PageRequest.of(params.currentPage, params.length, sort)

        val entities: org.springframework.data.domain.Page<T> = repository.findAll(specification, pageRequest)

        val totalRecords: Long = entities.getTotalElements()
        val cells: MutableList<Map<String, Any?>> = ArrayList()
        entities.forEach(Consumer { entity: T -> cells.add(mapper.toCellData(entity)) })

        val jsonMap: MutableMap<String, Any> = HashMap()
        jsonMap["draw"] = params.draw
        jsonMap["recordsTotal"] = totalRecords
        jsonMap["recordsFiltered"] = totalRecords
        jsonMap["data"] = cells
        var json: String? = null
        try {
            json = objectMapper.writeValueAsString(jsonMap)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
        return json
    }

    fun getSortFromParams(params: GridParams): Sort? {
        val sortInfo: Map<String, Any>? = try {
            val sortJSON = URLDecoder.decode(params.sort, StandardCharsets.UTF_8.name())
            objectMapper.readValue(sortJSON, object : TypeReference<Map<String, Any>>() {})
        } catch (e: Exception) {
            return null
        }

        if (sortInfo == null) return Sort.by(Sort.Direction.ASC, "id")

        val sortName = sortInfo["name"] as? String ?: "id"
        val sortDirection = if ((sortInfo["dir"]?.toString() ?: "1") == "1") {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }
        return Sort.by(sortDirection, sortName)
    }

    fun getFiltersFromParams(params: GridParams): List<GridFilter> {
        params.filter?.let {
            try {
                val filterJSON = URLDecoder.decode(it, "UTF-8")
                return objectMapper.readValue(filterJSON, object : TypeReference<List<GridFilter>>() {})
            } catch (e: Exception) {
                log.warn("Unable to parse filter JSON: $it", e)
            }
        }
        return emptyList()
    }
}