# Manage Jenkins :
install these Plugins in jenkins :
1. Pipeline: Stage View
2. Pipeline Utility Steps
3. Workspace Cleanup

# Manage Credentials and environment in jenkins = 

1. config file for ec2 (AWS)
2. credentials file for ec2 (AWS)
3. dockerhube credentials : DOCKERHUB_CREDENTIALS = credentials('docker-hub')
4. Github credentials : GIT_AUTH = credentials('github')
5. Docker name : DOCKER = 'ec2app'

# How to run the Pipeline: 

1. go to jenkins 
2. create new jop (name :"ec2-app") & (choose Pipeline)
3. choose Pipeline script fro SCM
4. In SCM choose Git
5. put this repo in the Repository URL = https://github.com/saleh2784/ec2-proj.git
6. in branch put : */development
7. in the script patch = Jenkinsfile
8. in Build Triggers mark "GitHub hook trigger for GITScm polling" 
9. Run the pipeline with parameters
10. Enter the INTERVAL "defualt 300"
11. Choose the branch ('main', 'development', 'PROD', 'saleh') "defualt development"
12. Enter the tag "defualt 1"
13. Run the pipeline


# How to run the argocd: 
** link for help : https://argo-cd.readthedocs.io/en/stable/getting_started/
# install argocd from my repo files : ## 

** create a new namespace:

kubectl apply -f namespace.yaml

** Install argocd

kubectl apply -f argocd.yaml

# OR from the link below:

** Create a new namespace:

kubectl create namespace argocd

** Install argocd vial the link:

kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml


** Change the argocd services to type loadbalancer using kubectl PATCH

kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'

** Port Forwarding:

kubectl port-forward svc/argocd-server -n argocd 8080:443

** to get the initial password (user is: admin )

kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo

** Create a GitHub webhook, on the settings page of the current GitHub repository:

create-the-webhook-in-the-git-provider : https://github.com/saleh2784/ec2-proj.git


# Login to the ArgoCD server: :

https://[argocd_load-balancer_DNS_name]

# Create a Helm repository:

1. From the ArgoCD UI, Select Settings -> Connect Repo
2. In Choose your connection method: Select VIA HTTPS
3. Type: git
4. Project: default
5. Repository URL: Specify your GIT repository : https://github.com/saleh2784/ec2-proj.git
6. Username : optional
7. Password : optional
8. Select "Skip server verification"
9. Click Connect yuo will get "Connection Status" = "Successful"


# Create an Application in Argo CD Defined By a Helm Chart:

From the ArgoCD Main :
1. Select Applications
2. Click "New App"
3. Application Name: ec2-app
4. Project Name: default
5. Repository URL: https://github.com/saleh2784/ec2-proj.git
6. Revision: HEAD
7. Path: helm-lab
8. Cluster URL: Select the default value
9. Namespace: argocd
10. Click Create
11. Click Sync -> click on Synchronize



