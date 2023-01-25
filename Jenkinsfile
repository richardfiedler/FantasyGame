pipeline {

agent any
    tools {
        maven 'MAVEN'
    }

    stages{

            stage('clean the code ')
            {
                steps{
                    sh 'mvn clean'
                }
            }

            stage('unit testing')
            {
                steps{
                    sh 'mvn test'
                }
            }
          
    }
}
