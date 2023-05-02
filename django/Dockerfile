FROM python:latest

RUN mkdir /usr/src/app
WORKDIR /usr/src/app

COPY . /usr/src/app/

RUN pip install --upgrade pip
RUN pip install django
RUN pip install -r requirements.txt
RUN pip install cairocffi

CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
EXPOSE 8000
