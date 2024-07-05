import 'package:cloud_firestore/cloud_firestore.dart';

class FirestoreService {
  final FirebaseFirestore _db = FirebaseFirestore.instance;

  // Crear una nueva votación
  Future<DocumentReference> createVotacion(String nombre) async {
    // Desactivar todas las votaciones activas actuales
    QuerySnapshot activeVotations = await _db.collection('votaciones').where('activa', isEqualTo: true).get();
    for (var doc in activeVotations.docs) {
      await doc.reference.update({'activa': false});
    }

    // Crear una nueva votación y marcarla como activa
    return _db.collection('votaciones').add({'nombre': nombre, 'activa': true});
  }

  // Cerrar una votación
  Future<void> closeVotacion(String votacionId) {
    return _db.collection('votaciones').doc(votacionId).update({'activa': false});
  }

  // Obtener la votación activa
  Future<String?> getActiveVotacion() async {
    QuerySnapshot querySnapshot = await _db.collection('votaciones').where('activa', isEqualTo: true).limit(1).get();
    if (querySnapshot.docs.isNotEmpty) {
      return querySnapshot.docs.first.id;
    }
    return null;
  }

  // Agregar un candidato a una votación
  Future<void> addCandidato(String votacionId, String candidato, String partido) {
    return _db.collection('votaciones').doc(votacionId).collection('candidatos').add({
      'candidato': candidato,
      'partido': partido,
      'votos': 0
    });
  }

  // Obtener los candidatos de una votación específica
  Stream<QuerySnapshot> getCandidatosStream(String votacionId) {
    return _db.collection('votaciones').doc(votacionId).collection('candidatos').snapshots();
  }

  // Incrementar el voto de un candidato
  Future<void> incrementarVoto(String votacionId, String candidatoId) {
    DocumentReference candidatoRef = _db.collection('votaciones').doc(votacionId).collection('candidatos').doc(candidatoId);
    return _db.runTransaction((transaction) async {
      DocumentSnapshot snapshot = await transaction.get(candidatoRef);
      if (!snapshot.exists) {
        throw Exception("Candidato no existe!");
      }
      int nuevosVotos = (snapshot.data() as Map<String, dynamic>)['votos'] + 1;
      transaction.update(candidatoRef, {'votos': nuevosVotos});
    });
  }
}
