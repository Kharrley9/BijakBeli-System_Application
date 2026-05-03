package com.bijakbeli.app.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bijakbeli.app.data.model.*
import com.bijakbeli.app.data.repository.MockDataRepository

/**
 * Shared ViewModel managing application state across all screens.
 * Handles shopping list, budget, user preferences, and search state.
 */
class BijakBeliViewModel : ViewModel() {

    // ─── User ─────────────────────────────────────────────
    val userName = mutableStateOf("Khalil")
    val userEmail = mutableStateOf("khalil@example.com")
    val monthlyBudget = mutableDoubleStateOf(300.0)

    val preferredStoreIds = mutableStateListOf("store_bp", "store_sq", "store_aeon")

    // ─── Shopping List ────────────────────────────────────
    private val _shoppingList = mutableStateListOf<ShoppingListItemData>()
    val shoppingList: List<ShoppingListItemData> get() = _shoppingList

    init {
        // Pre-populate with demo data
        _shoppingList.addAll(listOf(
            ShoppingListItemData("p1", 1, null, true),
            ShoppingListItemData("p2", 1, null, false),
            ShoppingListItemData("p3", 1, null, true),
            ShoppingListItemData("p4", 1, null, false)
        ))
    }

    fun addToShoppingList(productId: String) {
        val existing = _shoppingList.indexOfFirst { it.productId == productId }
        if (existing >= 0) {
            val item = _shoppingList[existing]
            _shoppingList[existing] = item.copy(quantity = item.quantity + 1)
        } else {
            _shoppingList.add(ShoppingListItemData(productId, 1))
        }
    }

    fun removeFromShoppingList(productId: String) {
        _shoppingList.removeAll { it.productId == productId }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        val index = _shoppingList.indexOfFirst { it.productId == productId }
        if (index >= 0) {
            if (newQuantity <= 0) {
                _shoppingList.removeAt(index)
            } else {
                _shoppingList[index] = _shoppingList[index].copy(quantity = newQuantity)
            }
        }
    }

    fun toggleChecked(productId: String) {
        val index = _shoppingList.indexOfFirst { it.productId == productId }
        if (index >= 0) {
            val item = _shoppingList[index]
            _shoppingList[index] = item.copy(isChecked = !item.isChecked)
        }
    }

    fun isInShoppingList(productId: String): Boolean =
        _shoppingList.any { it.productId == productId }

    // ─── Budget Calculations ──────────────────────────────
    fun getEstimatedTotal(): Double {
        return _shoppingList.sumOf { item ->
            val cheapest = MockDataRepository.getCheapestPrice(item.productId)
            (cheapest?.first ?: 0.0) * item.quantity
        }
    }

    fun getSpentAmount(): Double {
        // Simulated spent amount for demo
        return 180.0
    }

    fun getRemainingBudget(): Double {
        return monthlyBudget.doubleValue - getSpentAmount()
    }

    // ─── Cheapest Store (FR-14) ───────────────────────────
    fun getCheapestStoreRecommendations(): List<StoreRecommendation> {
        return MockDataRepository.getCheapestStoreForList(_shoppingList)
    }

    // ─── Savings (FR-15) ──────────────────────────────────
    fun getSavingsEstimate(): SavingsEstimate {
        return MockDataRepository.getSavingsEstimate(_shoppingList)
    }

    // ─── Search ───────────────────────────────────────────
    val searchQuery = mutableStateOf("")
    val selectedCategory = mutableStateOf<String?>(null)
    val selectedStoreFilter = mutableStateOf<String?>(null)

    fun getSearchResults(): List<Product> {
        var results = MockDataRepository.searchProducts(searchQuery.value)
        selectedCategory.value?.let { cat ->
            results = results.filter { it.category.equals(cat, ignoreCase = true) }
        }
        return results
    }

    fun clearFilters() {
        selectedCategory.value = null
        selectedStoreFilter.value = null
    }
}
