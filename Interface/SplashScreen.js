// screens/SplashScreen.js
import React, { useEffect, useRef } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Animated,
  Dimensions,
  StatusBar,
} from 'react-native';

const { width, height } = Dimensions.get('window');

export default function SplashScreen({ navigation }) {
  const fadeAnim = useRef(new Animated.Value(0)).current;
  const slideAnim = useRef(new Animated.Value(30)).current;
  const dotAnim1 = useRef(new Animated.Value(0.3)).current;
  const dotAnim2 = useRef(new Animated.Value(0.3)).current;
  const dotAnim3 = useRef(new Animated.Value(0.3)).current;

  useEffect(() => {
    // Fade in logo and tagline
    Animated.parallel([
      Animated.timing(fadeAnim, {
        toValue: 1,
        duration: 800,
        useNativeDriver: true,
      }),
      Animated.timing(slideAnim, {
        toValue: 0,
        duration: 800,
        useNativeDriver: true,
      }),
    ]).start();

    // Loading dots animation
    const animateDots = () => {
      Animated.sequence([
        Animated.timing(dotAnim1, { toValue: 1, duration: 300, useNativeDriver: true }),
        Animated.timing(dotAnim2, { toValue: 1, duration: 300, useNativeDriver: true }),
        Animated.timing(dotAnim3, { toValue: 1, duration: 300, useNativeDriver: true }),
        Animated.delay(200),
        Animated.parallel([
          Animated.timing(dotAnim1, { toValue: 0.3, duration: 200, useNativeDriver: true }),
          Animated.timing(dotAnim2, { toValue: 0.3, duration: 200, useNativeDriver: true }),
          Animated.timing(dotAnim3, { toValue: 0.3, duration: 200, useNativeDriver: true }),
        ]),
      ]).start(() => animateDots());
    };
    animateDots();

    // Navigate after 3 seconds
    const timer = setTimeout(() => {
      navigation?.navigate('Login');
    }, 3000);

    return () => clearTimeout(timer);
  }, []);

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#1A4731" />

      {/* Background pattern dots */}
      <View style={styles.bgPattern}>
        {[...Array(20)].map((_, i) => (
          <View key={i} style={[styles.bgDot, { opacity: 0.06 }]} />
        ))}
      </View>

      {/* Cart Icon */}
      <Animated.View
        style={[
          styles.logoContainer,
          { opacity: fadeAnim, transform: [{ translateY: slideAnim }] },
        ]}
      >
        <View style={styles.cartIcon}>
          {/* Cart SVG-style using Views */}
          <View style={styles.cartBody}>
            <View style={styles.cartHandle} />
            <View style={styles.cartBasket} />
            <View style={styles.cartWheelRow}>
              <View style={styles.cartWheel} />
              <View style={styles.cartWheel} />
            </View>
          </View>
        </View>

        <Text style={styles.brandName}>BijakBeli</Text>
        <Text style={styles.tagline}>Smart Grocery</Text>
        <Text style={styles.tagline2}>Budget Comparison</Text>
      </Animated.View>

      {/* Loading Indicator */}
      <View style={styles.loadingContainer}>
        <Animated.View style={[styles.loadingDot, { opacity: dotAnim1 }]} />
        <Animated.View style={[styles.loadingDot, { opacity: dotAnim2 }]} />
        <Animated.View style={[styles.loadingDot, { opacity: dotAnim3 }]} />
        <Text style={styles.loadingText}> Loading...</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#1A4731',
    alignItems: 'center',
    justifyContent: 'center',
  },
  bgPattern: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    flexDirection: 'row',
    flexWrap: 'wrap',
    padding: 20,
  },
  bgDot: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: '#fff',
    margin: 10,
  },
  logoContainer: {
    alignItems: 'center',
    marginBottom: 80,
  },
  cartIcon: {
    width: 80,
    height: 80,
    marginBottom: 20,
    alignItems: 'center',
    justifyContent: 'center',
  },
  cartBody: {
    alignItems: 'center',
  },
  cartHandle: {
    width: 50,
    height: 20,
    borderWidth: 4,
    borderColor: '#F5C842',
    borderBottomWidth: 0,
    borderTopLeftRadius: 12,
    borderTopRightRadius: 12,
    marginBottom: 0,
  },
  cartBasket: {
    width: 60,
    height: 30,
    backgroundColor: '#F5C842',
    borderRadius: 6,
  },
  cartWheelRow: {
    flexDirection: 'row',
    marginTop: 6,
    gap: 20,
  },
  cartWheel: {
    width: 12,
    height: 12,
    borderRadius: 6,
    backgroundColor: '#F5C842',
  },
  brandName: {
    fontSize: 36,
    fontWeight: '800',
    color: '#FFFFFF',
    letterSpacing: 1,
    marginTop: 8,
  },
  tagline: {
    fontSize: 16,
    color: 'rgba(255,255,255,0.75)',
    fontWeight: '400',
    marginTop: 4,
  },
  tagline2: {
    fontSize: 16,
    color: 'rgba(255,255,255,0.75)',
    fontWeight: '400',
  },
  loadingContainer: {
    position: 'absolute',
    bottom: 60,
    flexDirection: 'row',
    alignItems: 'center',
  },
  loadingDot: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#F5C842',
    marginHorizontal: 3,
  },
  loadingText: {
    color: 'rgba(255,255,255,0.6)',
    fontSize: 13,
    marginLeft: 4,
  },
});
