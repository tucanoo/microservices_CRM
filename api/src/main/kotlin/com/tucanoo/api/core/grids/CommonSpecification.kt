package com.tucanoo.api.core.grids

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CommonSpecification<T>(private val filters: List<GridFilter>) : Specification<T> {
    override fun toPredicate(root: Root<T>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {

        val predicates = mutableListOf<Predicate>()

        filters.forEach { filter ->
            if (filter.active != true)
                return@forEach

            when (filter.type) {
                "date" -> {
                    if (!StringUtils.hasText(filter.value))
                        return@forEach

                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val startTime = LocalDate.parse(filter.value, formatter).atStartOfDay()
                    val endTime = startTime.toLocalDate().plusDays(1).atStartOfDay()

                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.name), startTime))
                    predicates.add(criteriaBuilder.lessThan(root.get(filter.name), endTime))
                }

                "boolean" -> {
                    getBooleanPredicate(filter, root, criteriaBuilder)?.let { predicates.add(it) }
                }

                else -> {
                    getPredicate(filter, root, criteriaBuilder)?.let { predicates.add(it) }
                }
            }
        }

        return if (predicates.isNotEmpty()) criteriaBuilder.and(*predicates.toTypedArray()) else null


    }

    companion object {
        fun <T> getPredicate(filter: GridFilter, root: Root<T>, criteriaBuilder: CriteriaBuilder): Predicate? {
            if (!StringUtils.hasText(filter.value)) {
                return null
            }
            return when (filter.operator) {
                "contains" -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(filter.name)),
                    "%${filter.value?.lowercase()}%"
                )

                "notContains" -> criteriaBuilder.notLike(
                    criteriaBuilder.lower(root.get(filter.name)),
                    "%${filter.value?.lowercase()}%"
                )

                "eq" -> criteriaBuilder.equal(root.get<String>(filter.name), filter.value)
                "neq" -> criteriaBuilder.notEqual(root.get<String>(filter.name), filter.value)
                "empty" -> criteriaBuilder.isNull(root.get<String>(filter.name))
                "notEmpty" -> criteriaBuilder.isNotNull(root.get<String>(filter.name))
                "startsWith" -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(filter.name)),
                    "${filter.value?.lowercase()}%"
                )

                "endsWith" -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(filter.name)),
                    "%${filter.value?.lowercase()}"
                )

                else -> null
            }
        }

        fun <T> getBooleanPredicate(filter: GridFilter, root: Root<T>, criteriaBuilder: CriteriaBuilder): Predicate? {
            // Convert the filter value to a boolean, defaulting to false if the conversion fails
            val booleanValue = filter.value?.toBooleanStrictOrNull() ?: return null

            val path = root.get<Boolean>(filter.name)
            return when (filter.operator) {
                "eq" -> if (booleanValue) criteriaBuilder.isTrue(path) else criteriaBuilder.or(criteriaBuilder.isFalse(path), criteriaBuilder.isNull(path))
                "neq" -> if (!booleanValue) criteriaBuilder.isTrue(path) else criteriaBuilder.and(criteriaBuilder.isFalse(path), criteriaBuilder.isNotNull(path))
                else -> null
            }
        }
    }
}