sudo docker ps -a -q --filter "name=back" | grep -q . && docker stop back && docker rm back | true
docker run -d -p 8080:8080 --name back e105/back:1.0 -e Profile=dev
docker image prune -af
