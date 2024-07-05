import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'firebase_options.dart';
import 'firestore.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(const Votaciones());
}

class Votaciones extends StatelessWidget {
  const Votaciones({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Votaciones',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const VotacionesHome(),
    );
  }
}

class VotacionesHome extends StatefulWidget {
  const VotacionesHome({Key? key}) : super(key: key);

  @override
  _VotacionesHomeState createState() => _VotacionesHomeState();
}

class _VotacionesHomeState extends State<VotacionesHome> {
  final FirestoreService firestoreService = FirestoreService();
  final TextEditingController textControllerCandidato = TextEditingController();
  final TextEditingController textControllerPartido = TextEditingController();
  final TextEditingController textControllerVotacion = TextEditingController();

  List<Map<String, dynamic>> candidatosList = [];

  String? votacionId;
  bool votacionActiva = false;
  List<Map<String, dynamic>> historialVotaciones = [];
  String? selectedCandidatoId;

  @override
  void initState() {
    super.initState();
    _checkVotacionActiva();
    _loadHistorialVotaciones();
  }

  Future<void> _votar(String candidatoId) async {
    if (votacionId != null) {
      await firestoreService.votarPorCandidato(votacionId!, candidatoId);
      setState(() {
        selectedCandidatoId = candidatoId;
      });
    }
  }

  Future<void> _checkVotacionActiva() async {
    DocumentSnapshot? activeVotacion = await firestoreService.getActiveVotacion();
    if (activeVotacion != null && activeVotacion.exists) {
      setState(() {
        votacionId = activeVotacion.id;
        votacionActiva = true;
      });

      List<Map<String, dynamic>> candidatos = await firestoreService.getCandidatos(activeVotacion.id);

      setState(() {
        candidatosList = candidatos.map((candidato) {
          return {
            'id': candidato['id'],
            'candidato': candidato['nombre'],
            'partido': candidato['partido'],
          };
        }).toList();
      });
    }
  }

  Future<void> _loadHistorialVotaciones() async {
    QuerySnapshot snapshot = await firestoreService.getVotaciones();
    setState(() {
      historialVotaciones = snapshot.docs.map((doc) => {'id': doc.id, 'data': doc.data()}).toList();
    });
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
      _loadHistorialVotaciones();
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

  Future<void> _mostrarResultados(String votacionId) async {
    Map<String, dynamic> resultados = await firestoreService.getResultados(votacionId);
    String ganador = resultados['ganador'] ?? 'Sin ganador';
    List<Map<String, dynamic>> data = List<Map<String, dynamic>>.from(resultados['data'] ?? []);

    if (data.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('No hay datos para mostrar'))
      );
      return;
    }

    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Resultados'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              ...data.map((candidato) => Text(
                '${candidato['nombre']}: ${candidato['votos']} votos',
                style: const TextStyle(fontSize: 16),
              )),
              const SizedBox(height: 20),
              Text(
                'Ganador: $ganador',
                style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.red),
              ),
            ],
          ),
          actions: [
            TextButton(
              child: const Text('Cerrar'),
              onPressed: () => Navigator.of(context).pop(),
            ),
          ],
        );
      },
    );
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
          if (historialVotaciones.isNotEmpty)
            Container(
              height: 100,
              child: ListView.builder(
                scrollDirection: Axis.horizontal,
                itemCount: historialVotaciones.length,
                itemBuilder: (context, index) {
                  String titulo = historialVotaciones[index]['data']?['titulo'] ?? '';
                  return GestureDetector(
                    onTap: () {
                      _mostrarResultados(historialVotaciones[index]['id']);
                    },
                    child: Card(
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Center(child: Text(titulo)),
                      ),
                    ),
                  );
                },
              ),
            ),
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
                String candidatoId = candidatosList[index]['id']!;

                return Card(
                  color: selectedCandidatoId == candidatoId ? Colors.lightBlueAccent : Colors.white,
                  child: ListTile(
                    leading: const Icon(Icons.person),
                    title: Text(candidato),
                    subtitle: Text(partido),
                    onTap: () {
                      _votar(candidatoId);
                    },
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
                                      'id': DateTime.now().millisecondsSinceEpoch.toString(),
                                    });
                                  });
                                  textControllerCandidato.clear();
                                  textControllerPartido.clear();
                                  Navigator.pop(context);
                                } else {
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
