import React, { useEffect, useState } from 'react';
import { FlatList, SafeAreaView, StyleSheet, Text, View } from 'react-native';
import xml2js from 'xml2js';

// Definir tipos para los datos
interface Row {
  title: string;
  link: string;
  description: string;
  pubdate: string;
}

const App: React.FC = () => {
  const [data, setData] = useState<Row[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    fetch('https://www.inegi.org.mx/rss/noticias/xmlfeeds?p=4,29')  // Reemplaza con tu URL de API real
      .then((response) => response.text())
      .then((xml) => {
        xml2js.parseString(xml, { explicitArray: false }, (err: Error | null, result: any) => { // Explicitly type the 'result' parameter as 'any'
          if (err) {
            console.error(err);
            return;
          }
          const rows = result.root.row; // Ajusta según la estructura de tu XML
          setData(rows);
          setLoading(false);
        });
      })
      .catch((error) => console.error(error));
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <FlatList
        data={data}
        keyExtractor={(item, index) => index.toString()}
        renderItem={({ item }) => (
          <View style={styles.item}>
            <Text style={styles.title}>{item.title}</Text>
            <Text style={styles.link}>{item.link}</Text>
            <Text style={styles.description}>{item.description}</Text>
            <Text style={styles.pubdate}>{item.pubdate}</Text>
          </View>
        )}
      />
    </SafeAreaView>
)};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    paddingHorizontal: 20,
  },
  loading: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    fontSize: 20,
  },
  item: {
    backgroundColor: '#f9c2ff',
    padding: 20,
    marginVertical: 8,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
  },
  link: {
    fontSize: 16,
    color: 'blue',
  },
  description: {
    fontSize: 16,
  },
  pubdate: {
    fontSize: 12,
    color: 'gray',
  },
});

export default App;
