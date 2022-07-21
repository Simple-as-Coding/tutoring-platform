# ResponseEntity vs @ResponseBody vs @ResponseStatus

`ResponseEntity` is meant to represent the entire HTTP response. You can control anything that goes into it: `status code`, `headers`, and `body`.

`@ResponseBody` is a marker for the HTTP response body and `@ResponseStatus` declares the status code of the HTTP response.

`@ResponseStatus` isn't very flexible. It marks the entire method, so you have to be sure that your handler method 
will always behave the same way. And you still can't set the headers. You'd need the `HttpServletResponse`.

**Basically, `ResponseEntity` lets you do more.**

To complete the answer:

It's true that `ResponseEntity` gives you more flexibility but in most cases you won't need it and you'll end up with these `ResponseEntity` everywhere in your controller thus making it difficult to read and understand.

If you want to handle special cases like errors (_Not Found_, _Conflict_, etc.), you can add a `HandlerExceptionResolver` to your Spring configuration. So in your code, you just throw a specific exception (`NotFoundException` for instance) and decide what to do in your **Handler** (setting the HTTP status to 404), making the Controller code more clear.


According to official documentation: Creating REST Controllers with the @RestController annotation

`@RestController` is a stereotype annotation that combines `@ResponseBody` and `@Controller`. More than that, it gives more meaning to your Controller and also may carry additional semantics in future releases of the framework.

It seems that it's best to use `@RestController` for clarity, but you can also combine it with `ResponseEntity` for flexibility when needed.

For example:

```java
@RestController
public class MyController {

    @GetMapping(path = "/test")
    @ResponseStatus(HttpStatus.OK)
    public User test() {
        User user = new User();
        user.setName("Name 1");

        return user;
    }

}
```
is the same as:

```java
@RestController
public class MyController {

    @GetMapping(path = "/test")
    public ResponseEntity<User> test() {
        User user = new User();
        user.setName("Name 1");

        HttpHeaders responseHeaders = new HttpHeaders();
        // ...
        return new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
    }

}
```


Or we can just use this:
```java
        return ResponseEntity.ok().headers(responseHeaders).body(user);
```

This way, you can define ResponseEntity only when needed.



What if we have added
```java
 @ResponseStatus(HttpStatus.OK)
``` 
on the method, but method returns
```java
 	return new ResponseEntity<>(user, responseHeaders, HttpStatus.NOT_FOUND);
 ```
?

```java
	@ResponseStatus(HttpStatus.OK)
```
is ignored when you return
```java 
	ResponseEntity<>(user, responseHeaders, HttpStatus.NOT_FOUND)
```

From JavaDocs of the `ResponseStatus`.
> The status code is applied to the HTTP response when the handler method is invoked and overrides status information set by other means, like `{@code ResponseEntity}` or `{@code "redirect:"}`.



A proper REST API should have below components in response

1. Status Code
2. Response Body
3. Location to the resource which was altered(for example, if a resource was created, client would be interested to know the url of that location)
   The main purpose of `ResponseEntity` was to provide the option 3, rest options could be achieved without `ResponseEntity`.

So if you want to provide the location of resource then using `ResponseEntity` would be better else it can be avoided.

Consider an example where a API is modified to provide all the options mentioned

```java
   
   // Step 1 - Without any options provided
   @RequestMapping(value="/{id}", method=RequestMethod.GET)
   public @ResponseBody Spittle spittleById(@PathVariable long id) {
     return spittleRepository.findOne(id);
   }
   
   // Step 2- We need to handle exception scenarios, as step 1 only caters happy path.
   @ExceptionHandler(SpittleNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public Error spittleNotFound(SpittleNotFoundException e) {
     long spittleId = e.getSpittleId();
     return new Error(4, "Spittle [" + spittleId + "] not found");
   }
   
   // Step 3 - Now we will alter the service method, **if you want to provide location**
   @RequestMapping(
       method=RequestMethod.POST
       consumes="application/json")
   public ResponseEntity<Spittle> saveSpittle(
       @RequestBody Spittle spittle,
       UriComponentsBuilder ucb) {
   
     Spittle spittle = spittleRepository.save(spittle);
     HttpHeaders headers = new HttpHeaders();
     URI locationUri =
     ucb.path("/spittles/")
         .path(String.valueOf(spittle.getId()))
         .build()
         .toUri();
     headers.setLocation(locationUri);
     ResponseEntity<Spittle> responseEntity =
         new ResponseEntity<Spittle>(
             spittle, headers, HttpStatus.CREATED)
     return responseEntity;
   }
   
   // Step4 - If you are not interested to provide the url location, you can omit ResponseEntity and go with
   @RequestMapping(
       method=RequestMethod.POST
       consumes="application/json")
   @ResponseStatus(HttpStatus.CREATED)
   public Spittle saveSpittle(@RequestBody Spittle spittle) {
     return spittleRepository.save(spittle);
   }
```

## Links
- https://sztukakodu.pl/jak-definiowac-kody-http-odpowiedzi-w-springu/
- https://stackoverflow.com/questions/26549379/when-use-responseentityt-and-restcontroller-for-spring-restful-applications
- https://zetcode.com/springboot/responseentity/
- https://www.baeldung.com/spring-response-entity
