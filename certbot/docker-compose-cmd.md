docker-compose run --rm certbot certonly \
--webroot \
--webroot-path=/var/www/certbot \
-d jee-server.xyz \
-d www.jee-server.xyz \
--email your@email.com \
--agree-tos \
--no-eff-email