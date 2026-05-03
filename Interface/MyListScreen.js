// screens/MyListScreen.js
import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  StatusBar,
} from 'react-native';

const INITIAL_ITEMS = [
  {
    id: '1',
    name: 'Beras 10kg',
    store: 'Mydin',
    price: 27.50,
    qty: 1,
    checked: true,
  },
  {
    id: '2',
    name: 'Minyak Masak 2kg',
    store: "Lotus's",
    price: 13.50,
    qty: 1,
    checked: false,
  },
  {
    id: '3',
    name: 'Susu 1L',
    store: 'BP Mall',
    price: 6.20,
    qty: 1,
    checked: false,
  },
  {
    id: '4',
    name: 'Roti',
    store: 'Mydin',
    price: 4.60,
    qty: 1,
    checked: false,
  },
];

export default function MyListScreen({ navigation }) {
  const [items, setItems] = useState(INITIAL_ITEMS);
  const budget = 300;
  const spent = 80;
  const remaining = budget - spent;

  const toggleCheck = (id) => {
    setItems((prev) =>
      prev.map((item) => (item.id === id ? { ...item, checked: !item.checked } : item))
    );
  };

  const updateQty = (id, delta) => {
    setItems((prev) =>
      prev.map((item) =>
        item.id === id ? { ...item, qty: Math.max(1, item.qty + delta) } : item
      )
    );
  };

  const estimatedTotal = items.reduce((sum, item) => sum + item.price * item.qty, 0);

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor="#FFFFFF" />

      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation?.goBack()} style={styles.backBtn}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>My List</Text>
        <TouchableOpacity style={styles.editBtn}>
          <Text style={styles.editText}>Edit</Text>
        </TouchableOpacity>
      </View>

      {/* Budget Summary Bar */}
      <View style={styles.budgetBar}>
        <View style={styles.budgetItem}>
          <Text style={styles.budgetItemLabel}>Budget</Text>
          <Text style={styles.budgetItemValue}>RM{budget.toFixed(2)}</Text>
        </View>
        <View style={styles.budgetDivider} />
        <View style={styles.budgetItem}>
          <Text style={styles.budgetItemLabel}>Spent</Text>
          <Text style={[styles.budgetItemValue, { color: '#E65100' }]}>RM{spent.toFixed(2)}</Text>
        </View>
        <View style={styles.budgetDivider} />
        <View style={styles.budgetItem}>
          <Text style={styles.budgetItemLabel}>Remaining</Text>
          <Text style={[styles.budgetItemValue, { color: '#2D7A4F' }]}>RM{remaining.toFixed(2)}</Text>
        </View>
      </View>

      <ScrollView style={styles.scrollArea} showsVerticalScrollIndicator={false}>
        {/* List Items */}
        {items.map((item) => (
          <View key={item.id} style={styles.listItem}>
            {/* Checkbox */}
            <TouchableOpacity
              style={[styles.checkbox, item.checked && styles.checkboxChecked]}
              onPress={() => toggleCheck(item.id)}
            >
              {item.checked && <Text style={styles.checkmark}>✓</Text>}
            </TouchableOpacity>

            {/* Item Info */}
            <View style={styles.itemInfo}>
              <Text style={[styles.itemName, item.checked && styles.itemNameChecked]}>
                {item.name}
              </Text>
              <Text style={styles.itemStore}>{item.store}</Text>
            </View>

            {/* Price */}
            <Text style={styles.itemPrice}>RM{(item.price * item.qty).toFixed(2)}</Text>

            {/* Qty Controls */}
            <View style={styles.qtyControl}>
              <TouchableOpacity
                style={styles.qtyBtn}
                onPress={() => updateQty(item.id, -1)}
              >
                <Text style={styles.qtyBtnText}>−</Text>
              </TouchableOpacity>
              <Text style={styles.qtyValue}>{item.qty}</Text>
              <TouchableOpacity
                style={[styles.qtyBtn, styles.qtyBtnAdd]}
                onPress={() => updateQty(item.id, 1)}
              >
                <Text style={[styles.qtyBtnText, { color: '#FFFFFF' }]}>+</Text>
              </TouchableOpacity>
            </View>
          </View>
        ))}

        {/* Add Item Button */}
        <TouchableOpacity
          style={styles.addItemRow}
          onPress={() => navigation?.navigate('Search')}
        >
          <Text style={styles.addItemIcon}>+</Text>
          <Text style={styles.addItemText}>Add item</Text>
        </TouchableOpacity>

        {/* Estimated Total */}
        <View style={styles.totalRow}>
          <Text style={styles.totalLabel}>Estimated Total:</Text>
          <Text style={styles.totalAmount}>RM{estimatedTotal.toFixed(2)}</Text>
        </View>

        {/* Compare Total Button */}
        <TouchableOpacity
          style={styles.compareButton}
          onPress={() => navigation?.navigate('Compare')}
        >
          <Text style={styles.compareButtonText}>Compare Total</Text>
        </TouchableOpacity>

        <View style={{ height: 40 }} />
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#F5F7F5' },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    paddingTop: 52,
    paddingBottom: 16,
    paddingHorizontal: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.05,
    shadowRadius: 4,
    elevation: 2,
  },
  backBtn: { padding: 4 },
  backArrow: { fontSize: 22, color: '#1A1A1A' },
  headerTitle: {
    flex: 1,
    textAlign: 'center',
    fontSize: 18,
    fontWeight: '700',
    color: '#1A1A1A',
  },
  editBtn: { padding: 4 },
  editText: { fontSize: 15, color: '#2D7A4F', fontWeight: '600' },
  budgetBar: {
    flexDirection: 'row',
    backgroundColor: '#FFFFFF',
    paddingVertical: 14,
    paddingHorizontal: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#F0F0F0',
  },
  budgetItem: { flex: 1, alignItems: 'center' },
  budgetItemLabel: { fontSize: 11, color: '#888', marginBottom: 3 },
  budgetItemValue: { fontSize: 14, fontWeight: '700', color: '#1A1A1A' },
  budgetDivider: { width: 1, backgroundColor: '#E8E8E8', marginVertical: 4 },
  scrollArea: { flex: 1, paddingHorizontal: 16, paddingTop: 14 },
  listItem: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 14,
    padding: 14,
    marginBottom: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.05,
    shadowRadius: 6,
    elevation: 1,
    gap: 10,
  },
  checkbox: {
    width: 24,
    height: 24,
    borderRadius: 7,
    borderWidth: 2,
    borderColor: '#CCCCCC',
    alignItems: 'center',
    justifyContent: 'center',
  },
  checkboxChecked: {
    backgroundColor: '#2D7A4F',
    borderColor: '#2D7A4F',
  },
  checkmark: { color: '#FFFFFF', fontSize: 13, fontWeight: '700' },
  itemInfo: { flex: 1 },
  itemName: { fontSize: 14, fontWeight: '600', color: '#1A1A1A', marginBottom: 2 },
  itemNameChecked: { textDecorationLine: 'line-through', color: '#AAAAAA' },
  itemStore: { fontSize: 12, color: '#888' },
  itemPrice: { fontSize: 14, fontWeight: '700', color: '#1A4731', minWidth: 60, textAlign: 'right' },
  qtyControl: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  qtyBtn: {
    width: 28,
    height: 28,
    borderRadius: 8,
    borderWidth: 1.5,
    borderColor: '#CCCCCC',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#FAFAFA',
  },
  qtyBtnAdd: {
    backgroundColor: '#1A4731',
    borderColor: '#1A4731',
  },
  qtyBtnText: { fontSize: 16, fontWeight: '700', color: '#555', lineHeight: 20 },
  qtyValue: { fontSize: 14, fontWeight: '600', color: '#1A1A1A', minWidth: 16, textAlign: 'center' },
  addItemRow: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 12,
    paddingHorizontal: 4,
    gap: 10,
  },
  addItemIcon: {
    width: 28,
    height: 28,
    borderRadius: 8,
    backgroundColor: '#E8F5E9',
    color: '#2D7A4F',
    fontSize: 20,
    textAlign: 'center',
    lineHeight: 28,
    fontWeight: '700',
  },
  addItemText: { fontSize: 14, color: '#2D7A4F', fontWeight: '600' },
  totalRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 12,
    borderTopWidth: 1,
    borderTopColor: '#E8E8E8',
    marginTop: 4,
    marginBottom: 16,
  },
  totalLabel: { fontSize: 14, color: '#555', fontWeight: '600' },
  totalAmount: { fontSize: 18, fontWeight: '800', color: '#1A4731' },
  compareButton: {
    backgroundColor: '#1A4731',
    borderRadius: 14,
    paddingVertical: 16,
    alignItems: 'center',
    shadowColor: '#1A4731',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
    elevation: 4,
  },
  compareButtonText: { fontSize: 16, fontWeight: '700', color: '#FFFFFF', letterSpacing: 0.5 },
});
