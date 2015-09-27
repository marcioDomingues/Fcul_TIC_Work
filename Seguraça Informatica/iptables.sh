#!/bin/sh 
# This is a comment 
echo "usage ./iptables.sh <secShareServer address> <secShareServer port>" 

IP=$1
PORT=$2

echo "args:" 
echo adress:  $IP 
echo port:  $PORT 

# Delete all existing rules
sudo /sbin/iptables -F

# Setting default filter policy
# block all
sudo /sbin/iptables -P INPUT DROP
sudo /sbin/iptables -P OUTPUT DROP
sudo /sbin/iptables -P FORWARD DROP

# PERMIÇÔES PARA SERVIÇOS SUPORTADOS

# Allow incoming icmp only from nemo
# Permite a entrada de pacotes ICMP do tipo "echo-request"("ping") da maquina nemo, com o ip 10.101.85.18.
# Permite a saida de pacotes IMCP do tipo "echo-reply" para a mesma maquina.
# Flag 0 para "echo-request"
# Flag 8 para "echo-request"
# O protocolo ICMP pode considerar sessões, depois de um pedido-resposta ("ping-pong" roundtrip) 
# e pode considerar os pacotes seguintes como em modo ESTABLISHED. 
# (ver tutorial iptables 1.2.1 por Oskar Andreasson capitulo 7 ponto 6 "ICMP connections"[1])
# No nosso caso nao definimos regras respeitantes ao estado da ligação.  
#  
# [1]https://www.frozentux.net/iptables-tutorial/chunkyhtml/x1582.html
sudo /sbin/iptables -A INPUT -p icmp --icmp-type 8 -s 10.101.85.18 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -p icmp --icmp-type 0 -d 10.101.85.18 -j ACCEPT

# Allow tcp from clients to secure server and allow replies
# Aceita pedidos de ligação TCP ao servidor e ao porto do serviço SecShareServer
# Permite o trafego de entrada e saida de pacotes relacionados com ligações já estabelecidas com 
# o servidor SecShareServer.
sudo /sbin/iptables -A INPUT -p tcp -d $IP --dport $PORT -m state --state NEW,ESTABLISHED -j ACCEPT 
sudo /sbin/iptables -A OUTPUT -p tcp -s $IP --sport $PORT -m state --state ESTABLISHED -j ACCEPT

# Allow incoming SSH only from a spcific network
# Permite a entrada de pacotes com pedidos de ligacao SSH (serviços de Secure SHell) ao porto 22 à maquina do 
# servidor por IP's de clientes provinientes da rede interna ao laboratorio, na gama de IP's 10.101.151.30/22.
# Permite o trafego de entrada e saida de pacotes relacionados com ligações já estabelecidas. 
sudo /sbin/iptables -A INPUT -p tcp -s 10.101.151.30/22 --dport 22 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo /sbin/iptables -A OUTPUT -p tcp -d 10.101.151.30/22 --sport 22 -m state --state ESTABLISHED -j ACCEPT

# Allow DNS services
# Quando um utilizador corre um "query" de DNS na maquina onde esta o servidor
# sao permitidas a saida de todos os pacotes para o porto 53.
# E permitida igualmente a entrada de pacotes de reply aos "queries" aos portos acima de 1024
# de onde foi feito o query.
# Sao permitidas ligaçoes UDP e TCP devido as caracteristicas do serviço de DNS:
# "queries" DNS inferiores a 512 bytes utilizam o protocolo UDP 
# "queries" de maior dimensao utilizam TCP 
# Ja que nenhuma expecificacao foi dada no enunciado, decidiu-se dar permicao aos dois protocolos
sudo /sbin/iptables -A OUTPUT -p udp -s $IP --sport 1024:65535 --dport 53 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo /sbin/iptables -A INPUT -p udp --sport 53 -d $IP --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT

sudo /sbin/iptables -A OUTPUT -p tcp -s $IP --sport 1024:65535 --dport 53 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo /sbin/iptables -A INPUT -p tcp --sport 53 -d $IP --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
 

# alow services from the machines needed
# Permite o trafego de entrada e saida de pacotes direccionados
# aos ip's referidos no enunciado, garantindo o normal funcionamento dos laboratorios 
# DCs
sudo /sbin/iptables -A INPUT -s 10.101.85.10 -j ACCEPT
sudo /sbin/iptables -A INPUT -s 10.101.253.11 -j ACCEPT
sudo /sbin/iptables -A INPUT -s 10.101.253.12 -j ACCEPT
sudo /sbin/iptables -A INPUT -s 10.101.253.13 -j ACCEPT

sudo /sbin/iptables -A OUTPUT -d 10.101.85.10 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -d 10.101.253.11 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -d 10.101.253.12 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -d 10.101.253.13 -j ACCEPT

#Storage
sudo /sbin/iptables -A INPUT -s 10.101.249.63 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -d 10.101.249.63 -j ACCEPT

#Iate/Falua
sudo /sbin/iptables -A INPUT -s 10.101.85.6 -j ACCEPT
sudo /sbin/iptables -A INPUT -s 10.101.85.138 -j ACCEPT

sudo /sbin/iptables -A OUTPUT -d 10.101.85.6 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -d 10.101.85.138 -j ACCEPT

#Nemo
sudo /sbin/iptables -A INPUT -s 10.101.85.18 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -d 10.101.85.18 -j ACCEPT

#proxy
sudo /sbin/iptables -A INPUT -s 10.101.85.134 -j ACCEPT
sudo /sbin/iptables -A OUTPUT -d 10.101.85.134 -j ACCEPT


#allow loopback
sudo /sbin/iptables -A INPUT -i lo -j ACCEPT
sudo /sbin/iptables -A OUTPUT -o lo -j ACCEPT

#allow all establish
sudo /sbin/iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT 
sudo /sbin/iptables -A OUTPUT -m state --state ESTABLISHED,RELATED -j ACCEPT



