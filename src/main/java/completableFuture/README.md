# Usage of CompletableFuture  
Suppose we are going to design a weather forecaster to gather information from website.  
We have 5 weather centers and 1 health center to gather data from.  

Weather center offers a service to query temperature and moisture at specific location.  
Health center offers a service to query comfort level at specific weather(temperature, moisture). 
 
Unfortunately, both service offer a synchronous API only.  

How can we use these API to take asynchronous calls?
