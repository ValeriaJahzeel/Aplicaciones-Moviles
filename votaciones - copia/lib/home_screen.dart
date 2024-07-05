import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:votaciones/continuar_votacion.dart';
import 'package:votaciones/mostrar_resultados.dart';
import 'package:votaciones/firestore.dart';

import 'crear_votacion.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final FirestoreService firestoreService = FirestoreService();
  String? activeVotacionId;

  @override
  void initState() {
    super.initState();
    _getActiveVotacion();
  }

  Future<void> _getActiveVotacion() async {
    String? id = await firestoreService.getActiveVotacion();
    setState(() {
      activeVotacionId = id;
    });
  }

  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;
    return Scaffold(
      body: Stack(
          children:[
            const Positioned(
              top: 70,
              left: 20,
              child: Text("Administra tus \nvotaciones",
                  style: TextStyle(
                      fontFamily: 'ArvoBold',
                      fontSize: 40,
                      height: 1,  // interlineado
                      color: Colors.black)),
            ),
            const Positioned(
                top: 200,
                left: 30,
                child: Text("Historial",
                    style: TextStyle(
                        fontFamily: 'ArvoBold',
                        fontSize: 18,
                        color: Colors.black))
            ),
            Positioned(
              top: 230,
              height: 150,
              width: width, // Descomentado para definir el ancho
              child: Padding(
                padding: const EdgeInsets.only(right: 20.0, left: 20.0),
                child: Container(
                  clipBehavior: Clip.none,
                  child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    itemCount: 4, //historialVotaciones.length,
                    itemBuilder: (context, index) {
                      return Card(
                        clipBehavior: Clip.hardEdge,
                        child: InkWell(
                          splashColor: Colors.blue.withAlpha(30),
                          onTap: () {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (_) => PantallaMostrarResultados()
                                ));
                          },
                          child: SizedBox(
                            width: MediaQuery.of(context).size.width * 0.4, // Ancho relativo
                            height: 150,
                            child: const Padding(
                              padding: EdgeInsets.all(8.0),
                              child: Text('Ejemplo',
                                  style: TextStyle(
                                      fontFamily: 'Arvo',
                                      fontSize: 12,
                                      color: Colors.black)),
                            ),
                          ),),);
                    },),),),
            ),
            const Positioned(
                top: 400,
                left: 30,
                child: Text("¿Qué deseas hacer?",
                    style: TextStyle(
                        fontFamily: 'ArvoBold',
                        fontSize: 18,
                        color: Colors.black))
            ),
            Positioned(   // Continuar votacion
                top: 430,
                width: width,
                child: Padding(
                  padding: const EdgeInsets.only(right: 20.0, left: 20.0),
                  child: Card(
                    clipBehavior: Clip.hardEdge,
                    child: InkWell(
                      splashColor: Colors.blue.withAlpha(30),
                      onTap: activeVotacionId == null ? null : () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (_) => ContinuarVotacion(votacionId: activeVotacionId!)
                            ));
                      },
                      child: SizedBox(
                        height: 150,
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Text(activeVotacionId == null ? 'No hay votación activa' : 'Continuar votación actual',
                              style: const TextStyle(
                                  fontFamily: 'Arvo',
                                  fontSize: 30,
                                  color: Colors.black)),
                        ),
                      ),),),
                )),
            Positioned(   // Crear votacion
                top: 600,
                width: width,
                child: Padding(
                  padding: const EdgeInsets.only(right: 20.0, left: 20.0),
                  child: Card(
                    clipBehavior: Clip.hardEdge,
                    child: InkWell(
                      splashColor: Colors.blue.withAlpha(30),
                      onTap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (_) => const CrearVotacion()
                            ));
                      },
                      child: const SizedBox(
                        height: 150,
                        child: Padding(
                          padding: EdgeInsets.all(8.0),
                          child: Text('Crear nueva votación',
                              style: TextStyle(
                                  fontFamily: 'Arvo',
                                  fontSize: 30,
                                  color: Colors.black)),
                        ),
                      ),),),
                ))
          ]),
    );
  }
}
