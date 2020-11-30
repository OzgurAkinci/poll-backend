# poll-backend

### Docker
```bash
docker build . --tag poll-backend
docker network create poll-network
docker pull postgres:latest
docker run -d --network poll-network --name mypg -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=polldb -p 5430:5432 postgres
docker run -d --network poll-network --name poll-backend-container -p 8183:8182 poll-backend
```
	
