// screens/HomeScreen.js
import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  StatusBar,
  FlatList,
} from 'react-native';

const PREFERRED_STORES = [
  { id: '1', name: "Lotus's", logo: '🏪' },
  { id: '2', name: 'MYDIN', logo: '🛒' },
  { id: '3', name: 'BP Mall', logo: '🏬' },
];

const TODAY_DEALS = [
  {
    id: '1',
    store: "LOTUS'S",
    badge: '10% OFF',
    title: 'Fresh Items',
    desc: 'Valid till 29 Apr',
    color: '#E8F5E9',
    storeColor: '#1A4731',
    emoji: '🥦',
  },
  {
    id: '2',
    store: 'MYDIN',
    badge: '',
    title: 'Muncul Sale\nBeras 10kg',
    desc: 'Was RM19 → RM27.50',
    color: '#FFF3E0',
    storeColor: '#E65100',
    emoji: '🍚',
  },
];

const QUICK_ACTIONS = [
  { id: '1', icon: '🔍', label: 'Search', screen: 'Search' },
  { id: '2', icon: '📷', label: 'Scan', screen: 'Scan' },
  { id: '3', icon: '📋', label: 'My List', screen: 'MyList' },
  { id: '4', icon: '🎁', label: 'Promotions', screen: 'Promotions' },
];

export default function HomeScreen({ navigation }) {
  const [searchText, setSearchText] = useState('');

  const monthlyBudget = 300;
  const spent = 120;
  const remaining = monthlyBudget - spent;
  const progressPercent = (spent / monthlyBudget) * 100;

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#1A4731" />

      {/* Header */}
      <View style={styles.header}>
        <View>
          <View style={styles.greetingRow}>
            <Text style={styles.greeting}>Hi Khalil</Text>
            <Text style={styles.waveEmoji}> 👋</Text>
          </View>
          <Text style={styles.subGreeting}>Let's shop smart today!</Text>
        </View>
        <TouchableOpacity style={styles.notifButton}>
          <Text style={styles.notifIcon}>🔔</Text>
        </TouchableOpacity>
      </View>

      <ScrollView style={styles.scrollArea} showsVerticalScrollIndicator={false}>

        {/* Search Bar */}
        <View style={styles.searchContainer}>
          <Text style={styles.searchIcon}>🔍</Text>
          <TextInput
            style={styles.searchInput}
            placeholder="Search grocery items..."
            placeholderTextColor="#A0A0A0"
            value={searchText}
            onChangeText={setSearchText}
            onFocus={() => navigation?.navigate('Search')}
          />
          {searchText.length > 0 && (
            <TouchableOpacity onPress={() => setSearchText('')}>
              <Text style={styles.clearIcon}>✕</Text>
            </TouchableOpacity>
          )}
        </View>

        {/* Preferred Stores */}
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Preferred Stores</Text>
          <View style={styles.storesRow}>
            {PREFERRED_STORES.map((store) => (
              <TouchableOpacity key={store.id} style={styles.storeChip}>
                <Text style={styles.storeEmoji}>{store.logo}</Text>
                <Text style={styles.storeName}>{store.name}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>

        {/* Today's Best Deals */}
        <View style={styles.section}>
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionTitle}>Today's Best Deals</Text>
            <TouchableOpacity onPress={() => navigation?.navigate('Promotions')}>
              <Text style={styles.seeAll}>See All</Text>
            </TouchableOpacity>
          </View>
          <ScrollView horizontal showsHorizontalScrollIndicator={false} style={styles.dealsScroll}>
            {TODAY_DEALS.map((deal) => (
              <TouchableOpacity
                key={deal.id}
                style={[styles.dealCard, { backgroundColor: deal.color }]}
              >
                <View style={[styles.dealStoreBadge, { backgroundColor: deal.storeColor }]}>
                  <Text style={styles.dealStoreText}>{deal.store}</Text>
                </View>
                {deal.badge !== '' && (
                  <View style={styles.dealBadge}>
                    <Text style={styles.dealBadgeText}>{deal.badge}</Text>
                  </View>
                )}
                <Text style={styles.dealEmoji}>{deal.emoji}</Text>
                <Text style={styles.dealTitle}>{deal.title}</Text>
                <Text style={styles.dealDesc}>{deal.desc}</Text>
              </TouchableOpacity>
            ))}
          </ScrollView>
        </View>

        {/* Budget Overview */}
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Budget Overview</Text>
          <View style={styles.budgetCard}>
            <View style={styles.budgetRow}>
              <View>
                <Text style={styles.budgetLabel}>Monthly Budget</Text>
                <Text style={styles.budgetAmount}>RM{monthlyBudget.toFixed(2)}</Text>
              </View>
              <View style={{ alignItems: 'flex-end' }}>
                <Text style={styles.budgetLabel}>Remaining</Text>
                <Text style={[styles.budgetAmount, { color: '#2D7A4F' }]}>
                  RM{remaining.toFixed(2)}
                </Text>
              </View>
            </View>
            <View style={styles.progressBarBg}>
              <View style={[styles.progressBarFill, { width: `${progressPercent}%` }]} />
            </View>
            <Text style={styles.budgetSpentText}>
              Spent: RM{spent.toFixed(2)} ({progressPercent.toFixed(0)}%)
            </Text>
          </View>
        </View>

        {/* Quick Actions */}
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Quick Actions</Text>
          <View style={styles.quickActionsRow}>
            {QUICK_ACTIONS.map((action) => (
              <TouchableOpacity
                key={action.id}
                style={styles.quickActionItem}
                onPress={() => navigation?.navigate(action.screen)}
              >
                <View style={styles.quickActionIcon}>
                  <Text style={styles.quickActionEmoji}>{action.icon}</Text>
                </View>
                <Text style={styles.quickActionLabel}>{action.label}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>

        <View style={{ height: 80 }} />
      </ScrollView>

      {/* Bottom Tab Bar */}
      <View style={styles.tabBar}>
        {[
          { icon: '🏠', label: 'Home', screen: 'Home' },
          { icon: '🔍', label: 'Compare', screen: 'Compare' },
          { icon: '💰', label: 'Budget', screen: 'Budget' },
          { icon: '👤', label: 'Profile', screen: 'Profile' },
        ].map((tab, index) => (
          <TouchableOpacity
            key={index}
            style={styles.tabItem}
            onPress={() => navigation?.navigate(tab.screen)}
          >
            <Text style={styles.tabIcon}>{tab.icon}</Text>
            <Text style={[styles.tabLabel, index === 0 && styles.tabLabelActive]}>
              {tab.label}
            </Text>
          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#F5F7F5' },
  header: {
    backgroundColor: '#1A4731',
    paddingTop: 52,
    paddingBottom: 20,
    paddingHorizontal: 20,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  greetingRow: { flexDirection: 'row', alignItems: 'center' },
  greeting: { fontSize: 24, fontWeight: '800', color: '#FFFFFF' },
  waveEmoji: { fontSize: 22 },
  subGreeting: { fontSize: 14, color: 'rgba(255,255,255,0.75)', marginTop: 2 },
  notifButton: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: 'rgba(255,255,255,0.15)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  notifIcon: { fontSize: 18 },
  scrollArea: { flex: 1 },
  searchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 14,
    marginHorizontal: 16,
    marginTop: 16,
    marginBottom: 8,
    paddingHorizontal: 14,
    paddingVertical: 12,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.06,
    shadowRadius: 6,
    elevation: 2,
  },
  searchIcon: { fontSize: 16, marginRight: 8, color: '#999' },
  searchInput: { flex: 1, fontSize: 14, color: '#1A1A1A' },
  clearIcon: { fontSize: 14, color: '#999', padding: 4 },
  section: { paddingHorizontal: 16, marginTop: 20 },
  sectionHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
  sectionTitle: { fontSize: 16, fontWeight: '700', color: '#1A1A1A', marginBottom: 12 },
  seeAll: { fontSize: 13, color: '#2D7A4F', fontWeight: '600' },
  storesRow: { flexDirection: 'row', gap: 10 },
  storeChip: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 20,
    paddingHorizontal: 14,
    paddingVertical: 8,
    borderWidth: 1.5,
    borderColor: '#E0E0E0',
    gap: 6,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.05,
    shadowRadius: 3,
    elevation: 1,
  },
  storeEmoji: { fontSize: 14 },
  storeName: { fontSize: 13, fontWeight: '600', color: '#333' },
  dealsScroll: { marginHorizontal: -4 },
  dealCard: {
    width: 150,
    borderRadius: 14,
    padding: 14,
    marginHorizontal: 4,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.06,
    shadowRadius: 6,
    elevation: 2,
  },
  dealStoreBadge: {
    alignSelf: 'flex-start',
    borderRadius: 6,
    paddingHorizontal: 8,
    paddingVertical: 3,
    marginBottom: 6,
  },
  dealStoreText: { fontSize: 10, fontWeight: '700', color: '#fff' },
  dealBadge: {
    backgroundColor: '#FF4444',
    borderRadius: 6,
    paddingHorizontal: 8,
    paddingVertical: 2,
    alignSelf: 'flex-start',
    marginBottom: 6,
  },
  dealBadgeText: { fontSize: 11, fontWeight: '700', color: '#fff' },
  dealEmoji: { fontSize: 28, marginBottom: 6 },
  dealTitle: { fontSize: 13, fontWeight: '600', color: '#1A1A1A', lineHeight: 18 },
  dealDesc: { fontSize: 11, color: '#666', marginTop: 4 },
  budgetCard: {
    backgroundColor: '#FFFFFF',
    borderRadius: 16,
    padding: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.06,
    shadowRadius: 8,
    elevation: 2,
  },
  budgetRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 14,
  },
  budgetLabel: { fontSize: 12, color: '#888', marginBottom: 2 },
  budgetAmount: { fontSize: 18, fontWeight: '700', color: '#1A1A1A' },
  progressBarBg: {
    height: 8,
    backgroundColor: '#E8F5E9',
    borderRadius: 4,
    overflow: 'hidden',
    marginBottom: 8,
  },
  progressBarFill: {
    height: '100%',
    backgroundColor: '#2D7A4F',
    borderRadius: 4,
  },
  budgetSpentText: { fontSize: 12, color: '#888', textAlign: 'right' },
  quickActionsRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  quickActionItem: { alignItems: 'center', flex: 1 },
  quickActionIcon: {
    width: 56,
    height: 56,
    borderRadius: 16,
    backgroundColor: '#FFFFFF',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 6,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.06,
    shadowRadius: 6,
    elevation: 2,
  },
  quickActionEmoji: { fontSize: 22 },
  quickActionLabel: { fontSize: 12, color: '#555', fontWeight: '500' },
  tabBar: {
    flexDirection: 'row',
    backgroundColor: '#FFFFFF',
    borderTopWidth: 1,
    borderTopColor: '#EBEBEB',
    paddingBottom: 20,
    paddingTop: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: -2 },
    shadowOpacity: 0.06,
    shadowRadius: 8,
    elevation: 8,
  },
  tabItem: { flex: 1, alignItems: 'center' },
  tabIcon: { fontSize: 20 },
  tabLabel: { fontSize: 11, color: '#999', marginTop: 2 },
  tabLabelActive: { color: '#2D7A4F', fontWeight: '700' },
});
