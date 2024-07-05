import 'package:cloud_firestore/cloud_firestore.dart';

class FirestoreService {
  final FirebaseFirestore _db = FirebaseFirestore.instance;

  Future<void> votarPorCandidato(String votacionId, String candidatoId) async {
    DocumentReference candidatoRef = _db.collection('votaciones').doc(votacionId).collection('candidatos').doc(candidatoId);
    return _db.runTransaction((transaction) async {
      DocumentSnapshot candidatoSnapshot = await transaction.get(candidatoRef);
      if (!candidatoSnapshot.exists) {
        throw Exception("Candidato no encontrado");
      }

      int nuevosVotos = (candidatoSnapshot['votos'] ?? 0) + 1;
      transaction.update(candidatoRef, {'votos': nuevosVotos});
    });
  }

  Future<DocumentSnapshot?> getActiveVotacion() async {
    QuerySnapshot snapshot = await _db.collection('votaciones').where('activa', isEqualTo: true).limit(1).get();
    if (snapshot.docs.isNotEmpty) {
      return snapshot.docs.first;
    }
    return null;
  }

  Future<QuerySnapshot> getVotaciones() async {
    return _db.collection('votaciones').get();
  }

  Future<DocumentReference> createVotacion(String titulo) {
    return _db.collection('votaciones').add({
      'titulo': titulo,
      'activa': true,
    });
  }

  Future<void> addCandidato(String votacionId, String nombre, String partido) {
    return _db.collection('votaciones').doc(votacionId).collection('candidatos').add({
      'nombre': nombre,
      'partido': partido,
      'votos': 0,
    });
  }

  Future<void> closeVotacion(String votacionId) {
    return _db.collection('votaciones').doc(votacionId).update({
      'activa': false,
    });
  }

  Future<Map<String, dynamic>> getResultados(String votacionId) async {
    QuerySnapshot snapshot = await _db.collection('votaciones').doc(votacionId).collection('candidatos').get();
    List<Map<String, dynamic>> data = snapshot.docs.map((doc) => {
      'nombre': doc['nombre'],
      'partido': doc['partido'],
      'votos': doc['votos'],
    }).toList();

    data.sort((a, b) => b['votos'].compareTo(a['votos']));
    String ganador = data.isNotEmpty ? data.first['nombre'] : 'Sin ganador';

    return {
      'ganador': ganador,
      'data': data,
    };
  }

  Future<List<Map<String, dynamic>>> getCandidatos(String votacionId) async {
    QuerySnapshot candidatosSnapshot = await _db.collection('votaciones')
        .doc(votacionId)
        .collection('candidatos')
        .get();

    return candidatosSnapshot.docs.map((doc) =>
    {
      'id': doc.id,
      'nombre': doc['nombre'],
      'partido': doc['partido'],
      'votos': doc['votos'],
    }).toList();
  }
}
