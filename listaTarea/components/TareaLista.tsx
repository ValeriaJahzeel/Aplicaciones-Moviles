import * as React from 'react';
import { FlatList, Text } from 'react-native';
import { tareaData } from '../datos/tareas'
import Tareas from './Tarea';

export default function TareaLista() {
     return (
          <FlatList
               data={tareaData}
               renderItem={({ item }) => <Tareas {...item} />}
               keyExtractor={(item) => item.id.toString()}
          />
     );
}