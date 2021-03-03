# Pacman Kata

## Objetivos
- Modelado en objetos
- Eventos asincronicos
- TDD

## Contexto y Constraints

Estamos desarrollando una API para Atary®, que controla maquinolas viejas de juego
mediante un framework.
 
### Moviendo al Pacman 

El framework nos exije que al momento de iniciarlo le pasemos una instancia de Pacman
para poder moverlo al momento de capturar el joystick.

```kotlin
interface AtariPacman {
    fun goTo(side: String)
}

val pacman :AtariPacman = Pacman()

AtariControllers.init(pacman)
``` 
Los `side`'s pueden ser:
- UP
- DOWN
- RIGHT
- LEFT

### Mostrando el juego

Para poder terminar representando algo en la pantalla del `Atary` debemos utilizar un
socket que nos provee Atary®. El mismo esta en el proyecto como otro package, y su firma
es:

```kotlin
val screen = AtariScreen.getSocket()
val points = 0

val board = [
    ["", "", "P", "", ""]
]

screen.show(board, points)
```

`board` es una matriz de 2 dimensiones de String, donde cada elemento representa una celda del `board`.
`"P"` representa la posicion del pacman.

### El diseño del board

Los diseñadores tienen una herramienta magica que les permite dibujar a mano el board
y pasarlo a una matriz de caracteres.

Por eso es conveniente que su board pueda crearse a partir de esta matriz.

Board completo:

```kotlin
val board = listOf(
    listOf("╔","═","═","═","═","═","═","═","═","═","═","═","═","╗","╔","═","═","═","═","═","═","═","═","═","═","═","═","╗"),
    listOf("║","*","*","*","*","*","*","*","*","*","*","*","*","║","║","*","*","*","*","*","*","*","*","*","*","*","*","║"),
    listOf("║","*","╔","═","═","╗","*","╔","═","═","═","╗","*","║","║","*","╔","═","═","═","╗","*","╔","═","═","╗","*","║"),
    listOf("║","*","║"," "," ","║","*","║"," "," "," ","║","*","║","║","*","║"," "," "," ","║","*","║"," "," ","║","*","║"),
    listOf("║","*","╚","═","═","╝","*","╚","═","═","═","╝","*","╚","╝","*","╚","═","═","═","╝","*","╚","═","═","╝","*","║"),
    listOf("║","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","║"),
    listOf("║","*","╔","═","═","╗","*","╔","╗","*","╔","═","═","═","═","═","═","╗","*","╔","╗","*","╔","═","═","╗","*","║"),
    listOf("║","*","╚","═","═","╝","*","║","║","*","╚","═","═","╗","╔","═","═","╝","*","║","║","*","╚","═","═","╝","*","║"),
    listOf("║","*","*","*","*","*","*","║","║","*","*","*","*","║","║","*","*","*","*","║","║","*","*","*","*","*","*","║"),
    listOf("╚","═","═","═","═","╗","*","║","╚","═","═","╗","*","║","║","*","╔","═","═","╝","║","*","╔","═","═","═","═","╝"),
    listOf(" "," "," "," "," ","║","*","║","╔","═","═","╝","*","╚","╝","*","╚","═","═","╗","║","*","║"),
    listOf(" "," "," "," "," ","║","*","║","║","*","*","*","*","*","*","*","*","*","*","║","║","*","║"),
    listOf(" "," "," "," "," ","║","*","║","║","*","╔","═","═"," "," ","═","═","╗","*","║","║","*","║"),
    listOf("═","═","═","═","═","╝","*","╚","╝","*","║"," "," "," "," "," "," ","║","*","╚","╝","*","╚","═","═","═","═","═"),
    listOf("*","*","*","*","*","*","*","*","*","*","║"," "," "," "," "," "," ","║","*","*","*","*","*","*","*","*","*","*"),
    listOf("═","═","═","═","═","╗","*","╔","╗","*","║"," "," "," "," "," "," ","║","*","╔","╗","*","╔","═","═","═","═","═"),
    listOf(" "," "," "," "," ","║","*","║","║","*","╚","═","═","═","═","═","═","╝","*","║","║","*","║"),
    listOf(" "," "," "," "," ","║","*","║","║","*","*","*","*","*","*","*","*","*","*","║","║","*","║"),
    listOf(" "," "," "," "," ","║","*","║","║","*","╔","═","═","═","═","═","═","╗","*","║","║","*","║"),
    listOf("╔","═","═","═","═","╝","*","╚","╝","*","╚","═","═","╗","╔","═","═","╝","*","╚","╝","*","╚","═","═","═","═","╗"),
    listOf("║","*","*","*","*","*","*","*","*","*","*","*","*","║","║","*","*","*","*","*","*","*","*","*","*","*","*","║"),
    listOf("║","*","╔","═","═","╗","*","╔","═","═","═","╗","*","║","║","*","╔","═","═","═","╗","*","╔","═","═","╗","*","║"),
    listOf("║","*","╚","═","╗","║","*","╚","═","═","═","╝","*","╚","╝","*","╚","═","═","═","╝","*","║","╔","═","╝","*","║"),
    listOf("║","*","*","*","║","║","*","*","*","*","*","*","*","P","*","*","*","*","*","*","*","*","║","║","*","*","*","║"),
    listOf("╚","═","╗","*","║","║","*","╔","╗","*","╔","═","═","═","═","═","═","╗","*","╔","╗","*","║","║","*","╔","═","╝"),
    listOf("╔","═","╝","*","╚","╝","*","║","║","*","╚","═","═","╗","╔","═","═","╝","*","║","║","*","╚","╝","*","╚","═","╗"),
    listOf("║","*","*","*","*","*","*","║","║","*","*","*","*","║","║","*","*","*","*","║","║","*","*","*","*","*","*","║"),
    listOf("║","*","╔","═","═","═","═","╝","╚","═","═","╗","*","║","║","*","╔","═","═","╝","╚","═","═","═","═","╗","*","║"),
    listOf("║","*","╚","═","═","═","═","═","═","═","═","╝","*","╚","╝","*","╚","═","═","═","═","═","═","═","═","╝","*","║"),
    listOf("║","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","║"),
    listOf("╚","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","═","╝"," ")
)
```

El mismo tambien esta en [board](./board)

## Iteración 1: _Movimiento_

Debemos poder mover al pacman hacia la derecha, izquierda, arriba y abajo.

En esta iteracion se espera que el pacman inicie en un board de pruebas, como el que 
vemos arriba. Un board con una sola fila y 5 columnas. El pacman no puede moverse ni
arriba ni abajo (Los metodos deben existir pero no debe fallar si se quiere mover).
Si se llega al final de la fila/columna, solo puede moverse en sentido contrario al borde donde
se encuentra. 

Board de pruebas recomendado (pueden usarse los que quieran):
```kotlin
val board = listOf(
    listOf("", "", "P", "", "")
)
```

## Iteracion 2: _Alimento_

Las celdas (cada posicion dentro de las filas del array del board) deben comenzar
rellenas si contienen `"*"`. Para eso debemos mostrarlas asi`"*"`.

Una vez el pacman se mueva en una celda donde haya una pelotita, luego la pelotita debe desapecer.

En esta iteracion se espera que el board arranque con las pelotitas, pero a medida que 
el pacman las pasa por arriba desaparecen quedando vacias.

## Iteracion 3: _Puntaje_

Cada vez que el pacman come una pelotita suma 10 puntos.

Imprimir como una fila mas abajo del board los puntos del pacman

En esta iteracion se espera que el pacman arranque con 0 puntos, y a medida que avanza sobre las pelotitas los puntos
se incrementan en 10.

## Iteracion 4: Implementar divisores

Debe implementarse un nuevo tipo de celda, los divisores. Estos no permiten al pacman
pasar. Si se llega al borde y no hay divisor, entonces se aparece en la misma fila/columna pero
al principio.

En esta iteracion se espera poder representar un pacman el board y que el Pacman se
mueva sin transpasar los divisores y poder volver desde el fin al inicio y biceversa.
