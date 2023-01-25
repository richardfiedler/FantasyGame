pipeline { 
     agent { docker { image 'maven:3.8.7-eclipse-temurin-11' } }

    stages { 
        
        stage('Build') { 
           steps {
                sh 'mvn clean install'
            }
        }
        
         stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
