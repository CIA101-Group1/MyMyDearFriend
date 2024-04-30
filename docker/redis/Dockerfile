FROM redis:7.0.8

MAINTAINER peihui

EXPOSE 6379

COPY redis.conf /usr/local/etc/redis/redis.conf
CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]
