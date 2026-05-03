package com.bijakbeli.app.data.model

/**
 * Core product entity matching SRS Section 6.1
 */
data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val imageUrl: String? = null,
    val description: String = "",
    val unitSize: String
)

/**
 * Store entity for Batu Pahat retail locations
 */
data class Store(
    val id: String,
    val name: String,
    val mallName: String,
    val location: String = "Batu Pahat",
    val isActive: Boolean = true
)

/**
 * Price record with timestamp (FR-9)
 */
data class PriceRecord(
    val productId: String,
    val storeId: String,
    val regularPrice: Double,
    val promoPrice: Double? = null,
    val lastUpdated: String
)

/**
 * Promotion entry with start/end dates (FR-10)
 */
data class PromotionData(
    val id: String,
    val productId: String,
    val storeId: String,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val discountPercent: Int? = null
)

/**
 * Shopping list item with store selection
 */
data class ShoppingListItemData(
    val productId: String,
    val quantity: Int = 1,
    val selectedStoreId: String? = null,
    val isChecked: Boolean = false
)

/**
 * Store recommendation result (FR-14)
 */
data class StoreRecommendation(
    val storeId: String,
    val storeName: String,
    val totalCost: Double,
    val itemsAvailable: Int,
    val totalItems: Int,
    val savings: Double
)

/**
 * Savings estimation (FR-15)
 */
data class SavingsEstimate(
    val totalIfCheapest: Double,
    val totalIfMostExpensive: Double,
    val potentialSavings: Double,
    val savingsPercentage: Double
)

/**
 * Alternative product suggestion (FR-16)
 */
data class AlternativeProduct(
    val product: Product,
    val cheapestPrice: Double,
    val cheapestStore: String,
    val priceDifference: Double
)
