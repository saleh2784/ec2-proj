pipeline {
    agent any
    parameters {
        string(name: 'INTERVAL', defaultValue: '300' )
        choice choices: [ 'development', 'main', 'saleh'], name: 'branch'
        string(name: 'TAG', defaultValue: '1' )

    }
    environment {
//         CRED = credentials('credentials')
//         CONFIG = credentials('config')
        DOCKER = 'ec2app'
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
        GIT_AUTH = credentials('github')
        // int TAG = readFile(file: 'tag.txt')
        
    }

    stages {
        stage('Print Build Number') {
            steps {
                script {
                    echo "my Build number is : ${BUILD_NUMBER}"
                }
            }
        }
        
        stage('Initialize') {
            steps {
                cleanWs()
                // kill old containers !!!
                sh "docker kill ${DOCKER}:${params.TAG} || true"
                // Removing exited containers 
                sh "docker ps -q -f status=exited | xargs --no-run-if-empty docker rm || true"
                //delete old images 
                // sh 'docker image prune -fa || true'


            }
        }
        stage('Get SCM') {
            // when {
            //     expression{
            //         branch '${params.branch}'
            //     }
            // }
            steps {
                git branch: 'development', credentialsId: 'github', url: 'https://github.com/saleh2784/ec2-proj.git'

                // git branch: "${params.branch}", url: 'https://github.com/saleh2784/ec2-proj.git'
            }
        }
        stage('Git Push to Main'){
            steps{
                // git branch: 'development', credentialsId: 'github', url: 'https://github.com/saleh2784/ec2-proj.git'
                sh 'git config --local credential.helper "!f() { echo username=$GIT_AUTH_USR; echo password=$GIT_AUTH_PSW; }; f"'
                // sh 'git config --global user.name \"saleh2784\"'
                // sh 'git config --global user.email saleh2784@gmail.com'
                sh 'echo \"hello world\" > ss.txt'
                sh 'git add ss.txt'
                sh 'git commit -am \"test\"'
                sh 'git push origin saleh'  
            }
        }
        
        stage('docker build'){
            steps {
                // get the .aws credentials & config to use them in the container
                // sh "cat $CRED | tee credentials"
                // sh "cat $CONFIG | tee config"
                
                // build the image from the Dockerfile
                sh (script: "docker build -t ${DOCKER}:${params.TAG} . ", returnStdout: true)
                // sh "docker build -t ${DOCKER}:${TAG} . "

                // view the docker images
                sh "docker images"
            }
        }
        stage('docker Run & Deploy'){
            steps {
                // running the container with the Inteval time and with the build number (the name of the container include the build number)
                sh (script : "docker run -itd --name ${DOCKER} --env INTERVAL=${params.INTERVAL} ${DOCKER}:${params.TAG}", returnStdout: true  )
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
			    echo "${DOCKER}:${TAG}.${BUILD_NUMBER}"
                // docker tag from the local repo
                sh """docker tag ${DOCKER}:${params.TAG} saleh2784/${DOCKER}:${params.TAG}.${BUILD_NUMBER}"""
			    // push the image with the Build_number to docker-hub
				sh (script : """docker push saleh2784/${DOCKER}:${params.TAG}.${BUILD_NUMBER}""", returnStdout: false)
                // echo the name of the image that i push to docker-hub 
                echo " my push image name is : ${DOCKER}:${params.TAG}.${BUILD_NUMBER}"
				// to download the image from the dockerhub run this command : "docker pull saleh2784/ec2app:tagname.build_number"
               
			}
		}
        stage('helm') {
            
			steps {
			   
                // install yq
                sh (script : """ apt install wget -y""", returnStdout: false)
                sh (script : """wget https://github.com/mikefarah/yq/releases/latest/download/yq_linux_amd64 -O /usr/bin/yq &&\
                chmod +x /usr/bin/yq""", returnStdout: false)
			    // need to check the path for the helm ## /home/jenkins/workspace/ec2/helm-lab
                dir('/home/jenkins/workspace/ec2/helm-lab/') {
                sh (script : """ cat values.yaml | grep tag """)
                sh (script : """ yq -i \'.image.tag = \"${params.TAG}.${BUILD_NUMBER}\"\' values.yaml """, returnStdout: false)
                sh (script : """ cat values.yaml | grep tag """)
                }
               
			}
		}
        
        // stage('Git Push to Main'){
        //     steps{
        //         // git branch: 'development', credentialsId: 'github', url: 'https://github.com/saleh2784/ec2-proj.git'
        //         sh 'git config --local credential.helper "!f() { echo username=$GIT_AUTH_USR; echo password=$GIT_AUTH_PSW; }; f"'
        //         // sh 'git config --global user.name \"saleh2784\"'
        //         // sh 'git config --global user.email saleh2784@gmail.com'
        //         sh 'echo \"hello world\" > 2.txt'
        //         sh 'git add 2.txt'
        //         sh 'git commit -am \"test\"'
        //         sh 'git push origin HEAD:main'  
        //     }
        // }

    }
	post {
        always {
            // docker logout 
		    sh 'docker logout'
		}
    }

}
