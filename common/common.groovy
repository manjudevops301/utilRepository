def uploadWarArtifactory() {
	script {
		server = Artifactory.server prop.ARTIFACT_ID
		uploadSpec = """{
			"files":[{
			"pattern": "target/*.war",
			"target": "Java-war-snapshots/${artifactId}/${version}.${buildNo}/"
			}]
		}"""
		server.upload(uploadSpec) 	
	}
}

def sendingTheEmail() {
	emailext( 
			attachLog: true,
			subject: '${DEFAULT_SUBJECT}', 
			body: '${DEFAULT_CONTENT}',
			to: prop.BUILD_EMAIL_RECIPIENT
		);
	print 'mail sent'
}

def cleanTheWorkspace() {
	script {
		bat 'rm -rf ../'+jobName+'/*'
	}
	print 'cleaned workspace'
}
return this