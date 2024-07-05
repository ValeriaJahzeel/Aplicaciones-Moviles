import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:votaciones/firestore.dart';

class CrearVotacion extends StatefulWidget {
  const CrearVotacion({super.key});

  @override
  State<CrearVotacion> createState() => _CrearVotacionState();
}

class _CrearVotacionState extends State<CrearVotacion> {
  final FirestoreService firestoreService = FirestoreService();
  final TextEditingController textControllerCandidato = TextEditingController();
  final TextEditingController textControllerPartido = TextEditingController();
  final TextEditingController textControllerVotacion = TextEditingController();

  List<Map<String, String>> candidatosList = [];
  String? votacionId;
  bool votacionActiva = false;

  @override
  void initState() {
    super.initState();
    _checkVotacionActiva();
  }

  Future<void> _checkVotacionActiva() async {
    DocumentSnapshot? activeVotacion = (await firestoreService.getActiveVotacion()) as DocumentSnapshot<Object?>?;
    if (activeVotacion != null) {
      setState(() {
        votacionId = activeVotacion.id;
        votacionActiva = true;
      });
    }
  }

  @override
  void dispose() {
    textControllerCandidato.dispose();
    textControllerPartido.dispose();
    textControllerVotacion.dispose();
    super.dispose();
  }

  Future<void> _iniciarVotacion() async {
    if (textControllerVotacion.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Por favor, ingrese un nombre para la votación'))
      );
      return;
    }

    if (candidatosList.length < 2) {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Debe haber al menos 2 candidatos para iniciar la votación'))
      );
      return;
    }

    DocumentReference votacionRef = await firestoreService.createVotacion(textControllerVotacion.text);
    votacionId = votacionRef.id;

    for (var candidato in candidatosList) {
      await firestoreService.addCandidato(votacionId!, candidato['candidato']!, candidato['partido']!);
    }

    setState(() {
      votacionActiva = true;
    });

    ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Votación iniciada y candidatos agregados a la base de datos'))
    );
  }

  Future<void> _cerrarVotacion() async {
    if (votacionId != null) {
      await firestoreService.closeVotacion(votacionId!);

      setState(() {
        votacionActiva = false;
        votacionId = null;
        candidatosList.clear();
        textControllerVotacion.clear();
      });

      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Votación cerrada'))
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Crear votación',
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
      body: Column(
        children: [
          if (!votacionActiva)
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                controller: textControllerVotacion,
                decoration: const InputDecoration(
                  labelText: 'Nombre de la Votación',
                  floatingLabelBehavior: FloatingLabelBehavior.always,
                  icon: Icon(Icons.how_to_vote),
                ),
              ),
            ),
          Flexible(
            child: ListView.builder(
              itemCount: candidatosList.length,
              itemBuilder: (context, index) {
                String candidato = candidatosList[index]['candidato']!;
                String partido = candidatosList[index]['partido']!;

                return Card(
                  child: ListTile(
                    leading: const Icon(Icons.person),
                    title: Text(candidato),
                    subtitle: Text(partido),
                  ),
                );
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                backgroundColor: votacionActiva ? Colors.red : Colors.lightGreen,
                minimumSize: Size(width, 50),
              ),
              onPressed: votacionActiva ? _cerrarVotacion : _iniciarVotacion,
              child: Text(
                votacionActiva ? 'Cerrar votación' : 'Iniciar votación',
                style: const TextStyle(
                    fontSize: 18,
                    fontFamily: 'Arvo',
                    color: Colors.black),
              ),
            ),
          ),
          if (!votacionActiva)
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                ),
                onPressed: () {
                  // El formulario flotante
                  showDialog(
                      context: context,
                      builder: (BuildContext context) {
                        return AlertDialog(
                          scrollable: true,
                          title: const Text('Añadir candidato',
                              style: TextStyle(
                                  fontFamily: 'ArvoBold',
                                  fontSize: 20,
                                  color: Colors.black)),
                          content: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Form(
                              child: Column(
                                children: <Widget>[
                                  TextFormField(
                                    controller: textControllerCandidato,
                                    decoration: const InputDecoration(
                                      labelText: 'Nombre',
                                      floatingLabelBehavior: FloatingLabelBehavior.always,
                                      icon: Icon(Icons.person),
                                    ),
                                  ),
                                  TextFormField(
                                    controller: textControllerPartido,
                                    decoration: const InputDecoration(
                                      labelText: 'Partido',
                                      floatingLabelBehavior: FloatingLabelBehavior.always,
                                      icon: Icon(Icons.how_to_vote),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          actions: [
                            ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                                backgroundColor: Colors.black,
                                minimumSize: const Size(double.infinity, 36),
                              ),
                              child: const Text("Añadir",
                                  style: TextStyle(
                                      fontSize: 13,
                                      fontFamily: 'Arvo',
                                      color: Colors.white)),
                              onPressed: () {
                                if (textControllerCandidato.text.isNotEmpty && textControllerPartido.text.isNotEmpty) {
                                  setState(() {
                                    candidatosList.add({
                                      'candidato': textControllerCandidato.text,
                                      'partido': textControllerPartido.text,
                                    });
                                  });
                                  textControllerCandidato.clear();
                                  textControllerPartido.clear();
                                  Navigator.pop(context);
                                } else {
                                  // Mostrar un mensaje de error si los campos están vacíos
                                  ScaffoldMessenger.of(context).showSnackBar(
                                      const SnackBar(content: Text('Por favor, rellena todos los campos'))
                                  );
                                }
                              },
                            )
                          ],
                        );
                      });
                },
                child: const Text('+',
                    style: TextStyle(
                        fontSize: 50,
                        fontFamily: 'Arvo',
                        color: Colors.black)),
              ),
            ),
        ],
      ),
    );
  }
}
