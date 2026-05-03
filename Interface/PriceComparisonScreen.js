// screens/PriceComparisonScreen.js
import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  StatusBar,
} from 'react-native';

const PRICE_DATA = [
  {
    id: '1',
    store: "Lotus's",
    storeLogo: '🏪',
    price: 28.90,
    originalPrice: 32.00,
    discount: '10% OFF',
    promo: 'Valid today - 30 Apr',
    stock: 16,
    distance: 2.1,
    isBest: false,
    storeColor: '#1A4731',
  },
  {
    id: '2',
    store: 'MYDIN',
    storeLogo: '🛒',
    price: 27.50,
    originalPrice: null,
    discount: 'Murah Sale',
    promo: null,
    stock: 5,
    distance: 5.3,
    isBest: true,
    storeColor: '#D32F2F',
  },
  {
    id: '3',
    store: 'BP Mall',
    storeLogo: '🏬',
    price: 29.00,
    originalPrice: null,
    discount: null,
    promo: 'No Promo',
    stock: 20,
    distance: 3.8,
    isBest: false,
    storeColor: '#1565C0',
  },
];

export default function PriceComparisonScreen({ navigation }) {
  const [saved, setSaved] = useState(false);
  const bestPrice = Math.min(...PRICE_DATA.map((d) => d.price));
  const maxPrice = Math.max(...PRICE_DATA.map((d) => d.price));
  const savings = maxPrice - bestPrice;

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor="#FFFFFF" />

      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation?.goBack()} style={styles.backBtn}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Beras Super 10kg</Text>
        <TouchableOpacity onPress={() => setSaved(!saved)}>
          <Text style={styles.heartIcon}>{saved ? '❤️' : '🤍'}</Text>
        </TouchableOpacity>
      </View>

      <ScrollView showsVerticalScrollIndicator={false}>
        {/* Product Card */}
        <View style={styles.productCard}>
          <View style={styles.productImageBox}>
            <Text style={styles.productEmoji}>🍚</Text>
          </View>
          <View style={styles.productInfo}>
            <Text style={styles.productName}>Beras Super 10kg</Text>
            <Text style={styles.productDesc}>Local White Rice</Text>
          </View>
        </View>

        {/* Price Comparison Title */}
        <View style={styles.sectionHeader}>
          <Text style={styles.sectionTitle}>Price Comparison</Text>
        </View>

        {/* Price Cards */}
        {PRICE_DATA.map((item) => (
          <View
            key={item.id}
            style={[styles.priceCard, item.isBest && styles.priceCardBest]}
          >
            {item.isBest && (
              <View style={styles.bestPriceBanner}>
                <Text style={styles.bestPriceBannerText}>⭐ BEST PRICE</Text>
              </View>
            )}
            <View style={styles.priceCardTop}>
              {/* Store Info */}
              <View style={styles.storeInfo}>
                <View style={[styles.storeIconBox, { backgroundColor: item.storeColor + '18' }]}>
                  <Text style={styles.storeEmoji}>{item.storeLogo}</Text>
                </View>
                <View>
                  <Text style={[styles.storeName, { color: item.storeColor }]}>{item.store}</Text>
                  <Text style={styles.storeDistance}>📍 {item.distance} km</Text>
                </View>
              </View>

              {/* Price */}
              <View style={styles.priceCol}>
                <Text style={[styles.priceMain, item.isBest && styles.priceMainBest]}>
                  RM{item.price.toFixed(2)}
                </Text>
                {item.originalPrice && (
                  <Text style={styles.priceOriginal}>RM{item.originalPrice.toFixed(2)}</Text>
                )}
              </View>
            </View>

            {/* Promo / Stock Row */}
            <View style={styles.priceCardBottom}>
              <View style={styles.stockInfo}>
                <Text style={styles.stockText}>Stock: {item.stock}</Text>
              </View>
              {item.discount ? (
                <View style={[styles.promoBadge, { backgroundColor: item.isBest ? '#E8F5E9' : '#FFF3E0' }]}>
                  <Text style={[styles.promoBadgeText, { color: item.isBest ? '#2D7A4F' : '#E65100' }]}>
                    {item.discount}
                  </Text>
                </View>
              ) : (
                <Text style={styles.noPromoText}>{item.promo}</Text>
              )}
            </View>

            {/* Promo validity */}
            {item.promo && item.promo !== 'No Promo' && (
              <Text style={styles.promoValidity}>{item.promo}</Text>
            )}
          </View>
        ))}

        {/* Savings Summary */}
        <View style={styles.savingsCard}>
          <Text style={styles.savingsTitle}>💡 BEST PRICE at Mydin</Text>
          <Text style={styles.savingsAmount}>RM{bestPrice.toFixed(2)}</Text>
          <Text style={styles.savingsDesc}>
            You save <Text style={styles.savingsHighlight}>RM{savings.toFixed(2)}</Text> vs most expensive
          </Text>
        </View>

        {/* Action Buttons */}
        <View style={styles.actionRow}>
          <TouchableOpacity
            style={styles.addToListBtn}
            onPress={() => navigation?.navigate('MyList')}
          >
            <Text style={styles.addToListText}>+ Add to List</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.setAlertBtn}>
            <Text style={styles.setAlertText}>🔔 Set Alert</Text>
          </TouchableOpacity>
        </View>

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
  headerTitle: { flex: 1, textAlign: 'center', fontSize: 16, fontWeight: '700', color: '#1A1A1A' },
  heartIcon: { fontSize: 22 },
  productCard: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    padding: 16,
    marginHorizontal: 16,
    marginTop: 16,
    borderRadius: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.05,
    shadowRadius: 6,
    elevation: 2,
  },
  productImageBox: {
    width: 70,
    height: 70,
    borderRadius: 14,
    backgroundColor: '#F0F7F2',
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 14,
  },
  productEmoji: { fontSize: 38 },
  productInfo: { flex: 1 },
  productName: { fontSize: 17, fontWeight: '700', color: '#1A1A1A', marginBottom: 4 },
  productDesc: { fontSize: 13, color: '#888' },
  sectionHeader: { paddingHorizontal: 16, marginTop: 20, marginBottom: 10 },
  sectionTitle: { fontSize: 16, fontWeight: '700', color: '#1A1A1A' },
  priceCard: {
    backgroundColor: '#FFFFFF',
    borderRadius: 16,
    marginHorizontal: 16,
    marginBottom: 10,
    padding: 14,
    borderWidth: 1.5,
    borderColor: '#E8E8E8',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.04,
    shadowRadius: 6,
    elevation: 2,
    overflow: 'hidden',
  },
  priceCardBest: {
    borderColor: '#2D7A4F',
    borderWidth: 2,
  },
  bestPriceBanner: {
    backgroundColor: '#E8F5E9',
    paddingHorizontal: 10,
    paddingVertical: 4,
    marginHorizontal: -14,
    marginTop: -14,
    marginBottom: 12,
  },
  bestPriceBannerText: { fontSize: 12, fontWeight: '700', color: '#2D7A4F', textAlign: 'center' },
  priceCardTop: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 10,
  },
  storeInfo: { flexDirection: 'row', alignItems: 'center', gap: 10 },
  storeIconBox: {
    width: 40,
    height: 40,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  storeEmoji: { fontSize: 20 },
  storeName: { fontSize: 15, fontWeight: '700' },
  storeDistance: { fontSize: 12, color: '#888', marginTop: 2 },
  priceCol: { alignItems: 'flex-end' },
  priceMain: { fontSize: 20, fontWeight: '800', color: '#1A1A1A' },
  priceMainBest: { color: '#2D7A4F' },
  priceOriginal: {
    fontSize: 13,
    color: '#AAA',
    textDecorationLine: 'line-through',
  },
  priceCardBottom: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  stockInfo: {},
  stockText: { fontSize: 12, color: '#888' },
  promoBadge: {
    borderRadius: 8,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  promoBadgeText: { fontSize: 12, fontWeight: '700' },
  noPromoText: { fontSize: 12, color: '#AAA' },
  promoValidity: { fontSize: 11, color: '#999', marginTop: 6 },
  savingsCard: {
    backgroundColor: '#1A4731',
    borderRadius: 16,
    marginHorizontal: 16,
    marginTop: 10,
    padding: 16,
    alignItems: 'center',
  },
  savingsTitle: { fontSize: 13, fontWeight: '700', color: '#A5D6A7', marginBottom: 4 },
  savingsAmount: { fontSize: 28, fontWeight: '800', color: '#FFFFFF', marginBottom: 4 },
  savingsDesc: { fontSize: 13, color: 'rgba(255,255,255,0.75)' },
  savingsHighlight: { color: '#F5C842', fontWeight: '700' },
  actionRow: {
    flexDirection: 'row',
    paddingHorizontal: 16,
    marginTop: 16,
    gap: 12,
  },
  addToListBtn: {
    flex: 1,
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
  addToListText: { fontSize: 15, fontWeight: '700', color: '#FFFFFF' },
  setAlertBtn: {
    flex: 1,
    backgroundColor: '#FFFFFF',
    borderRadius: 14,
    paddingVertical: 16,
    alignItems: 'center',
    borderWidth: 1.5,
    borderColor: '#1A4731',
  },
  setAlertText: { fontSize: 15, fontWeight: '700', color: '#1A4731' },
});
