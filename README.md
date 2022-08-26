# ec2-proj
# create ec2.py code 
# create Dcokerfile 
# create pipeline deckartive
# put the pipeline as Jenkinsfile in github
# github wehbook

# branch (DEV / PROD) --- The above setup should allow you to run two Jenkins servers (DEV / PROD) where you can tests , upgrade and update your Jenkins dev env before you merge your changes into MAIN

# $JENKINSBUILDNUMBER --- -	Use jenkins to run your build on every push under branch development. Mark every image created with the number of the $JENKINSBUILDNUMBER. For example:yanivomc/k8stest:JENKINSBUILDNUMBER

# Create a manual step that depends on all build steps before it allows you to deploy your application to your local DOCKER Engine (by running docker stop , pull , run ) 

# a.	Find a way to Validate that your application is indeed running and if it failed … Rollback to previous version (in place upgrade)
# b.	If validated push image to docker repo

# Create a readable README.MD file that explain how to build/run/test  your code 




