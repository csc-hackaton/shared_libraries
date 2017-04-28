// vars/kickRun.groovy
class retStruct  {
	String planRunID;
	String site;
	String kickURL;


	
}
//kickRun ("Austin","63cd86ccf475c17b95f5c9572ce01082fe4e2160a3ba973ea11268a4326ecc28d3f46539a77a900a44bb987c32395ad4","/var/csaf/openatf/engine/atf_motor.pl -c /workspace/arupacic/hackathon/plan3.yaml -c /workspace/arupacic/hackathon/host.yaml -p MY_LIB::Hello_Austin --bypassprekey","KICK_ATF_COMMAND")

def call(String site, String token, String cmd, String planID) \{
   // Any valid steps can be called from this code, just like in other
   // Scripted Pipeline
   def site = "Austin";
   def token = "63cd86ccf475c17b95f5c9572ce01082fe4e2160a3ba973ea11268a4326ecc28d3f46539a77a900a44bb987c32395ad4";
   def cmd = "/var/csaf/openatf/engine/atf_motor.pl -c /workspac    e/arupacic/hackathon/plan3.yaml -c /workspace/arupacic/hackathon/host.yaml -p MY_LIB::Hello_Austin --bypassprekey";
   def planID = "KICK_ATF_COMMAND";
   print "Executing KICK Plan"
   def parameters = "'{"
   parameters += "\"SITE\":\"" + site +"\","
   parameters += "\"COMMAND\":\"" + cmd + "\""
   parameters += "}'"
   print "POST message:" + parameters;
   def post = new URL("https://kick.cisco.com/api/engine/run/"+planID).openConnection();
   post.setRequestMethod("POST");
   post.setDoOutput(true)
   post.setRequestProperty("Content-Type","application/json");
   post.setRequestProperty("token",token);
   post.getOutputStream().write(parameters.getBytes("UTF-8"));
   def postRC = post.getResponseCode();
   println(postRC);
   if(postRC.equals(200)){
      output = post.getInputStream().getText();
      println(output); 
      def slurp = new JsonSlurperClassic();
      def testDetails = slurp.parseText(output)
      def planRunID = testDetails['run_id'];
      print ("PlanRunID:" + planRunID);
   }
}
