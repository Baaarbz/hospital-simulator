<h1 align="center">Welcome to Hospital Simulator 👋</h1>

## Author

👤 **Eduardo Barbosa Tarrío**

* Github: [@Baaarbz](https://github.com/Baaarbz)
* LinkedIn: [@eduardobarbosatarrio](https://linkedin.com/in/eduardobarbosatarrio)

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#assumptions">Assumptions</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li>
         <a href="#run-it">Run it</a>
         <ul>
            <li><a href="#windows">Windows</a></li>
            <li><a href="#unix">Unix</a></li>
         </ul>
        </li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

___

## About The Project

Hospital Simulator, which can simulate the future patients health state, based on their current health state and a list
of drugs they are
administered.

<p align="right">(<a href="#top">back to top</a>)</p>

### Assumptions

- Invalid input will be ignored
- Define drugs collection input as `Set` to ignore duplications, due to it does not matter if the user input three times
  a Paracetamol or not.
- In order to keep the app as simple as possible, instead of using POJOs for `Patients` and `Drugs`, I decided to
  use only the `enums` as domain, due to that POJOs won't have any special logic by its' health state or whatever, and in this way, I can
  simplify the way I use and validate the input and also de code.
- Split in two the business rules:
    - How to treat different health states and its results:

| Health state       | Cure                             | Prevent death |
|--------------------|----------------------------------|---------------|
| (**F**)ever        | (**As**)pirin, (**P**)aracetamol |               |
| (**D**)iabetes     |                                  | (**In**)sulin |
| (**H**)ealthy      |                                  |               |
| (**X**)Dead        |                                  |               |
| (**T**)uberculosis | (**An**)tibiotic                 |               |

- What effects have the different combinations of drugs:

| Drugs                             | Initial health state | Health state after applying drugs |
|-----------------------------------|----------------------|-----------------------------------|
| (**In**)sulin + (**An**)tibiotic  | (**H**)ealthy        | (**F**)ever                       |
| (**P**)aracetamol + (**As**)pirin | *ANY*                | (**X**)Dead                       |

- Define an "in memory" repository for each case, in order to obtain more maintainability and also scalability.
- The `FlyingSpaghettiMonster` will be summoned only when there is at least one dead patient.

<p align="right">(<a href="#top">back to top</a>)</p>

___

## Getting Started

### Prerequisites

* [JDK 17](https://openjdk.java.net/projects/jdk/17/)
* [Gradle](https://gradle.org/)

### Run it

#### Windows

   ```bash
   cd .\path\to\project
   .\gradlew shadowJar
   java -jar .\build\libs\HospitalSimulator-1.0-SNAPSHOT-all.jar F P
   ```

<p align="right">(<a href="#top">back to top</a>)</p>

#### Unix

   ```bash
   cd ~/path/to/project
   ./gradlew shadowJar
   java -jar build/libs/HospitalSimulator-1.0-SNAPSHOT-all.jar F P
   ```

<p align="right">(<a href="#top">back to top</a>)</p>
