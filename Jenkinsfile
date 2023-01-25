pipeline { 
     agent { docker { image 'maven:3.8.7-eclipse-temurin-11' } }

    stages { 
        steps {
                sh 'mvn clean install'
            }
        stage('Build') { 
            steps { 
               echo 'This is a minimal pipeline.' 
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
