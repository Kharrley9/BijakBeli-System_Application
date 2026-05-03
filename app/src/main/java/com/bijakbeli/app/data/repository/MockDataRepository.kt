package com.bijakbeli.app.data.repository

import com.bijakbeli.app.data.model.*

/**
 * Mock data repository providing demo data for FYP presentation.
 * Simulates what would come from a MongoDB/Express backend.
 * Uses Batu Pahat stores as specified in SRS Section 2.5.
 */
object MockDataRepository {

    // ─── Stores (SRS Section 2.5) ─────────────────────────
    val stores = listOf(
        Store("store_bp", "BP Mall", "BP Mall Batu Pahat"),
        Store("store_sq", "Square One", "Square One Mall Batu Pahat"),
        Store("store_aeon", "AEON Big", "AEON Big Batu Pahat")
    )

    fun getStore(id: String): Store? = stores.find { it.id == id }

    // ─── Products ─────────────────────────────────────────
    val products = listOf(
        Product("p1", "Beras Super 10kg", "Jasmine", "Rice", unitSize = "10kg",
            description = "Premium local white rice"),
        Product("p2", "Minyak Masak 2kg", "Knife", "Cooking Oil", unitSize = "2kg",
            description = "Refined palm cooking oil"),
        Product("p3", "Susu Segar 1L", "Farm Fresh", "Dairy", unitSize = "1L",
            description = "Fresh full cream milk"),
        Product("p4", "Roti Gardenia", "Gardenia", "Bakery", unitSize = "400g",
            description = "Classic white bread"),
        Product("p5", "Telur Gred A (30s)", "Local", "Eggs", unitSize = "30 pcs",
            description = "Grade A chicken eggs"),
        Product("p6", "Gula Pasir 1kg", "CSR", "Sugar", unitSize = "1kg",
            description = "Fine white sugar"),
        Product("p7", "Milo ACTIV-GO 1kg", "Nestlé", "Beverages", unitSize = "1kg",
            description = "Chocolate malt drink powder"),
        Product("p8", "Tepung Gandum 1kg", "Cap Sauh", "Flour", unitSize = "1kg",
            description = "All-purpose wheat flour")
    )

    fun getProduct(id: String): Product? = products.find { it.id == id }

    fun searchProducts(query: String): List<Product> {
        if (query.isBlank()) return products
        val lower = query.lowercase()
        return products.filter {
            it.name.lowercase().contains(lower) ||
            it.brand.lowercase().contains(lower) ||
            it.category.lowercase().contains(lower)
        }
    }

    fun getProductsByCategory(category: String): List<Product> =
        products.filter { it.category.equals(category, ignoreCase = true) }

    val categories: List<String>
        get() = products.map { it.category }.distinct()

    // ─── Price Records (FR-7, FR-9) ───────────────────────
    val priceRecords = listOf(
        // Beras Super 10kg
        PriceRecord("p1", "store_bp",  29.90, 26.90, "17 Apr 2025, 8:00 AM"),
        PriceRecord("p1", "store_sq",  28.50, null,  "17 Apr 2025, 9:00 AM"),
        PriceRecord("p1", "store_aeon",27.90, 25.90, "16 Apr 2025, 5:00 PM"),

        // Minyak Masak 2kg
        PriceRecord("p2", "store_bp",  14.90, null,  "17 Apr 2025, 8:00 AM"),
        PriceRecord("p2", "store_sq",  13.50, 12.80, "17 Apr 2025, 9:00 AM"),
        PriceRecord("p2", "store_aeon",14.20, null,  "16 Apr 2025, 5:00 PM"),

        // Susu Segar 1L
        PriceRecord("p3", "store_bp",  6.90,  null,  "17 Apr 2025, 8:00 AM"),
        PriceRecord("p3", "store_sq",  6.50,  5.90,  "17 Apr 2025, 9:00 AM"),
        PriceRecord("p3", "store_aeon",6.20,  null,  "16 Apr 2025, 5:00 PM"),

        // Roti Gardenia
        PriceRecord("p4", "store_bp",  4.60,  null,  "17 Apr 2025, 8:00 AM"),
        PriceRecord("p4", "store_sq",  4.50,  null,  "17 Apr 2025, 9:00 AM"),
        PriceRecord("p4", "store_aeon",4.80,  4.30,  "16 Apr 2025, 5:00 PM"),

        // Telur Gred A (30s)
        PriceRecord("p5", "store_bp",  13.90, 12.50, "17 Apr 2025, 8:00 AM"),
        PriceRecord("p5", "store_sq",  14.50, null,  "17 Apr 2025, 9:00 AM"),
        PriceRecord("p5", "store_aeon",13.50, null,  "16 Apr 2025, 5:00 PM"),

        // Gula Pasir 1kg
        PriceRecord("p6", "store_bp",  3.20,  null,  "17 Apr 2025, 8:00 AM"),
        PriceRecord("p6", "store_sq",  3.10,  2.90,  "17 Apr 2025, 9:00 AM"),
        PriceRecord("p6", "store_aeon",3.30,  null,  "16 Apr 2025, 5:00 PM"),

        // Milo 1kg
        PriceRecord("p7", "store_bp",  18.90, 16.90, "17 Apr 2025, 8:00 AM"),
        PriceRecord("p7", "store_sq",  19.50, null,  "17 Apr 2025, 9:00 AM"),
        PriceRecord("p7", "store_aeon",18.50, null,  "16 Apr 2025, 5:00 PM"),

        // Tepung Gandum 1kg
        PriceRecord("p8", "store_bp",  2.80,  null,  "17 Apr 2025, 8:00 AM"),
        PriceRecord("p8", "store_sq",  2.70,  null,  "17 Apr 2025, 9:00 AM"),
        PriceRecord("p8", "store_aeon",2.90,  2.50,  "16 Apr 2025, 5:00 PM")
    )

    fun getPricesForProduct(productId: String): List<PriceRecord> =
        priceRecords.filter { it.productId == productId }

    /** The effective (lowest available) price for a product at a store */
    fun getEffectivePrice(record: PriceRecord): Double =
        record.promoPrice ?: record.regularPrice

    /** Cheapest price for a product across all stores */
    fun getCheapestPrice(productId: String): Pair<Double, Store>? {
        val prices = getPricesForProduct(productId)
        if (prices.isEmpty()) return null
        val best = prices.minByOrNull { getEffectivePrice(it) } ?: return null
        val store = getStore(best.storeId) ?: return null
        return getEffectivePrice(best) to store
    }

    // ─── Promotions (FR-10, FR-11) ────────────────────────
    val promotions = listOf(
        PromotionData("promo1", "p1", "store_aeon", "Rice Week Sale",
            "AEON Big Beras Super 10kg at special price",
            "14 Apr 2025", "21 Apr 2025", 8),
        PromotionData("promo2", "p7", "store_bp", "Milo Madness",
            "BP Mall Milo 1kg discount",
            "15 Apr 2025", "22 Apr 2025", 11),
        PromotionData("promo3", "p2", "store_sq", "Cooking Essentials",
            "Square One cooking oil deals",
            "16 Apr 2025", "23 Apr 2025", 5),
        PromotionData("promo4", "p5", "store_bp", "Egg-stra Savings",
            "BP Mall telur at lowest price",
            "14 Apr 2025", "20 Apr 2025", 10),
        PromotionData("promo5", "p4", "store_aeon", "Bakery Bonanza",
            "AEON Big roti deals",
            "15 Apr 2025", "19 Apr 2025", 7),
        PromotionData("promo6", "p6", "store_sq", "Sweet Deal",
            "Square One sugar promotion",
            "16 Apr 2025", "25 Apr 2025", 6)
    )

    fun getActivePromotions(): List<PromotionData> = promotions // all active for demo

    fun getPromotionsByStore(storeId: String): List<PromotionData> =
        promotions.filter { it.storeId == storeId }

    fun getPromotionsForProduct(productId: String): List<PromotionData> =
        promotions.filter { it.productId == productId }

    // ─── Cheapest Store Recommendation (FR-14) ────────────
    fun getCheapestStoreForList(items: List<ShoppingListItemData>): List<StoreRecommendation> {
        return stores.map { store ->
            var total = 0.0
            var available = 0
            items.forEach { item ->
                val price = priceRecords.find {
                    it.productId == item.productId && it.storeId == store.id
                }
                if (price != null) {
                    total += getEffectivePrice(price) * item.quantity
                    available++
                }
            }
            StoreRecommendation(
                storeId = store.id,
                storeName = store.name,
                totalCost = total,
                itemsAvailable = available,
                totalItems = items.size,
                savings = 0.0 // calculated after
            )
        }.sortedBy { it.totalCost }.let { sorted ->
            if (sorted.isEmpty()) return emptyList()
            val max = sorted.last().totalCost
            sorted.map { it.copy(savings = max - it.totalCost) }
        }
    }

    // ─── Savings Estimate (FR-15) ─────────────────────────
    fun getSavingsEstimate(items: List<ShoppingListItemData>): SavingsEstimate {
        var cheapestTotal = 0.0
        var expensiveTotal = 0.0

        items.forEach { item ->
            val prices = getPricesForProduct(item.productId)
            if (prices.isNotEmpty()) {
                val cheapest = prices.minOf { getEffectivePrice(it) }
                val expensive = prices.maxOf { getEffectivePrice(it) }
                cheapestTotal += cheapest * item.quantity
                expensiveTotal += expensive * item.quantity
            }
        }

        val savings = expensiveTotal - cheapestTotal
        val pct = if (expensiveTotal > 0) (savings / expensiveTotal) * 100 else 0.0

        return SavingsEstimate(
            totalIfCheapest = cheapestTotal,
            totalIfMostExpensive = expensiveTotal,
            potentialSavings = savings,
            savingsPercentage = pct
        )
    }

    // ─── Alternative Products (FR-16) ─────────────────────
    fun getAlternativeProducts(productId: String): List<AlternativeProduct> {
        val product = getProduct(productId) ?: return emptyList()
        val sameCategory = products.filter { it.category == product.category && it.id != productId }
        val currentCheapest = getCheapestPrice(productId)?.first ?: return emptyList()

        return sameCategory.mapNotNull { alt ->
            val cheapest = getCheapestPrice(alt.id) ?: return@mapNotNull null
            AlternativeProduct(
                product = alt,
                cheapestPrice = cheapest.first,
                cheapestStore = cheapest.second.name,
                priceDifference = currentCheapest - cheapest.first
            )
        }.sortedBy { it.cheapestPrice }
    }
}
