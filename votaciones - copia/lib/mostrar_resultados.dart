import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class PantallaMostrarResultados extends StatefulWidget {
  const PantallaMostrarResultados({super.key});

  @override
  State<PantallaMostrarResultados> createState() => _PantallaMostrarResultadosState();
}

class _PantallaMostrarResultadosState extends State<PantallaMostrarResultados> {
  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;
    var height = MediaQuery.of(context).size.height;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Historial de votaciones',
            style: TextStyle(
                fontFamily: 'Arvo',
                fontSize: 20,
                color: Colors.black)),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, size: 30),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
      body: Stack(
        children: [

        ],
      ),
    );

  }
}
