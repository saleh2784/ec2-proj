pipeline {
    agent any
    parameters {
        string defaultValue: '300', name: 'INTERVAL'
    }
    environment {
        CRED = credentials('credentials')
        CONFIG = credentials('config')
        DOCKER = 'ec2app'
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
    }

    stages {
        stage('Print Build Number') {
            steps {
                print(env.BUILD_NUMBER)

            }
        }
 
        stage('Initialize') {
            steps {
                // cleanWs()
                // NTC {env.BUILD_NUMBER} =! previous BUILD_NUMBER (env.previous.BUILD_NUMBER)
                // kill old containers 
                sh "docker kill ${DOCKER} || true"
                // Removing exited containers
                sh "docker ps -q -f status=exited | xargs --no-run-if-empty docker rm || true"
                //docker delete none tag images
                sh "docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi"
                // Removing old containers
                sh "docker rm ${DOCKER}-${env.BUILD_NUMBER} || true"
                //delete old images
                sh "docker rmi -f ${DOCKER}-${env.BUILD_NUMBER} || true"
            }
        }
        stage('Get SCM') {
            steps {
                git branch: 'main', url: 'https://github.com/saleh2784/ec2-proj.git'
            }
        }
        stage('docker build'){
            steps {
                // get the .aws credentials & config to use them in the container
                sh "cat $CRED | tee credentials"
                sh "cat $CONFIG | tee config"
                
                // build the image from the Dockerfile
                sh "docker build -t ${DOCKER}-${env.BUILD_NUMBER} . "
                
                // sh "docker build -t saleh2784/${DOCKER}-${env.BUILD_NUMBER}:${tagname} . "
                //sh 'docker build -t bharathirajatut/nodeapp:latest .'
                // view the docker images
                sh "docker images"
            }
        }
        stage('docker Run & Deploy'){
            steps {
                // running the container with the Inteval time and with the build number (the name of the container include the build number)
                sh "docker run -itd --name ${DOCKER}-${env.BUILD_NUMBER} --env INTERVAL=${params.INTERVAL} ${DOCKER}-${env.BUILD_NUMBER}" 
                // sh "docker run -itd --name saleh2784/${DOCKER}-${env.BUILD_NUMBER}:${tagname} --env INTERVAL=${params.INTERVAL} saleh2784/${DOCKER}-${env.BUILD_NUMBER} &"  

            }
        }
        stage('DockerHub Login') {

			steps {
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin '
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin  ' 
			}
		}

		stage('Push the image to DockerHub') {

			steps {
			    sh 'docker tag ${DOCKER}-${env.BUILD_NUMBER}:latest saleh2784/${DOCKER}:latest'
				sh 'docker push saleh2784/${DOCKER}:latest'
				// to download the image from the dockerhub run this command below :
				// docker pull saleh2784/ec2app:tagname
			}
		}
    }
	post {
        always {
            // Removing login credentials
		    sh 'docker logout'
		}
    }
}
// used for reference:  https://github.com/ranazrad/machineScanner