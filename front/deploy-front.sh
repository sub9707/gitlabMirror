
sudo docker ps -a -q --filter "name=front" | grep -q . && docker stop front && docker rm front | true
docker run -d -p 80:80 -p 443:443 -v /home/ubuntu/nginx/conf.d:/etc/nginx/conf.d --name front e105/front:1.0
docker image prune -af
