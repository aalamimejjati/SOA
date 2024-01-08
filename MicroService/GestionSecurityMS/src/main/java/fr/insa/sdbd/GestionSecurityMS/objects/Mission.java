package fr.insa.sdbd.GestionSecurityMS.objects;


public class Mission {
		private int volunteer , requester , validator ,id;
		private String content , status;
		
		public Mission() {}
	


		public int getID() {
			return id;
		}
		
		public void setID(int id) {
			this.id = id;
		}

		public int getValidator() {
			return validator;
		}
		
		public void setValidator(int validator) {
			this.validator = validator;
		}

		public int getVolunteer() {
			return volunteer;
		}

		public void setVolunteer(int volunteer) {
			this.volunteer = volunteer;
		}

		public int getRequester() {
			return requester;
		}

		public void setRequester(int requester) {
			this.requester = requester;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		
	
}
