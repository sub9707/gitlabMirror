FROM python:3.7.7

RUN pip install -r requirements.txt

WORKDIR /usr/src/app
COPY . .

CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
EXPOSE 8000
