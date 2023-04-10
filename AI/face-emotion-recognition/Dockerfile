FROM node:18.12.1-alpine

WORKDIR /app_f

COPY package*.json ./

RUN npm install --force

# node_modules 폴더 삭제 (선택 사항, 이미 위에서 강제로 설치했기 때문에 필요하지 않을 수 있음)
# RUN rm -rf node_modules

COPY . .

RUN npm run build

EXPOSE 3000

CMD [ "npm", "start" ]
