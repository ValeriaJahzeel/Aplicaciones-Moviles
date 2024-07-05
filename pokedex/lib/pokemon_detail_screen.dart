import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';

class PokemonDetailScreen extends StatefulWidget {
  final pokemonDetail;
  final Color color;
  final int heroTag;

  const PokemonDetailScreen(
      {super.key,
      this.pokemonDetail,
      required this.color,
      required this.heroTag});

  @override
  State<PokemonDetailScreen> createState() => _PokemonDetailScreenState();
}

class _PokemonDetailScreenState extends State<PokemonDetailScreen> {
  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;
    var height = MediaQuery.of(context).size.height;
    return Scaffold(
      // la pagina de informacion
      backgroundColor: widget.color,
      body: Stack(
        alignment: Alignment.center,
        children: [
          Positioned(
            top: 40,
            left: 10,
            child: IconButton(    // icono de la flecha hacia atras
                icon:
                    const Icon(Icons.arrow_back, color: Colors.white, size: 30),
                onPressed: () {
                  Navigator.pop(context);

                }),
          ),
          Positioned(
              top: 90,
              left: 20,
              child: Text(
                widget.pokemonDetail['name'],
                style: const TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                    fontSize: 30),
              )),
          Positioned(
              top: 140,
              left: 20,
              child: Container(
                decoration: const BoxDecoration(
                    borderRadius: BorderRadius.all(
                      Radius.circular(10),
                    ),
                    color: Colors.black26),
                child: Padding(
                  padding: const EdgeInsets.only(
                      left: 8.0, right: 8.0, top: 4.0, bottom: 4.0),
                  child: Text(
                    widget.pokemonDetail['type'].join(', '),
                    style: const TextStyle(color: Colors.white),
                  ),
                ),
              )),
          Positioned(
              top: height * 0.2,
              right: -30,
              child: Image.asset(
                'images/pokeball.png',
                height: 200,
                fit: BoxFit.fitHeight,
              )),
          Positioned(
            bottom: 0,
            child: Container(
              // tarejeta blanca de informacioón
              width: width,
              height: height * 0.6,
              decoration: const BoxDecoration(
                  borderRadius: BorderRadius.only(
                      topLeft: Radius.circular(20),
                      topRight: Radius.circular(20)),
                  color: Colors.white),
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    const SizedBox(
                      height: 30,
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Container(
                              width: width * 0.3,
                              child: const Text(
                                "Nombre",
                                style: TextStyle(
                                  color: Colors.blueGrey,
                                  fontSize: 18,
                                ),
                              )),
                          Container(
                              child: Text(
                                widget.pokemonDetail['name'],
                                style: const TextStyle(
                                    color: Colors.black,
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold),
                              )),
                        ],),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Container(
                              width: width * 0.3,
                              child: const Text(
                                "Altura",
                                style: TextStyle(
                                  color: Colors.blueGrey,
                                  fontSize: 18,
                                ),
                              )),
                          Container(
                              child: Text(
                                widget.pokemonDetail['height'],
                                style: const TextStyle(
                                    color: Colors.black,
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold),
                              )),
                        ],),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Container(
                              width: width * 0.3,
                              child: const Text(
                                "Peso",
                                style: TextStyle(
                                  color: Colors.blueGrey,
                                  fontSize: 18,
                                ),
                              )),
                          Container(
                              child: Text(
                                widget.pokemonDetail['weight'],
                                style: const TextStyle(
                                    color: Colors.black,
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold),
                              )),
                        ],),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Container(
                              width: width * 0.3,
                              child: const Text(
                                "Debilidades",
                                style: TextStyle(
                                  color: Colors.blueGrey,
                                  fontSize: 18,
                                ),
                              )),
                          Container(
                              child: Text(
                                widget.pokemonDetail['weaknesses'].join(", "),
                                style: const TextStyle(
                                    color: Colors.black,
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold),
                              )),
                        ],),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Container(
                              width: width * 0.3,
                              child: const Text(
                                "Evoluciones",
                                style: TextStyle(
                                  color: Colors.blueGrey,
                                  fontSize: 18,
                                ),
                              )),
                          widget.pokemonDetail['next_evolution'] != null ?
                          SizedBox(
                            height: 20,
                              width: width * 0.55,
                              child: ListView.builder(
                                scrollDirection: Axis.horizontal,
                                itemCount: widget.pokemonDetail['next_evolution'].length,
                                itemBuilder: (context, index){
                                  return Padding(
                                      padding: const EdgeInsets.only(right: 8.0),
                                    child: Text(widget.pokemonDetail['next_evolution'][index]['name'],
                                    style: const TextStyle(
                                      color: Colors.black,
                                      fontSize: 16,
                                      fontWeight: FontWeight.bold
                                    ),),
                                  );
                                },
                              ),
                          ): const Text('Evolución máxima alcanzada',
                            style: TextStyle(
                                color: Colors.black,
                                fontSize: 16,
                                fontWeight: FontWeight.bold
                            ),),
                        ],),
                    )
                  ],
                ),
              ),
            ),
          ),
          Positioned(
              top: (height * 0.10) + 100,
              left: (width / 2) - 100,
              child: CachedNetworkImage(
                imageUrl: widget.pokemonDetail['img'],
                height: 200,
                fit: BoxFit.fitHeight,
              ))
        ],
      ),
    );
  }
}
