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
	print 'mail sent'
}

def cleanTheWorkspace() {
	deleteDir()
	print 'cleaned workspace'
}
return this