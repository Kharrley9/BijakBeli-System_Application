// App.js  (or navigation/AppNavigator.js)
// Install dependencies first:
//   npm install @react-navigation/native @react-navigation/native-stack
//   npm install react-native-screens react-native-safe-area-context

import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import SplashScreen from './screens/SplashScreen';
import LoginScreen from './screens/LoginScreen';
import HomeScreen from './screens/HomeScreen';
import SearchScreen from './screens/SearchScreen';
import PriceComparisonScreen from './screens/PriceComparisonScreen';
import PromotionsScreen from './screens/PromotionsScreen';
import MyListScreen from './screens/MyListScreen';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="Splash"
        screenOptions={{ headerShown: false }}
      >
        <Stack.Screen name="Splash" component={SplashScreen} />
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Search" component={SearchScreen} />
        <Stack.Screen name="PriceComparison" component={PriceComparisonScreen} />
        <Stack.Screen name="Promotions" component={PromotionsScreen} />
        <Stack.Screen name="MyList" component={MyListScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
