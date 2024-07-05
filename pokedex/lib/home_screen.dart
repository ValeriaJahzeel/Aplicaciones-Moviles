import 'dart:convert';

import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart' as http;
import 'package:pokedex/pokemon_detail_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  var pokeApi =
      "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";
  late List pokedex = [];
  List<String> pokemonSeleccionado = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    if (mounted) {
      fetchPokemonData();
    }
  }

  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;
    var height = MediaQuery.of(context).size.height;
    return Scaffold(
      body: Stack(
        children: [
          Positioned(
            // imagen de la top bar
            top: -15,
            right: -50,
            child: Image.asset('images/pokeball.png',
                width: 200, fit: BoxFit.fitWidth),
          ),
          const Positioned(
              // texto de la top bar
              top: 80,
              left: 20,
              child: Text("Pokedex",
                  style: TextStyle(
                      fontSize: 30,
                      fontWeight: FontWeight.bold,
                      color: Colors.black))),
          /*const Positioned(
          // texto de la botton bar
            bottom: 80,
            left: 20,
            child: Text("Selecciona a tu equipo: ",
                style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.black))),*/
          Positioned(
            // toda la sección de contenido
            top: 130, // que tanto se baja
            bottom: 0, // que tanto se sube
            width: width,
            child: Column(
              children: [
                pokedex != null
                    ? Expanded(
                        child: GridView.builder(
                          gridDelegate:
                              const SliverGridDelegateWithFixedCrossAxisCount(
                            crossAxisCount: 2, // cantidad de columnas
                            childAspectRatio: 1.3, // alto de la imagen
                          ),
                          itemCount: pokedex.length,
                          itemBuilder: (context, index) {
                            var type = pokedex[index]['type'][0];
                            return InkWell(
                              child: Padding(
                                  padding: const EdgeInsets.symmetric(
                                      // padding de las cards
                                      vertical: 8.0,
                                      horizontal: 8),
                                  child: Container(
                                    decoration: BoxDecoration(
                                      color: type == 'Grass'
                                          ? Colors.greenAccent
                                          : type == 'Fire'
                                              ? Colors.red
                                              : type == 'Water'
                                                  ? Colors.blue
                                                  : type == 'Electric'
                                                      ? Colors.yellowAccent
                                                      : type == 'Rock'
                                                          ? Colors.grey
                                                          : type == 'Ground'
                                                              ? Colors.brown
                                                              : type ==
                                                                      'Psychic'
                                                                  ? Colors
                                                                      .indigo
                                                                  : type ==
                                                                          'Fighting'
                                                                      ? Colors
                                                                          .orange
                                                                      : type ==
                                                                              'Bug'
                                                                          ? Colors
                                                                              .lightGreen
                                                                          : type == 'Ghost'
                                                                              ? Colors.deepPurple
                                                                              : type == 'Normal'
                                                                                  ? Colors.black26
                                                                                  : type == 'Poison'
                                                                                      ? Colors.deepPurpleAccent
                                                                                      : Colors.pink,
                                      borderRadius:
                                          BorderRadius.all(Radius.circular(20)),
                                    ),
                                    child: Stack(children: [
                                      Positioned(
                                          // Para la imaen de adentro de las card
                                          bottom: -10,
                                          right: -10,
                                          child: Image.asset(
                                            'images/pokeball.png',
                                            height: 100,
                                            fit: BoxFit.fitHeight,
                                          )),
                                      Positioned(
                                        // nombre del pokemon
                                        top: 20,
                                        left: 10,
                                        child: Text(
                                          pokedex[index]['name'],
                                          style: const TextStyle(
                                            fontWeight: FontWeight.bold,
                                            fontSize: 18,
                                            color: Colors.white,
                                          ),
                                        ),
                                      ),
                                      Positioned(
                                        // tipo del pokemon (wagua, insecto, fuego, pasto, etc..)
                                        top: 50,
                                        left: 20,
                                        child: Container(
                                            decoration: const BoxDecoration(
                                              borderRadius: BorderRadius.all(
                                                  Radius.circular(20)),
                                              color: Colors.black26,
                                            ),
                                            child: Padding(
                                              padding: const EdgeInsets.only(
                                                  // el tipo, pero su cajita
                                                  left: 8.0,
                                                  right: 8.0,
                                                  top: 4,
                                                  bottom: 4),
                                              child: Text(
                                                type.toString(),
                                                style: const TextStyle(
                                                    color: Colors.white),
                                              ),
                                            )),
                                      ),
                                      Positioned(
                                          // imagen dentro de la card
                                          bottom: 5,
                                          right: 5,
                                          child: CachedNetworkImage(
                                            imageUrl: pokedex[index]['img'],
                                            height: 100,
                                            fit: BoxFit.fitHeight,
                                          )),
                                    ]),
                                  )),
                              onLongPress: (){  // agregar el pokemon seleccionado
                                onTapPokemon(index,context);
                              },
                              onTap: () {
                                //TODO Navigate to new detail screen
                                Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (_) => PokemonDetailScreen(
                                              pokemonDetail: pokedex[index],
                                              color: type == 'Grass'
                                                  ? Colors.greenAccent
                                                  : type == 'Fire'
                                                      ? Colors.red
                                                      : type == 'Water'
                                                          ? Colors.blue
                                                          : type == 'Electric'
                                                              ? Colors
                                                                  .yellowAccent
                                                              : type == 'Rock'
                                                                  ? Colors.grey
                                                                  : type ==
                                                                          'Ground'
                                                                      ? Colors
                                                                          .brown
                                                                      : type ==
                                                                              'Psychic'
                                                                          ? Colors
                                                                              .indigo
                                                                          : type == 'Fighting'
                                                                              ? Colors.orange
                                                                              : type == 'Bug'
                                                                                  ? Colors.lightGreen
                                                                                  : type == 'Ghost'
                                                                                      ? Colors.deepPurple
                                                                                      : type == 'Normal'
                                                                                          ? Colors.black26
                                                                                          : type == 'Poison'
                                                                                              ? Colors.deepPurpleAccent
                                                                                              : Colors.pink,
                                              heroTag: index,
                                            )));
                              },
                            );
                          },
                        ),
                      )
                    : const Center(
                        child: CircularProgressIndicator(),
                      )
              ],
            ),
          ),
        ],
      ),
      extendBody: true,
      bottomNavigationBar: Stack(
        alignment: Alignment.bottomLeft,
        children: [
          Positioned(
            bottom: 0,
            height: 150,
            width: width,
            child: Container(
              alignment: Alignment.bottomLeft,
              decoration: const BoxDecoration(
                borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
                color: Colors.white,
              ),
              child: ListView.builder(
                scrollDirection: Axis.horizontal,
                itemCount: pokemonSeleccionado.length,
                itemBuilder: (context, index) {
                  return SizedBox(
                    //height: 80,
                    width: 135,
                    child: Dismissible(
                      key: Key(pokemonSeleccionado[index]),
                      onDismissed: (direction) {
                        setState(() {
                          pokemonSeleccionado.removeAt(index);
                        });
                      },
                      child: CachedNetworkImage(
                        imageUrl: pokemonSeleccionado[index],
                        //height: 70,
                      ),
                    ),
                  );
                },
              ),
            ),
          ),
          const Positioned(
            bottom: 120,
            left: 20,
            child: Text(
              "Selecciona tu equipo: ",
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 18,
              ),
            ),
          ),
        ],
      ),




    );
  }

  void onTapPokemon(index,context) {
    var pokemonUrl = pokedex[index]['img'];
    if (!pokemonSeleccionado.contains(pokemonUrl)) {
      if (pokemonSeleccionado.length < 3) {
        setState(() {
          pokemonSeleccionado.add(pokemonUrl);
        });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
          content: Text('¡Ya has seleccionado 3 Pokémon!'),
        ));
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
        content: Text('¡Este Pokémon ya está seleccionado!'),
      ));
    }
  }


  void fetchPokemonData() async {
    var url = Uri.https("raw.githubusercontent.com",
        "/Biuni/PokemonGO-Pokedex/master/pokedex.json");

    http.get(url).then((value) {
      if (value.statusCode == 200) {
        var decodedJsonData = jsonDecode(value.body);
        pokedex = decodedJsonData['pokemon'];
        print(pokedex[0]['name']);
        setState(() {});
      }
    });
  }
}
