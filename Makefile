load:
	while true; do \
		curl --silent --output /dev/null http://localhost:8081/facts/123456; \
		sleep 0.1; \
	done \

load-client-errors:
	while true; do \
		curl --silent --output /dev/null http://localhost:8081/facts/100001; \
		sleep 0.5; \
	done \

load-server-errors:
	while true; do \
		curl --silent --output /dev/null http://localhost:8081/errors; \
		sleep 0.9; \
	done \

chaos:
	docker exec toxiproxy /toxiproxy-cli toxic add --toxicName base-latency --type latency --downstream --toxicity 1.0 --attribute latency=500 --attribute jitter=0 fact-db

order:
	docker exec toxiproxy /toxiproxy-cli toxic remove --toxicName base-latency fact-db
