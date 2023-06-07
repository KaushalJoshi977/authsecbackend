#!/bin/bash
REPO_NAME=10april
REPO_NAME_TO=10april
GITEA_USER=risadmin
GITEA_PASS=admin1234
GITEA_EMAIL=ganeshk@dekatc.com
GIT_BRANCH="origin main"
DOMAIN=13.126.217.36:31633
MSG=10april
#*************************************************************
GIT_URL=http://$DOMAIN
GIT_URL_FROM=http://$GIT_URL/$GITEA_USER/$REPO_NAME.git
GIT_URL_TO=http://$GIT_URL/$GITEA_USER/$REPO_NAME_TO.git
CURRENT_PATH=$(pwd)
sudo git clone http://$GITEA_USER:$GITEA_PASS@$DOMAIN/$GITEA_USER/$REPO_NAME.git
sudo git clone http://$GITEA_USER:$GITEA_PASS@$DOMAIN/$GITEA_USER/$REPO_NAME_TO.git
sudo cp -r $REPO_NAME/*  $REPO_NAME_TO
cd $CURRENT_PATH/$REPO_NAME_TO
sudo git config --global user.email $GITEA_EMAIL
sudo git config --global user.name $GITEA_USER
sudo git add .
sudo git checkout -b main
sudo git commit -m "$MSG"
git remote add origin http://$DOMAIN/$GITEA_USER/$REPO_NAME_TO.git
sudo git push -u $GIT_BRANCH
cd ..
#sudo rm -rf $REPO_NAME
#sudo rm -rf $REPO_NAME_TO