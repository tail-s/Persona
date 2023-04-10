pipeline {
  agent any

  stages {

    stage('init'){
      steps{
        sh "echo init"
      }
    }
    
    stage('Gradle Build'){
      steps{
        sh "echo build"
        sh "cd ${env.WORKSPACE}/Backend && chmod +x ./gradlew && ./gradlew build"
      }
    }

    stage('React.JS Image Build') {
      steps {
        script {
          def frontendDir = "${env.WORKSPACE}/AI/face-emotion-recognition"
          def dockerfile = "${frontendDir}/Dockerfile"
          docker.build("persona-front-image:${env.BUILD_NUMBER}", "-f ${dockerfile} ${frontendDir}")
        }
      }
    }

    stage('Springboot Image Build') {
      steps {
        script {
              def backendDir = "${env.WORKSPACE}/Backend"
              def dockerfile = "${backendDir}/Dockerfile"
              docker.build("persona-springboot-image:${env.BUILD_NUMBER}", "-f ${dockerfile} ${backendDir}")

        }
      }
    }

    stage('fastapi Image Build') {
      steps {
        script {
              def AIDir = "${env.WORKSPACE}/AI/server"
              def dockerfile = "${AIDir}/Dockerfile"
              docker.build("persona-fastapi-image:${env.BUILD_NUMBER}", "-f ${dockerfile} ${AIDir}")

        }
      }
    }

    stage('Remove Docker container') {
      steps {
        script {
          try {
            sh 'docker ps -f name=springboot -q | xargs --no-run-if-empty docker container stop'
            sh 'docker ps -f name=frontend -q | xargs --no-run-if-empty docker container stop'
            sh 'docker ps -f name=fastapi -q | xargs --no-run-if-empty docker container stop'
            sh 'docker container ls -a -f name=springboot -q | xargs -r docker container rm'
            sh 'docker container ls -a -f name=frontend -q | xargs -r docker container rm'
            sh 'docker container ls -a -f name=fastapi -q | xargs -r docker container rm'

          } catch (err) {
            echo "Failed to stop the container"
          }
        }
      }
    }
    
    stage('Run Docker container') {
      steps {
        script {
          docker.image("persona-springboot-image:${env.BUILD_NUMBER}").run("--network persona-network --name springboot -p 8080:8080")
          docker.image("persona-front-image:${env.BUILD_NUMBER}").run("--name frontend -p 3000:3000")
          docker.image("persona-fastapi-image:${env.BUILD_NUMBER}").run("--network persona-network --name fastapi -p 8000:8000")
        }
      }
    }
  }
}
