// screens/PromotionsScreen.js
import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  StatusBar,
} from 'react-native';

const STORE_FILTERS = ['All', "Lotus's", 'Mydin', 'BP Mall'];

const PROMOTIONS = [
  {
    id: '1',
    store: "Lotus's",
    storeColor: '#1A4731',
    title: "Lotus's",
    badge: '10% OFF',
    badgeColor: '#E8F5E9',
    badgeTextColor: '#1A4731',
    description: 'Fresh Items',
    validUntil: 'Valid till 29 Apr 2025',
    emoji: '🥦',
    bg: '#F0F7F2',
  },
  {
    id: '2',
    store: 'Mydin',
    storeColor: '#D32F2F',
    title: 'MYDIN',
    badge: 'MURAH SALE',
    badgeColor: '#FFEBEE',
    badgeTextColor: '#D32F2F',
    description: 'Up to 30% OFF',
    validUntil: 'Valid till 27 Apr 2025',
    emoji: '🍚',
    bg: '#FFF5F5',
  },
  {
    id: '3',
    store: 'BP Mall',
    storeColor: '#1565C0',
    title: 'BP Mall',
    badge: 'Weekend Special',
    badgeColor: '#E3F2FD',
    badgeTextColor: '#1565C0',
    description: 'Buy 2 Save More',
    validUntil: 'Valid till 28 Apr 2025',
    emoji: '🥕',
    bg: '#F0F4FF',
  },
];

export default function PromotionsScreen({ navigation }) {
  const [activeFilter, setActiveFilter] = useState('All');

  const filtered =
    activeFilter === 'All'
      ? PROMOTIONS
      : PROMOTIONS.filter((p) => p.store === activeFilter);

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor="#FFFFFF" />

      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation?.goBack()} style={styles.backBtn}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Weekly Promotions</Text>
        <TouchableOpacity style={styles.notifBtn}>
          <Text style={styles.notifIcon}>🔔</Text>
        </TouchableOpacity>
      </View>

      {/* Store Filter Tabs */}
      <View style={styles.filterRow}>
        <ScrollView horizontal showsHorizontalScrollIndicator={false}>
          <View style={styles.filterTabs}>
            {STORE_FILTERS.map((filter) => (
              <TouchableOpacity
                key={filter}
                style={[
                  styles.filterTab,
                  activeFilter === filter && styles.filterTabActive,
                ]}
                onPress={() => setActiveFilter(filter)}
              >
                <Text
                  style={[
                    styles.filterTabText,
                    activeFilter === filter && styles.filterTabTextActive,
                  ]}
                >
                  {filter}
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </ScrollView>
      </View>

      <ScrollView style={styles.scrollArea} showsVerticalScrollIndicator={false}>
        {filtered.map((promo) => (
          <TouchableOpacity key={promo.id} style={[styles.promoCard, { backgroundColor: promo.bg }]}>
            {/* Card Header */}
            <View style={styles.promoCardHeader}>
              <View style={[styles.storeTag, { backgroundColor: promo.storeColor }]}>
                <Text style={styles.storeTagText}>{promo.title}</Text>
              </View>
              <View style={[styles.promoBadge, { backgroundColor: promo.badgeColor }]}>
                <Text style={[styles.promoBadgeText, { color: promo.badgeTextColor }]}>
                  {promo.badge}
                </Text>
              </View>
            </View>

            {/* Card Body */}
            <View style={styles.promoCardBody}>
              <View style={styles.promoEmojiBox}>
                <Text style={styles.promoEmoji}>{promo.emoji}</Text>
              </View>
              <View style={styles.promoTextArea}>
                <Text style={styles.promoDescription}>{promo.description}</Text>
                <View style={styles.validRow}>
                  <Text style={styles.calendarIcon}>📅</Text>
                  <Text style={styles.validText}>{promo.validUntil}</Text>
                </View>
              </View>
            </View>
          </TouchableOpacity>
        ))}

        {/* View All Button */}
        <TouchableOpacity style={styles.viewAllButton}>
          <Text style={styles.viewAllText}>View All Promotions</Text>
        </TouchableOpacity>

        <View style={{ height: 40 }} />
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
            <Text style={styles.tabLabel}>{tab.label}</Text>
          </TouchableOpacity>
        ))}
      </View>
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
  notifBtn: { padding: 4 },
  notifIcon: { fontSize: 22 },
  filterRow: {
    backgroundColor: '#FFFFFF',
    paddingVertical: 10,
    paddingHorizontal: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#F0F0F0',
  },
  filterTabs: { flexDirection: 'row', gap: 8 },
  filterTab: {
    paddingHorizontal: 18,
    paddingVertical: 8,
    borderRadius: 20,
    backgroundColor: '#F0F0F0',
  },
  filterTabActive: {
    backgroundColor: '#1A4731',
  },
  filterTabText: { fontSize: 13, fontWeight: '600', color: '#666' },
  filterTabTextActive: { color: '#FFFFFF' },
  scrollArea: { flex: 1, paddingHorizontal: 16, paddingTop: 16 },
  promoCard: {
    borderRadius: 18,
    padding: 16,
    marginBottom: 14,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.06,
    shadowRadius: 8,
    elevation: 2,
  },
  promoCardHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 12,
  },
  storeTag: {
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 5,
  },
  storeTagText: { fontSize: 13, fontWeight: '700', color: '#FFFFFF' },
  promoBadge: {
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 5,
  },
  promoBadgeText: { fontSize: 12, fontWeight: '700' },
  promoCardBody: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 14,
  },
  promoEmojiBox: {
    width: 72,
    height: 72,
    borderRadius: 16,
    backgroundColor: 'rgba(255,255,255,0.7)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  promoEmoji: { fontSize: 40 },
  promoTextArea: { flex: 1 },
  promoDescription: { fontSize: 16, fontWeight: '700', color: '#1A1A1A', marginBottom: 8 },
  validRow: { flexDirection: 'row', alignItems: 'center', gap: 5 },
  calendarIcon: { fontSize: 13 },
  validText: { fontSize: 12, color: '#666' },
  viewAllButton: {
    backgroundColor: '#1A4731',
    borderRadius: 14,
    paddingVertical: 16,
    marginHorizontal: 0,
    marginTop: 6,
    marginBottom: 10,
    alignItems: 'center',
    shadowColor: '#1A4731',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.25,
    shadowRadius: 8,
    elevation: 4,
  },
  viewAllText: { fontSize: 15, fontWeight: '700', color: '#FFFFFF' },
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
});
