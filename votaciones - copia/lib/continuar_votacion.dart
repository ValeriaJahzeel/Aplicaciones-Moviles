import 'package:flutter/material.dart';
import 'package:votaciones/firestore.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class ContinuarVotacion extends StatefulWidget {
  final String votacionId;

  const ContinuarVotacion({super.key, required this.votacionId});

  @override
  State<ContinuarVotacion> createState() => _ContinuarVotacionState();
}

class _ContinuarVotacionState extends State<ContinuarVotacion> {
  final FirestoreService firestoreService = FirestoreService();

  void _cerrarVotacion() async {
    await firestoreService.closeVotacion(widget.votacionId);
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Votación cerrada. Ya no se reciben más respuestas.')),
    );
    Navigator.pop(context);
  }

  void _votarPorCandidato(String candidatoId) {
    firestoreService.incrementarVoto(widget.votacionId, candidatoId);
  }

  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Continuar votación',
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
      body: StreamBuilder<QuerySnapshot>(
        stream: firestoreService.getCandidatosStream(widget.votacionId),
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            List<DocumentSnapshot> candidatosList = snapshot.data!.docs;

            return ListView.builder(
              itemCount: candidatosList.length,
              itemBuilder: (context, index) {
                DocumentSnapshot candidatoDoc = candidatosList[index];
                Map<String, dynamic> candidatoData = candidatoDoc.data() as Map<String, dynamic>;

                String candidato = candidatoData['candidato'];
                String partido = candidatoData['partido'];
                int votos = candidatoData['votos'];

                return Card(
                  child: ListTile(
                    leading: const Icon(Icons.person),
                    title: Text(candidato),
                    subtitle: Text('$partido\nVotos: $votos'),
                    trailing: IconButton(
                      icon: const Icon(Icons.thumb_up),
                      onPressed: () => _votarPorCandidato(candidatoDoc.id),
                    ),
                  ),
                );
              },
            );
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else {
            return const Center(child: CircularProgressIndicator());
          }
        },
      ),
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.all(20.0),
        child: ElevatedButton(
          style: ElevatedButton.styleFrom(
            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
            backgroundColor: Colors.red,
            minimumSize: Size(width, 50),
          ),
          onPressed: _cerrarVotacion,
          child: const Text('Cerrar votación',
              style: TextStyle(
                  fontSize: 18,
                  fontFamily: 'Arvo',
                  color: Colors.black)),
        ),
      ),
    );
  }
}
