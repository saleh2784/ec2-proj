FROM python:3.8-slim 
WORKDIR /usr/src/app
COPY . . 
RUN pip install -r /requirements/requirements.txt
CMD ["python", "ec2.py"]

