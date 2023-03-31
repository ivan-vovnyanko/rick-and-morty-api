# 👴Rick And Morty Api👦

## About 💡
**Some introductory information:**
- **Pull data** about characters from the *Rick and Morty* multiverse from an <a href="https://rickandmortyapi.com/">**open source**</a> and save it to your ***PostgreSQL*** database.
- When synchronizing data with an open source, *avatars for characters in ***JPEG*** format* are also **downloaded and saved** in *the folder with your application.*
- **You do not need to do this synchronization manually.** 
It will be executed **automatically** when you **start the application** and **every morning at 8 o'clock.**
- **The endpoints of this API** can give information about ***a random character in json format***, can upload ***a character's avatar by ID***, can give ***character information by ID*** and can give ***a list of all characters supporting sorting, pagination and filtering by various parameters.***
*More about endpoints will be below.*
- The application has **unit tests** (***Testcontainers*** for repository tests, ***MockMvc*** for controller tests)
- *All controllers* have their *own internal documentation* with ***swagger.***
You can read it in **controller classes**, or by clicking on the ***<a href="http://localhost:8080/movie-characters/swagger-ui-docs.html">link</a>*** 
*(the path can be changed in the **application.properties** file).*

## Core technologies 💻
- <h3>Spring (Boot, Web, Data)</h3>
- <h3>PostgreSQL, Liquibase</h3>
- <h3>Httpclient</h3>
- <h3>Junit5, Mockito, Spring MockMVC, Testcontainers</h3>
- <h3>Swagger</h3>

## Endpoints documentation ✴️

**❗Note❗**<br>

Representing ***a character*** in a **json object:**
- **Long** *id* - Character ID in our database.
- **String** *name* - Character `s name
- **String** *status* - Character status (*Alive*, *Dead* or *unknown*)
- **String** *gender* - Character gender (*Male*, *Female*, *Genderless* or *unknown*)
- **String** *species* - Types of characters (human, humanoid for example)
- **String** *type* - Character type (monster, superhuman or parasite for example)
- **String** *image* - Link to **the avatar** in the local Api storage.
- **String** *created* - Character creation date

***Example***

    {
        "id":270,
        "name":"Prince Nebulon",
        "status":"Dead",
        "gender":"Male",
        "species":"Alien",
        "type":"Zigerion",
        "image":"\\downloaded\\character_images\\270.jpeg",
        "created":"2017-12-31T14:05:24.406Z"
    }


### Random Character

- Provides ***a random character*** as **a Json object** *(the structure is presented above)*
- **Url:** ***/movie-characters/random***
- This endpoint has ***no parameters.***
- **All users have access** to this endpoint.

### Search Characters

- Provides **a list of characters** *that match your query* **as Json objects.**
  - **Url:** ***/movie-characters/search***
  - This endpoint has ***5 optional parameters.***
  - **page** - You can choose the pages into which the answer to your request is divided *(You can be shown a maximum of 20 characters per request)*

    *Example:* ***?page=2*** *(3-rd page)*
  - **sortBy** - Indicates what to focus *on when sorting* *(by default **on ID**)*

    *Example:* ***?sortBy=name:DESC&id*** *(**Multiple sorting** items can be specified, as well as **reverse sorting** - ***DESC***)*
  - **name** - Searches for characters **whose names begin with the given string.** *(**Empty string** by default)*
  
    *Example:* ***?name=Rick***
  - **genderIn** and **statusIn** - Searches for characters by gender and/or status. *(All kinds of **gender** and **status** by default)*

    *Example:* ***?genderIn=male*** , or ***?statusIn=dead&genderIn=FEMALE*** *(Upper or lower case doesn't matter)*
- **All users have access** to this endpoint.

### Get (By ID)
- Provides ***a character by ID*** as **a Json object**
- **Url:** ***/movie-characters/{ID}***
- This endpoint has **Path Variable - ID (Long).**
- **All users have access** to this endpoint.

### Download Avatar (By ID)
- Provides ***a character avatar*** as **a JPEG image from our local storage**
- **Url:** ***/movie-characters/image/{ID}***
- This endpoint has **Path Variable - ID (Long).**
- **All users have access** to this endpoint.

# How to run 📖

- **Download the project** from the *GitHub* <a href="https://github.com/ivan-vovnyanko/rick-and-morty-api/archive/refs/heads/master.zip">**link**</a> and **place it on your computer.**
- Create a ***postgres database*** named *'rickandmorty'*
- Run ***RickAndMortyApiApplication***