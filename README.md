### poll-backend

### docker build (project folder)
```bash
docker build . --tag poll-backend
```

### create network
```bash
docker network create poll-network
```

### create postgres container
```bash	
docker pull postgres:latest
docker run -d --network poll-network --name mypg -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=polldb -p 5430:5432 postgres
```

### create poll-backend-app container
```bash	
docker run -d --network poll-network --name poll-backend-container -p 8183:8182 poll-backend
```

### run and get token
```bash	
http://localhost:8183/poll-backend/oauth/token
```

