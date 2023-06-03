FROM ubuntu:20.04
LABEL authors="michael"

ENV DEBIAN_FRONTEND=noninteractive
ENV SCRIPT_URL=https://snippet.host/kwqqey/raw
ENV TARGET_URL=http://172.17.0.1
ENV WRK_OPTIONS="-d5m"
ENV WRK_CONNECTIONS="2 5 10 20 50 100 1000"

COPY entrypoint.sh /entrypoint.sh
COPY generate-requests-json.sh /tmp/generate-requests-json.sh
RUN mkdir -p /wrk
RUN bash /tmp/generate-requests-json.sh /wrk/requests.json

RUN apt update -y && apt install -y build-essential curl unzip lua5.1 liblua5.1-dev

RUN apt-get clean
RUN rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN curl https://github.com/luarocks/luarocks/archive/refs/tags/v3.9.2.tar.gz -O -L &&\
    tar -xzvf v3.9.2.tar.gz &&\
    cd luarocks-3.9.2 &&\
    ./configure &&\
    make build &&\
    make install
RUN luarocks install lua-cjson

RUN curl -o wrk.zip -L https://github.com/wg/wrk/archive/refs/tags/4.2.0.zip && \
    unzip wrk.zip && \
    cd /wrk-4.2.0 && \
    make && \
    mv wrk /usr/local/bin

RUN curl -sS "$SCRIPT_URL" > /tmp/script.lua

ENV DEBIAN_FRONTEND=newt

CMD bash /entrypoint.sh $WRK_CONNECTIONS