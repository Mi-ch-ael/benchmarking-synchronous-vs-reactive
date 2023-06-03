#!/bin/sh

for number in "$@"
	do :
        	wrk -s /tmp/script.lua "${WRK_OPTIONS}" -c"${number}" \
        	 "${TARGET_URL}:8080/"
	done
for number in "$@"
	do :
        	wrk -s /tmp/script.lua "${WRK_OPTIONS}" -c"${number}" \
        	 "${TARGET_URL}:8090/"
	done