
java -jar .\target\profile-0.0.1-SNAPSHOT.jar  
curl http://localhost:8081/


StatusCode        : 200
StatusDescription :
Content           : Hello from default properties
RawContent        : HTTP/1.1 200
Keep-Alive: timeout=60
Connection: keep-alive
Content-Length: 29
Content-Type: text/plain;charset=UTF-8
Date: Mon, 04 Nov 2024 22:14:00 GMT

                    Hello from default properties
Forms             : {}
Headers           : {[Keep-Alive, timeout=60], [Connection, keep-alive], [Content-Length, 29], [Content-Type, text/plain;charset=UTF-8]...}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : mshtml.HTMLDocumentClass
RawContentLength  : 29

java -jar .\target\profile-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod  
curl http://localhost:8082/


StatusCode        : 200
StatusDescription :
Content           : Hello from production profile
RawContent        : HTTP/1.1 200
Keep-Alive: timeout=60
Connection: keep-alive
Content-Length: 29
Content-Type: text/plain;charset=UTF-8
Date: Mon, 04 Nov 2024 22:15:01 GMT

                    Hello from production profile
Forms             : {}
Headers           : {[Keep-Alive, timeout=60], [Connection, keep-alive], [Content-Length, 29], [Content-Type, text/plain;charset=UTF-8]...}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : mshtml.HTMLDocumentClass
RawContentLength  : 29

    