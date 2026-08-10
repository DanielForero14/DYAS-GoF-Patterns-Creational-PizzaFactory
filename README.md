# 🍕 Pizza Factory — Abstract Factory

## Patrones Creacionales — Parte I

**Autor:** Daniel Felipe Forero Sánchez

---

## 📌 Descripción del proyecto

Este proyecto corresponde a la refactorización de un sistema encargado de controlar el proceso automático de fabricación de pizzas.

El objetivo principal fue aplicar el patrón de diseño creacional **Abstract Factory** para desacoplar el proceso de preparación de una pizza de las implementaciones concretas utilizadas para:

- Amasar.
- Moldear.
- Hornear.

Inicialmente, el sistema trabajaba directamente con clases correspondientes a pizzas de masa delgada. Después de la refactorización, el mismo proceso puede trabajar con diferentes familias de productos.

Se implementaron tres variantes:

- 🍕 **Pizza Delgada:** masa convencional.
- 🍞 **Pizza Gruesa:** masa tipo pan.
- 🌾 **Pizza Integral:** masa integral.

La lógica principal de preparación permanece igual independientemente de la familia seleccionada.

---

# 🎯 Objetivo de la refactorización

El sistema original creaba directamente los componentes necesarios para preparar una pizza:

```text
PreparadorPizza
│
├── AmasadorPizzaDelgada
├── MoldeadorPizzaDelgada
└── HorneadorPizzaDelgada
```

Esto generaba un alto acoplamiento, ya que `PreparadorPizza` dependía directamente de una variante específica.

Si aparecía una nueva máquina para fabricar otro tipo de pizza, sería necesario modificar la lógica principal.

La solución consistió en separar:

```text
PROCESO DE PREPARACIÓN
│
├── Amasar
├── Moldear
├── Aplicar ingredientes
└── Hornear

          ≠

IMPLEMENTACIÓN CONCRETA
│
├── Delgada
├── Gruesa
└── Integral
```

Para lograr esta separación se implementó el patrón **Abstract Factory**.

---

# 🏭 Patrón Abstract Factory

El patrón **Abstract Factory** permite crear familias de objetos relacionados sin que el código que los utiliza necesite conocer directamente sus clases concretas.

En este proyecto, la fábrica abstracta está representada por:

```java
AFabricaPizza
```

Esta interfaz define los métodos necesarios para crear los tres productos involucrados en la fabricación:

```java
AmasadorPizza crearAmasador();
HorneadorPizza crearHorneador();
MoldeadorPizza crearMoldeador();
```

Los productos abstractos son:

```text
AmasadorPizza
HorneadorPizza
MoldeadorPizza
```

Cada uno representa una etapa diferente del proceso.

| Producto abstracto | Responsabilidad |
|---|---|
| `AmasadorPizza` | Realizar el proceso de amasado |
| `MoldeadorPizza` | Dar forma y tamaño a la pizza |
| `HorneadorPizza` | Realizar el proceso de horneado |

---

# 🧩 Fábricas concretas

Se implementaron tres fábricas concretas:

```text
FabricaPizzaDelgada
FabricaPizzaGruesa
FabricaPizzaIntegral
```

Cada fábrica crea una familia completa de productos compatibles.

La estructura general es:

```text
                         AFabricaPizza
                              │
           ┌──────────────────┼──────────────────┐
           │                  │                  │
 FabricaPizzaDelgada  FabricaPizzaGruesa  FabricaPizzaIntegral
           │                  │                  │
      ┌────┼────┐        ┌────┼────┐        ┌────┼────┐
      │    │    │        │    │    │        │    │    │
 Amasador Moldeador   Amasador Moldeador  Amasador Moldeador
        Horneador            Horneador           Horneador
```

Esto garantiza que al seleccionar una fábrica se obtengan los componentes correspondientes a la misma familia.

---

# 🍕 Familia Pizza Delgada

La familia Delgada representa la máquina original encargada de preparar pizzas de masa convencional.

```text
FabricaPizzaDelgada
│
├── AmasadorPizzaDelgada
├── MoldeadorPizzaDelgada
└── HorneadorPizzaDelgada
```

Cada producto implementa su correspondiente interfaz abstracta.

---

# 🍞 Familia Pizza Gruesa

La segunda familia representa la variante encargada de preparar pizzas de masa gruesa o masa tipo pan.

```text
FabricaPizzaGruesa
│
├── AmasadorPizzaGruesa
├── MoldeadorPizzaGruesa
└── HorneadorPizzaGruesa
```

Aunque el proceso general sigue siendo el mismo, las operaciones concretas corresponden a las características de esta variante.

---

# 🌾 Familia Pizza Integral

También se implementó una familia para pizzas de masa integral.

```text
FabricaPizzaIntegral
│
├── AmasadorPizzaIntegral
├── MoldeadorPizzaIntegral
└── HorneadorPizzaIntegral
```

Esta familia implementa las mismas abstracciones, pero proporciona el comportamiento correspondiente a una pizza integral.

---

# 🔌 Desacoplamiento de `PreparadorPizza`

Una de las modificaciones más importantes fue eliminar de `PreparadorPizza` la creación directa de productos concretos.

En lugar de trabajar con:

```java
AmasadorPizzaDelgada
HorneadorPizzaDelgada
MoldeadorPizzaDelgada
```

la clase utiliza las abstracciones:

```java
AmasadorPizza
HorneadorPizza
MoldeadorPizza
```

Los objetos son proporcionados por la fábrica:

```java
AmasadorPizza am = fabrica.crearAmasador();
HorneadorPizza hpd = fabrica.crearHorneador();
MoldeadorPizza mp = fabrica.crearMoldeador();
```

De esta forma, `PreparadorPizza` conoce **qué operaciones necesita realizar**, pero no necesita conocer las clases concretas encargadas de realizarlas.

---

# 🔄 Proceso de preparación

Independientemente de la familia seleccionada, el proceso general permanece igual:

```text
        INICIO
          │
          ▼
       AMASAR
          │
          ▼
       MOLDEAR
          │
          ▼
APLICAR INGREDIENTES
          │
          ▼
       HORNEAR
          │
          ▼
         FIN
```

En código, la lógica utiliza únicamente productos abstractos:

```java
AmasadorPizza am = fabrica.crearAmasador();
HorneadorPizza hpd = fabrica.crearHorneador();
MoldeadorPizza mp = fabrica.crearMoldeador();

am.amasar();

if (tam == Tamano.PEQUENO) {
    mp.moldearPizzaPequena();
} else if (tam == Tamano.MEDIANO) {
    mp.moldearPizzaMediana();
}

aplicarIngredientes(ingredientes);

hpd.hornear();
```

Esta secuencia no necesita modificarse dependiendo del tipo de pizza.

---

# ⚙️ Selección de la fábrica

La familia utilizada se determina mediante una implementación de:

```java
AFabricaPizza
```

Por ejemplo, para utilizar la familia Gruesa:

```java
AFabricaPizza fabrica = new FabricaPizzaGruesa();
```

Para utilizar la familia Delgada:

```java
AFabricaPizza fabrica = new FabricaPizzaDelgada();
```

Y para utilizar la familia Integral:

```java
AFabricaPizza fabrica = new FabricaPizzaIntegral();
```

Después de seleccionar la fábrica, el mismo `PreparadorPizza` puede trabajar con cualquiera de las familias.

Conceptualmente:

```text
Fábrica seleccionada
        │
        ▼
   AFabricaPizza
        │
        ├── crearAmasador()
        ├── crearMoldeador()
        └── crearHorneador()
        │
        ▼
   PreparadorPizza
```

---

# 🏗️ Arquitectura general

La arquitectura final puede resumirse así:

```text
                          AFabricaPizza
                               │
            ┌──────────────────┼──────────────────┐
            │                  │                  │
       DELGADA              GRUESA             INTEGRAL
            │                  │                  │
       ┌────┼────┐        ┌────┼────┐        ┌────┼────┐
       │    │    │        │    │    │        │    │    │
       A    M    H        A    M    H        A    M    H
       │    │    │        │    │    │        │    │    │
       └────┴────┴────────┴────┴────┴────────┴────┴────┘
                               │
                               ▼
                        PreparadorPizza
```

Donde:

```text
A = AmasadorPizza
M = MoldeadorPizza
H = HorneadorPizza
```

La idea principal es:

> **PreparadorPizza conoce el proceso de fabricación, mientras la fábrica decide qué familia de máquinas realiza cada operación.**

---

# 📂 Estructura principal del proyecto

La implementación se encuentra principalmente en:

```text
src/main/java/edu/unisabana/pizzafactory/
│
├── consoleview/
│   └── PreparadorPizza.java
│
└── model/
    │
    ├── AFabricaPizza.java
    │
    ├── AmasadorPizza.java
    ├── HorneadorPizza.java
    ├── MoldeadorPizza.java
    │
    ├── FabricaPizzaDelgada.java
    ├── AmasadorPizzaDelgada.java
    ├── HorneadorPizzaDelgada.java
    ├── MoldeadorPizzaDelgada.java
    │
    ├── FabricaPizzaGruesa.java
    ├── AmasadorPizzaGruesa.java
    ├── HorneadorPizzaGruesa.java
    ├── MoldeadorPizzaGruesa.java
    │
    ├── FabricaPizzaIntegral.java
    ├── AmasadorPizzaIntegral.java
    ├── HorneadorPizzaIntegral.java
    ├── MoldeadorPizzaIntegral.java
    │
    ├── Ingrediente.java
    ├── Tamano.java
    └── ExcepcionParametrosInvalidos.java
```

---

# 🛠️ Requisitos

Para compilar y ejecutar el proyecto es necesario tener instalado:

- Java.
- Apache Maven.
- Git.
- Un IDE o editor compatible con Java, como Visual Studio Code.

Las instalaciones pueden comprobarse mediante:

```powershell
java -version
```

```powershell
mvn -version
```

```powershell
git --version
```

---

# ▶️ Compilación

Desde la carpeta raíz del proyecto, donde se encuentra `pom.xml`, ejecutar:

```powershell
mvn clean compile
```

Una compilación correcta debe finalizar con:

```text
BUILD SUCCESS
```

---

# 🍕 Ejecución

Para ejecutar el programa:

```powershell
mvn exec:java "-Dexec.mainClass=edu.unisabana.pizzafactory.consoleview.PreparadorPizza"
```

Por ejemplo, utilizando:

```java
AFabricaPizza fabrica = new FabricaPizzaGruesa();
```

el sistema ejecuta la familia Gruesa manteniendo la secuencia:

```text
Amasar
   ↓
Moldear
   ↓
Aplicar ingredientes
   ↓
Hornear
```

---

# 🧠 Lo que aprendimos / Decisiones de diseño

Durante la refactorización se tomaron varias decisiones importantes.

### 1. Depender de abstracciones

`PreparadorPizza` dejó de depender directamente de:

```text
AmasadorPizzaDelgada
MoldeadorPizzaDelgada
HorneadorPizzaDelgada
```

y pasó a trabajar con:

```text
AmasadorPizza
MoldeadorPizza
HorneadorPizza
```

Esto reduce el acoplamiento entre la lógica principal y las implementaciones concretas.

---

### 2. Mantener el proceso independiente de la variante

El proceso:

```text
Amasar → Moldear → Aplicar ingredientes → Hornear
```

permanece igual para todas las familias.

Lo que cambia es la implementación encargada de realizar cada operación.

Esto permite modificar el comportamiento del sistema cambiando la fábrica utilizada, sin reescribir el algoritmo principal.

---

### 3. Crear familias completas de productos

Cada fábrica concreta crea los tres componentes correspondientes a su variante.

Por ejemplo:

```text
FabricaPizzaGruesa
        │
        ├── AmasadorPizzaGruesa
        ├── MoldeadorPizzaGruesa
        └── HorneadorPizzaGruesa
```

Esto evita mezclar accidentalmente componentes de diferentes tipos de pizza y mantiene coherencia entre los productos creados.

---

# 📝 Conclusión

La implementación de **Abstract Factory** permitió desacoplar el proceso de preparación de pizzas de las máquinas concretas utilizadas para realizar cada operación.

`PreparadorPizza` mantiene una única lógica:

```text
AMASAR
   ↓
MOLDEAR
   ↓
APLICAR INGREDIENTES
   ↓
HORNEAR
```

mientras `AFabricaPizza` determina qué familia concreta debe realizar esas operaciones.

El resultado puede resumirse como:

```text
MISMO PROCESO
     +
FÁBRICA DIFERENTE
     =
PIZZA DIFERENTE
```

Esta estructura permite mantener el código organizado, reducir dependencias entre clases y separar correctamente la creación de objetos de la lógica principal de preparación.

---

# 👨‍💻 Autor

**Daniel Felipe Forero Sánchez**

---

## 🍕 Pizza Factory — Abstract Factory

> **Un mismo proceso de preparación, diferentes familias de máquinas y una fábrica abstracta encargada de decidir cómo se fabrica cada pizza.**
