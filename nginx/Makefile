###
# Parameters
###
NGINX_VERSION=1.7.3
IN_PORT=8060
PROXY_PASS="http://127.0.0.1:8080"

# Please ignore
.PHONY : run reload stop install clean dirs foo
curdir = $(shell pwd)

# Everything
all: conf/nginx.conf dirs run

# Nginx commands
run:
	nohup ./sbin/nginx
reload:
	./sbin/nginx -s reload
stop:
	./sbin/nginx -s quit

# Installation commands
dirs:
	mkdir -p html/js html/css html/img

conf/nginx.conf: nginx.conf sbin/nginx
	@echo "Configuring";\
	cat nginx.conf \
	| sed "s%XXX_PROXY_PASS_XXX%$(PROXY_PASS)%" \
	| sed "s%XXX_IN_PORT_XXX%$(IN_PORT)%" \
	| sed "s%XXX_NGINX_PATH_XXX%$(curdir)%" \
	> conf/nginx.conf

sbin/nginx: nginx-$(NGINX_VERSION)/objs/nginx
	@echo "Installing";\
	$(MAKE) -C nginx-$(NGINX_VERSION) install
	rm -f conf/nginx.conf

nginx-$(NGINX_VERSION)/objs/nginx: nginx-$(NGINX_VERSION)/Makefile
	@echo "Building";\
	$(MAKE) -C nginx-$(NGINX_VERSION) all

nginx-$(NGINX_VERSION)/Makefile: nginx-$(NGINX_VERSION)/configure
	@echo "Configuring";\
	cd nginx-$(NGINX_VERSION) && \
	  ./configure --prefix=$(curdir)

nginx-$(NGINX_VERSION)/configure: nginx-$(NGINX_VERSION).tar.gz
	@echo "Decompressing";\
	tar -xvzf nginx-$(NGINX_VERSION).tar.gz

nginx-$(NGINX_VERSION).tar.gz:
	@echo "Downloading";\
	wget http://nginx.org/download/nginx-$(NGINX_VERSION).tar.gz

clean:
	rm -rf *.gz nginx-$(NGINX_VERSION) conf html logs sbin
