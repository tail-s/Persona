# Base image
FROM python:3.9-slim-buster

# Set working directory in container
WORKDIR /app_ai

# Copy requirements.txt to container
COPY requirements.txt requirements.txt

# Install dependencies
RUN pip install --upgrade pip && pip install -r requirements.txt 

RUN apt-get update && apt-get install -y libgl1-mesa-glx

RUN pip install python-multipart

# Install dependencies
RUN apt-get update && \
    apt-get install -y ffmpeg && \
    rm -rf /var/lib/apt/lists/*

# Copy entire app to container
COPY . .

# Expose port for the app
EXPOSE 8000

# Start app when container launches
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
