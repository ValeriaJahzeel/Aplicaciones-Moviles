import React,{FC} from "react";
import { View, Text } from "react-native";

interface TareaProps {
     id: number;
     descripcion: string;
     estaHecha: boolean;
     esHoy: boolean;
     hora: string;
}

const Tareas = ({descripcion, estaHecha, esHoy, hora}: TareaProps) => {
     return (
          <View>
          <Text>{descripcion}</Text>
          </View>
     );
}

export default Tareas;