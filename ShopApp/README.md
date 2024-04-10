# Create react app in docker

Create docker hub repository - publish
```
docker build -t pd112-api . 
docker run -it --rm -p 5559:8080 --name pd112_container pd112-api
docker run -d --restart=always --name pd112_container -p 5559:8080 pd112-api
docker ps -a
docker stop pd112_container
docker rm pd112_container

docker images --all
docker rmi pd112-api

docker login
docker tag pd112-api:latest novakvova/pd112-api:latest
docker push novakvova/pd112-api:latest

docker pull novakvova/pd112-api:latest
docker ps -a
docker run -d --restart=always --name pd112_container -p 5559:8080 novakvova/pd112-api


docker pull novakvova/pd112-api:latest
docker images --all
docker ps -a
docker stop pd112_container
docker rm pd112_container
docker run -d --restart=always --name pd112_container -p 5559:8080 novakvova/pd112-api
```

```nginx options /etc/nginx/sites-available/default
server {
    server_name   pd112.itstep.click *.pd112.itstep.click;
    location / {
       proxy_pass         http://localhost:5559;
       proxy_http_version 1.1;
       proxy_set_header   Upgrade $http_upgrade;
       proxy_set_header   Connection keep-alive;
       proxy_set_header   Host $host;
       proxy_cache_bypass $http_upgrade;
       proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
       proxy_set_header   X-Forwarded-Proto $scheme;
    }
}

sudo systemctl restart nginx
certbot
```



