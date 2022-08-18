pipeline {
    agent any
    parameters {
        string defaultValue: '300', name: 'INTERVAL'
    }
    environment {
        CRED = credentials('credentials')
        CONFIG = credentials('config')
        DOCKER = 'ec2app'

    }

    stages {
        stage('Initialize') {
            steps {
                cleanWs()
                sh "docker kill ${DOCKER}-${env.BUILD_NUMBER} || true"
                // sh "docker ps -q -f status=up | xargs --no-run-if-empty docker kill || true"
                sh "docker ps -q -f status=exited | xargs --no-run-if-empty docker rm || true"
                sh "docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi"
                sh "docker rm ${DOCKER}-${env.BUILD_NUMBER} || true"
                sh "docker rmi -f ${DOCKER}-${env.BUILD_NUMBER} || true"
            }
        }
        stage('Get SCM') {
            steps {
                git branch: 'main', url: 'https://github.com/saleh2784/ec2-proj.git'
                // git "https://github.com/saleh2784/ec2-proj.git"
            }
        }
        stage('docker build'){
            steps {
                sh "cat $CRED | tee credentials"
                sh "cat $CONFIG | tee config"
                sh "docker build -t ${DOCKER}-${env.BUILD_NUMBER} . "
                sh "docker images"
            }
        }
        stage('docker Run & Deploy'){
            steps {
                sh "docker run -itd --name ${DOCKER}-${env.BUILD_NUMBER} --env INTERVAL=${params.INTERVAL} ${DOCKER}-${env.BUILD_NUMBER} &"  
            }
        }
    }   
}


// https://github.com/ranazrad/machineScanner

