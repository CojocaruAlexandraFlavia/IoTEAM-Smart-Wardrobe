# Application and Motivation

In 2022, we have to think about our daily life problems and about solving them smart, fast and appropriate. 
One of these day-to-day problems is right in our own wardrobe. 
Choosing the right outfit is difficult because of the lack of inspiration, lack of time, not having the right clothes or not having them available when we want them to.
This was the motivation behind developing Smart Wardrobe Project.

Our project proposes a wardrobe upgrade that offers services like outfit recommendation and rating, shopping recommendation, a link with the washing machine and detecting user feature changes to have the right clothes for your features.

The project was created with Spring Framework and Maven Build Tool. The database was designed with MySQL and the endpoints were sent from Postman.

Disclaimer: This is a software solution. No hardware components or mechanism were used.

# Authetication

At first, the app requires an authetication in order to manage the user details (username, password but also physical features like height, weight, eye color, haircolor etc.). We used Spring Security for authetication and for request restriction when a user is not authenticated.

# Database Design and Management

Our database was created with MySQL integrated in the Spring Boot Application. We have the following entities: Item (clothing item), Coat, Outfit, History and User.
Regular clothing items and coats are treated different because they serve different purposes (a coat can be included or not in the outfit) and have different washing requirements. 
History has the outfits worn until present day.

## Relationships

* Item - Outfit => Many to Many

* Coat - Outfit => One to Many

* Outfit - History => One to Many

## Sorting

The Items can be selected by category, by style or be sorted depending on the last wearing date. For an optimal use of the wardrobe, we show the user's clothes in an ascending order, from the oldest clothes to the one worn most recently. 
For sorting after the last wear, an auxiliary function was performed to compare the items' wearing dates, influencing the sorting order. 


# Outfit Recommendation

The recommendations are based on several criteria. The most important one is color coordination. We used color theory to determine three recommendation functions and we used a diverse color palette: each basic color of the rainbow has eight shades (from darkest to lightest), supplemented by black, grey and white. The outfit always starts with the top piece and adapts the bottom and coat recommendation.

## Monochromatic

In this function, we determine which is the color family of the top shade. Based on this, we choose randomly two different shades from the same family. The bottom piece and the coat must have one of the three colors (top color or the generated ones).

An non-color monochromatic outfit contains black, white and grey.

## Analogous

Also known as dominance color harmony, this color scheme comprises colors that are right next to each other on the color wheel. In this color scheme palette, one color is always dominant and brighter than the others. So, the generators returns two neighbouring colors on the same column in the color array (the dark colors are mixed with dark, light with light). The bottom piece and the coat must have one of the three colors (top color or the generated ones). 

Black, white and grey are also acceptable colors.


## Pastel

The pastel color scheme is similar with the monochromatic one, but instead of using two random shades from the same family, it uses two random generated shades from the lightest shades of each family. The bottom piece and the coat must have one of the three colors (top color or the generated ones).

Black and white are not acceptable color, but grey is.

## Weather Criteria

The weather is an important criteria when selecting an outfit. 
We are returning the weather conditions using two APIs: one for the IP and one for weather conditions.

The IP information is determined by getting our device IP from [AmazonAWS](http://checkip.amazonaws.com). This IP is then sent to [ip-API](http://ip-api.com) that returns a JSON file with the country, country code, region name, city and timezone.

From this, we extract the city or region name (e.g. ip-api returns that Bucharest is the region name, Sector 6 is the city) and we use them to get the weather conditions.

The weather is determined by connecting to the [WeatherAPI](https://www.weatherapi.com/). From the city name (or region name) this API returns a JSON with so many weather related data, from which we select the felt temperature in Celsius, the humidity, the last update of the recorded data and whether it is day or night. 

Our main focus from the weather is the temperature, from which we determine the following rules:

* under 5 degrees - no tshirt, skirts or dresses

* under 18 degrees - must have a coat


## Style Criteria

A recommended outfit has to have pieces of the same style.

## Size Criteria

Every recommendation function uses the user size as a guideline. Every item from the recommendation outfit needs to be the same size as the user.
The user size is calculated depending on the weight and height.

# Outfit History, Rating and Favourites

## History

Once an outfit is worn once, it is saved in history. We decided to save the history of the last seven days of outfits in a JSON file.

## Rating

Every generated outfit that is chosen will be added to the database, after which we can add a rating, that is calculated basod on the number of stars and ratings given.

## Favourites

Our favourites option displays the outfits with a rating over a chosen value, basically a list of the best rated outfits.


# Washing Functionality

## Color Separation

Our system has the option of displaying all the clothes that need to be washed (e.g. worn more than three time since the last wash) and, for every washing category (WHITE, BLACK, COLOR), it mocks a connection to an API for washing instructions.

## Special Instructions

Once a request is made for the dirty items list, in one of the washing color zone, the function returns a list of all the items that needs to be washed, completed with a set of instructions, custom made for each material present in the list. The instructions are requested from a JSON file. 

# Shopping Recommendation

The shopping recommendation appears when the system determines that you have a shortage in a category of clothes.

We simulated a connection with a known store by designing a JSON file. Data was stored for available items: barcode, material, size, color, item style, category, and wash instructions for its color. Depending on the missing items (discovered after recommendations), we can select what category of clothes we need to buy (tshirts, pants etc.) and all available items are returned for shopping.

# User Feature Change Detection

The getUsersFromFile() functionality takes the features of the user from a json file (which mimicks a mirror) and then saves them in the database. The whole process would be possible with AI and image processing, so we only included a mock version of it.

The findAllItems() functionality "scans" the wardrobe and returns every item of clothing that's in it. 

The updateWardrobe functionality scans the wardrobe for items that don't fit the user anymore. For that, it compares the size of the user, calculated with their weight and height, and compares it to the sizes of all the items in the wardrobe.

The webcam functionality lets the user take pictures of themselves and saves it to memory as a picture. This feature is also a part of mimicking the mirror, but is also an beneficial addition to the wardrobe, because it lets you take selfies and test out the outfits.

