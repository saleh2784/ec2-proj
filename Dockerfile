FROM python:3.8-slim 
ARG USER=boto3
RUN useradd -ms /bin/bash ${USER}
USER ${USER}
WORKDIR /home/${USER}
COPY . . 

RUN mkdir /home/${USER}/.aws && \
    mv config /home/${USER}/.aws && \
    mv credentials /home/${USER}/.aws && \
    pip install -r requirements.txt

CMD ["python", "ec2.py"]



# WORKDIR /usr/src/app
# COPY . . 
# RUN pip install -r requirements.txt
# CMD ["python", "ec2.py"]
