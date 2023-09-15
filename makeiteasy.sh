#!/bin/bash
COLORON="\033[95m"
COLOROFF="\033[0m"
SCRIPTHOME=`pwd`

# ######################################
# SUB ROUTINE
# ######################################
PUBKEY=""
getPubKey() {
   if [ "$PUBKEY" == "" ] ; then
      cd /helium/cli
      . ./env.sh
      PUBKEY=`./helium_cli env info | grep public_key | head -1 | cut -d ":" -f 2 | cut -d "\"" -f 2`
   fi
}


DEVADRRANGE=""
getAdrRange() {
   cd /helium/cli
   . ./env.sh
   entries=`./helium_cli org get | grep end_addr | wc -l`
   if [ $entries -gt 1 ] ; then
      echo "!! Your org have multiple devaddr range, only the first will be automatically configured"
   fi
   sstart=`./helium_cli org get | grep start_addr | head -1 | tr -s " " | cut -d ":" -f 2 | cut -d \" -f 2`
   sstop=`./helium_cli org get | grep end_addr | head -1 | tr -s " " | cut -d ":" -f 2 | cut -d \" -f 2`
   xstart=$((16#${sstart}))
   xstop=$((16#${sstop}))
   xsize=$(( xstop - xstart + 1 ))
   case $xsize in
      8)
	DEVADRRANGE="$sstart/29"
	;;
      16)
	DEVADRRANGE="$sstart/28"
	;;
      32)
	DEVADRRANGE="$sstart/27"
	;;
      64)
	DEVADRRANGE="$sstart/26"
	;;
      128)
	DEVADRRANGE="$sstart/25"
	;;
      256)
	DEVADRRANGE="$sstart/24"
	;;
      512)
	DEVADRRANGE="$sstart/23"
	;;
      1024)
	DEVADRRANGE="$sstart/22"
	;;
      *)
        echo "!! Invalid devadr range size"
	exit 4
	;;
   esac
}

SMTPSERVER=""
SMTPPORT=""
SMTPUSER=""
SMTPASS=""
askSmtpConfig() {
 if [ "$SMTPSERVER" == "" ] ; then
   read -p "Do you have a SMTP (email sending) configuration ready ? [Y/N] " smtpc
   case $smtpc in
      Y|y )
       ;;
      *)
       echo "You need to prepare your SMTP configuration before starting installation"
       echo "The service is usually using Sendgrid as email sender"
       echo "You need and API key and your contact email validated on Sendgrid to continue"
       exit 6
       ;;
   esac
   read -p "SMTP server name (default smtp.sendgrid.net) : " smtpserver
   if [ "$smtpserver" == "" ] ; then
     SMTPSERVER="smtp.sendgrid.net"
   else
     SMTPSERVER=$smtpserver
   fi
   read -p "SMTP server port (default 25) : " smtpport
   if [ "$smtpport" == "" ] ; then
     SMTPPORT=25
   else
     SMTPPORT=$smtpport
   fi
   read -p "SMTP server username (default apikey) : " smtpuser
   if [ "$smtpuser" == "" ] ; then
     SMTPUSER="apikey"
   else
     SMTPUSER=$smtpuser
   fi
   read -p "SMTP server password : " smtppass
   SMTPPASS=$smtppass
 fi
}


DOMAIN=""
PROTO=""
FULLDOMAIN=""
CONTACTEMAIL=""
askDomainName() {
  if [ "$DOMAIN" == "" ] ; then
    read -p "What is domain name (console.foor.bar) ? " domain
    DOMAIN=$domain
    read -p "Will it be HTTP or HTTPS ? " proto
    case $proto in
       HTTP | http )
          PROTO="http"
	  ;;
       HTTPS | https )
          PROTO="https"
	  ;;
       *)
	  echo "Invalid protocol"
	  exit 5
	  ;;
     esac
     FULLDOMAIN="$PROTO://$DOMAIN"
     read -p "What is the contact email to be used to send email (contact@foo.bar) ? " contact
     CONTACTEMAIL=$contact
  fi
}

cecho() {
  echo -e "${COLORON}### $1${COLOROFF}"
}

OUI="0"
NETID="00000"
DBPASSWORD=""
CHIRPSECRET=""

getDbPassword() {
  if [ "$DBPASSWORD" == "" ] ; then
    DBPASSWORD=`echo $RANDOM | md5sum | head -c 20`
  fi
}

getChirpSecret() {
  if [ "$CHIRPSECRET" == "" ] ; then
    CHIRPSECRET=`openssl rand -base64 32`
  fi
}

JWTSIGNKEY=""
getJwtSignKey() {
  if [ "$JWTSIGNKEY" == "" ] ; then
    JWTSIGNKEY=`echo $RANDOM | md5sum | head -c 32; echo $RANDOM | md5sum | head -c 32`
  fi
}

askForOui() {
  if [ $OUI == 0 ] ; then
    read -p "What is your OUI? " oui
    OUI=$oui
  fi
}

askForNetid() {
  if [ $NETID == "00000" ] ; then
    read -p "What is your NetId ? [24|3C] " netid
    case $netid in
      24 )
	 NETID="000024"
	 ;;
      3[Cc] )
	 NETID="00003C"
	 ;;
      *)
	echo "Invalid NetId"
	exit 3
    esac
  fi
}

APIKEY=""
createApiKey() {
  if [ "$APIKEY" == "" ] ; then
    cd /helium
    docker compose up -d chirpstack
    while ! docker exec -ti helium-chirpstack-1 /bin/ls >/dev/null 2>&1 ; do
     sleep 2
    done
    sleep 20
    APIKEY=`docker exec -ti helium-chirpstack-1 /usr/bin/chirpstack --config /etc/chirpstack create-api-key --name "admin key" | grep token | cut -d ":" -f 2 | tr -d "\r "`
    if [ "$APIKEY" == "" ] ; then
      echo "Chirpstack API key failure... retrying"
      APIKEY=`docker exec -ti helium-chirpstack-1 /usr/bin/chirpstack --config /etc/chirpstack create-api-key --name "admin key" | grep token | cut -d ":" -f 2 | tr -d "\r "`
    fi
    docker compose stop
    if [ "$APIKEY" == "" ] ; then
      echo "Chirpstack API key creation error"
      exit 7
    fi
  fi
}


# #######################################
# MAIN
# #######################################

# verification of the prerequisites
if [ "$UID" != "0" ]; then
  echo "Please run install script as root"
  exit 1
fi

if ! grep PRETTY /etc/os-release | grep -i "ubuntu.*22" > /dev/null ; then
  read -p "This script has been made for Ubuntu 22.x, are your sur you want to continue [Y/N] ?" resp
  case $resp in
    [Nn] )
       exit 2
       ;;
  esac
fi

# Add Yarn repo
if [ ! -f /etc/apt/sources.list.d/yarn.list ] ; then
  curl -sL https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
  echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list
fi

# Make sure we are up-to-date
cecho "Making sure your system is up-to-date"
DEBIAN_FRONTEND=noninteractive apt update -qq >/dev/null 2>/dev/null
DEBIAN_FRONTEND=noninteractive apt upgrade -y -qq >/dev/null 2>/dev/null

# Install the expected packages
cecho "Installing required packages"
for package in default-jdk make build-essential libssl-dev pkg-config protobuf-compiler yarn ca-certificates curl gnupg; do
  if ! dpkg -l | grep -q "^ii.*$package" ; then
    echo "[x] $package"
    DEBIAN_FRONTEND=noninteractive apt install $package -y -qq >/dev/null
  else
    echo "[ ] $package"
  fi
done

# Install docker environement
DOCKER=0
if command -v docker >/dev/null 2>&1; then
  # we have docker, do we need to install ?
  if [ -f /etc/apt/keyrings/docker.gpg ] ; then
     # looks like the install we want
     DOCKER=1
  else
     # looks like a different install
     DOCKER=1
     read -p "Your Docker install seems different than the one expected, can we reinstall it [Y/N] ?" resp
     case $resp in
      [Yy] )
  	for pkg in docker.io docker-doc docker-compose podman-docker containerd runc; do sudo apt-get remove $pkg; done
	DOCKER=0
       ;;
     esac
  fi
fi
if [ $DOCKER -eq 0 ] ; then
  cecho "Installing Docker"
  install -m 0755 -d /etc/apt/keyrings
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
  chmod a+r /etc/apt/keyrings/docker.gpg
  echo \
  "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  DEBIAN_FRONTEND=noninteractive apt update -qq >/dev/null 2>/dev/null
  DEBIAN_FRONTEND=noninteractive apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y -qq >/dev/null
fi

# Install Rust
if [ ! -f $HOME/.cargo/env ] ; then
  cecho "Installing Rust"
  curl https://sh.rustup.rs -sSf | sh -s -- -y >/dev/null
fi

# Install Helium-Chirpstack
if [ ! -d /helium/configuration ] ; then
 cecho "Creating helium-chirpstack structure"
 make init
fi

# Helium cli
if [ ! -f /helium/cli/helium_cli ] ; then
 cecho "Build helium-cli"
 cd /tmp/
 source "$HOME/.cargo/env"
 git clone https://github.com/helium/helium-config-service-cli.git
 cd helium-config-service-cli
 cargo b --release
 cp target/release/helium-config-service-cli /helium/cli/helium_cli
 cargo clean
 cd /helium/cli
 ./helium_cli env generate-keypair keypair.bin
 askForOui
 askForNetid
 echo "export HELIUM_CONFIG_HOST=http://mainnet-config.helium.io:6080/" > /helium/cli/env.sh
 echo "export HELIUM_KEYPAIR_BIN=./keypair.bin" >> /helium/cli/env.sh
 echo "export HELIUM_NET_ID=${NETID}">> /helium/cli/env.sh
 echo "export HELIUM_OUI=${OUI}" >> /helium/cli/env.sh
 echo "export HELIUM_MAX_COPIES=99" >> /helium/cli/env.sh
fi

if egrep "@DBPASSWORD@" /helium/configuration/postgresql/initdb/001-init-chirpstack.sh >/dev/null ; then
  cecho "Configuring database"
  getDbPassword
  sed -i "s/@DBPASSWORD@/$DBPASSWORD/g" /helium/configuration/postgresql/initdb/001-init-chirpstack.sh
  sed -i "s/@DBPASSWORD@/$DBPASSWORD/g" /helium/configuration/chirpstack/chirpstack.toml
  sed -i "s/@DBPASSWORD@/$DBPASSWORD/g" /helium/configuration/helium/configuration.properties
fi

if egrep "@CHIRPSECRET@" /helium/configuration/chirpstack/chirpstack.toml > /dev/null ; then
  cecho "Configuring chirpstack"
  getChirpSecret
  sed -i "s#@CHIRPSECRET@#$CHIRPSECRET#g" /helium/configuration/chirpstack/chirpstack.toml
  askForNetid
  sed -i "s/@NETID@/$NETID/g" /helium/configuration/chirpstack/chirpstack.toml
  getAdrRange
  sed -i "s!@ADDRRANGE@!$DEVADRRANGE!g" /helium/configuration/chirpstack/chirpstack.toml
fi

# configure configuration.properties
if egrep "@DOMAIN@" /helium/configuration/helium/configuration.properties >/dev/null ; then
 askDomainName
 askSmtpConfig
 cecho "Creating an API key from chirpstack"
 createApiKey
fi

if egrep "@DOMAIN@" /helium/configuration/helium/configuration.properties > /dev/null ; then
  cecho "Configuring helium companion"
  askForNetid
  askForOui
  getJwtSignKey
  getPubKey
  sed -i "s|@FULLDOMAIN@|$FULLDOMAIN|g" /helium/configuration/helium/configuration.properties
  sed -i "s/@DOMAIN@/$DOMAIN/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@CONTACTEMAIL@/$CONTACTEMAIL/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@NETID@/$NETID/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@OUI@/$OUI/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@JWTSIGNKEY@/$JWTSIGNKEY/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@HELIUMPUBKEY@/$PUBKEY/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@APIADMINKEY@/$APIKEY/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@SMTPSERVER@/$SMTPSERVER/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@SMTPPORT@/$SMTPPORT/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@SMTPUSER@/$SMTPUSER/g" /helium/configuration/helium/configuration.properties
  sed -i "s/@SMTPPASS@/$SMTPPASS/g" /helium/configuration/helium/configuration.properties
fi

# build the companion
cecho "Building the companion software"
cd $SCRIPTHOME
make build

# configure nginx
if egrep "@DOMAIN@" /helium/configuration/nginx/default.conf > /dev/null ; then
   cecho "Configuring nginx"
   askDomainName
   if [ "$PROTO" == "http" ] ; then
      cecho "Configuring as http"
      cp /helium/configuration/nginx/default.conf /helium/configuration/nginx/default.conf.setup
      cp /helium/configuration/nginx/default.conf.nossl /helium/configuration/nginx/default.conf
      sed -i "s|@DOMAIN@|$DOMAIN|g" /helium/configuration/nginx/default.conf
   else
      cecho "Configuring as https"
      sed -i "s|@DOMAIN@|$DOMAIN|g" /helium/configuration/nginx/default.conf
      cecho "Getting certificate"
      cd /helium
      docker compose up -d nginx
      sleep 10
      docker compose run --rm certbot certonly --webroot --webroot-path /var/www/certbot/ -d $DOMAIN
      sleep 5
      docker compose stop
      mv /helium/configuration/nginx/default.conf /helium/configuration/nginx/default.conf.withoutssl
      cp /helium/configuration/nginx/default.conf.withssl /helium/configuration/nginx/default.conf
      sed -i "s|@DOMAIN@|$DOMAIN|g" /helium/configuration/nginx/default.conf
   fi
fi


# ready to run it !
cecho "Installation completed"
getPubKey
cd /helium
echo "The installation is completed, before being able to run it, you must request Helium Foundation"
echo "to add your public key ($PUBKEY) as a delegated key for you OUI"
echo "Make sure to open TCP ports 80/443 and UDP 1700-1708"
echo "Once done, you just need, to get started, to run docker compose up -d"
echo "Default admin user password is admin, please change it !"

