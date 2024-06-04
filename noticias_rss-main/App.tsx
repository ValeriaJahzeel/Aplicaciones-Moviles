import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import NewsList from './src/NewsList';

const App: React.FC = () => {
    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.headerText}>Noticias RSS/INEGI</Text>
            </View>
            <View style={styles.main}>
                <NewsList />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    header: {
        padding: 20,
        backgroundColor: '#f0f0f0',
    },
    headerText: {
        fontSize: 24,
        fontWeight: 'bold',
    },
    main: {
        flex: 1,
        padding: 20,
    },
});

export default App;

