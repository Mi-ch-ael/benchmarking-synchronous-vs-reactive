#!/bin/bash

touch "$1"
echo -e "[" > "$1"
for i in {1..999} ; do :
  echo -e "{\"path\": \"/$i\", \"method\": \"GET\"}," >> "$1"
done
echo -e "{\"path\": \"/1000\", \"method\": \"GET\"}" >> "$1"
echo -e "]" >> "$1"