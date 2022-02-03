# IoTEAM-Smart-Wardrobe

<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]




<!-- PROJECT LOGO -->
<br />
<div align="center">
  <!-- <a href="https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a> -->

<h3 align="center">SMART WARDROBE</h3>

  <p align="center">
    Smart, fast and appropriate solution in your own wardrobe
    <br />
    <a href="https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#mqtt">MQTT</a></li>
    <li><a href="#testing">Testing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#team">Team</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

Our project is an IoT Solution for a Smart Wardrobe, that has different functionalities in order to replace an ordinary object in the house, making an easier life for the user. 

Smart wardrobe has the following functionalities:

* authentication

* clothes and coats management

* outfit recommendations (depending on the weather, color theory, user size etc.)

* outfit history, rating and favourite list

* clothes selection for washing (including instruction for different materials)

* shopping recommendation

* user feature change detection

Every functionality is carefully morphed in this IoT solution.


<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Security](https://spring.io/projects/spring-security)
* [Maven](https://maven.apache.org/)
* [MQTT](https://mqtt.org/)
* [MySQL](https://www.mysql.com/)
* [SonarQube](https://www.sonarqube.org/)
* [JaCoCo](https://www.baeldung.com/jacoco)
* [Swagger](https://swagger.io/)
* [Postman](https://www.postman.com/)
* [Java](https://www.java.com/en/)


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.

Configure MQTT-mosquitto

1. install mosquitto - https://mosquitto.org/download/
2. open run as administrator command prompt
3. navigate to Mosquitto root file -- somthing like C:\Program Files (x86)\mosquitto.
4. Start the Mosquitto service by running the command: "net start mosquitto". 

### Installation

1. Make sure you have an available IDE (e.g. IntelliJ), suitable for Spring Boot Projects
2. Clone the repo
   ```sh
   git clone https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe.git
   ```
3. Install/configure Postman
4. Run SmartWardrobeApplication.java from the IDE.


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

For a more detailed description, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Create App
    - [ ] Authetication
    - [ ] Database Design
    - [ ] Database Management
- [ ] Outfit Recommendation
    - [ ] Color Generator
    - [ ] Weather Criteria
    - [ ] Size and style filter
- [ ] Outfit Additionals
    - [ ] History
    - [ ] Rating
    - [ ] Favourites
- [ ] Washing Functionality
    - [ ] Color sorting
    - [ ] Special Instruction Set Display
- [ ] Shopping Recommendation
    - [ ] Shortage detection
    - [ ] Category Selection
- [ ] User Feature Change Detection
    - [ ] Size calculating
    - [ ] Wardrobe Filtering


See the [open issues](https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- MQTT -->
## MQTT

It is created with a mosquitto broker that implements MQTT using a Publisher and a Subscriber.

In our case, the Publisher posts information about the weather condition (humidity, temperature, time_of_day) every minute on the "weather" topic once the app starts.

Once the user is logged in, the publisher posts all available items from the wardrobe every minute on the "items" topic.

Also, a new publish can be made, e.g. from Postman, using a post request with a JSON (that contains id, qos, topic, message) in the body.

The Async Documentation is made by hand in mqtt.yml.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- TESTING -->
## Testing

For JaCoCo, it is necessary the following dependency:
```
<dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>org.jacoco.agent</artifactId>
    <version>0.8.7</version>
    <scope>test</scope>
</dependency>
```

For SonarQube please check the next [documentation](https://docs.sonarqube.org/latest/setup/get-started-2-minutes/).

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- TEAM -->
## Team

Project Link: [https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe](IoTeam Smart Wardrobe)

[@AlexandraFlaviaCojocaru](https://github.com/CojocaruAlexandraFlavia)
[@AlexandraBuruiana](https://github.com/alexandraburu23)
[@MariaFlorea](https://github.com/FloreaMaria)
[@AndreeaGavrila](https://github.com/AndreeaGavrila)
[@MariaTudor](https://github.com/maria-tudor)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgements

* [Color-Scheme-Theory](https://shilpaahuja.com/color-schemes/)
* [Weather Grabber Model](https://gist.github.com/Shynixn/77c20572a483e0b45c4afe926326300a)
* [Washing Instruction](https://thefabricstoreonline.com/pages/fabric-care-instructions)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe.svg?style=for-the-badge
[contributors-url]: https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe.svg?style=for-the-badge
[forks-url]: https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/network/members
[stars-shield]: https://img.shields.io/github/stars/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe.svg?style=for-the-badge
[stars-url]: https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/stargazers
[issues-shield]: https://img.shields.io/github/issues/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe.svg?style=for-the-badge
[issues-url]: https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/issues
[license-shield]: https://img.shields.io/github/license/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe.svg?style=for-the-badge
[license-url]: https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/blob/master/LICENSE.txt
