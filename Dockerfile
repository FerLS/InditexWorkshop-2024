# Base image
FROM mysql:8.0.36

# Env vars
ENV MYSQL_DATABASE=database_name
ENV MYSQL_ROOT_PASSWORD=Hackathon032024!

# Copy .sql file to container - Docker version
COPY inditex-locker.sql /docker-entrypoint-initdb.d 
# Copy .sql file into /var/lib/mysql-init/ - Podman version
COPY inditex-locker.sql /var/lib/mysql-init/

# Expose port
EXPOSE 3306