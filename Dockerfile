FROM python:3.8-slim 
# use ARG User
ARG USER=boto3
#  add user {USER=boto3} to use it instead of the root user
RUN useradd -ms /bin/bash ${USER}
USER ${USER}
# go to the home directory for the {USER=boto3} 
WORKDIR /home/${USER}
# copy everything to WORKDIR include the ec2.py
COPY . . 
# create .aws filder and move the config & credentials from the jenkins credentials inside it & install the pip requirments (boto3) 
RUN mkdir /home/${USER}/.aws && \
    mv config /home/${USER}/.aws && \
    mv credentials /home/${USER}/.aws && \
    pip install -r requirements.txt
# run the app 
CMD ["python", "ec2.py"]

