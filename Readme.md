# Directa24 Back-End Developer Challenge 

In this challenge, the REST API contains information about a collection of movie released after the year 2010, directed by acclaimed directors.  
Given the threshold value, the goal is to use the API to get the list of the names of the directors with most movies directed. Specifically, the list of names of directors with movie count strictly greater than the given threshold.   
The list of names must be returned in alphabetical order.  

To access the collection of users perform HTTP GET request to:
https://directa24-movies.wiremockapi.cloud/api/movies/search?page=<pageNumber>
where <pageNumber> is an integer denoting the page of the results to return.

For example, GET request to:
https://directa24-movies.wiremockapi.cloud/api/movies/search?page=2
will return the second page of the collection of movies. Pages are numbered from 1, so in order to access the first page, you need to ask for page number 1.
The response to such request is a JSON with the following 5 fields:

- page: The current page of the results  
- per_page: The maximum number of movies returned per page.  
- total: The total number of movies on all pages of the result.  
- total_pages: The total number of pages with results.  
- data: An array of objects containing movies returned on the requested page  

Each movie record has the following schema:  
- Title: title of the movie  
- Year: year the movie was released  
- Rated: movie rating  
- Released: movie release date  
- Runtime: movie duration time in minutes  
- Genre: move genre  
- Director: movie director  
- Writer: movie writers  
- Actors: movie actors  

## Posible Solutions
### Clone repository and complete the Function
  
Complete the function:

    List<String> getDirectors(int threshold)

getDirectors has the following parameter:
- threshold: integer denoting the threshold value for the number movies a person has directed

The function must return a list of strings denoting the name of the directors whose number of movies directed is strictly greater than the given threshold. 
The directors name in the list must be ordered in alphabetical order.


#### Sample Input For Custom Testing
    4  
#### Sample Output
    Martin Scorsese
    Woody Allen
    
The threshold value is 4, so the result must contain directors names with more than 4 movies directed.   
There are 2 such directors and names in the alphabetical order listed in Sample Output.

### Alternative: Spring Boot solution

An alternative solution to clonning the repository and implementing the function can be creating a new Spring Boot project with Rest endpoint:       
```
/api/directors?threshold=4
```
The solution can be shared via .zip file or sharing a github repository url via email. 

## Solution

## Configuration

### Environment Variable

The application uses the `BASE_URL` environment variable to configure the base URL for requests. You can set it as follows:

```bash
export BASE_URL=https://directa24-movies.wiremockapi.cloud
```
### Swagger

Interactive API documentation is available through Swagger UI. You can access it at the following URL:
```
http://localhost:8080/swagger-ui/index.html
```

## Requirements

- Java 11 or higher
- Maven
- Lombok (for reducing boilerplate code)

## Running the Application

1. Clone the repository:

    ```bash
    git clone <repository URL>
    ```
2. Navigate to the project directory:

    ```bash
    cd <project directory>
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```
4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

5. Make sure the BASE_URL environment variable is set correctly before running the application.