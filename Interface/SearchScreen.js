// screens/SearchScreen.js
import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  FlatList,
  StatusBar,
} from 'react-native';

const RECENT_SEARCHES = ['Beras 10kg', 'Minyak Masak', 'Susu'];

const CATEGORIES = [
  { id: '1', emoji: '🍚', label: 'Rice' },
  { id: '2', emoji: '🍳', label: 'Cooking Oil' },
  { id: '3', emoji: '🥛', label: 'Milk' },
  { id: '4', emoji: '🍞', label: 'Bread' },
  { id: '5', emoji: '🥩', label: 'More' },
];

const RESULTS = [
  {
    id: '1',
    name: 'Beras Super 10kg',
    store: "Lotus's",
    price: 'RM28.90',
    isBest: true,
    emoji: '🍚',
  },
  {
    id: '2',
    name: 'Beras Import 10kg',
    store: 'Mydin',
    price: 'RM27.50',
    isBest: false,
    emoji: '🍚',
  },
  {
    id: '3',
    name: 'Beras Wangi 10kg',
    store: 'BP Mall',
    price: 'RM29.00',
    isBest: false,
    emoji: '🍚',
  },
];

export default function SearchScreen({ navigation }) {
  const [query, setQuery] = useState('Beras 10kg');
  const [activeCategory, setActiveCategory] = useState('1');

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor="#FFFFFF" />

      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation?.goBack()} style={styles.backBtn}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Search</Text>
        <TouchableOpacity style={styles.scanBtn}>
          <Text style={styles.scanIcon}>📷</Text>
        </TouchableOpacity>
      </View>

      {/* Search Bar */}
      <View style={styles.searchContainer}>
        <Text style={styles.searchIcon}>🔍</Text>
        <TextInput
          style={styles.searchInput}
          placeholder="Search grocery items..."
          placeholderTextColor="#A0A0A0"
          value={query}
          onChangeText={setQuery}
          returnKeyType="search"
        />
        {query.length > 0 && (
          <TouchableOpacity onPress={() => setQuery('')}>
            <Text style={styles.clearIcon}>✕</Text>
          </TouchableOpacity>
        )}
      </View>

      <ScrollView style={styles.scrollArea} showsVerticalScrollIndicator={false}>

        {/* Recent Searches */}
        <View style={styles.section}>
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionTitle}>Recent Searches</Text>
            <TouchableOpacity>
              <Text style={styles.clearAll}>Clear</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.recentRow}>
            {RECENT_SEARCHES.map((item, idx) => (
              <TouchableOpacity
                key={idx}
                style={styles.recentChip}
                onPress={() => setQuery(item)}
              >
                <Text style={styles.recentIcon}>🕐</Text>
                <Text style={styles.recentText}>{item}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>

        {/* Categories */}
        <View style={styles.section}>
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionTitle}>Categories</Text>
            <TouchableOpacity>
              <Text style={styles.seeAll}>See All</Text>
            </TouchableOpacity>
          </View>
          <ScrollView horizontal showsHorizontalScrollIndicator={false}>
            <View style={styles.categoriesRow}>
              {CATEGORIES.map((cat) => (
                <TouchableOpacity
                  key={cat.id}
                  style={[
                    styles.categoryItem,
                    activeCategory === cat.id && styles.categoryItemActive,
                  ]}
                  onPress={() => setActiveCategory(cat.id)}
                >
                  <View
                    style={[
                      styles.categoryIcon,
                      activeCategory === cat.id && styles.categoryIconActive,
                    ]}
                  >
                    <Text style={styles.categoryEmoji}>{cat.emoji}</Text>
                  </View>
                  <Text
                    style={[
                      styles.categoryLabel,
                      activeCategory === cat.id && styles.categoryLabelActive,
                    ]}
                  >
                    {cat.label}
                  </Text>
                </TouchableOpacity>
              ))}
            </View>
          </ScrollView>
        </View>

        {/* Results */}
        <View style={styles.section}>
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionTitle}>
              Results ({RESULTS.length})
            </Text>
            <TouchableOpacity style={styles.filterBtn}>
              <Text style={styles.filterIcon}>⚙</Text>
              <Text style={styles.filterText}>Filter</Text>
            </TouchableOpacity>
          </View>

          {RESULTS.map((item) => (
            <TouchableOpacity
              key={item.id}
              style={styles.resultCard}
              onPress={() => navigation?.navigate('PriceComparison', { item })}
            >
              <View style={styles.resultImageBox}>
                <Text style={styles.resultEmoji}>{item.emoji}</Text>
              </View>
              <View style={styles.resultInfo}>
                <Text style={styles.resultName}>{item.name}</Text>
                <Text style={styles.resultStore}>{item.store}</Text>
              </View>
              <View style={styles.resultRight}>
                <Text style={styles.resultPrice}>{item.price}</Text>
                {item.isBest && (
                  <View style={styles.bestBadge}>
                    <Text style={styles.bestBadgeText}>Best</Text>
                  </View>
                )}
                <TouchableOpacity style={styles.addButton}>
                  <Text style={styles.addButtonText}>+</Text>
                </TouchableOpacity>
              </View>
            </TouchableOpacity>
          ))}
        </View>

        <View style={{ height: 30 }} />
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
  headerTitle: { flex: 1, textAlign: 'center', fontSize: 18, fontWeight: '700', color: '#1A1A1A' },
  scanBtn: { padding: 4 },
  scanIcon: { fontSize: 22 },
  searchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 14,
    marginHorizontal: 16,
    marginTop: 14,
    marginBottom: 4,
    paddingHorizontal: 14,
    paddingVertical: 12,
    borderWidth: 1.5,
    borderColor: '#E0E0E0',
  },
  searchIcon: { fontSize: 16, marginRight: 8, color: '#999' },
  searchInput: { flex: 1, fontSize: 15, color: '#1A1A1A' },
  clearIcon: { fontSize: 14, color: '#999', padding: 4 },
  scrollArea: { flex: 1 },
  section: { paddingHorizontal: 16, marginTop: 18 },
  sectionHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 10 },
  sectionTitle: { fontSize: 15, fontWeight: '700', color: '#1A1A1A' },
  clearAll: { fontSize: 13, color: '#FF4444', fontWeight: '600' },
  seeAll: { fontSize: 13, color: '#2D7A4F', fontWeight: '600' },
  recentRow: { flexDirection: 'row', flexWrap: 'wrap', gap: 8 },
  recentChip: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 20,
    paddingHorizontal: 12,
    paddingVertical: 7,
    borderWidth: 1,
    borderColor: '#E0E0E0',
    gap: 5,
  },
  recentIcon: { fontSize: 12 },
  recentText: { fontSize: 13, color: '#555' },
  categoriesRow: { flexDirection: 'row', gap: 12, paddingRight: 16 },
  categoryItem: { alignItems: 'center', width: 64 },
  categoryIcon: {
    width: 56,
    height: 56,
    borderRadius: 16,
    backgroundColor: '#FFFFFF',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 6,
    borderWidth: 1.5,
    borderColor: '#E8E8E8',
  },
  categoryIconActive: {
    backgroundColor: '#E8F5E9',
    borderColor: '#2D7A4F',
  },
  categoryEmoji: { fontSize: 24 },
  categoryLabel: { fontSize: 11, color: '#888', textAlign: 'center' },
  categoryLabelActive: { color: '#2D7A4F', fontWeight: '700' },
  filterBtn: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
    backgroundColor: '#FFFFFF',
    borderRadius: 8,
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderWidth: 1,
    borderColor: '#E0E0E0',
  },
  filterIcon: { fontSize: 12 },
  filterText: { fontSize: 12, color: '#555', fontWeight: '600' },
  resultCard: {
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
    elevation: 2,
  },
  resultImageBox: {
    width: 52,
    height: 52,
    borderRadius: 12,
    backgroundColor: '#F0F7F2',
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 12,
  },
  resultEmoji: { fontSize: 28 },
  resultInfo: { flex: 1 },
  resultName: { fontSize: 14, fontWeight: '600', color: '#1A1A1A', marginBottom: 3 },
  resultStore: { fontSize: 12, color: '#888' },
  resultRight: { alignItems: 'flex-end', gap: 4 },
  resultPrice: { fontSize: 15, fontWeight: '700', color: '#1A4731' },
  bestBadge: {
    backgroundColor: '#E8F5E9',
    borderRadius: 6,
    paddingHorizontal: 8,
    paddingVertical: 2,
  },
  bestBadgeText: { fontSize: 10, color: '#2D7A4F', fontWeight: '700' },
  addButton: {
    width: 30,
    height: 30,
    borderRadius: 8,
    backgroundColor: '#1A4731',
    alignItems: 'center',
    justifyContent: 'center',
  },
  addButtonText: { fontSize: 18, color: '#FFFFFF', fontWeight: '700', lineHeight: 22 },
});
