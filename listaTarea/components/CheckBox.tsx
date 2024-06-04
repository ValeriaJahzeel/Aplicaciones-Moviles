import React, {FC} from "react";
import { TouchableOpacity, StyleSheet, View } from "react-native";  
import {Entypo} from '@expo/vector-icons';

interface CheckBoxProps {
     id: number;
     descripcion: string;
     estaHecha: boolean;
     esHoy: boolean;
     hora: string;
}


const CheckBox: FC<CheckBoxProps> = ({
     id,
     descripcion,
     estaHecha,
     esHoy,
     hora,
}) => {
     return esHoy ? (
          <TouchableOpacity style={estaHecha ? styles.cheked : styles.unchecked}>
               {estaHecha ? <Entypo name="check" size={16} color="FAFAFA" /> : null}
          </TouchableOpacity>
     ) : (
          <View style={styles.esHoy} />
     );
};

const CheckBox: FC<CheckBoxProps> = ({ })

const styles = StyleSheet.create({
     cheked: {
          width: 20,
          height: 20,
          margin: right
          borderRadius: 8,
          backgroundColor: '#FF0000',
          justifyContent: 'center',
          
          alignItems: 'center',
     },   
     }
