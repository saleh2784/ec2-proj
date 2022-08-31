# ec2-proj
# create ec2.py code 
# create Dcokerfile 
# create pipeline deckartive
# put the pipeline as Jenkinsfile in github
# github wehbooks 
# branch (DEV / PROD) --- The above setup should allow you to run two Jenkins servers (DEV / PROD) where you can tests , upgrade and update your Jenkins dev env before you merge your changes into MAIN
# $JENKINSBUILDNUMBER --- -	Use jenkins to run your build on every push under branch development. Mark every image created with the number of the $JENKINSBUILDNUMBER. For example:yanivomc/k8stest:JENKINSBUILDNUMBER
# Create a manual step that depends on all build steps before it allows you to deploy your application to your local DOCKER Engine (by running docker stop , pull , run ) 

Manage Jenkins :
1. Manage Plugins :
"Install these plugins"
a. Pipeline: Stage View
b. Pipeline Utility Steps
c. Workspace Cleanup

2. Manage Credentials :
how to run the app :
a. config file for ec2 
b. credentials file for ec2 
c. dockerhube credenyials (user name & secret key) 

How to run the Pipeline: 

1. go to jenkins 
2. create new jop "ec2-app" & chose Pipeline
3. choose Pipeline script fro SCM
4. In SCM choose Git
5. put this repo in the Repository URL = https://github.com/saleh2784/ec2-proj.git
6. in branch put : */main
7. in the script patch = Jenkinsfile
8. in Build Triggers mark "GitHub hook trigger for GITScm polling" 
9. go to the repo : https://github.com/saleh2784/ec2-proj.git & replace the tag number in tag.txt that you want for example : 3.1
10. run the pipeline 



