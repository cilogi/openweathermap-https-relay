# HTTPS access to OpenWeatherMap API

[OpenWeatherMap](https://openweathermap.org/api) provides a free API which can be called from the Browser
to get weather information worldwide.

The free tier of the API does not allow access via <code>HTTPS</code>.  Unfortunately, now that browsers have tightened
up access restrictions this means that any web app which uses <code>HTTPS</code> can't access the API.

This code provides a simple Java App which runs on Google App Engine and relays requests.

So, if you have a request like

    http://api.openweathermap.org/data/2.5/weather?q=Glasgow&APPID=<token>

you can install this relay (for free) on App Engine and call

    https://<your-app-id>.appspot.com/data/2.5/weather?q=Glasgow&APPID=<token>

There is a copy of the app running at

    https://openweathermap-https-relay.appspot.com

Feel free to use this copy rather than installing your own, but it may stop working if the (very limited) resources
are exhausted.

The License is GNU.