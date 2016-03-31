# -*- coding: utf-8 -*-
from fabric.api import env
from fabric.api import cd
from fabric.api import run
from fabric.api import sudo
from fabric.api import local
from fabric.api import get
from fabric.api import put
from fabric.context_managers import cd
from fabric.context_managers import lcd
from fabric.contrib.files import exists
import sys


env.user = 'root'
env.password = 'zjuLYP2014'
env.hosts = ['115.28.77.19']

def get_version():
    run('mvn -version')
    run('ls')
        
def make_package_local():
    with lcd('..'):
        local('mvn clean install')    


def restart_server():
    sudo('/usr/java/tomcat7/bin/shutdown.sh')
    sudo('/usr/java/tomcat7/bin/startup.sh')

def start_server():
    sudo('/usr/java/tomcat7/bin/startup.sh')

def copy_file():
    local('rsync -aruv --delete-before ../target/SSM/WEB-INF/ %s@%s:~/WEB-INF/' % (env.user, env.hosts[0]))
    sudo('cp -r /root/WEB-INF /usr/java/tomcat7/webapps/SSM/')
    
def clean():
	with cd('/usr/java/tomcat7/webapps/SSM/WEB-INF/'):
		sudo('rm -rf *')
	
def log():
    sudo('tail -f /usr/java/tomcat7/logs/catalina.out')

def deploy(force=False):
    make_package_local()
    copy_file()
    restart_server()
    log()


    
