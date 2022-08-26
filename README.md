# ec2-proj
# create ec2.py code 
# create Dcokerfile 
# create pipeline deckartive
# put the pipeline as Jenkinsfile in github
# github wehbook
# branch (DEV / PROD) --- The above setup should allow you to run two Jenkins servers (DEV / PROD) where you can tests , upgrade and update your Jenkins dev env before you merge your changes into MAIN
# $JENKINSBUILDNUMBER --- -	Use jenkins to run your build on every push under branch development. Mark every image created with the number of the $JENKINSBUILDNUMBER. For example:yanivomc/k8stest:JENKINSBUILDNUMBER
# Create a manual step that depends on all build steps before it allows you to deploy your application to your local DOCKER Engine (by running docker stop , pull , run ) 

how to run the app :

1. go to jenkins 
2. create new jop "ec2-app" & chose Pipeline
3. choose Pipeline script fro SCM
4. In SCM choose Git
5. put this repo in the Repository URL = https://github.com/saleh2784/ec2-proj.git
6. in branch put : */main
7. in the script patch = Jenkinsfile
8. go to the rep : https://github.com/saleh2784/ec2-proj.git & replace the tag number in tag.txt 
9. run the pipeline 



NTC a & b : 

# a.	Find a way to Validate that your application is indeed running and if it failed … Rollback to previous version (in place upgrade)
# b.	If validated push image to docker repo



NTC how to use readfile for the tag.txt

