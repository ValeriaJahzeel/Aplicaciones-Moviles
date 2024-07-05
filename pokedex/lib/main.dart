import 'package:flutter/material.dart';
import 'package:pokedex/home_screen.dart';

void main(){
  runApp(Pokedex());
}

class Pokedex extends StatelessWidget {
  const Pokedex({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: HomeScreen(),
    );
  }
}
