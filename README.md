# photoAlbumManager
Simple Photo Album Manager implementing a REST API

This REST API implemention utilizes Java 8/Maven/Springboot/SpringMVC Rest/JPA and runs in an embedded Tomcat container within Springboot.  

How to run the app:

-Clone the repository
-Make sure you are runnning Java 8
-Build the project and exectue the tests by running the folloing:
  > mvn clean package
-Once the .jar file has been built, cd into the project's /target directory and startup the app as such:
  > java -jar photoAlbumManager-0.0.1-SNAPSHOT.jar
-Watch the stdout for exceptions and eventually you'll see output similar to the following, indicating the app is running: 

2017-03-31 17:49:42.807  INFO 18927 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-03-31 17:49:42.814  INFO 18927 --- [           main] com.rpo.PhotoAlbumMgrApplication         : Started PhotoAlbumMgrApplication in 9.398 seconds (JVM running for 10.036)


REST API endpoints details:

- initialize dummy album and photo data in an embedded H2 database via external REST calls 
    -Method: GET
	  -Resource endpoint: http://localhost:8080/init
    -Expected response code: 200

- retrieve list of all albums, with associated photo records
    -Method: GET
	  -Resource endpoint: http://localhost:8080/albums
    -Expected response code: 200

- retrieve a single album
    -Method: GET
	  -Resource endpoint: http://localhost:8080/albums/{albumId}
    -Expected response code: 200

- create a new album
    -Method: POST
	  -Resource endpoint: http://localhost:8080/albums
    -Body: 
        -Content-Type: application/json
        -Example:
            {
                "userId": 1,
                "title": "my family album"
            }
    -Expected response code: 201

- update an album
    -Method: PUT
	  -Resource endpoint: http://localhost:8080/albums/{albumId}
    -Body: 
        -Content-Type: application/json
        -Example:
            {
                "userId": 12,
                "title": "yearbook"
            }
    -Expected response code: 200

- delete an album (NOTE: Albums cannot be deleted if any associated photos exist)
    -Method: DELETE
	  -Resource endpoint: http://localhost:8080/albums/{albumId}
    -Expected response code: 200

- retrieve a list of all photos
    -Method: GET
	  -Resource endpoint: http://localhost:8080/photos
    -Expected response code: 200

- retreive a list of photos for a given album
    -Method: GET
	  -Resource endpoint: http://localhost:8080/photos/album/{albumId}
    -Expected response code: 200

- retrieve a single photo
    -Method: GET
	  -Resource endpoint: http://localhost:8080/photos/{photoId}
    -Expected response code: 200

- create a new photo within an existing album
    -Method: POST
	  -Resource endpoint: http://localhost:8080/photos/album/{albumId}
    -Body: 
        -Content-Type: application/json
        -Example:
            {
                "title": "accusamus beatae ad facilis cum similique qui sunt",
                "url": "http://placehold.it/600/92c952",
                "thumbnailUrl": "http://placehold.it/150/92c952"
            }
    -Expected response code: 201

- update a photo
    -Method: PUT
	  -Resource endpoint: http://localhost:8080/photos/{photoId}
    -Body: 
        -Content-Type: application/json
        -Example:
            {
                "title": "officia porro iure quia iusto qui ipsa ut modi",
                "url": "http://placehold.it/600/24f355",
                "thumbnailUrl": "http://placehold.it/150/24f355"
            }
    -Expected response code: 200

- delete a photo
    -Method: DELETE
	  -Resource endpoint: http://localhost:8080/photos/{photoId}
    -Expected response code: 200
